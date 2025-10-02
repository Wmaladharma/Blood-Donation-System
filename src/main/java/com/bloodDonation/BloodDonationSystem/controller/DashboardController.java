package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.User;
import com.bloodDonation.BloodDonationSystem.repository.UserRepository;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import com.bloodDonation.BloodDonationSystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session, User.Role.ADMIN)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/login";
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalDonors", donorRepository.count());
        model.addAttribute("totalAppointments", appointmentRepository.count());
        
        return "admin_dashboard";
    }

    // Receptionist Dashboard (existing dashboard.html)
    @GetMapping("/receptionist/dashboard")
    public String receptionistDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session, User.Role.RECEPTIONIST)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/login";
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "dashboard"; // This is the existing receptionist dashboard
    }

    // Doctor Dashboard
    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session, User.Role.DOCTOR)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/login";
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("todayAppointments", appointmentRepository.findByDate(java.time.LocalDate.now()));
        
        return "doctor_dashboard";
    }

    // Nurse Dashboard
    @GetMapping("/nurse/dashboard")
    public String nurseDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session, User.Role.NURSE)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/login";
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "nurse_dashboard";
    }

    // Blood Bank Manager Dashboard
    @GetMapping("/manager/dashboard")
    public String managerDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session, User.Role.BLOOD_BANK_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/login";
        }
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "manager_dashboard";
    }

    private boolean isAuthorized(HttpSession session, User.Role requiredRole) {
        User currentUser = (User) session.getAttribute("currentUser");
        return currentUser != null && currentUser.getRole() == requiredRole;
    }
}
