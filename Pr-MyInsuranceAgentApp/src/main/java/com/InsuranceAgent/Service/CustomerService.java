package com.InsuranceAgent.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InsuranceAgent.Dao.CustomerRepo;
import com.InsuranceAgent.Entity.Customer;
import com.InsuranceAgent.Entity.Policy;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;
	
//	Create customer
	public Customer createCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
//	Read customers all details
	public Customer getCustomerById(int id) {
		return customerRepo.findById(id).orElse(null);
	}
	
	
//	delete customer
	public void deleteCustomer(int id) {
		customerRepo.deleteById(id);
	}
	
	
//update customer
	public Customer updateCustomerAllDetails(int id, Customer customer) {
	    Optional<Customer> optionalCustomer = customerRepo.findById(id);
	    
	    if (optionalCustomer.isPresent()) {
	    	Customer existingCustomer = optionalCustomer.get();
	        BeanUtils.copyProperties(customer, existingCustomer, "id");
	        return customerRepo.save(existingCustomer);
	    } else {
	        return null;
	    }
	}
	
	
//	show customer all policies
	public List<Policy> findPolicyByCustomerId(int customerId){
		Customer customer=customerRepo.findById(customerId).orElse(null);
		if (customer!=null) {
			return customer.getPolicies();
		}
		return new ArrayList<>();
	}
}
