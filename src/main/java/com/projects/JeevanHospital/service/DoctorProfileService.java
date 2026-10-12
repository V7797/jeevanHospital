package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;

import com.projects.JeevanHospital.model.DoctorProfile;

public interface DoctorProfileService {
    
    List<DoctorProfile> getAllDoctors();
    
    Optional<DoctorProfile> getDoctorById(Long doctorId);
    
    DoctorProfile registerDoctor(DoctorProfile doctorProfile);
    
    DoctorProfile updateDoctor(DoctorProfile doctorProfile, Long doctorId);
    
    void deleteDoctor(Long doctorId);
    
    // 🌟 Advanced Filters for Hospital Portals
    List<DoctorProfile> getDoctorsBySpecialty(String specialty);
}
