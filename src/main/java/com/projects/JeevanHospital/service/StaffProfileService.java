package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;
import com.projects.JeevanHospital.model.StaffProfile;

public interface StaffProfileService {
    
    List<StaffProfile> getAllStaff(); // Standardised plural naming
    
    Optional<StaffProfile> getStaffById(Long staffId);
    
    StaffProfile registerStaff(StaffProfile staff);
    
    StaffProfile updateStaff(StaffProfile staffDetails, Long staffId); // Added explicit path ID parameter
    
    void deleteStaffById(Long staffId);
    
    // 🌟 Advanced Operational Filters for HR & Shift Scheduling Panels
    List<StaffProfile> getStaffByDepartment(String department);
    
    List<StaffProfile> getActiveStaff(boolean isActive);
}
