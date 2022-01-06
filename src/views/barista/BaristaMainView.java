package views.barista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.CartHandler;
import controllers.EmployeeHandler;
import views.BaseView;
import views.auth.LoginView;
import views.hrd.InsertEmployeeView;

public class BaristaMainView extends BaseView{

	private JButton viewCartBtn;
	private JButton addToCartBtn;
	
	public BaristaMainView() {
		super("Barista Home", 600, 200);
	}
	
	@Override
	protected void initialize() {
		addToCartBtn = new JButton("Add to Cart");
		viewCartBtn = new JButton("Cart Management");
		
		addToCartBtn.setBackground(AppColor.SECONDARY);
		viewCartBtn.setBackground(AppColor.SECONDARY);
		
		addToCartBtn.addActionListener(this);
		viewCartBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		contentPanel.add(addToCartBtn);
		contentPanel.add(viewCartBtn);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		contentPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new LoginView();
			this.dispose();
		} else if (e.getSource() == addToCartBtn) {
			CartHandler.getInstance().viewAddToCartForm();
			this.dispose();
		} else if (e.getSource() == viewCartBtn) {
			CartHandler.getInstance().viewCheckoutForm();
			this.dispose();
		}
		
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

}
