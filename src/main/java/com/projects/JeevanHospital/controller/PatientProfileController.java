package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.PatientProfile;
import com.projects.JeevanHospital.service.PatientProfileService;

@RestController
@RequestMapping("/api/patients")
public class PatientProfileController {

    @Autowired
    private PatientProfileService patientService;

    @GetMapping
    public ResponseEntity<List<PatientProfile>> getAllPatients() {
        List<PatientProfile> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientProfile> getPatientById(@PathVariable("id") Long patientId) {
        return patientService.getPatientById(patientId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientProfile> registerPatient(@RequestBody PatientProfile patient) {
        PatientProfile registeredPatient = patientService.registerPatient(patient);
        return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientProfile> updatePatient(
            @PathVariable("id") Long patientId, 
            @RequestBody PatientProfile updatedDetails) {
        PatientProfile updatedPatient = patientService.updatePatient(updatedDetails, patientId);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable("id") Long patientId) {
        patientService.deletePatientById(patientId);
        return ResponseEntity.ok("Patient profile deleted successfully.");
    }
}
