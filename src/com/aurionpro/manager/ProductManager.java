package com.aurionpro.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.model.Product;

public class ProductManager {
	private List<Product> products;
	private static final String Product_filepath = "products.txt";

	public ProductManager() {
		products = new ArrayList<>();
	}

	public List<Product> getProduct() {
		return products;
	}

	public void addProduct(Product p) {
		boolean flag = false;
		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(p.getName())) {
				System.out.println("Product already exists");
				flag = true;
				break;
			}
		}
		if (!(flag)) {
			products.add(p);
		}
	}

	public void saveProducts(List<Product> products) {
		serializeProduct(products);
	}

	public void loadProduct() {
		List<Product> loadedProduct = deserializeProduct();
		if (loadedProduct != null) {
			products = loadedProduct;
		} else {
			products = new ArrayList<>();
		}
	}

	public void updateProductById(int id, Product updatedProduct) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getId() == id) {
				products.set(i, updatedProduct);
				System.out.println("Product updated successfully.");
				return;
			}
		}
		System.out.println("Product with ID " + id + " not found.");
	}

	public List<Product> searchProductsById(int id) {
		List<Product> matchedProducts = new ArrayList<>();
		for (Product product : products) {
			if (product.getId() == id) {
				matchedProducts.add(product);
			}
		}
		return matchedProducts;
	}

	public List<Product> searchProductsByName(String name) {
		List<Product> matchedProducts = new ArrayList<>();
		for (Product product : products) {
			if (product.getName().toLowerCase().equals(name.toLowerCase())) {
				matchedProducts.add(product);
			}
		}
		return matchedProducts;
	}

	public void deleteAllProducts(List<Product> products) {
		products.clear();
		saveProducts(products);
	}

	public void deleteProduct(int idToRemove) {
		products.removeIf(product -> product.getId() == idToRemove);
	}

	public Product getProductsById(int id) {
		for (Product pro : products) {
			if (pro.getId() == id) {
				return pro;
			}
		}
		System.out.println("Product not found");
		return null;
	}

	public void getAllProducts() {
		for (Product pro : products) {
			System.out.println(pro);
		}
	}

	private void serializeProduct(List<Product> p) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(Product_filepath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
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

	public List<Product> deserializeProduct() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(Product_filepath);
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<Product> products = (ArrayList<Product>) ois.readObject();
			return products;

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
