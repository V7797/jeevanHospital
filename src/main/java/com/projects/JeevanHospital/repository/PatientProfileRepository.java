package com.projects.JeevanHospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.PatientProfile;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long>{
	 // CORRECTED: Fixed method naming convention from 'Byemail' to 'ByEmail'
    boolean existsByEmail(String email);
    
    // CORRECTED: Fixed parameter naming convention from 'ContactNumber' to 'contactNumber'
    boolean existsByContactNumber(String contactNumber);
    
    // Custom finder method to quickly retrieve a patient profile by email
    Optional<PatientProfile> findByEmail(String email);

}
