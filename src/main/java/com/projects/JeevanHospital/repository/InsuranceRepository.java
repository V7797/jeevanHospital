package com.projects.JeevanHospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.InsurancePolicy;

@Repository
public interface InsuranceRepository extends JpaRepository<InsurancePolicy, Long>{
	  // CORRECTED: Fixed method naming convention from 'Bypolicy' to 'ByPolicy'
    boolean existsByPolicyNumber(String policyNumber);
    
    // Custom finder method to quickly retrieve policy details by number
    Optional<InsurancePolicy> findByPolicyNumber(String policyNumber);

}
