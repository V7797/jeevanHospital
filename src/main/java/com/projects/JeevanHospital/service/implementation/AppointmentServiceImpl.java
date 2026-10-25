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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientProfileRepository patientRepository;

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
    	//.AppointmentDetail byId = appointmentRepository.findById(patientId).orElseThrow(()->new IllegalArgumentException("Appointment not fpund with the id"+id));
    	//1.check existing dr.profile
    	DoctorProfile doctor = doctorRepository.findById(doctorId).orElseThrow(()->new IllegalStateException("Doctor not found with the id"+doctorId));
    	//1.check existing patient.profile
    	PatientProfile patient = patientRepository.findById(patientId).orElseThrow(()->new IllegalStateException("Patient not found with the id"+patientId));
    	//3.check localdatetime request and database request by using appointmant repoository doctordata
    	LocalDateTime requesttime=appointment.getAppointmentDate();
    	List<AppointmentDetail> existappointment = appointmentRepository.findByDoctor_DoctorId(doctorId);
    	boolean isdoctorbusy=existappointment.stream().anyMatch(ins->ins.getAppointmentDate().equals(requesttime));
    	
    	if (isdoctorbusy) {
			throw new IllegalStateException("The selected doctor is already bookeed at"+requesttime);
		}
    	 	
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
