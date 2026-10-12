package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.MedicalHistory;
import com.projects.JeevanHospital.model.PatientProfile;
import com.projects.JeevanHospital.model.DoctorProfile;
import com.projects.JeevanHospital.repository.MedicalHistoryRepository;
import com.projects.JeevanHospital.repository.PatientProfileRepository;
import com.projects.JeevanHospital.repository.DoctorRepository;
import com.projects.JeevanHospital.service.MedicalHistoryService;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private PatientProfileRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> getAllMedicalRecords() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistory> getMedicalRecordById(Long medicalHistoryId) {
        return medicalHistoryRepository.findById(medicalHistoryId);
    }

    @Override
    @Transactional
    public MedicalHistory registerMedicalRecord(MedicalHistory medicalHistory, Long patientId, Long doctorId) {
        // 1. Verify Patient Profile exists or throw clear explanation
        PatientProfile patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient profile not found with ID: " + patientId));

        // 2. Verify Doctor Profile exists or throw clear explanation
        DoctorProfile doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found with ID: " + doctorId));

        // 3. Map relations to entity
        medicalHistory.setPatient(patient);
        medicalHistory.setDoctorProfile(doctor); // Binds perfectly with your refactored DoctorProfile entity

        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    @Transactional
    public MedicalHistory updateMedicalRecord(MedicalHistory updatedRecord, Long medicalHistoryId) {
        MedicalHistory existingRecord = medicalHistoryRepository.findById(medicalHistoryId)
                .orElseThrow(() -> new IllegalArgumentException("Medical history record not found with ID: " + medicalHistoryId));

        // Update modifiable parameters securely
        existingRecord.setDiagnosis(updatedRecord.getDiagnosis());
        existingRecord.setPrescription(updatedRecord.getPrescription());
        existingRecord.setLabResults(updatedRecord.getLabResults());
        existingRecord.setRecordDate(updatedRecord.getRecordDate());

        return medicalHistoryRepository.save(existingRecord);
    }

    @Override
    @Transactional
    public void deleteMedicalRecordById(Long medicalHistoryId) {
        if (!medicalHistoryRepository.existsById(medicalHistoryId)) {
            throw new IllegalArgumentException("Cannot delete. Medical history record not found with ID: " + medicalHistoryId);
        }
        medicalHistoryRepository.deleteById(medicalHistoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> getMedicalRecordsByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException("Patient profile not found with ID: " + patientId);
        }
        return medicalHistoryRepository.findByPatient_PatientId(patientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> getMedicalRecordsByDoctorId(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Doctor profile not found with ID: " + doctorId);
        }
        return medicalHistoryRepository.findByDoctorProfile_DoctorId(doctorId);
    }
}
