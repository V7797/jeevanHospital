package com.projects.JeevanHospital.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "patient_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"appointments", "wardRoom", "insurance"}) // Protects Hibernate performance and prevents infinite loops
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId; // Normalized camelCase name

    @NotBlank(message = "First Name is required")
    @Size(max = 15)
    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 15)
    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true, length = 100)
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", 
        message = "Invalid email format pattern"
    )
    private String email;

    @NotBlank(message = "Contact Number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @NotNull(message = "Date Of Birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDateTime dateOfBirth;

    @NotBlank(message = "Blood Group is required")
    @Column(name = "blood_group", nullable = false, length = 5)
    private String bloodGroup;

    @NotBlank(message = "Emergency contact name is required")
    @Column(name = "emergency_contact_name", nullable = false)
    private String emergencyContactName; // Fixed double 'm' typo

    @NotBlank(message = "Emergency contact phone is required")
    @Column(name = "emergency_contact_phone", nullable = false, length = 15)
    private String emergencyContactPhone; // Fixed double 'm' typo

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AppointmentDetail> appointments;
    
    // ✅ FIXED: Changed from @ManyToAny to standard JPA @ManyToOne relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_room_id", nullable = true)
    @JsonIgnore
    private WardRoom wardRoom; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_policy_id")
    @JsonIgnore
    private InsurancePolicy insurance;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
