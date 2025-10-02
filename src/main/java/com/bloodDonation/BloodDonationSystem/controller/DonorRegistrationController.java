package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/donor")
public class DonorRegistrationController {

    @Autowired
    private DonorRepository donorRepository;

    @GetMapping("/register")
    public String showDonorRegistrationForm() {
        return "donor_registration";
    }

    @PostMapping("/register")
    public String processDonorRegistration(@RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String email,
                                         @RequestParam String phone,
                                         @RequestParam String address,
                                         @RequestParam String bloodType,
                                         @RequestParam LocalDate dateOfBirth,
                                         @RequestParam String gender,
                                         @RequestParam(required = false) Integer weight,
                                         @RequestParam(required = false) Integer height,
                                         @RequestParam(required = false) String emergencyContactName,
                                         @RequestParam(required = false) String emergencyContactPhone,
                                         @RequestParam(required = false) String emergencyContactRelation,
                                         RedirectAttributes redirectAttributes) {
        try {
            // Validation
            if (donorRepository.findByEmail(email) != null) {
                redirectAttributes.addFlashAttribute("error", "A donor with this email already exists!");
                return "redirect:/donor/register";
            }
            
            //eligibility check
            int age = java.time.Period.between(dateOfBirth, LocalDate.now()).getYears();
            if (age < 17) {
                redirectAttributes.addFlashAttribute("error", "You must be at least 17 years old to donate blood.");
                return "redirect:/donor/register";
            }
            
            if (age > 65) {
                redirectAttributes.addFlashAttribute("error", "For safety reasons, we require medical clearance for donors over 65. Please contact us directly.");
                return "redirect:/donor/register";
            }
            

            if (weight != null && weight < 110) {
                redirectAttributes.addFlashAttribute("error", "Minimum weight requirement for blood donation is 110 lbs.");
                return "redirect:/donor/register";
            }
            

            Donor newDonor = new Donor(firstName, lastName, email, phone, address, bloodType, dateOfBirth, gender);
            

            if (weight != null) newDonor.setWeight(weight);
            if (height != null) newDonor.setHeight(height);
            if (emergencyContactName != null && !emergencyContactName.trim().isEmpty()) {
                newDonor.setEmergencyContactName(emergencyContactName);
            }
            if (emergencyContactPhone != null && !emergencyContactPhone.trim().isEmpty()) {
                newDonor.setEmergencyContactPhone(emergencyContactPhone);
            }
            if (emergencyContactRelation != null && !emergencyContactRelation.trim().isEmpty()) {
                newDonor.setEmergencyContactRelation(emergencyContactRelation);
            }
            

            Donor savedDonor = donorRepository.save(newDonor);
            

            redirectAttributes.addFlashAttribute("donor", savedDonor);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Welcome to our blood donor community.");
            
            return "redirect:/donor/success";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/donor/register";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        return "success";
    }

    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmailAvailability(@RequestParam String email) {
        return donorRepository.findByEmail(email) == null;
    }
}
