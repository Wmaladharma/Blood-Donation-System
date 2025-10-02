package com.bloodDonation.BloodDonationSystem.config;

import com.bloodDonation.BloodDonationSystem.model.Donor;
import com.bloodDonation.BloodDonationSystem.model.Appointment;
import com.bloodDonation.BloodDonationSystem.model.User;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import com.bloodDonation.BloodDonationSystem.repository.AppointmentRepository;
import com.bloodDonation.BloodDonationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize data if database is empty
        if (userRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        // Create sample users for each role
        User admin = new User("admin", "admin123", "System Administrator", "admin@bloodbank.com", User.Role.ADMIN);
        User receptionist = new User("receptionist", "recep123", "Mary Johnson", "receptionist@bloodbank.com", User.Role.RECEPTIONIST);
        User doctor = new User("doctor", "doc123", "Dr. Robert Smith", "doctor@bloodbank.com", User.Role.DOCTOR);
        User nurse = new User("nurse", "nurse123", "Sarah Wilson", "nurse@bloodbank.com", User.Role.NURSE);
        User manager = new User("manager", "manager123", "David Brown", "manager@bloodbank.com", User.Role.BLOOD_BANK_MANAGER);
        
        userRepository.save(admin);
        userRepository.save(receptionist);
        userRepository.save(doctor);
        userRepository.save(nurse);
        userRepository.save(manager);
        
        // Create sample donors
        Donor donor1 = new Donor("John Smith", "john.smith@email.com", "555-0101", "123 Main St", "O+");
        Donor donor2 = new Donor("Jane Doe", "jane.doe@email.com", "555-0102", "456 Oak Ave", "A+");
        Donor donor3 = new Donor("Mike Johnson", "mike.johnson@email.com", "555-0103", "789 Pine Rd", "B+");
        Donor donor4 = new Donor("Sarah Wilson", "sarah.wilson@email.com", "555-0104", "321 Elm St", "AB+");
        Donor donor5 = new Donor("David Brown", "david.brown@email.com", "555-0105", "654 Maple Dr", "O-");

        donorRepository.save(donor1);
        donorRepository.save(donor2);
        donorRepository.save(donor3);
        donorRepository.save(donor4);
        donorRepository.save(donor5);

        // Create sample appointments
        Appointment apt1 = new Appointment("John Smith", LocalDate.now().plusDays(1), LocalTime.of(9, 0));
        apt1.setStatus("Scheduled");
        
        Appointment apt2 = new Appointment("Jane Doe", LocalDate.now().plusDays(2), LocalTime.of(10, 30));
        apt2.setStatus("Confirmed");
        
        Appointment apt3 = new Appointment("Mike Johnson", LocalDate.now().plusDays(3), LocalTime.of(14, 0));
        apt3.setStatus("Scheduled");

        appointmentRepository.save(apt1);
        appointmentRepository.save(apt2);
        appointmentRepository.save(apt3);

        System.out.println("Sample data initialized successfully!");
        System.out.println("=== LOGIN CREDENTIALS ===");
        System.out.println("Admin: admin / admin123");
        System.out.println("Receptionist: receptionist / recep123");
        System.out.println("Doctor: doctor / doc123");
        System.out.println("Nurse: nurse / nurse123");
        System.out.println("Manager: manager / manager123");
        System.out.println("========================");
    }
}
