package views.auth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Element;

import color.AppColor;
import controllers.AuthController;
import models.Employee;
import views.BaseView;
import views.barista.BaristaMainView;
import views.hrd.HRDMainView;
import views.manager.ManagerMainView;
import views.product_admin.ProductAdminMainView;

public class LoginView extends BaseView{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginBtn;

	public LoginView() {		
		super("Login", 600, 300);
	}
	
	@Override
	protected void initialize() {
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginBtn = new JButton("Log In");
		
		usernameField.setPreferredSize(new Dimension(300, 30));
		passwordField.setPreferredSize(new Dimension(300, 30));
		
		
		usernameField.setBorder(
				BorderFactory.createCompoundBorder(
						usernameField.getBorder(), 
						BorderFactory.createEmptyBorder(5, 5, 5, 5)
				));
		
		passwordField.setBorder(
				BorderFactory.createCompoundBorder(
						passwordField.getBorder(), 
						BorderFactory.createEmptyBorder(5, 5, 5, 5)
				));
		
		loginBtn.setBackground(AppColor.PRIMARY);
		loginBtn.setForeground(Color.WHITE);
		loginBtn.addActionListener(this);
	}
	
	@Override
	protected void setNorth() {
		JLabel header = new JLabel("Login to Coffee Vibes");
		header.setForeground(Color.WHITE);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(AppColor.PRIMARY);
		topPanel.add(header);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	@Override
	protected void setCenter() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel centerPanel = new JPanel(layout);
		
		gbc.insets = new Insets(0, 8, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(new JLabel("Username"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(usernameField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(new JLabel("Password"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(passwordField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 10;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		centerPanel.add(loginBtn, gbc);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == loginBtn) {
			AuthController controller = AuthController.getInstance();
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			
			Employee emp = controller.Login(username, password);
			if (emp == null) {
				showFirstError(controller);
			}  else {
				controller.setUser(emp);
				redirectByRole(emp);
			}
		}
	}
	
	private void redirectByRole(Employee emp) {
		switch(emp.getPositionID()) {
			case 1: //HRD
				new HRDMainView();
				this.dispose();
				break;
			case 2: //Barista
				new BaristaMainView();
				this.dispose();
				break;
			case 3: // Manager
				new ManagerMainView();
				this.dispose();
				break;
			case 4: // Product Admin
				new ProductAdminMainView();
				this.dispose();
				break;
		}
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}
}
