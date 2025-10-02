package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Communication;
import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.repository.CommunicationRepository;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/communications")
public class CommunicationController {

    @Autowired
    private CommunicationRepository communicationRepository;
    
    @Autowired
    private DonorRepository donorRepository;

    @GetMapping
    public String listCommunications(Model model) {
        List<Communication> communications = communicationRepository.findAll();
        List<Donor> donors = donorRepository.findAll();
        
        model.addAttribute("communications", communications);
        model.addAttribute("donors", donors);
        
        return "donor_communication";
    }

    @PostMapping("/send")
    public String sendCommunication(@RequestParam String donorName, 
                                  @RequestParam String message,
                                  RedirectAttributes redirectAttributes) {
        try {
            Communication communication = new Communication(donorName, message);
            communicationRepository.save(communication);
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error sending message: " + e.getMessage());
        }
        return "redirect:/communications";
    }

    @GetMapping("/delete/{id}")
    public String deleteCommunication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (communicationRepository.existsById(id)) {
                communicationRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Communication deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Communication not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting communication: " + e.getMessage());
        }
        return "redirect:/communications";
    }
}

