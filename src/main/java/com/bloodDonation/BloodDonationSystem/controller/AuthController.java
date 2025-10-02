package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.User;
import com.bloodDonation.BloodDonationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Simple password check (in production, use proper password hashing)
                if (user.getPassword().equals(password) && user.isActive()) {
                    // Store user in session
                    session.setAttribute("currentUser", user);
                    session.setAttribute("userRole", user.getRole());
                    session.setAttribute("userName", user.getFullName());
                    
                    // Redirect based on role
                    return redirectToDashboard(user.getRole());
                } else {
                    redirectAttributes.addFlashAttribute("error", "Invalid credentials or account inactive!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed: " + e.getMessage());
        }
        
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("roles", User.Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String confirmPassword,
                                    @RequestParam String fullName,
                                    @RequestParam String email,
                                    @RequestParam User.Role role,
                                    RedirectAttributes redirectAttributes) {
        try {
            // Validation
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
                return "redirect:/register";
            }
            
            if (userRepository.existsByUsername(username)) {
                redirectAttributes.addFlashAttribute("error", "Username already exists!");
                return "redirect:/register";
            }
            
            if (userRepository.existsByEmail(email)) {
                redirectAttributes.addFlashAttribute("error", "Email already exists!");
                return "redirect:/register";
            }
            
            // Create new user
            User newUser = new User(username, password, fullName, email, role);
            userRepository.save(newUser);
            
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully!");
        return "redirect:/login";
    }

    private String redirectToDashboard(User.Role role) {
        switch (role) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case RECEPTIONIST:
                return "redirect:/receptionist/dashboard";
            case DOCTOR:
                return "redirect:/doctor/dashboard";
            case NURSE:
                return "redirect:/nurse/dashboard";
            case BLOOD_BANK_MANAGER:
                return "redirect:/manager/dashboard";
            default:
                return "redirect:/login";
        }
    }
}
