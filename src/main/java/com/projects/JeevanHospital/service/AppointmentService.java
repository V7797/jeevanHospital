package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;

import com.projects.JeevanHospital.model.AppointmentDetail;

public interface AppointmentService {

    List<AppointmentDetail> getAllAppointments();
    
    Optional<AppointmentDetail> getAppointmentById(Long appointmentId);
    
    AppointmentDetail registerAppointment(AppointmentDetail appointment, Long doctorId, Long patientId);
    
    AppointmentDetail updateAppointment(AppointmentDetail appointment, Long appointmentId);
    
    void deleteAppointment(Long appointmentId);
    
    AppointmentDetail updateAppointmentStatus(Long appointmentId, String status);
    
    // 🌟 Newly Added Historical & Dashboard Queries
    List<AppointmentDetail> getAppointmentsByDoctorId(Long doctorId);
    
    List<AppointmentDetail> getAppointmentsByPatientId(Long patientId);

}
