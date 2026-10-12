package com.projects.JeevanHospital.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "billing_invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "patient") // Prevents recursive looping issues during serialization loops
public class BillingInvoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingid;

    // ✅ FIXED: Added missing connection to PatientProfile entity to satisfy BillingRepository queries
    @NotNull(message = "Patient assignment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private PatientProfile patient;

    @NotNull(message = "Consultation/Service charges are required")
    @Column(name = "service_charges", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceCharges;

    @Column(name = "room_charges", precision = 10, scale = 2)
    private BigDecimal roomCharges = BigDecimal.ZERO;

    @Column(name = "insurance_deduction", precision = 10, scale = 2)
    private BigDecimal insuranceDeduction = BigDecimal.ZERO;

    @NotNull(message = "Total payable amount is required")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotBlank(message = "Payment status is required")
    @Column(name = "payment_status", nullable = false) // e.g., "PAID", "PENDING", "PARTIALLY_PAID"
    private String paymentStatus;

    @Column(name = "bill_date", nullable = false)
    private LocalDateTime billDate;

    @PrePersist
    protected void onCreate() {
        this.billDate = LocalDateTime.now();
        if (this.roomCharges == null) {
            this.roomCharges = BigDecimal.ZERO;
        }
        if (this.insuranceDeduction == null) {
            this.insuranceDeduction = BigDecimal.ZERO;
        }
    }
}
