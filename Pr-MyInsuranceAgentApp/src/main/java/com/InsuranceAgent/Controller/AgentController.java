package com.InsuranceAgent.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InsuranceAgent.Dao.AgentRepo;
import com.InsuranceAgent.Dao.CustomerRepo;
import com.InsuranceAgent.Entity.AgentLogin;
import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.Agent;
import com.InsuranceAgent.Service.AgentService;

@RestController
@RequestMapping("/api/")
public class AgentController {

    @Autowired
    private AgentService service;

    @Autowired
    private AgentRepo agentRepo;

    @Autowired
    private CustomerRepo custRepo;

    // Create agent
    @PostMapping("/createAgent")
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        Agent createdAgent = service.createAgent(agent);
        return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
    }

    // Read all agents
    @GetMapping("/allAgents")
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = service.getAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    // Read agent using ID
    @GetMapping("/Agent/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable int id) {
        Agent agent = service.getAgentById(id);
        if (agent != null) {
            return new ResponseEntity<>(agent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete agent
    @DeleteMapping("/deleteAgent/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable int id) {
        service.deleteAgent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update agent
    @PutMapping("/updateAgent/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable("id") int id, @RequestBody Agent agent) {
        Agent updatedAgent = service.updateAgent(id, agent);
        if (updatedAgent != null) {
            return new ResponseEntity<>(updatedAgent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Login agent
    @PostMapping("/loginAgent")
    public ResponseEntity<Map<String, Object>> loginAgent(@RequestBody AgentLogin agentLogin) {
        Optional<Agent> agent = Optional.ofNullable(agentRepo.findByEmail(agentLogin.getEmail()));

        if (agent.isPresent()) {
            Agent licAgent = agent.get();
            if (agentLogin.getPassword().equals(licAgent.getPass())) {
                // Create a response with agent ID
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login success");
                response.put("agentId", licAgent.getId());
                response.put("fname", licAgent.getFname());
                

                // Debug log for response
                System.out.println("Response sent: " + response);
             // Log successful login
                System.out.println("Login successful for Customer ID: " + licAgent.getId());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("message", "Invalid password"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }
    }


    
    // Get all customers of a specific agent
    @GetMapping("/allCustomers/{id}")
    public ResponseEntity<List<Customer>> findPoliciesByCustomerId(@PathVariable int id) {
        List<Customer> customers = custRepo.findCustomerByAgentId(id);
        if (!customers.isEmpty()) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
