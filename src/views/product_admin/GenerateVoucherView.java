package views.product_admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import color.AppColor;
import controllers.VoucherHandler;
import views.BaseView;

public class GenerateVoucherView extends BaseView {

	private JTextField discountField;
	private JButton generateBtn;
	
	public GenerateVoucherView() {
		super("Generate Voucher", 600, 300);
	}
	
	@Override
	protected void initialize() {
		discountField = new JTextField();
		generateBtn = new JButton("Generate Voucher");
		
		discountField.setPreferredSize(new Dimension(300, 30));
		
		generateBtn.setBackground(AppColor.PRIMARY);
		generateBtn.setForeground(Color.WHITE);
		generateBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(new JLabel("Discount (1 - 100)"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(discountField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 10;
		centerPanel.add(generateBtn, gbc);
		
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
			new ProductAdminMainView();
			this.dispose();
		} else if (e.getSource() == generateBtn) {
			generate();
		}
	}
	
	private void generate() {
		String disc = discountField.getText();
		VoucherHandler controller = VoucherHandler.getInstance();
		
		if (controller.insertVoucher(disc) == null) {
			showFirstError(controller);
		} else {
			showSuccessMsg(controller, "Voucher generated successfully :D");
			discountField.setText("");
		}
		
	}

}
