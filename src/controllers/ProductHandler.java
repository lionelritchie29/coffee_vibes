package controllers;

import java.util.Vector;

import models.Product;
import views.BaseView;
import views.product_admin.ProductManagementView;

public class ProductHandler extends BaseController{

	private static ProductHandler instance = null;
	
	public static ProductHandler getInstance() {
		if (instance == null) {
			instance = new ProductHandler();
		}
		
		return instance;
	}
	
	private ProductHandler() {
		super();
	}
	
	public Product insertProduct(String name, String description, String price, String stock) {
		if (name.isEmpty() || description.isEmpty() || price.isEmpty() || stock.isEmpty()) {
			errorMessages.add("All field must be fillled!");
			return null;
		} else if (!isNumeric(stock) || !isNumeric(price)) {
			errorMessages.add("Price and stock must be numeric!");
			return null;
		} else if (Integer.parseInt(stock) < 0) {
			errorMessages.add("Stock must be greater than zero!");
			return null;
		} else if (Integer.parseInt(price) < 1) {
			errorMessages.add("Price must be greater than one!");
			return null;
		}
		
		Product model = new Product(-1, name, description, Integer.parseInt(price), Integer.parseInt(stock));
		
		if (model.insertNewProduct() == null) {
			errorMessages.add("Failed when inserting product to database :/");
			return null;
		}
		
		return model;
	}
	
	public Product updateProduct(String productID, String name, String description, String price) {
		Product model = new Product();
		
		if (productID.isEmpty() || name.isEmpty() || description.isEmpty() || price.isEmpty()) {
			errorMessages.add("All field must be fillled!");
			return null;
		} else if (!isNumeric(productID) || !isNumeric(price)) {
			errorMessages.add("Product ID and Price must be numeric!");
			return null;
		} else if (model.getProduct(Integer.parseInt(productID)) == null) {
			errorMessages.add("Product with those ID does not exists!");
			return null;
		} else if (Integer.parseInt(price) < 1) {
			errorMessages.add("Price must be greater than one!");
			return null;
		}
		
		model.setProductID(Integer.parseInt(productID));
		model.setName(name);
		model.setDescription(description);
		model.setPrice(Integer.parseInt(price));
		
		if (model.updateProduct() == null) {
			errorMessages.add("Failed when updating product to database :/");
			return null;
		}
		
		return model;
	}
	
	public Product deleteProduct(String productID)  {
		Product model = new Product();
		
		if (productID.isEmpty()) {
			errorMessages.add("Product ID must be fillled!");
			return null;
		} else if (!isNumeric(productID)) {
			errorMessages.add("Product ID must be numeric!");
			return null;
		} else if (model.getProduct(Integer.parseInt(productID)) == null) {
			errorMessages.add("Product with those ID does not exists!");
			return null;
		}
		
		Product prod = model.getProduct(Integer.parseInt(productID));
		
		if (!prod.deleteProduct()) {
			errorMessages.add("Failed when deleting product from database :/");
			return null;
		}
		
		return prod;
	}
	
	public Vector<Product> getAllProducts() {
		Product model = new Product();
		return model.getAllProducts();
	}
	
	public Product getProduct(int productID) {
		Product model = new Product();
		return model.getProduct(productID);
	}
	
	public BaseView viewProductManagementForm() {
		return new ProductManagementView();
	}
}
