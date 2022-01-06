package controllers;

import java.util.Vector;

import models.CartItem;
import models.Product;
import views.barista.AddToCartView;
import views.barista.CartManagementView;
import views.barista.TransactionManagementForm;

public class CartHandler extends BaseController{
	
	private static CartHandler instance = null;
	private Vector<CartItem> listItem;
	
	public static CartHandler getInstance() {
		if (instance == null) {
			instance = new CartHandler();
		}
		
		return instance;
	}
	
	private CartHandler() {
		super();
		listItem = new Vector<CartItem>();
	}
	
	public CartItem addToCart(String productID, String quantity) {
		int qty = 0;
		if(productID.isEmpty() ||  quantity.isEmpty()) {
			errorMessages.add("Product ID and Quantity must be filled!");
			return null;
		}
		if(!isNumeric(quantity)) {
			errorMessages.add("Quantity must be numeric!");
			return null;
		}
		
		qty = Integer.parseInt(quantity);
		Product p = ProductHandler.getInstance().getProduct(Integer.parseInt(productID));
		
		if(qty <= 0) {
			errorMessages.add("Minimum quantity is 1!");
			return null;
		}
		
		if(p == null || qty > p.getStock()) {
			errorMessages.add("Insufficient stock!");
			return null;
		}
		
		if(!checkQuantityInCart(p, qty)) {
			errorMessages.add("Unfortunately, "
					+ "quantity for this item in your cart has bigger than the stock!");
			return null;
		}
		
		CartItem item = updateProductCartQuantity(p.getProductID(), qty);
		
		if(item == null) {
			item = new CartItem(p, qty);
			listItem.add(item);
		}
	
		return item;
	}
	
	public CartItem updateProductCartQuantity(int productID, int quantity) {
		for(CartItem item : listItem) {
			if(item.getProduct().getProductID() == productID) {
				item.setQuantity(item.getQuantity() + quantity);
				return item;
			}
		}
		return null;
	}
	
	public boolean checkQuantityInCart(Product p, int quantity) {
		for(CartItem item : listItem) {
			if(item.getProduct().getProductID() == p.getProductID()) {
				if(item.getQuantity() + quantity > p.getStock()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean removeProductFromCart(String productID) {
		if(productID.isEmpty()) {
			errorMessages.add("Please choose the cart item!");
			return false;
		}
		
		int index = 0;
		for(CartItem item : listItem) {
			if(item.getProduct().getProductID() == Integer.parseInt(productID)) {
				break;
			}
			index++;
		}
		
		listItem.remove(index);
		
		return true;
	}
	
	public void clearCart() {
		this.listItem.clear();
	}

	public AddToCartView viewAddToCartForm() {
		return new AddToCartView();
	}
	
	public CartManagementView viewCheckoutForm() {
		return new CartManagementView();
	}
	
	public Vector<CartItem> getCart(){
		return this.listItem;
	}
}
