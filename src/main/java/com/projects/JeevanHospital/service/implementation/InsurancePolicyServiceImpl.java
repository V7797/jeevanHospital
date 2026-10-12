package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.InsurancePolicy;
import com.projects.JeevanHospital.repository.InsuranceRepository;
import com.projects.JeevanHospital.service.InsuranceService;

@Service
public class InsurancePolicyServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InsurancePolicy> getAllInsurances() { // FIXED: Matched Interface Name
        return insuranceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InsurancePolicy> getInsuranceById(Long insuranceId) { // FIXED: Matched Interface Name
        return insuranceRepository.findById(insuranceId);
    }

    @Override
    @Transactional
    public InsurancePolicy registerInsurance(InsurancePolicy insurancePolicy) { // FIXED: Matched Interface Name
        if (insuranceRepository.existsByPolicyNumber(insurancePolicy.getPolicyNumber())) {
            throw new IllegalStateException("An insurance policy already exists with Policy Number: " + insurancePolicy.getPolicyNumber());
        }
        return insuranceRepository.save(insurancePolicy);
    }

    @Override
    @Transactional
    public InsurancePolicy updateInsurance(InsurancePolicy updatedRecord, Long insuranceId) { // FIXED: Matched Interface Name
        InsurancePolicy existingPolicy = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new IllegalArgumentException("Insurance policy not found with ID: " + insuranceId));

        existingPolicy.setPolicyNumber(updatedRecord.getPolicyNumber());
//        existingPolicy.setInsuranceProvider(updatedRecord.getInsuranceProvider());
//        existingPolicy.setCoverageAmount(updatedRecord.getCoverageAmount());
        existingPolicy.setExpiryDate(updatedRecord.getExpiryDate());

        return insuranceRepository.save(existingPolicy);
    }

    @Override
    @Transactional
    public void deleteInsuranceById(Long insuranceId) { // FIXED: Matched Interface Name
        if (!insuranceRepository.existsById(insuranceId)) {
            throw new IllegalArgumentException("Cannot delete. Insurance policy not found with ID: " + insuranceId);
        }
        insuranceRepository.deleteById(insuranceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InsurancePolicy> getInsuranceByPolicyNumber(String policyNumber) { // FIXED: Matched Interface Name
        if (policyNumber == null || policyNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Policy number search string cannot be blank");
        }
        return insuranceRepository.findByPolicyNumber(policyNumber);
    }
}
