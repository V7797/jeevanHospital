package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;

import com.projects.JeevanHospital.model.MedicalHistory;

public interface MedicalHistoryService {
	  List<MedicalHistory> getAllMedicalRecords(); // Standardised naming pattern
	    
	    Optional<MedicalHistory> getMedicalRecordById(Long medicalHistoryId);
	    
	    MedicalHistory registerMedicalRecord(MedicalHistory medicalHistory, Long patientId, Long doctorId);
	    
	    MedicalHistory updateMedicalRecord(MedicalHistory medicalHistory, Long medicalHistoryId);
	    
	    void deleteMedicalRecordById(Long medicalHistoryId); // FIXED: Capitalised primitive 'void' keyword
	    
	    // 🌟 Added Usecases for Clinical Portals
	    List<MedicalHistory> getMedicalRecordsByPatientId(Long patientId);
	    
	    List<MedicalHistory> getMedicalRecordsByDoctorId(Long doctorId);

}
