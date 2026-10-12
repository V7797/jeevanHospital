package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.PatientProfile;
import com.projects.JeevanHospital.repository.PatientProfileRepository;
import com.projects.JeevanHospital.service.PatientProfileService;

@Service
public class PatientProfileServiceImpl implements PatientProfileService {

    @Autowired
    private PatientProfileRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PatientProfile> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientProfile> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    @Override
    @Transactional
    public PatientProfile registerPatient(PatientProfile patient) {
        // 1. Conflict Prevention: Validate unique email address strings
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalStateException("A patient record already exists with the email address: " + patient.getEmail());
        }

        // 2. Conflict Prevention: Validate unique primary phone numbers
        if (patientRepository.existsByContactNumber(patient.getContactNumber())) {
            throw new IllegalStateException("A patient record already exists with the contact number: " + patient.getContactNumber());
        }

        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public PatientProfile updatePatient(PatientProfile updatedDetails, Long patientId) {
        PatientProfile existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient profile not found with ID: " + patientId));

        // Safely map incoming updates to existing persistent record fields
        existingPatient.setFirstName(updatedDetails.getFirstName());
        existingPatient.setLastName(updatedDetails.getLastName());
        existingPatient.setContactNumber(updatedDetails.getContactNumber());
        existingPatient.setEmail(updatedDetails.getEmail());
//        existingPatient.setAddress(updatedDetails.getAddress());
//        existingPatient.setBirthDate(updatedDetails.getBirthDate());
//        existingPatient.setGender(updatedDetails.getGender());
        existingPatient.setBloodGroup(updatedDetails.getBloodGroup());

        return patientRepository.save(existingPatient);
    }

    @Override
    @Transactional
    public void deletePatientById(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException("Cannot delete. Patient profile not found with ID: " + patientId);
        }
        patientRepository.deleteById(patientId);
    }
}
