package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Coordination;
import com.bloodDonation.BloodDonationSystem.repository.CoordinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/coordination")
public class CoordinationController {

    @Autowired
    private CoordinationRepository coordinationRepository;

    @GetMapping
    public String listCoordinations(Model model) {
        List<Coordination> coordinations = coordinationRepository.findAll();
        model.addAttribute("coordinations", coordinations);
        return "coordination";
    }

    @PostMapping("/send")
    public String sendCoordination(@RequestParam String recipient, 
                                 @RequestParam String information,
                                 RedirectAttributes redirectAttributes) {
        try {
            Coordination coordination = new Coordination(recipient, information);
            coordinationRepository.save(coordination);
            redirectAttributes.addFlashAttribute("success", "Information sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error sending information: " + e.getMessage());
        }
        return "redirect:/coordination";
    }

    @GetMapping("/delete/{id}")
    public String deleteCoordination(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (coordinationRepository.existsById(id)) {
                coordinationRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Coordination deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Coordination not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting coordination: " + e.getMessage());
        }
        return "redirect:/coordination";
    }
}

