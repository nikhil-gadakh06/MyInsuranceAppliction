package com.InsuranceAgent.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InsuranceAgent.Dao.AgentRepo;
import com.InsuranceAgent.Entity.Agent;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class AgentService {
	
	@Autowired
	private AgentRepo agentRepo;
	
	
//	Create Agent
	public Agent createAgent(Agent agent) {
		return agentRepo.save(agent);
	}
	
//	Read all Agent
	public List<Agent> getAllAgents(){
		return agentRepo.findAll();
	}
	
//	Read Agent by id
	public Agent getAgentById(int id) {
		return agentRepo.findById(id).orElse(null);
	}
	
	
//	delete agent
	public void deleteAgent(int id) {
		agentRepo.deleteById(id);
	}
	
	
//update Agent
	public Agent updateAgent(int id, Agent agent) {
	    Optional<Agent> optionalAgent = agentRepo.findById(id);
	    
	    if (optionalAgent.isPresent()) {
	        Agent existingAgent = optionalAgent.get();
	        BeanUtils.copyProperties(agent, existingAgent, "id");
	        return agentRepo.save(existingAgent);
	    } else {
	        return null;
	    }
	}
}
