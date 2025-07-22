package com.aurionpro.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.model.Customer;

public class CustomerManager {
	private List<Customer> customers;
	private static final String Customer_filepath = "customers.txt";

	public CustomerManager() {
		customers = new ArrayList<>();
		loadCustomer();
	}

	public List<Customer> getCustomer() {
		return customers;
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
		saveCustomers(customers);
	}

	public Customer getCustomerByName(String name) {
		for (Customer c : customers) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	public void saveCustomers(List<Customer> customers) {
		serializeCustomer(customers);
	}

	public void loadCustomer() {
		List<Customer> loadedCustomer = deserializeCustomer();
		if (loadedCustomer != null) {
			customers = loadedCustomer;
		} else {
			customers = new ArrayList<>();
		}
	}

	public void getAllCustomers() {
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	private void serializeCustomer(List<Customer> c) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(Customer_filepath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(c);
		} catch (Exception e) {
			System.out.println("Couldnt add product in file\n" + e.getMessage());
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<Customer> deserializeCustomer() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(Customer_filepath);
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<Customer> customers = (ArrayList<Customer>) ois.readObject();
			return customers;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				fis.close();
				ois.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return null;
	}

}
