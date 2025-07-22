package com.aurionpro.operations;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.manager.CustomerManager;
import com.aurionpro.manager.ProductManager;
import com.aurionpro.model.Product;

public class AdminOperations {

	private static ProductManager productManager;
	private CustomerManager customerManager;

	public AdminOperations(ProductManager productManager, CustomerManager customerManager) {
		AdminOperations.productManager = productManager;
		this.customerManager = customerManager;
	}

	public void showAdminMenu() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\nAdmin Menu:");
			System.out.println("1. Add product");
			System.out.println("2. Update product by ID");
			System.out.println("3. Get product by ID");
			System.out.println("4. Remove product by ID");
			System.out.println("5. View all products");
			System.out.println("6. Search product by name");
			System.out.println("7. Back to menu");
			System.out.println("8. View all customers");
			System.out.println("9. Exit application");
			System.out.print("Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				addProduct(sc);
				productManager.saveProducts(productManager.getProduct());
				break;
			case 2:
				updateProductById(sc);
				productManager.saveProducts(productManager.getProduct());
				break;
			case 3:
				getProductById(sc);
				productManager.saveProducts(productManager.getProduct());
				break;
			case 4:
				removeProductById(sc);
				productManager.saveProducts(productManager.getProduct());
				break;
			case 5:
				viewAllProducts();
				productManager.saveProducts(productManager.getProduct());
				break;
			case 6:
				searchProductByName(sc);
				productManager.saveProducts(productManager.getProduct());
				break;
			case 7:
				productManager.saveProducts(productManager.getProduct());
				System.out.println("Returning to main menu...");
				return;
			case 8:
				viewAllCustomers();
				customerManager.saveCustomers(customerManager.getCustomer());
				break;
			case 9:
				System.out.println("Exiting application. Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
	}

	private static void addProduct(Scanner sc) {
		int id;

		while (true) {
			try {
				System.out.print("Enter product ID: ");
				id = Integer.parseInt(sc.nextLine());
				Product existing = productManager.getProductsById(id);
				if (existing != null) {
					System.out.println("Product ID already exists. Enter a different ID.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Product ID must be a number.");
			}
		}

		// Validating product name
		String name;
		while (true) {
			System.out.print("Enter product name: ");
			name = sc.nextLine().trim();
			if (name.isEmpty()) {
				System.out.println("Product name cannot be empty.");
			} else if (!name.matches("[a-zA-Z ]+")) {
				System.out.println("Product name must contain only alphabets and spaces.");
			} else {
				break;
			}
		}

		// Validating price
		double price;
		while (true) {
			try {
				System.out.print("Enter product price: ");
				price = Double.parseDouble(sc.nextLine());
				if (price < 0) {
					System.out.println("Price must be a positive number.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid price. Please enter a valid number.");
			}
		}

		Product product = new Product(id, name, price);
		productManager.addProduct(product);
		System.out.println("Product added successfully.");
	}

	private void updateProductById(Scanner sc) {
		System.out.print("Enter product ID to update: ");
		int id = sc.nextInt();
		sc.nextLine();

		Product existingProduct = productManager.getProductsById(id);
		if (existingProduct != null) {
			System.out.println("Current product details: " + existingProduct);
			System.out.print("Enter new product name: ");
			String name = sc.nextLine();
			System.out.print("Enter new product price: ");
			double price = sc.nextDouble();
			sc.nextLine();

			Product updatedProduct = new Product(id, name, price);
			productManager.updateProductById(id, updatedProduct);
		} else {
			System.out.println("Product not found.");
		}
	}

	private void getProductById(Scanner sc) {
		System.out.print("Enter product ID: ");
		int id = sc.nextInt();
		Product product = productManager.getProductsById(id);
		if (product != null) {
			System.out.println(product);
		} else {
			System.out.println("Product not found.");
		}
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

	private void removeProductById(Scanner sc) {
		System.out.print("Enter product ID to remove: ");
		int id = sc.nextInt();
		sc.nextLine();
		productManager.deleteProduct(id);
		System.out.println("Product removed.");
	}

	private void viewAllProducts() {
		productManager.getAllProducts();
	}

	private void viewAllCustomers() {
		customerManager.getAllCustomers();
	}
}
