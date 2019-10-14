package com.capg.rms.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="retailer_info")
public class Retailer {
	@Id 
	@Column(name="retailer_id")
	private int id;
	@Column(name="retailername")
	private String name;
	private String email;
	private String password;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "retailer_id")
	private List<Order> order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Retailer [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	
	
	
	
}
