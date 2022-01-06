package views.product_admin;

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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.ProductHandler;
import views.BaseView;

public class InsertProductView extends BaseView {

	private JButton insertBtn;
	
	private JTextField nameField;
	private JTextField descField;
	private JTextField stockField;
	private JTextField priceField;

	public InsertProductView() {
		super("Insert Product", 600, 325);
	}
	
	@Override
	protected void initialize() {
		insertBtn = new JButton("Insert Product");
		
		nameField = new JTextField();
		descField = new JTextField();
		stockField = new JTextField();
		priceField = new JTextField();
		
		nameField.setPreferredSize(new Dimension(300, 20));
		descField.setPreferredSize(new Dimension(300, 20));
		stockField.setPreferredSize(new Dimension(300, 20));
		priceField.setPreferredSize(new Dimension(300, 20));
		
		insertBtn.setBackground(AppColor.PRIMARY);
		insertBtn.setForeground(Color.WHITE);
		
		insertBtn.addActionListener(this);
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
		centerPanel.add(new JLabel("Description") , gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(descField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(new JLabel("Price"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(priceField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		centerPanel.add(new JLabel("Stock"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		centerPanel.add(stockField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipady = 10;
		centerPanel.add(insertBtn, gbc);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ProductAdminMainView();
			this.dispose();
		} else if (e.getSource() == insertBtn) {
			insert();
		}
	}

	private void insert() {
		String name = nameField.getText();
		String desc = descField.getText();
		String price = priceField.getText();
		String stock = stockField.getText();
		
		ProductHandler controller = ProductHandler.getInstance();
		
		if (controller.insertProduct(name, desc, price, stock) == null) {
			showFirstError(controller);
		} else {
			showSuccessMsg(controller, "Add product success ^_^");
			nameField.setText("");
			descField.setText("");
			priceField.setText("");
			stockField.setText("");
		}
	}

	@Override
	protected void setSouth() {
		// TODO Auto-generated method stub
		
	}
}
