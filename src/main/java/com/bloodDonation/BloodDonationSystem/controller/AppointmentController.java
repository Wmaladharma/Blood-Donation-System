package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.model.Appointment;
import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.repository.AppointmentRepository;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DonorRepository donorRepository;

    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointment_management";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment, RedirectAttributes redirectAttributes) {
        try {
            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("success", "Appointment saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving appointment: " + e.getMessage());
        }
        return "redirect:/appointments";
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            model.addAttribute("appointment", appointmentOpt.get());
            
            // Add donors for dropdown
            List<Donor> donors = donorRepository.findAll();
            model.addAttribute("donors", donors);
            
            // Add status options
            List<String> statuses = Arrays.asList("Scheduled", "Confirmed", "Completed", "Cancelled", "No Show");
            model.addAttribute("statuses", statuses);
            
            return "appointment_edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Appointment not found!");
            return "redirect:/appointments";
        }
    }

    @PostMapping("/update")
    public String updateAppointment(@ModelAttribute Appointment appointment, 
                                  @RequestParam(required = false) Long donorId,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (appointment.getId() != null) {
                Optional<Appointment> existingAppointment = appointmentRepository.findById(appointment.getId());
                if (existingAppointment.isPresent()) {
                    Appointment existing = existingAppointment.get();
                    
                    // Set donor if provided
                    if (donorId != null) {
                        Optional<Donor> donor = donorRepository.findById(donorId);
                        if (donor.isPresent()) {
                            existing.setDonor(donor.get());
                            existing.setDonorName(donor.get().getName());
                        }
                    }
                    
                    existing.setDate(appointment.getDate());
                    existing.setTime(appointment.getTime());
                    existing.setAppointmentType(appointment.getAppointmentType());
                    existing.setStatus(appointment.getStatus());
                    existing.setNotes(appointment.getNotes());
                    
                    appointmentRepository.save(existing);
                    redirectAttributes.addFlashAttribute("success", "Appointment updated successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Appointment not found!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid appointment ID!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating appointment: " + e.getMessage());
        }
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (appointmentRepository.existsById(id)) {
                appointmentRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Appointment deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Appointment not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting appointment: " + e.getMessage());
        }
        return "redirect:/appointments";
    }
}

