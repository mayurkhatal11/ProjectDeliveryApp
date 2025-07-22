package com.aurionpro.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;
	LocalDate date;
	List<LineItem> items;
	static double discount = 5;
	double totalOrderCost;
	String paymentMethod;
	String deliveryPartner;

	public Order(int id, LocalDate date, List<LineItem> items, String paymentMethod, String deliveryPartner) {
		this.id = id;
		this.date = date;
		this.items = items;
		this.paymentMethod = paymentMethod;
		this.deliveryPartner = deliveryPartner;
	}

	public static double getDiscount() {
		return discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getDeliveryPartner() {
		return deliveryPartner;
	}

	public double calculateOrderPrice() {
		totalOrderCost = 0;
		for (LineItem item : items) {
			totalOrderCost += item.getTotalCost();
		}
		if (totalOrderCost > 500) {
			totalOrderCost -= (totalOrderCost * discount) / 100;
		}
		return totalOrderCost;
	}

}
