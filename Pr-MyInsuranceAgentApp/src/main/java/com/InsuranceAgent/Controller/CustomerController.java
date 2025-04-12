package com.InsuranceAgent.Controller;

import java.util.HashMap;
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
import com.InsuranceAgent.Entity.Agent;
import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.CustomerLogin;
import com.InsuranceAgent.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepo customerRepo;
    
    @Autowired
    private AgentRepo agentRepo;

    // Create customer
    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // Fetch the Agent using the agentId from the customer object
        Agent agent = agentRepo.findById(customer.getAgent().getId())
                                      .orElseThrow(() -> new RuntimeException("Agent not found"));

        // Associate the Agent with the Customer
        customer.setAgent(agent);

        // Save the Customer with the associated Agent
        Customer createdCustomer = service.createCustomer(customer);

        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }


    // Read all details of customer using ID
    @GetMapping("/Cust/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        System.out.println("Fetching customer with ID: " + id);
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            System.out.println("Customer not found for ID: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete customer
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update customer
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> updateCustomerAllDetails(@PathVariable("id") int id, @RequestBody Customer customer) {
        Customer updatedCustomer = service.updateCustomerAllDetails(id, customer);
        if (updatedCustomer != null) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/loginCustomer")
    public ResponseEntity<Map<String, Object>> loginCustomer(@RequestBody CustomerLogin customerLogin) {
        // Find the customer by email
        Optional<Customer> customer = Optional.ofNullable(customerRepo.findByEmail(customerLogin.getEmail()));

        // Check if customer exists
        if (customer.isPresent()) {
            Customer cust = customer.get();

            // Verify password
            if (customerLogin.getPassword().equals(cust.getPass())) {
                // Create a response map with customerId
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login success");
                response.put("customerId", cust.getId());
                
             // Debug log for response
                System.out.println("Response sent: " + response);

                // Log successful login
                System.out.println("Login successful for Customer ID: " + cust.getId());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Log invalid password
                System.out.println("Invalid password for Customer Email: " + customerLogin.getEmail());
                return new ResponseEntity<>(Map.of("message", "Invalid password"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            // Log user not found
            System.out.println("User not found for Email: " + customerLogin.getEmail());
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }
    }



}
