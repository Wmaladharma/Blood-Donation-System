package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            // Redirect to appropriate dashboard based on role
            switch (currentUser.getRole()) {
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
        return "redirect:/login";
    }
}
