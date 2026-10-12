
    
package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;
import com.projects.JeevanHospital.model.BillingInvoice;

public interface BillingService {
    
    List<BillingInvoice> getAllBillings();
    
    Optional<BillingInvoice> getBillingById(Long billingId);
    
    BillingInvoice registerBilling(BillingInvoice billing);
    
    BillingInvoice updateBilling(BillingInvoice billing, Long billingId);
    
    void deleteBillingById(Long billingId);
    
    BillingInvoice updatePaymentStatus(Long billingId, String status);
    
    List<BillingInvoice> getBillingsByStatus(String status);
}

	


