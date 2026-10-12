package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.BillingInvoice;
import com.projects.JeevanHospital.repository.BillingRepository;
import com.projects.JeevanHospital.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BillingInvoice> getAllBillings() {
        return billingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BillingInvoice> getBillingById(Long billingId) {
        return billingRepository.findById(billingId);
    }

    @Override
    @Transactional
    public BillingInvoice registerBilling(BillingInvoice billing) {
        // 1. Calculate Net Payable Balance using your exact entity fields
        calculateNetBalance(billing);

        // 2. Fallback status configuration
        if (billing.getPaymentStatus() == null) {
            billing.setPaymentStatus("PENDING");
        }

        return billingRepository.save(billing);
    }

    @Override
    @Transactional
    public BillingInvoice updateBilling(BillingInvoice updatedRecord, Long billingId) {
        BillingInvoice existingBilling = billingRepository.findById(billingId)
                .orElseThrow(() -> new IllegalArgumentException("Billing invoice not found with ID: " + billingId));

        // Audit Lockout: Block mutations if the invoice is already fully paid
        if ("PAID".equalsIgnoreCase(existingBilling.getPaymentStatus())) {
            throw new IllegalStateException("Settled invoices cannot be modified.");
        }

        // Update your exact matching entity parameters
        existingBilling.setServiceCharges(updatedRecord.getServiceCharges());
        existingBilling.setRoomCharges(updatedRecord.getRoomCharges());
        existingBilling.setInsuranceDeduction(updatedRecord.getInsuranceDeduction());
        
        if (updatedRecord.getPaymentStatus() != null) {
            existingBilling.setPaymentStatus(updatedRecord.getPaymentStatus());
        }
        
        // Recalculate net balance on change updates
        calculateNetBalance(existingBilling);

        return billingRepository.save(existingBilling);
    }

    @Override
    @Transactional
    public void deleteBillingById(Long billingId) {
        BillingInvoice billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new IllegalArgumentException("Billing invoice not found with ID: " + billingId));
        
        if ("PAID".equalsIgnoreCase(billing.getPaymentStatus())) {
            throw new IllegalStateException("Audit security failure: Settled invoices cannot be deleted.");
        }
        
        billingRepository.deleteById(billingId);
    }

    @Override
    @Transactional
    public BillingInvoice updatePaymentStatus(Long billingId, String status) {
        BillingInvoice billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new IllegalArgumentException("Billing invoice not found with ID: " + billingId));
        
        billing.setPaymentStatus(status.toUpperCase());
        return billingRepository.save(billing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillingInvoice> getBillingsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment status search term cannot be blank");
        }
        return billingRepository.findByPaymentStatus(status.toUpperCase());
    }

    /**
     * Calculation engine adjusted to your exact fields: Total = Service + Room - Insurance
     */
    private void calculateNetBalance(BillingInvoice billing) {
        BigDecimal total = BigDecimal.ZERO;
        
        if (billing.getServiceCharges() != null) {
            total = total.add(billing.getServiceCharges());
        }
        if (billing.getRoomCharges() != null) {
            total = total.add(billing.getRoomCharges());
        }
        if (billing.getInsuranceDeduction() != null) {
            total = total.subtract(billing.getInsuranceDeduction());
        }

        // Net payable value cannot be drop below zero
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        billing.setTotalAmount(total);
    }
}
