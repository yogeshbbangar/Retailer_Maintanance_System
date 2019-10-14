package com.capg.rms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capg.rms.beans.Order;
import com.capg.rms.beans.Product;
import com.capg.rms.beans.Retailer;

@Repository
public class RetailerMaintianceSystemDAOImpl implements RetailerMaintainanceSystemDAO {

	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistanceUnit");

	@Override
	public Boolean createProfile(Retailer retailer) {
		Boolean state = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			// create user
			em.persist(retailer);
			em.getTransaction().commit();
			em.close();
			state = true;
		} catch (Exception e) {
		}
		return state;
	}

	@Override
	public Product getProduct(int productId) {
		EntityManager em = emf.createEntityManager();
		Product product = em.find(Product.class, productId);
		em.close();
		return product;
	}

	@Override
	public Retailer loginProfile(int retailerId, String password) {
		Retailer retailer = null;
		try {
			EntityManager em = emf.createEntityManager();
			TypedQuery<Retailer> query = em.createQuery("from Retailer r where id= :id and password= :passwd",
					Retailer.class);

			// setting paramters
			query.setParameter("id", retailerId);
			query.setParameter("passwd", password);
			List<Retailer> retailers = query.getResultList();
			if (retailers.size() > 0) {
				retailer = retailers.get(0);
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retailer;
	}

	@Override
	public Boolean updatePassword(int retailerId, String password) {
		EntityManager em = emf.createEntityManager();
		Retailer retailer = em.find(Retailer.class, retailerId);
		retailer.setPassword(password);
		return true;
	}

	@Override
	public Order orderProduct(Order order) {

		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();
			em.close();
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> getOrder(int retailerId) {
		List<Order> orderLi = null;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Order> query = em.createQuery("from Order o where retailerId= :rid", Order.class);
			query.setParameter("rid", retailerId);
			orderLi = query.getResultList();
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderLi;
	}
}
