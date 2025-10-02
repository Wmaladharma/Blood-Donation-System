package com.bloodDonation.BloodDonationSystem.repository;

import com.bloodDonation.BloodDonationSystem.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor> findByBloodType(String bloodType);
    List<Donor> findByNameContainingIgnoreCase(String name);
    Donor findByEmail(String email);
}

