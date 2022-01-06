package views.hrd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.EmployeeHandler;
import controllers.PositionHandler;
import models.Position;
import views.BaseView;

public class InsertEmployeeView extends BaseView{
	
	private JButton insertBtn;
	
	private Vector<String> positionStrings;
	private Vector<Integer> positionIds;
	
	private JTextField nameField;
	private JComboBox positionCb;
	private JTextField salaryField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	

	public InsertEmployeeView() {
		super("Insert New Employee", 600, 325);
	}

	@Override
	protected void initialize() {
		insertBtn = new JButton("Insert Employee");
		
		setPositionComboBox();
		
		nameField = new JTextField();
		positionCb = new JComboBox(positionStrings);
		salaryField = new JTextField();
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		nameField.setPreferredSize(new Dimension(300, 20));
		
		insertBtn.setBackground(AppColor.PRIMARY);
		insertBtn.setForeground(Color.WHITE);
		insertBtn.addActionListener(this);
	}
	
	private void setPositionComboBox() {
		positionStrings = new Vector<>();
		positionIds = new Vector<>();
		
		Vector<Position> positions = PositionHandler.getInstance().getAllPositions();
		for (Position pos: positions) {
			positionStrings.add(pos.getName());
			positionIds.add(pos.getPositionID());
		}
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
		gbc.gridy = 1;
		centerPanel.add(new JLabel("Position") , gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(positionCb, gbc);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new HRDMainView();
			this.dispose();
		} else if (e.getSource() == insertBtn) {
			insert();
		}
	}
	
	private void insert() {
		int positionID = positionIds.get(positionCb.getSelectedIndex());
		
		String name = nameField.getText();
		Position position = PositionHandler.getInstance().getPosition(positionID);
		String salary = salaryField.getText();
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		
		EmployeeHandler controller = EmployeeHandler.getInstance();
		if (controller.insertEmployee(name, position, salary, username, password) == null) {
			showFirstError(controller);
		} else {
			showSuccessMsg(controller, "Succesfully added new employee! :D");
			nameField.setText("");
			salaryField.setText("");
			usernameField.setText("");
			passwordField.setText("");
		}
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}

}
