package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.DoctorProfile;
@Repository
public interface DoctorRepository extends JpaRepository<DoctorProfile, Long> {

    // Checks if a record exists with the exact email address
    boolean existsByEmail(String email);
    
    // Checks if a record exists with the exact contact number
    boolean existsByContactNumber(String contactNumber);
    
    // Usecase: Find medical specialists dynamically for booking panels
    List<DoctorProfile> findBySpecialty(String specialty);

}
