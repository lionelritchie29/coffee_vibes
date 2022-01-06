package views.barista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import color.AppColor;
import controllers.AuthController;
import controllers.TransactionHandler;
import controllers.VoucherHandler;
import views.BaseView;

public class TransactionManagementForm extends BaseView{

	private JTextField voucherField;
	private JButton checkoutBtn;
	private JLabel totalPriceLbl;
	private int price;
	
	public TransactionManagementForm() {
		super("Transaction Management", 600, 300);
	}
	
	@Override
	protected void initialize() {
		voucherField = new JTextField();
		checkoutBtn = new JButton("Checkout");
		
		voucherField.setPreferredSize(new Dimension(300, 30));
		
		checkoutBtn.setBackground(AppColor.PRIMARY);
		checkoutBtn.setForeground(Color.WHITE);
		
		checkoutBtn.addActionListener(this);
		
		price = TransactionHandler.getInstance().getTotalPrice();
		totalPriceLbl = new JLabel("Rp. " + price);
	}
	
	@Override
	protected void setCenter() {
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(new JLabel("Total Price"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(totalPriceLbl, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(new JLabel("Voucher"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(voucherField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 10;
		centerPanel.add(checkoutBtn, gbc);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel);
	}
	
	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new CartManagementView();
			this.dispose();
		} else if (e.getSource() == checkoutBtn) {
			checkout();
		}
	}
	
	private void checkout() {
		String voucherTxt = voucherField.getText();
		int newTotalPrice = price;
		TransactionHandler controller = TransactionHandler.getInstance();
		
		if(!voucherTxt.isEmpty()) {
			newTotalPrice = controller.getVoucher(voucherTxt);
			
			if(newTotalPrice == -1) {
				showFirstError(controller);
				return;
			}
			
			totalPriceLbl.setText("Rp. " + newTotalPrice);
			JOptionPane.showMessageDialog(null, "Total price after discount : Rp. " + newTotalPrice);
		}
		
		if(JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if(controller.insertTransaction(voucherTxt, 
					AuthController.getInstance().getUser().getEmployeeID(), newTotalPrice) == null) {
				showFirstError(controller);
			}
			else {
				showSuccessMsg(controller, "Successfully checkout!");
				new BaristaMainView();
				this.dispose();
			}
		} 
		else {
			totalPriceLbl.setText("Rp. " + TransactionHandler.getInstance().getTotalPrice());
			voucherField.setText("");
		}
		
	}

}
