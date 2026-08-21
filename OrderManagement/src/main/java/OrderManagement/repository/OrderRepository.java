
package OrderManagement.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import OrderManagement.entity.CustomerOrder;

@Repository
public class OrderRepository {

	@Autowired
	SessionFactory sf;

	public String saveOrder(CustomerOrder order) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		session.persist(order);

		tx.commit();
		session.close();
		return "Order placed successfully";
	}

	public List<CustomerOrder> getAllOrders() {
		Session session = sf.openSession();
		List<CustomerOrder> list = session.createQuery("from CustomerOrder", CustomerOrder.class).list();
		session.close();
		return list;
	}

	public CustomerOrder getSingleOrder(int orderId) {
		Session session = sf.openSession();
		CustomerOrder order = session.get(CustomerOrder.class, orderId);
		session.close();
		return order;
	}

	public String updateOrder(CustomerOrder order) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		session.merge(order);

		tx.commit();
		session.close();
		return "Order status updated successfully";
	}
}