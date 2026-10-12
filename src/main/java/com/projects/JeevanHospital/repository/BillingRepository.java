package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.BillingInvoice;

@Repository
public interface BillingRepository extends JpaRepository<BillingInvoice , Long> {
    
    // Un-commented and optimized for production use:
    List<BillingInvoice> findByPaymentStatus(String paymentStatus);
    
    // Custom finder method to locate billing records tied to a specific patient profile
    List<BillingInvoice> findByPatient_PatientId(Long patientId);


}
