package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.DoctorProfile;
import com.projects.JeevanHospital.repository.DoctorRepository;
import com.projects.JeevanHospital.service.DoctorProfileService;


@Service
public class DoctorProfileServiceImpl implements DoctorProfileService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorProfile> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorProfile> getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    @Override
    @Transactional
    public DoctorProfile registerDoctor(DoctorProfile doctorProfile) {
        // 1. Conflict Check: Ensure Email is unique before creating a record
        if (doctorRepository.existsByEmail(doctorProfile.getEmail())) {
            throw new IllegalStateException("A doctor profile already exists with the email: " + doctorProfile.getEmail());
        }

        // 2. Conflict Check: Ensure Contact Number is unique
        if (doctorRepository.existsByContactNumber(doctorProfile.getContactNumber())) {
            throw new IllegalStateException("A doctor profile already exists with the phone number: " + doctorProfile.getContactNumber());
        }

        return doctorRepository.save(doctorProfile);
    }

    @Override
    @Transactional
    public DoctorProfile updateDoctor(DoctorProfile updatedRecord, Long doctorId) {
        DoctorProfile existingDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found with ID: " + doctorId));

        // Update contact details and schedule allocations safely
        existingDoctor.setFirstName(updatedRecord.getFirstName());
        existingDoctor.setLastName(updatedRecord.getLastName());
        existingDoctor.setSpecialty(updatedRecord.getSpecialty());
        existingDoctor.setContactNumber(updatedRecord.getContactNumber());
        existingDoctor.setEmail(updatedRecord.getEmail());
        existingDoctor.setRoomNumber(updatedRecord.getRoomNumber());
        existingDoctor.setConsultationFee(updatedRecord.getConsultationFee());
        existingDoctor.setAvailabilitySchedule(updatedRecord.getAvailabilitySchedule());

        return doctorRepository.save(existingDoctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Cannot delete. Doctor profile not found with ID: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorProfile> getDoctorsBySpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty search criteria cannot be blank");
        }
        return doctorRepository.findBySpecialty(specialty);
    }

}
