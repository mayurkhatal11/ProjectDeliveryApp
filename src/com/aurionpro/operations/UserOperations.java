package com.aurionpro.operations;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.manager.CustomerManager;
import com.aurionpro.manager.ProductManager;
import com.aurionpro.model.Customer;
import com.aurionpro.model.LineItem;
import com.aurionpro.model.Order;
import com.aurionpro.model.Product;

public class UserOperations {
	private ProductManager productManager;
	private static int orderCounter = 1;
	private static int lineItemCounter = 1;
	private CustomerManager customerManager;
	private Customer currentCustomer;

	public UserOperations(ProductManager productManager, CustomerManager customerManager, Customer currentCustomer) {
		this.productManager = productManager;
		this.customerManager = customerManager;
		this.currentCustomer = currentCustomer;
	}

	public void showUserMenu() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\nUser Menu:");
			System.out.println("1. View all products");
			System.out.println("2. Buy product");
			System.out.println("3. Search product by name");
			System.out.println("4. Back to main menu");
			System.out.println("5. Exit");
			System.out.println("Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				viewAllProducts();
				break;
			case 2:
				buyProduct(sc);
				break;
			case 3:
				searchProductByName(sc);
				break;
			case 4:
				productManager.saveProducts(productManager.getProduct());
				return;
			case 5:
				System.out.println("Exiting application. Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private void viewAllProducts() {
		productManager.getAllProducts();
	}

	private void searchProductByName(Scanner sc) {
		System.out.print("Enter product name: ");
		String name = sc.nextLine();
		List<Product> matched = productManager.searchProductsByName(name);
		if (matched.isEmpty()) {
			System.out.println("No matching products found.");
		} else {
			matched.forEach(System.out::println);
		}
	}

	private void buyProduct(Scanner sc) {
		List<LineItem> lineItems = new ArrayList<>();
		boolean buying = true;

		while (buying) {
			System.out.print("Enter product ID to buy: ");
			int id = sc.nextInt();
			sc.nextLine();

			Product product = productManager.getProductsById(id);
			if (product != null) {
				System.out.print("Enter quantity: ");
				int qty = sc.nextInt();
				sc.nextLine();
				// checking if product already exists in the current order
				boolean productExists = false;

				for (LineItem item : lineItems) {
					if (item.getProduct().getId() == product.getId()) {
						item.setQuantity(item.getQuantity() + qty); // updating a quantity
						productExists = true;
						System.out.println("Updated quantity for existing product.");
						break;
					}
				}

				if (!productExists) {
					LineItem item = new LineItem(lineItemCounter++, qty, product);
					lineItems.add(item);
					System.out.println("Product added to the order.");
				}

				System.out.print("Add another product? (yes/no): ");
				if (!sc.nextLine().equalsIgnoreCase("yes")) {
					buying = false;
				}
			} else {
				System.out.println("Product not found.");
			}
		}

		if (!lineItems.isEmpty()) {
			// Asking for payment
			String paymentMethod;
			while (true) {
				System.out.print("Enter payment method (Cash/UPI): ");
				paymentMethod = sc.nextLine().trim();
				if (paymentMethod.equalsIgnoreCase("cash") || paymentMethod.equalsIgnoreCase("upi")) {
					break;
				} else {
					System.out.println("Invalid payment method. Try again.");
				}
			}

			// Assigning random delivery partner
			String[] partners = { "Zomato", "Swiggy" };
			String deliveryPartner = partners[(int) (Math.random() * partners.length)];

			// Creating order
			Order order = new Order(orderCounter++, LocalDate.now(), lineItems, paymentMethod, deliveryPartner);

			// Adding order to current customer
			if (currentCustomer.getOrders() == null) {
				currentCustomer.setOrders(new ArrayList<>());
			}
			currentCustomer.getOrders().add(order);
			customerManager.saveCustomers(customerManager.getCustomer());

			// Printing invoice
			printInvoice(order);
		} else {
			System.out.println("No items added.");
		}
	}

	private void printInvoice(Order order) {
		System.out.println("\n----- INVOICE -----");
		System.out.println("Date: " + order.getDate());
		for (LineItem item : order.getItems()) {
			System.out.println("Product: " + item.getProduct().getName() + " | Quantity: " + item.getQuantity()
					+ " | Price: Rs." + item.getProduct().getPrice() + " | Subtotal: Rs." + item.getTotalCost());
		}
		double total = order.calculateOrderPrice();

		System.out.println("Discount Applied: " + Order.getDiscount());

		System.out.println("Total Amount: Rs." + total);
		System.out.println("Payment Mode: " + order.getPaymentMethod());
		System.out.println("Delivery Partner: " + order.getDeliveryPartner());
		System.out.println("---------------------");

	}

}
