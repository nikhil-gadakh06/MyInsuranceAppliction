package com.InsuranceAgent.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.Agent;
import com.InsuranceAgent.Entity.Policy;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Customer findByEmail(String email);


//	List<Customer> findCustomerById(int id);
	List<Customer> findCustomerByAgentId(int id);
//	List<Customer> findByLicAgent_Id(int agentId);

}
