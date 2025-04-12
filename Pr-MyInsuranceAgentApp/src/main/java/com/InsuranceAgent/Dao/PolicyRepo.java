package com.InsuranceAgent.Dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.InsuranceAgent.Entity.Policy;

public interface PolicyRepo extends JpaRepository<Policy, Integer>{

	List<Policy> findPolicyByCustomerId(int id);
	
	 @Query("SELECT p FROM Policy p WHERE p.dueDate BETWEEN :startDate AND :endDate")
	 List<Policy> findPoliciesWithinDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
}
