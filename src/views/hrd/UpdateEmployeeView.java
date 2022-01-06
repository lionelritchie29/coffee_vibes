package views.hrd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.EmployeeHandler;
import controllers.PositionHandler;
import models.Employee;
import models.Position;
import views.BaseView;

public class UpdateEmployeeView extends BaseView {

	private JButton backBtn;
	private JButton insertBtn;
	
	private JTextField nameField;
	private JTextField salaryField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private Employee employee;

	public UpdateEmployeeView(Employee employee) {
		super("Update Employee", 600, 325);
		this.employee = employee;
		fillForm();
		finalize();
	}

	@Override
	protected void initialize() {
		backBtn = new JButton("Back");
		insertBtn = new JButton("Update Employee");
		
		nameField = new JTextField();
		salaryField = new JTextField();
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		nameField.setPreferredSize(new Dimension(300, 20));
		
		insertBtn.setBackground(AppColor.PRIMARY);
		insertBtn.setForeground(Color.WHITE);
		insertBtn.addActionListener(this);
		backBtn.addActionListener(this);
	}
	
	@Override
	protected void setNorth() {
		JLabel header = new JLabel("Update employee");
		header.setForeground(Color.WHITE);
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(AppColor.PRIMARY);
		topPanel.add(backBtn);
		topPanel.add(header);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	@Override
	protected void setCenter() {
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 0.5;
		gbc.insets = new Insets(8, 8, 8, 8);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(new JLabel("Name"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(nameField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(new JLabel("Salary"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(salaryField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		centerPanel.add(new JLabel("Username"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		centerPanel.add(usernameField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		centerPanel.add(new JLabel("Password"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		centerPanel.add(passwordField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.ipady = 10;
		centerPanel.add(insertBtn, gbc);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel);
	}
	
	private void fillForm() {
		nameField.setText(employee.getName());
		salaryField.setText(employee.getSalary().toString());
		usernameField.setText(employee.getUsername());
		passwordField.setText(employee.getPassword());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new EmployeeManagementView();
			this.dispose();
		} else if (e.getSource() == insertBtn) {
			update();
		}
	}
	
	private void update() {		
		String name = nameField.getText();
		String salary = salaryField.getText();
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			EmployeeHandler controller = EmployeeHandler.getInstance();
			if (controller.updateEmployee(employee.getEmployeeID(), name, salary, username, password) == null) {
				showFirstError(controller);
			} else {
				showSuccessMsg(controller, "Succesfully updated employee! :D");
			}
		} 
		
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

}
