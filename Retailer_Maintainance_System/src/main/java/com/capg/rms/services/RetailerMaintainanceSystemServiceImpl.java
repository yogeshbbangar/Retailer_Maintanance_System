package com.capg.rms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capg.rms.beans.Order;
import com.capg.rms.beans.Product;
import com.capg.rms.beans.Retailer;
import com.capg.rms.dao.RetailerMaintainanceSystemDAO;
import com.capg.rms.dao.RetailerMaintianceSystemDAOImpl;
@Service
public class RetailerMaintainanceSystemServiceImpl implements RetailerMaintainanceSystemServices{


	RetailerMaintainanceSystemDAO db = new RetailerMaintianceSystemDAOImpl();
	
	@Override
	public Boolean createProfile(Retailer retailer) {
		return db.createProfile(retailer);
	}


	@Override
	public Retailer loginProfile(int retailerId, String password) {
		return db.loginProfile(retailerId, password);
	}

	@Override
	public Boolean updatePassword(int retailerId, String password) {
		return db.updatePassword(retailerId, password);
	}


	@Override
	public Order orderProduct(Order order) {
		return db.orderProduct(order);
	}

	@Override
	public List<Order> getOrder(int retailerId) {
		return db.getOrder(retailerId);
	}


	@Override
	public Product getProduct(int productId) {
		return db.getProduct(productId);
	}

	



	
}





