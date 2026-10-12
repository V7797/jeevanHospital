package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.InsurancePolicy;
import com.projects.JeevanHospital.service.InsuranceService;

@RestController
@RequestMapping("/api/insurances")
public class InsurancePolicyController {

    @Autowired
    private InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<List<InsurancePolicy>> getAllInsurances() {
        List<InsurancePolicy> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicy> getInsuranceById(@PathVariable("id") Long insuranceId) {
        return insuranceService.getInsuranceById(insuranceId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsurancePolicy> registerInsurance(@RequestBody InsurancePolicy insurancePolicy) {
        InsurancePolicy savedPolicy = insuranceService.registerInsurance(insurancePolicy);
        return new ResponseEntity<>(savedPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePolicy> updateInsurance(
            @PathVariable("id") Long insuranceId, 
            @RequestBody InsurancePolicy updatedRecord) {
        InsurancePolicy updatedPolicy = insuranceService.updateInsurance(updatedRecord, insuranceId);
        return ResponseEntity.ok(updatedPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsuranceById(@PathVariable("id") Long insuranceId) {
        insuranceService.deleteInsuranceById(insuranceId);
        return ResponseEntity.ok("Insurance policy deleted successfully.");
    }

    @GetMapping("/policy/{policyNumber}")
    public ResponseEntity<InsurancePolicy> getInsuranceByPolicyNumber(@PathVariable("policyNumber") String policyNumber) {
        return insuranceService.getInsuranceByPolicyNumber(policyNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
