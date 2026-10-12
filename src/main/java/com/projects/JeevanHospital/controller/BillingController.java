package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.BillingInvoice;
import com.projects.JeevanHospital.service.BillingService;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public ResponseEntity<List<BillingInvoice>> getAllBillings() {
        List<BillingInvoice> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingInvoice> getBillingById(@PathVariable("id") Long billingId) {
        return billingService.getBillingById(billingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BillingInvoice> registerBilling(@RequestBody BillingInvoice billing) {
        BillingInvoice createdBilling = billingService.registerBilling(billing);
        return new ResponseEntity<>(createdBilling, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingInvoice> updateBilling(
            @PathVariable("id") Long billingId, 
            @RequestBody BillingInvoice updatedRecord) {
        BillingInvoice updatedBilling = billingService.updateBilling(updatedRecord, billingId);
        return ResponseEntity.ok(updatedBilling);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBillingById(@PathVariable("id") Long billingId) {
        billingService.deleteBillingById(billingId);
        return ResponseEntity.ok("Billing invoice deleted successfully.");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BillingInvoice> updatePaymentStatus(
            @PathVariable("id") Long billingId, 
            @RequestParam("status") String status) {
        BillingInvoice updatedBilling = billingService.updatePaymentStatus(billingId, status);
        return ResponseEntity.ok(updatedBilling);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillingInvoice>> getBillingsByStatus(@PathVariable("status") String status) {
        List<BillingInvoice> billings = billingService.getBillingsByStatus(status);
        return ResponseEntity.ok(billings);
    }
}
