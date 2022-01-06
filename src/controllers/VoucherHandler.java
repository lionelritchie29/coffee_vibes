package controllers;

import java.util.Random;
import java.util.Vector;

import models.Voucher;
import views.BaseView;
import views.product_admin.VoucherManagementView;

public class VoucherHandler extends BaseController {

	private static VoucherHandler instance = null;
	
	public static VoucherHandler getInstance() {
		if (instance == null) {
			instance = new VoucherHandler();
		}
		
		return instance;
	}
	
	private VoucherHandler() {
		super();
	}
	
	public Voucher insertVoucher(String discount) {
		if (discount.isEmpty()) {
			errorMessages.add("Discount must be fillled!");
			return null;
		} else if (!isNumeric(discount)) {
			errorMessages.add("Discount must be numeric!");
			return null;
		} else if (Integer.parseInt(discount) < 1 || Integer.parseInt(discount) > 100) {
			errorMessages.add("Discount must be between 1 and 100!");
			return null;
		}
		
		Random rand = new Random();
		Voucher model = new Voucher();
		
		int randomId = -1;
		do {
			randomId = rand.nextInt(100000000) + 10000 + 1;
		} while (model.getVoucher(randomId) != null);
		
		model.setVoucherID(randomId);
		model.setDiscount(Integer.parseInt(discount));
		model.setStatus("ACTIVE");
		
		if (model.generateVoucher() == null) {
			errorMessages.add("Error when inserting voucher to database :/");
			return null;
		}
		
		return model;
	}
	
	public Voucher getVoucher(int voucherID) {
		Voucher model = new Voucher();
		return model.getVoucher(voucherID);
	}
	
	public Vector<Voucher> getAllVouchers() {
		Voucher model = new Voucher();
		return model.getAllVouchers();
	}
	
	public boolean deleteVoucher(String voucherId) {
		Voucher model = new Voucher();
		if (!model.deleteVoucher(Integer.parseInt(voucherId))) {
			errorMessages.add("Failed when deleting voucher from database");
			return false;
		}
		
		return true;
	}
	
	public Voucher updateVoucher(int voucherID) {
		Voucher model = getVoucher(voucherID);
		model.setStatus("INACTIVE");
		if(!model.updateVoucher(voucherID)) {
			errorMessages.add("Failed when updating voucher from database");
			return null;
		}
		return model;
	}
	
	public BaseView viewVoucherManagementForm() {
		return new VoucherManagementView();
	}
}
