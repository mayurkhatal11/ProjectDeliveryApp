package com.aurionpro.model;

import java.io.Serializable;

public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;
	int quantity;
	Product product;
	double totalCost;

	public LineItem(int id, int quantity, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getTotalCost() {
		return calculateLineItemCost(product);
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double calculateLineItemCost(Product product) {
		return product.getPrice() * quantity;
	}


}
