package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.MedicalHistory;
import com.projects.JeevanHospital.service.MedicalHistoryService;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getAllMedicalRecords() {
        List<MedicalHistory> records = medicalHistoryService.getAllMedicalRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistory> getMedicalRecordById(@PathVariable("id") Long medicalHistoryId) {
        return medicalHistoryService.getMedicalRecordById(medicalHistoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicalHistory> registerMedicalRecord(
            @RequestBody MedicalHistory medicalHistory,
            @RequestParam("patientId") Long patientId,
            @RequestParam("doctorId") Long doctorId) {
        MedicalHistory createdRecord = medicalHistoryService.registerMedicalRecord(medicalHistory, patientId, doctorId);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistory> updateMedicalRecord(
            @PathVariable("id") Long medicalHistoryId,
            @RequestBody MedicalHistory updatedRecord) {
        MedicalHistory savedRecord = medicalHistoryService.updateMedicalRecord(updatedRecord, medicalHistoryId);
        return ResponseEntity.ok(savedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecordById(@PathVariable("id") Long medicalHistoryId) {
        medicalHistoryService.deleteMedicalRecordById(medicalHistoryId);
        return ResponseEntity.ok("Medical history record deleted successfully.");
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalHistory>> getMedicalRecordsByPatientId(@PathVariable("patientId") Long patientId) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<MedicalHistory>> getMedicalRecordsByDoctorId(@PathVariable("doctorId") Long doctorId) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalRecordsByDoctorId(doctorId);
        return ResponseEntity.ok(records);
    }
}
