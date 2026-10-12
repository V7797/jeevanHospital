package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;

import com.projects.JeevanHospital.model.InsurancePolicy;

public interface InsuranceService {
	 List<InsurancePolicy> getAllInsurances();
	    
	    Optional<InsurancePolicy> getInsuranceById(Long insuranceId);
	    
	    InsurancePolicy registerInsurance(InsurancePolicy insurance);
	    
	    InsurancePolicy updateInsurance(InsurancePolicy insurance, Long insuranceId);
	    
	    void deleteInsuranceById(Long insuranceId); // FIXED: Added ID argument and closed parenthesis cleanly
	    
	    // 🌟 Advanced Lookups for Claims Verification Dashboards
	    Optional<InsurancePolicy> getInsuranceByPolicyNumber(String policyNumber);

}
