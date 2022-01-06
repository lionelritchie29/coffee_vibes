package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import models.CartItem;
import models.Product;
import models.Transaction;
import models.TransactionItem;
import models.Voucher;
import views.barista.TransactionManagementForm;
import views.manager.EmployeeManagementView;
import views.manager.TransactionItemManagementView;
import views.manager.TransactionManagementView;

public class TransactionHandler extends BaseController{

	private static TransactionHandler instance = null;
	
	public static TransactionHandler getInstance() {
		if (instance == null) {
			instance = new TransactionHandler();
		}
		
		return instance;
	}
	
	private TransactionHandler() {
		super();
	}
	
	public TransactionManagementForm viewTransactionManagementForm() {
		return new TransactionManagementForm();
	}
	
	public TransactionManagementView viewTransactionManagementView() {
		return new TransactionManagementView();
	}
	
	public TransactionItemManagementView viewTransactionItemManagementView(Integer transactionID) {
		return new TransactionItemManagementView(transactionID);
	}
	
	public int getTotalPrice() {
		int total = 0;
		Vector<CartItem> listItem = CartHandler.getInstance().getCart();
		for(CartItem item : listItem) {
			int price = item.getProduct().getPrice();
			total += price * item.getQuantity();
		}
		return total;
	}
	
	
	public int recalculateTotalPrice(int discount) {
		return getTotalPrice() - (getTotalPrice() * discount / 100);
	}

	public int getVoucher(String voucherTxt) {
		if(!isNumeric(voucherTxt)) {
			errorMessages.add("Voucher ID must be numeric!");
			return -1;
		}
		
		int voucher = Integer.parseInt(voucherTxt);
		Voucher v = VoucherHandler.getInstance().getVoucher(voucher);
		
		if(v == null) {
			errorMessages.add("Wrong voucher id!");
			return -1;
		}
		
		if(v.getStatus().equals("INACTIVE")) {
			errorMessages.add("This voucher has already been used!");
			return -1;
		}
		
		return recalculateTotalPrice(v.getDiscount());
	}
	
	public Transaction insertTransaction(String voucherID, int employeeID, int totalPrice) {
		int voucher = 0;
		
		if(!voucherID.isEmpty()) {
			voucher = Integer.parseInt(voucherID);
			if(VoucherHandler.getInstance().updateVoucher(voucher) == null) {
				errorMessages.add("Failed when updating voucher from database!");
				return null;
			}
		}
		
		Transaction model = new Transaction();
		model.setEmployeeID(employeeID);
		model.setTotalPrice(totalPrice);
		model.setVoucherID(voucher);
		model.setPurchaseDate(getCurrentDate());
		model.insertNewTransaction();
		
		int transactionID = model.getLastTransactionID();
		Vector<CartItem> listItem = CartHandler.getInstance().getCart();
		
		for(CartItem item : listItem) {
			item.getProduct().setStock(item.getProduct().getStock() - item.getQuantity());
			item.getProduct().updateStock();
			
			TransactionItem modelItem = new TransactionItem(transactionID, 
					item.getProduct().getProductID(), 
					item.getQuantity());
			modelItem.insertNewTransactionItem();
		}
		
		CartHandler.getInstance().clearCart();
		
		return model;
	}
	
	public Vector<Transaction> getAllTransactions() {
		Transaction model = new Transaction();
		return model.getAllTransactions();
	}
	
	public Transaction getTransaction(int transactionID) {
		Transaction model = new Transaction();
		return model.getTransaction(transactionID);
	}
	
	public Vector<TransactionItem> getAllTransactionItems(int transactionID) {
		TransactionItem model = new TransactionItem();
		return model.getAllTransactionItems(transactionID);
	}
	
	public java.sql.Date getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}
}
