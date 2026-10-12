package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.DoctorProfile;
import com.projects.JeevanHospital.service.DoctorProfileService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorProfileController {

    @Autowired
    private DoctorProfileService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorProfile>> getAllDoctors() {
        List<DoctorProfile> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorProfile> getDoctorById(@PathVariable("id") Long doctorId) {
        return doctorService.getDoctorById(doctorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DoctorProfile> registerDoctor(@RequestBody DoctorProfile doctorProfile) {
        DoctorProfile savedDoctor = doctorService.registerDoctor(doctorProfile);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorProfile> updateDoctor(
            @PathVariable("id") Long doctorId, 
            @RequestBody DoctorProfile updatedRecord) {
        DoctorProfile updatedDoctor = doctorService.updateDoctor(updatedRecord, doctorId);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor profile deleted successfully.");
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<DoctorProfile>> getDoctorsBySpecialty(@PathVariable("specialty") String specialty) {
        List<DoctorProfile> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }
}
