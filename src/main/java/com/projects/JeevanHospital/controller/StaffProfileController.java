package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.StaffProfile;
import com.projects.JeevanHospital.service.StaffProfileService;

@RestController
@RequestMapping("/api/staff")
public class StaffProfileController {

    @Autowired
    private StaffProfileService staffService;

    @GetMapping
    public ResponseEntity<List<StaffProfile>> getAllStaff() {
        List<StaffProfile> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffProfile> getStaffById(@PathVariable("id") Long staffId) {
        return staffService.getStaffById(staffId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StaffProfile> registerStaff(@RequestBody StaffProfile staff) {
        StaffProfile registeredStaff = staffService.registerStaff(staff);
        return new ResponseEntity<>(registeredStaff, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffProfile> updateStaff(
            @PathVariable("id") Long staffId, 
            @RequestBody StaffProfile staffDetails) {
        StaffProfile updatedStaff = staffService.updateStaff(staffDetails, staffId);
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaffById(@PathVariable("id") Long staffId) {
        staffService.deleteStaffById(staffId);
        return ResponseEntity.ok("Staff profile deleted successfully.");
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StaffProfile>> getStaffByDepartment(@PathVariable("department") String department) {
        List<StaffProfile> staffList = staffService.getStaffByDepartment(department);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/status")
    public ResponseEntity<List<StaffProfile>> getActiveStaff(@RequestParam("active") boolean isActive) {
        List<StaffProfile> staffList = staffService.getActiveStaff(isActive);
        return ResponseEntity.ok(staffList);
    }
}
