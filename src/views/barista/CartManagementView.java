package views.barista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import color.AppColor;
import controllers.CartHandler;
import controllers.ProductHandler;
import controllers.TransactionHandler;
import models.CartItem;
import models.Product;
import views.BaseView;
import views.product_admin.ProductManagementView;

public class CartManagementView extends BaseView{

	public CartManagementView() {
		super("Cart Management");
	}
	
	private Vector<CartItem> listItem;
	private JTable productTable;
	private JButton checkoutBtn;
	private JButton deleteBtn;
	
	private JTextField productIdField;
	
	@Override
	protected void initialize() {
		checkoutBtn = new JButton("Checkout");
		deleteBtn = new JButton("Delete");
		
		productIdField = new JTextField();
		
		productIdField.setPreferredSize(new Dimension(300, 30));
		productIdField.setEditable(false);
		
		checkoutBtn.setBackground(AppColor.PRIMARY);
		checkoutBtn.setForeground(Color.WHITE);
		
		deleteBtn.setBackground(Color.RED);
		deleteBtn.setForeground(Color.WHITE);
	
		checkoutBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
	}
	
	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		listItem = CartHandler.getInstance().getCart();
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		
		for (CartItem cartItem : listItem) {
			Product p = cartItem.getProduct();
			Object[] rowData = {p.getProductID(), p.getName(), p.getDescription(), p.getPrice(), cartItem.getQuantity()};
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
				productIdField.setText(productId.toString());
			}
		});
	}
	
	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Product ID");
		headers.add("Name");
		headers.add("Description");
		headers.add("Price");
		headers.add("Quantity");
		return headers;
	}
	
	@Override
	protected void setSouth() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(600, 250));
		GridBagConstraints gbc = new GridBagConstraints();
		
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
		gbc.gridy = 4;
		bottomPanel.add(deleteBtn, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		bottomPanel.add(checkoutBtn, gbc);

		
//		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new BaristaMainView();
			this.dispose();
		} else if (e.getSource() == checkoutBtn) {
			checkout();
		} else if(e.getSource() == deleteBtn) {
			delete();
		}
	}
	
	private void checkout() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			CartHandler controller = CartHandler.getInstance();
			
			if(controller.getCart().isEmpty()) {
				showAlert("There is no item in cart!");
				return;
			}
			
			TransactionHandler.getInstance().viewTransactionManagementForm();
			this.dispose();
		} 
	}

	private void delete() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			CartHandler controller = CartHandler.getInstance();
			
			String id = productIdField.getText();
			
			if (!controller.removeProductFromCart(id)){
				showFirstError(controller);
			} else {
				showSuccessMsg(controller, "Succesfully deleted cart item! :D");
				refresh();
			}
		} 
	}
	
	private void refresh() {
		mainPanel.removeAll();
		initialize();
		setNorth();
		setCenter();
		setSouth();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
}
