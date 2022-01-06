package views.product_admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.ProductHandler;
import controllers.VoucherHandler;
import views.BaseView;
import views.auth.LoginView;

public class ProductAdminMainView extends BaseView{
	
	private JButton insertProductBtn;
	private JButton generateVoucherBtn;
	private JButton viewProductsBtn;
	private JButton viewVoucherBtn;

	public ProductAdminMainView() {
		super("Product Admin Home", 600, 400);
	}	
	
	@Override
	protected void initialize() {
		insertProductBtn = new JButton("Insert Product");
		generateVoucherBtn = new JButton("Generate Voucher");
		viewProductsBtn = new JButton("View Products");
		viewVoucherBtn = new JButton("View Vouchers");
		
		insertProductBtn.setBackground(AppColor.SECONDARY);
		generateVoucherBtn.setBackground(AppColor.SECONDARY);
		viewProductsBtn.setBackground(AppColor.SECONDARY);
		viewVoucherBtn.setBackground(AppColor.SECONDARY);
		
		insertProductBtn.addActionListener(this);
		generateVoucherBtn.addActionListener(this);
		viewProductsBtn.addActionListener(this);
		viewVoucherBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		JPanel contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		
		contentPanel.add(insertProductBtn);
		contentPanel.add(viewProductsBtn);
		contentPanel.add(generateVoucherBtn);
		contentPanel.add(viewVoucherBtn);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		contentPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new LoginView();
			this.dispose();
		}
		
		if (e.getSource() == insertProductBtn) {
			new InsertProductView();
			this.dispose();
		} else if (e.getSource() == viewProductsBtn) {
			ProductHandler.getInstance().viewProductManagementForm();
			this.dispose();
		} else if (e.getSource() == generateVoucherBtn) {
			new GenerateVoucherView();
			this.dispose();
		} else if (e.getSource() == viewVoucherBtn) {
			VoucherHandler.getInstance().viewVoucherManagementForm();
			this.dispose();
		} 
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

}
