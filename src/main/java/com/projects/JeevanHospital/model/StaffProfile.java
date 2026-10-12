package com.projects.JeevanHospital.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "staff_profiles")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffid;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Role is required")
    @Column(nullable = false) // e.g., "NURSE", "RECEPTIONIST", "PHARMACIST", "ADMIN"
    private String role;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Contact number is required")
    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Email(message = "Invalid email")
    @Column(unique = true)
    private String email;

    // ✅ FIXED: Added missing field as an object Boolean to match findByIsActive query logic perfectly
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; 
}
