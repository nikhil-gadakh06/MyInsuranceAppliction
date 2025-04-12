package com.InsuranceAgent.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Policy")
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private long policyNo;
	private LocalDate startDate;
	private LocalDate expireDate;
	
	private String primium;
	private String paymentMethod;
	private LocalDate dueDate;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(long policyNo) {
		this.policyNo = policyNo;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public String getPrimium() {
		return primium;
	}

	public void setPrimium(String primium) {
		this.primium = primium;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Policy(int id, long policyNo, LocalDate startDate, LocalDate expireDate, String primium,
			String paymentMethod, LocalDate dueDate, Customer customer) {
		super();
		this.id = id;
		this.policyNo = policyNo;
		this.startDate = startDate;
		this.expireDate = expireDate;
		this.primium = primium;
		this.paymentMethod = paymentMethod;
		this.dueDate = dueDate;
		this.customer = customer;
	}

	public Policy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
