package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.model.User;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/donors")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @GetMapping
    public String listDonors(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session)) {
            redirectAttributes.addFlashAttribute("error", "Access denied! Please login.");
            return "redirect:/login";
        }
        
        List<Donor> donors = donorRepository.findAll();
        model.addAttribute("donors", donors);
        return "donor_profile_handling";
    }

    @PostMapping("/save")
    public String saveDonor(@ModelAttribute Donor donor, RedirectAttributes redirectAttributes) {
        try {
            donorRepository.save(donor);
            redirectAttributes.addFlashAttribute("success", "Donor added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding donor: " + e.getMessage());
        }
        return "redirect:/donors";
    }

    @GetMapping("/edit/{id}")
    public String editDonor(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Donor> donorOpt = donorRepository.findById(id);
        if (donorOpt.isPresent()) {
            model.addAttribute("donor", donorOpt.get());
            return "donor_edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Donor not found!");
            return "redirect:/donors";
        }
    }

    @PostMapping("/update")
    public String updateDonor(@ModelAttribute Donor donor, RedirectAttributes redirectAttributes) {
        try {
            // Ensure we're updating an existing donor
            if (donor.getId() != null) {
                Optional<Donor> existingDonor = donorRepository.findById(donor.getId());
                if (existingDonor.isPresent()) {
                    Donor existing = existingDonor.get();
                    existing.setName(donor.getName());
                    existing.setEmail(donor.getEmail());
                    existing.setPhone(donor.getPhone());
                    existing.setAddress(donor.getAddress());
                    existing.setBloodType(donor.getBloodType());
                    donorRepository.save(existing);
                    redirectAttributes.addFlashAttribute("success", "Donor updated successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Donor not found!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid donor ID!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating donor: " + e.getMessage());
        }
        return "redirect:/donors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (donorRepository.existsById(id)) {
                donorRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Donor deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Donor not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting donor: " + e.getMessage());
        }
        return "redirect:/donors";
    }
    
    private boolean isAuthorized(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return currentUser != null && (
            currentUser.getRole() == User.Role.ADMIN ||
            currentUser.getRole() == User.Role.RECEPTIONIST ||
            currentUser.getRole() == User.Role.DOCTOR ||
            currentUser.getRole() == User.Role.NURSE
        );
    }
}
