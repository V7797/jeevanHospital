package com.projects.JeevanHospital.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medical_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"patient", "doctorProfile"}) // Prevents infinite recursion & lazy loading performance drops
public class MedicalHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private Long medicalHistoryId;

    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading optimizes app performance
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @NotNull(message = "Doctor profile is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctorProfile; // UPDATED: Changed from Doctor to DoctorProfile

    @NotBlank(message = "Diagnosis details are required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String prescription; // List of medicines and dosages

    @Column(name = "lab_results", columnDefinition = "TEXT")
    private String labResults;

    @NotNull(message = "Record date is required")
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;
	
	
}
