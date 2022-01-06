package views.manager;

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
import controllers.TransactionHandler;
import models.Employee;
import models.Transaction;
import models.TransactionItem;
import views.BaseView;

public class TransactionItemManagementView extends BaseView {
	
	private Integer transactionID;
	
	public TransactionItemManagementView(Integer transactionID) {
		super("Transaction Item");
		this.transactionID = transactionID;
		fillData();
		finalize();
	}

	private Vector<TransactionItem> transactionItems;
	private JTable transactionItemTable;
	private JTextField transactionIdField;
	private DefaultTableModel model;
	private JPanel centerPanel;
		
	@Override
	protected void initialize() {
		transactionIdField = new JTextField();

		transactionIdField.setPreferredSize(new Dimension(300, 30));
		transactionIdField.setEditable(false);
	}

	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		model = new DefaultTableModel(headers, 0);

		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(775, 400));

		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void fillData() {
		transactionItems = TransactionHandler.getInstance().getAllTransactionItems(transactionID);
		
		for (TransactionItem tranItem : transactionItems) {
			Object[] rowData = { tranItem.getTransactionID(), tranItem.getProductID(), tranItem.getQuantity()};
			model.addRow(rowData);
		}
		
		transactionItemTable = new JTable(model);
		transactionItemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Integer tranId = (Integer) transactionItemTable.getValueAt(transactionItemTable.getSelectedRow(), 0);
				transactionIdField.setText(tranId.toString());
			}
		});

		JScrollPane sp = new JScrollPane(transactionItemTable);
		sp.setPreferredSize(new Dimension(600, 400));
		centerPanel.add(sp);
	}

	private void setTableListener() {
		transactionItemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int idx = transactionItemTable.getSelectedRow();
				Integer transactionId = (Integer) transactionItemTable.getValueAt(idx, 0);
				transactionIdField.setText(transactionId.toString());
			}
		});
	}

	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Transcation ID");
		headers.add("Product ID");
		headers.add("Quantity");
		return headers;
	}

	@Override
	protected void setSouth() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(325, 100));
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 8, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(new JLabel("Transaction ID"), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(transactionIdField, gbc);

		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new TransactionManagementView();
			this.dispose();
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
