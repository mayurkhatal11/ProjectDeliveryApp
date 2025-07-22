package com.aurionpro.test;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.manager.CustomerManager;
import com.aurionpro.manager.ProductManager;
import com.aurionpro.model.Customer;
import com.aurionpro.operations.AdminOperations;
import com.aurionpro.operations.UserOperations;

public class OrderTest {

	private static ProductManager productManager = new ProductManager();
	private static CustomerManager customerManager = new CustomerManager();
	private static int customerIdCounter = 1;

	public static void main(String[] args) {
		productManager.loadProduct();

		// setting or assigning id to the customer
		List<Customer> customerList = customerManager.getCustomer();
		int maxId = 0;
		for (Customer c : customerList) {
			if (c.getId() > maxId) {
				maxId = c.getId();
			}
		}
		customerIdCounter = maxId + 1;

		Scanner sc = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("\nAre you 'admin' or 'user'? (type 'exit' to quit)");
				String role = sc.nextLine().toLowerCase().trim();

				if (role.equals("exit")) {
					System.out.println("Exiting application. Goodbye!");
					break;
				}

				switch (role) {
				case "admin":
					new AdminOperations(productManager, customerManager).showAdminMenu();
					break;
				case "user":
					Customer customer = getOrCreateCustomer(sc);
					System.out.println("Welcome, " + customer.getName() + "! Your ID: " + customer.getId());
					new UserOperations(productManager, customerManager, customer).showUserMenu();
					break;
				default:
					System.out.println("Invalid input. Please enter 'admin', 'user', or 'exit'.");
				}
			} catch (Exception e) {
				System.out.println("Something went wrong: " + e.getMessage());
				sc.nextLine(); // Clear invalid input
			}
		}

		sc.close();
	}

	private static Customer getOrCreateCustomer(Scanner sc) {
		String name = "";
		while (true) {
			System.out.print("Enter your full name: ");
			name = sc.nextLine().trim();

			if (name.isEmpty()) {
				System.out.println("Name cannot be empty. Please enter a valid name.");
			} else if (!name.matches("[a-zA-Z ]+")) {
				System.out.println(
						"Name must contain only alphabets and spaces. No numbers or special characters allowed.");
			} else {
				break; // name is valid
			}
		}

		Customer existingCustomer = customerManager.getCustomerByName(name);
		if (existingCustomer != null) {
			return existingCustomer;
		}

		Customer newCustomer = new Customer(customerIdCounter++, name);
		customerManager.addCustomer(newCustomer);
		System.out.println("New customer created and saved.");
		return newCustomer;
	}

}
