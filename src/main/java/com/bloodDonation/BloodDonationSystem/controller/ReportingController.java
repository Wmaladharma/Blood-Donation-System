package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Appointment;
import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.repository.AppointmentRepository;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportingController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DonorRepository donorRepository;

    @GetMapping
    public String showReports() {
        return "reporting_tracking";
    }

    @PostMapping("/generate")
    public String generateReport(@RequestParam String reportType, Model model) {
        model.addAttribute("reportType", reportType);
        
        switch (reportType) {
            case "Donor Attendance":
                List<Appointment> appointments = appointmentRepository.findAll();
                model.addAttribute("appointments", appointments);
                break;
                
            case "Donation History":
                List<Donor> donors = donorRepository.findAll();
                model.addAttribute("donors", donors);
                break;
                
            case "Feedback Summary":
                // For now, just show the report type
                // In a real application, you might have feedback data
                break;
        }
        
        return "reporting_tracking";
    }
}

