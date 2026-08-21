package OrderManagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import OrderManagement.entity.CustomerOrder;
import OrderManagement.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService service;

	@PostMapping
	public Object placeOrder(@RequestBody CustomerOrder order) {
		Object res = service.placeOrder(order);
		return res;
	}

	@GetMapping("/above/{amount}")
	public List<Map<String, Object>> getOrdersAbove(@PathVariable double amount) {
		List<Map<String, Object>> list = service.getOrdersAbove(amount);
		return list;
	}

	@PutMapping("/{id}/status")
	public Object updateStatus(@PathVariable int id, @RequestBody Map<String, String> map) {
		String newStatus = map.get("status");
		Object res = service.updateStatus(id, newStatus);
		return res;
	}

	@GetMapping("/summary/{category}")
	public Map<String, Object> getCategorySummary(@PathVariable String category) {
		Map<String, Object> summary = service.getCategorySummary(category);
		return summary;
	}
}
