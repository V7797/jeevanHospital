package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.StaffProfile;
import com.projects.JeevanHospital.repository.StaffRepository;
import com.projects.JeevanHospital.service.StaffProfileService;

@Service
public class StaffProfileServiceImpl implements StaffProfileService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StaffProfile> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StaffProfile> getStaffById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    @Override
    @Transactional
    public StaffProfile registerStaff(StaffProfile staff) {
        // 1. Conflict Prevention: Check for duplicate email allocations
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new IllegalStateException("A staff profile already exists with the email address: " + staff.getEmail());
        }

        // 2. Conflict Prevention: Check for duplicate contact numbers
        if (staffRepository.existsByContactNumber(staff.getContactNumber())) {
            throw new IllegalStateException("A staff profile already exists with the contact number: " + staff.getContactNumber());
        }
        
        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public StaffProfile updateStaff(StaffProfile staffDetails, Long staffId) {
        StaffProfile existingStaff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff profile not found with ID: " + staffId));

        // Update changeable resource metrics safely
//        existingStaff.setFirstName(staffDetails.getFirstName());
//        existingStaff.setLastName(staffDetails.getLastName());
        existingStaff.setRole(staffDetails.getRole());
        existingStaff.setDepartment(staffDetails.getDepartment());
        existingStaff.setContactNumber(staffDetails.getContactNumber());
        existingStaff.setEmail(staffDetails.getEmail());
        //existingStaff.setActive(staffDetails.isActive());

        return staffRepository.save(existingStaff);
    }

    @Override
    @Transactional
    public void deleteStaffById(Long staffId) {
        if (!staffRepository.existsById(staffId)) {
            throw new IllegalArgumentException("Cannot delete. Staff profile not found with ID: " + staffId);
        }
        staffRepository.deleteById(staffId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffProfile> getStaffByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department filter criteria cannot be blank");
        }
        return staffRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffProfile> getActiveStaff(boolean isActive) {
        return staffRepository.findByIsActive(isActive);
    }
}
