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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import color.AppColor;
import controllers.EmployeeHandler;
import controllers.ProductHandler;
import models.Employee;
import models.Product;
import views.BaseView;

public class ProductManagementView extends BaseView {
	
	private Vector<Product> products;
	private JTable productTable;
	private JButton deleteBtn;
	private JButton updateBtn;
	private JButton addToCartBtn;
	
	private JTextField productIdField;
	private JTextField nameField;
	private JTextField descField;
	private JTextField priceField;
	

	public ProductManagementView() {
		super("Product Management");
	}
	
	@Override
	protected void initialize() {
		deleteBtn = new JButton("Delete");
		updateBtn = new JButton("Update");
		addToCartBtn = new JButton("Add to Cart");
		
		
		productIdField = new JTextField();
		nameField = new JTextField();
		descField = new JTextField();
		priceField = new JTextField();
		
		productIdField.setPreferredSize(new Dimension(300, 30));
		nameField.setPreferredSize(new Dimension(300, 30));
		descField.setPreferredSize(new Dimension(300, 30));
		priceField.setPreferredSize(new Dimension(300, 30));
		
		productIdField.setEditable(false);
		
		deleteBtn.setBackground(Color.RED);
		deleteBtn.setForeground(Color.WHITE);
		
		updateBtn.setBackground(AppColor.PRIMARY);
		updateBtn.setForeground(Color.WHITE);
		
		addToCartBtn.setBackground(Color.DARK_GRAY);
		addToCartBtn.setForeground(Color.WHITE);
		
		deleteBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		addToCartBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		products = ProductHandler.getInstance().getAllProducts();
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		
		for (Product p : products) {
			Object[] rowData = {p.getProductID(), p.getName(), p.getDescription(), p.getPrice(), p.getStock()};
			model.addRow(rowData);
		}
		
		productTable = new JTable(model);		
		setTableListener();
		JScrollPane sp = new JScrollPane(productTable);
		sp.setPreferredSize(new Dimension(775, 250));
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(600, 250));
		centerPanel.add(sp);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void setTableListener() {
		productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int idx = productTable.getSelectedRow();
				Integer productId = (Integer) productTable.getValueAt(idx, 0);
				String name = (String) productTable.getValueAt(idx, 1);
				String desc = (String) productTable.getValueAt(idx, 2);
				Integer price = (Integer) productTable.getValueAt(idx, 3);
				Integer stock = (Integer) productTable.getValueAt(idx, 4);
				
				productIdField.setText(productId.toString());
				nameField.setText(name.toString());
				descField.setText(desc.toString());
				priceField.setText(price.toString());
			}
		});
	}
	
	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Product ID");
		headers.add("Name");
		headers.add("Description");
		headers.add("Price");
		headers.add("Stock");
		return headers;
	}
	
	@Override
	protected void setSouth() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(600, 250));
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel label = new JLabel("Product ID");
		
		gbc.insets = new Insets(10, 8, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(new JLabel("Product ID"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(productIdField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		bottomPanel.add(new JLabel("Name"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		bottomPanel.add(nameField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		bottomPanel.add(new JLabel("Description"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		bottomPanel.add(descField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		bottomPanel.add(new JLabel("Price"), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		bottomPanel.add(priceField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;;
		bottomPanel.add(deleteBtn, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		bottomPanel.add(updateBtn, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		bottomPanel.add(addToCartBtn, gbc);
		
//		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ProductAdminMainView();
			this.dispose();
		} else if (e.getSource() == updateBtn) {
			update();
		} else if (e.getSource() == deleteBtn) {
			delete();
		} else if (e.getSource() == addToCartBtn) {
			
		}
	}
	
	private void update() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			ProductHandler controller = ProductHandler.getInstance();
			
			String id = productIdField.getText();
			String name = nameField.getText();
			String desc = descField.getText();
			String price = priceField.getText();
			
			if (controller.updateProduct(id, name, desc, price) == null){
				showFirstError(controller);
			} else {
				showSuccessMsg(controller, "Succesfully updated product! :D");
				new ProductManagementView();
				this.dispose();
			}
		} 
	}
	
	private void delete() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			ProductHandler controller = ProductHandler.getInstance();
			
			String id = productIdField.getText();
			
			if (controller.deleteProduct(id) == null){
				showFirstError(controller);
			} else {
				showSuccessMsg(controller, "Succesfully deleted product! :D");
				new ProductManagementView();
				this.dispose();
			}
		} 
	}

}
