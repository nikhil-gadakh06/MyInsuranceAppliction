package com.InsuranceAgent.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.InsuranceAgent.Dao.CustomerRepo;
import com.InsuranceAgent.Dao.PolicyRepo;
import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.Policy;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired
    private PolicyRepo policyRepo;
    
    @Autowired
    private CustomerRepo custRepo;

    // Add policy using customer ID
    @PostMapping("/addPolicy")
    public ResponseEntity<Policy> addPolicy(@RequestBody Policy policy) {
        

        // Fetch the Customer using the customerId from the policy object
        Customer customer = custRepo.findById(policy.getCustomer().getId())
                                   .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Associate the Customer with the Policy
        policy.setCustomer(customer);

        // Save the Policy with the associated Customer
        try {
            Policy savedPolicy = policyRepo.save(policy);
            return new ResponseEntity<>(savedPolicy, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error while saving policy: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    // Get policies using customer ID
    @GetMapping("/allPolicies/{id}")
    public ResponseEntity<List<Policy>> findPoliciesByCustomerId(@PathVariable int id) {
        List<Policy> policies = policyRepo.findPolicyByCustomerId(id);
        if (!policies.isEmpty()) {
            return new ResponseEntity<>(policies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Due date filter
    @GetMapping("/due")
    public ResponseEntity<List<Policy>> getPoliciesDueInNextDays() {
        int days = 30;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(days);

        List<Policy> policies = policyRepo.findPoliciesWithinDateRange(startDate, endDate);
        if (!policies.isEmpty()) {
            return new ResponseEntity<>(policies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
