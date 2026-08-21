package OrderManagement.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;

@Entity
public class CustomerOrder {
	@Id
    private int orderId;

    private String customerName;
    private String productName;
    private String category;
    private int quantity;
    private double pricePerUnit;
    private double discountPercent;
    private String paymentMode;
    private String orderStatus;

    public CustomerOrder() {
    }

	public CustomerOrder(int orderId, String customerName, String productName, String category, int quantity,
			double pricePerUnit, double discountPercent, String paymentMode, String orderStatus) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.productName = productName;
		this.category = category;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.discountPercent = discountPercent;
		this.paymentMode = paymentMode;
		this.orderStatus = orderStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "CustomerOrder [orderId=" + orderId + ", customerName=" + customerName + ", productName=" + productName
				+ ", category=" + category + ", quantity=" + quantity + ", pricePerUnit=" + pricePerUnit
				+ ", discountPercent=" + discountPercent + ", paymentMode=" + paymentMode + ", orderStatus="
				+ orderStatus + "]";
	}
    
}