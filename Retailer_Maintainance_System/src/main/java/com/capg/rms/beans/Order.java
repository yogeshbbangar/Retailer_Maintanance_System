 	package com.capg.rms.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "order_info")
@Component("order")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;

	@Column(name = "retailer_id")
	private int retailerId;

	private String productName;
	
	@Column(name="product_id")
	private int productId;
	@Column(name = "total_price")
	private double totalPrice;

	private int quantity;

	private double pricePerUnit;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", retailerId=" + retailerId + ", productName=" + productName
				+ ", productId=" + productId + ", totalPrice=" + totalPrice + ", quantity=" + quantity
				+ ", pricePerUnit=" + pricePerUnit + "]";
	}

}
