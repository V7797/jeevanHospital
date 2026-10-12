package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.StaffProfile;

@Repository
public interface StaffRepository extends JpaRepository<StaffProfile, Long> {
    
    // Checks if a staff member exists with the exact email address
    boolean existsByEmail(String email);
    
    // Checks if a staff member exists with the exact contact number
    boolean existsByContactNumber(String contactNumber);
    
    // Usecase: Filter hospital staff by department (e.g., "NURSING", "RECEPTION", "PHARMACY")
    List<StaffProfile> findByDepartment(String department);
    
    // Usecase: Filter staff members by their active employment status
    List<StaffProfile> findByIsActive(boolean isActive);
}
