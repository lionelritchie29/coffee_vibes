package views.hrd;

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
import controllers.EmployeeHandler;
import views.BaseView;
import views.auth.LoginView;

public class HRDMainView extends BaseView{

	private JButton viewEmployeeBtn;
	private JButton insertEmployeeBtn;
	
	public HRDMainView() {
		super("Human Resource Department Home", 600, 200);
	}
	
	@Override
	protected void initialize() {
		insertEmployeeBtn = new JButton("Insert Employees");
		viewEmployeeBtn = new JButton("Employee Management");
		
		insertEmployeeBtn.setBackground(AppColor.SECONDARY);
		viewEmployeeBtn.setBackground(AppColor.SECONDARY);
		
		insertEmployeeBtn.addActionListener(this);
		viewEmployeeBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		contentPanel.add(insertEmployeeBtn);
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
		} else if (e.getSource() == insertEmployeeBtn) {
			new InsertEmployeeView();
			this.dispose();
		} else if (e.getSource() == viewEmployeeBtn) {
			EmployeeHandler.getInstance().viewEmployeeManagementForm();
			this.dispose();
		}
		
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

}
