package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.MedicalHistory;
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long>{
	 
    // Custom finder method to fetch medical histories linked to a specific patient
    List<MedicalHistory> findByPatient_PatientId(Long patientId);
    
    // Custom finder method to fetch medical histories diagnosed by a specific doctor profile
    List<MedicalHistory> findByDoctorProfile_DoctorId(Long doctorId);

}
