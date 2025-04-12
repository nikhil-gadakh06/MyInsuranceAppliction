package com.InsuranceAgent.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.Agent;

@Repository
public interface AgentRepo extends JpaRepository<Agent, Integer> {
	Agent findByEmail(String email);


}
