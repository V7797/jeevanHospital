package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;
import com.projects.JeevanHospital.model.PatientProfile;

public interface PatientProfileService {
    
    List<PatientProfile> getAllPatients();
    
    Optional<PatientProfile> getPatientById(Long patientId);
    
    PatientProfile registerPatient(PatientProfile patient);
    
    PatientProfile updatePatient(PatientProfile patientDetails, Long patientId);
    
    void deletePatientById(Long patientId); // FIXED: Added missing tracking identifier parameter
}
