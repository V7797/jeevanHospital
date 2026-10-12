package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.AppointmentDetail;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDetail, Long>{
	 // Custom finder method to fetch appointments for a specific doctor
    List<AppointmentDetail> findByDoctor_DoctorId(Long doctorId);
    
    // Custom finder method to fetch appointments for a specific patient
    List<AppointmentDetail> findByPatient_PatientId(Long patientId);

}
