package com.projects.JeevanHospital.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "insurance_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "patients") // Prevents infinite recursion with Bidirectional mappings
public class InsurancePolicy {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceid;

    @NotBlank(message = "Insurance company name is required")
    @Size(max = 100)
    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @NotBlank(message = "Policy number is required")
    @Size(max = 50)
    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @NotBlank(message = "Policy type or plan name is required")
    @Size(max = 50)
    @Column(name = "plan_name", nullable = false)
    private String planName;

    @NotNull(message = "Coverage limit amount is required")
    @Positive(message = "Coverage limit cannot be negative")
    @Column(name = "coverage_limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal coverageLimit;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Insurance policy must be currently valid and expire in the future")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @OneToMany(mappedBy = "insurance", fetch = FetchType.LAZY)
    private List<PatientProfile> patients; // Updated type from Patient to PatientProfile if applicable
}
