package OrderManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import OrderManagement.repository.OrderRepository;
import OrderManagement.entity.CustomerOrder;

@Service
public class OrderService {

	@Autowired
	OrderRepository dao;

	// API 1: Validation, calculation and save
	public Object placeOrder(CustomerOrder order) {
		if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
			return "Customer name cannot be empty[cite: 1]";
		}
		if (order.getQuantity() <= 0) {
			return "Quantity must be greater than 0[cite: 1]";
		}
		if (order.getPricePerUnit() <= 0) {
			return "Price per unit must be greater than 0[cite: 1]";
		}
		if (order.getDiscountPercent() < 0 || order.getDiscountPercent() > 30) {
			return "Discount percentage must be between 0 and 30[cite: 1]";
		}
		if (!order.getPaymentMode().equals("UPI") && !order.getPaymentMode().equals("Card") && !order.getPaymentMode().equals("Cash")) {
			return "Payment mode must be UPI, Card or Cash[cite: 1]";
		}

		double grossAmount = order.getQuantity() * order.getPricePerUnit(); //[cite: 1]
		double discountAmount = grossAmount * order.getDiscountPercent() / 100; //[cite: 1]
		double finalAmount = grossAmount - discountAmount; //[cite: 1]

		dao.saveOrder(order);

		Map<String, Object> map = new HashMap<>();
		map.put("message", "Order placed successfully"); //[cite: 1]
		map.put("orderId", order.getOrderId()); //[cite: 1]
		map.put("customerName", order.getCustomerName()); //[cite: 1]
		map.put("grossAmount", grossAmount); //[cite: 1]
		map.put("discountAmount", discountAmount); //[cite: 1]
		map.put("finalAmount", finalAmount); //[cite: 1]
		return map;
	}

	// API 2: Get orders above final amount
	public List<Map<String, Object>> getOrdersAbove(double amount) {
		List<CustomerOrder> allOrders = dao.getAllOrders(); //[cite: 1]
		List<Map<String, Object>> result = new ArrayList<>();

		for (CustomerOrder o : allOrders) {
			double gross = o.getQuantity() * o.getPricePerUnit(); //[cite: 1]
			double discount = gross * o.getDiscountPercent() / 100; //[cite: 1]
			double finalAmt = gross - discount; //[cite: 1]

			if (finalAmt > amount) { //[cite: 1]
				Map<String, Object> map = new HashMap<>();
				map.put("orderId", o.getOrderId()); //[cite: 1]
				map.put("customerName", o.getCustomerName()); //[cite: 1]
				map.put("productName", o.getProductName()); //[cite: 1]
				map.put("quantity", o.getQuantity()); //[cite: 1]
				map.put("grossAmount", gross); //[cite: 1]
				map.put("discountAmount", discount); //[cite: 1]
				map.put("finalAmount", finalAmt); //[cite: 1]
				result.add(map);
			}
		}
		return result;
	}

	// API 3: Update status with flow rules
	public Object updateStatus(int orderId, String newStatus) {
		CustomerOrder order = dao.getSingleOrder(orderId);
		if (order == null) {
			return "Order not found";
		}

		String oldStatus = order.getOrderStatus();
		boolean valid = false;

		if (oldStatus.equalsIgnoreCase("Placed") && (newStatus.equalsIgnoreCase("Shipped") || newStatus.equalsIgnoreCase("Cancelled"))) { //[cite: 1]
			valid = true;
		} else if (oldStatus.equalsIgnoreCase("Shipped") && newStatus.equalsIgnoreCase("Delivered")) { //[cite: 1]
			valid = true;
		}

		if (!valid) {
			Map<String, Object> err = new HashMap<>();
			err.put("message", "Invalid order status transition"); //[cite: 1]
			err.put("currentStatus", oldStatus); //[cite: 1]
			err.put("requestedStatus", newStatus); //[cite: 1]
			return err;
		}

		order.setOrderStatus(newStatus);
		dao.updateOrder(order);

		Map<String, Object> res = new HashMap<>();
		res.put("message", "Order status updated successfully"); //[cite: 1]
		res.put("orderId", orderId); //[cite: 1]
		res.put("oldStatus", oldStatus); //[cite: 1]
		res.put("newStatus", newStatus); //[cite: 1]
		return res;
	}

	// API 4: Category Summary
	public Map<String, Object> getCategorySummary(String category) {
		List<CustomerOrder> allOrders = dao.getAllOrders();

		int totalOrders = 0;
		int delivered = 0;
		int placed = 0;
		int cancelled = 0;
		int totalQuantity = 0;
		double totalRevenue = 0;
		double highestVal = 0;
		String highestCust = null;

		for (CustomerOrder o : allOrders) {
			if (o.getCategory().equalsIgnoreCase(category)) {
				totalOrders++; //[cite: 1]
				totalQuantity += o.getQuantity(); //[cite: 1]

				if (o.getOrderStatus().equalsIgnoreCase("Delivered")) delivered++; //[cite: 1]
				if (o.getOrderStatus().equalsIgnoreCase("Placed")) placed++; //[cite: 1]
				if (o.getOrderStatus().equalsIgnoreCase("Cancelled")) cancelled++; //[cite: 1]

				double finalAmt = (o.getQuantity() * o.getPricePerUnit()) - ((o.getQuantity() * o.getPricePerUnit()) * o.getDiscountPercent() / 100);

				if (!o.getOrderStatus().equalsIgnoreCase("Cancelled")) { //[cite: 1]
					totalRevenue += finalAmt; //[cite: 1]
				}

				if (finalAmt > highestVal) {
					highestVal = finalAmt; //[cite: 1]
					highestCust = o.getCustomerName(); //[cite: 1]
				}
			}
		}

		Map<String, Object> summary = new HashMap<>();
		summary.put("category", category); //[cite: 1]
		summary.put("totalOrders", totalOrders); //[cite: 1]
		summary.put("deliveredOrders", delivered); //[cite: 1]
		summary.put("placedOrders", placed); //[cite: 1]
		summary.put("cancelledOrders", cancelled); //[cite: 1]
		summary.put("totalQuantity", totalQuantity); //[cite: 1]
		summary.put("totalRevenue", totalRevenue); //[cite: 1]
		summary.put("highestValueOrder", highestVal); //[cite: 1]
		summary.put("highestValueCustomer", highestCust); //[cite: 1]
		return summary;
	}
}