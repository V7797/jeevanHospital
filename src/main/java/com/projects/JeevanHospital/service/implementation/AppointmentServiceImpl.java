package com.projects.JeevanHospital.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.AppointmentDetail;
import com.projects.JeevanHospital.model.DoctorProfile;
import com.projects.JeevanHospital.model.PatientProfile;
import com.projects.JeevanHospital.repository.AppointmentRepository;
import com.projects.JeevanHospital.repository.DoctorRepository;
import com.projects.JeevanHospital.repository.PatientProfileRepository;
import com.projects.JeevanHospital.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientProfileRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDetail> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppointmentDetail> getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    @Transactional
    public AppointmentDetail registerAppointment(AppointmentDetail appointment, Long doctorId, Long patientId) {
        // 1. Verify Patient Profile exists
        PatientProfile patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient profile not found with ID: " + patientId));

        // 2. Verify Doctor Profile exists
        DoctorProfile doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found with ID: " + doctorId));

        // 3. Double-Booking Validation: Prevent scheduling overlapping patient hours
        LocalDateTime requestedTime = appointment.getAppointmentDate();
        List<AppointmentDetail> conflictingAppointments = appointmentRepository.findByDoctor_DoctorId(doctorId);
        
        boolean isDoctorBusy = conflictingAppointments.stream()
                .anyMatch(existing -> existing.getAppointmentDate().equals(requestedTime) 
                        && !"CANCELLED".equalsIgnoreCase(existing.getStatus()));
        
        if (isDoctorBusy) {
            throw new IllegalStateException("The selected doctor is already booked at " + requestedTime);
        }

        // 4. Bind Verified Relationships
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public AppointmentDetail updateAppointment(AppointmentDetail updatedRecord, Long appointmentId) {
        AppointmentDetail existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment record not found with ID: " + appointmentId));

        existingAppointment.setAppointmentDate(updatedRecord.getAppointmentDate());
        existingAppointment.setReasonForVisit(updatedRecord.getReasonForVisit());
        existingAppointment.setDiagnosisNotes(updatedRecord.getDiagnosisNotes());
        
        if (updatedRecord.getStatus() != null) {
            existingAppointment.setStatus(updatedRecord.getStatus().toUpperCase());
        }

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IllegalArgumentException("Cannot delete. Appointment record not found with ID: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    @Transactional
    public AppointmentDetail updateAppointmentStatus(Long appointmentId, String status) {
        AppointmentDetail appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment record not found with ID: " + appointmentId));
        
        appointment.setStatus(status.toUpperCase());
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDetail> getAppointmentsByDoctorId(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Doctor profile not found with ID: " + doctorId);
        }
        return appointmentRepository.findByDoctor_DoctorId(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDetail> getAppointmentsByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException("Patient profile not found with ID: " + patientId);
        }
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

}
