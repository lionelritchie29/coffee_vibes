package views.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.EmployeeHandler;
import controllers.TransactionHandler;
import models.Transaction;
import views.BaseView;
import views.auth.LoginView;
import views.hrd.InsertEmployeeView;

public class ManagerMainView extends BaseView{

	private JButton viewTransactionBtn;
	private JButton viewEmployeeBtn;
	
	public ManagerMainView() {
		super("Manager Home", 600, 200);
	}
	
	@Override
	protected void initialize() {
		viewTransactionBtn = new JButton("View Transaction");
		viewEmployeeBtn = new JButton("Employee Management");
		
		viewTransactionBtn.setBackground(AppColor.SECONDARY);
		viewEmployeeBtn.setBackground(AppColor.SECONDARY);
		
		viewTransactionBtn.addActionListener(this);
		viewEmployeeBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		contentPanel.add(viewTransactionBtn);
		contentPanel.add(viewEmployeeBtn);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		contentPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new LoginView();
			this.dispose();
		} else if (e.getSource() == viewTransactionBtn) {
			TransactionHandler.getInstance().viewTransactionManagementView();
			this.dispose();
		} else if (e.getSource() == viewEmployeeBtn) {
			EmployeeHandler.getInstance().viewEmployeeManagerManagementView();
			this.dispose();
		}
		
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}
}
