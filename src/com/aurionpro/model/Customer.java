package com.aurionpro.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	List<Order> orders;
	double totalOrderCost;

	public Customer(int id, String name, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.orders = orders;
	}

	public Customer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public double calculateTotalCustomerOrderCost() {
		totalOrderCost = 0;
		for (Order order : orders) {
			totalOrderCost += order.calculateOrderPrice();
			if (totalOrderCost > 500) {
				double discount = 5;
				totalOrderCost -= (totalOrderCost * discount) / 100;
			}
		}
		return totalOrderCost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, orders, totalOrderCost);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(orders, other.orders)
				&& Double.doubleToLongBits(totalOrderCost) == Double.doubleToLongBits(other.totalOrderCost);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
	
	

}
