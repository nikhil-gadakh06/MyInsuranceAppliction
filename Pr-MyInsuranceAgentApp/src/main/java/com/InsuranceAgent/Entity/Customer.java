package com.InsuranceAgent.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "CustomerTable")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String fullName;
	private String address;
	private String contact;
	
	private String email;
	private String pass;
	
	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Policy> policies=new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fullName=" + fullName + ", address=" + address + ", contact=" + contact
				+ ", email=" + email + ", pass=" + pass + ", agent=" + agent + ", policies=" + policies + "]";
	}

	public Customer(int id, String fullName, String address, String contact, String email, String pass, Agent agent,
			List<Policy> policies) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.pass = pass;
		this.agent = agent;
		this.policies = policies;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	
}
