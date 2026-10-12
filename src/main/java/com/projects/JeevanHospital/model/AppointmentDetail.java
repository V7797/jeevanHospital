package com.projects.JeevanHospital.model;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"patient", "doctor"}) // Prevents infinite recursion & lazy-loading issues
public class AppointmentDetail {
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentid;

    @NotNull(message = "Patient assignment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientProfile patient;
    
    @NotNull(message = "Doctor assignment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @NotNull(message = "Appointment date and time is required")
    @FutureOrPresent(message = "Appointment date must be in the present or future")
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Appointment status is required")
    @Column(nullable = false, length = 20)
    private String status; // e.g., "SCHEDULED", "CONFIRMED", "COMPLETED", "CANCELLED"

    @NotBlank(message = "Reason for visit or symptoms are required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String reasonForVisit;

    @Column(columnDefinition = "TEXT")
    private String diagnosisNotes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "SCHEDULED"; 
        }
    }

}
