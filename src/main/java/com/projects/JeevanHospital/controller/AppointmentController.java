package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.JeevanHospital.model.AppointmentDetail;
import com.projects.JeevanHospital.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*") // Allows front-end applications (React/Angular) to call your API safely
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // 1. GET: Fetch all appointments (http://localhost:8080/api/appointments)
    @GetMapping
    public ResponseEntity<List<AppointmentDetail>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // 2. GET: Fetch a single appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetail> getAppointmentById(@PathVariable("id") Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST: Book a brand new appointment
    @PostMapping("/register")
    public ResponseEntity<AppointmentDetail> registerAppointment(
            @RequestBody AppointmentDetail appointment,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("patientId") Long patientId) {
        
        AppointmentDetail createdAppointment = appointmentService.registerAppointment(appointment, doctorId, patientId);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // 4. PUT: Update scheduling parameters of an existing entry
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDetail> updateAppointment(
            @RequestBody AppointmentDetail appointment,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointment, id));
    }

    // 5. PATCH: Change status explicitly (e.g., Cancel or Confirm)
    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentDetail> updateAppointmentStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, status));
    }

    // 6. DELETE: Purge a scheduling record from active parameters
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // 7. GET: Dashboard tracking for specific Doctors
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDetail>> getAppointmentsByDoctorId(@PathVariable("doctorId") Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId));
    }

    // 8. GET: History tracking for specific Patients
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDetail>> getAppointmentsByPatientId(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }
}
