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
import controllers.CartHandler;
import controllers.EmployeeHandler;
import controllers.TransactionHandler;
import models.CartItem;
import models.Employee;
import models.Product;
import models.Transaction;
import views.BaseView;
import views.barista.BaristaMainView;
import views.hrd.UpdateEmployeeView;

public class TransactionManagementView extends BaseView {

	public TransactionManagementView() {
		super("Transaction Management");
	}

	private Vector<Transaction> transactions;
	private JTable transactionTable;
	private JButton viewDetailBtn;
	private JTextField transactionIdField;

	@Override
	protected void initialize() {
		viewDetailBtn = new JButton("View Transaction Detail");

		transactionIdField = new JTextField();

		transactionIdField.setPreferredSize(new Dimension(300, 30));
		transactionIdField.setEditable(false);

		viewDetailBtn.setBackground(AppColor.PRIMARY);
		viewDetailBtn.setForeground(Color.WHITE);

		viewDetailBtn.addActionListener(this);
	}

	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		transactions = TransactionHandler.getInstance().getAllTransactions();
		DefaultTableModel model = new DefaultTableModel(headers, 0);

		for (Transaction tran : transactions) {
			Object[] rowData = { tran.getTransactionID(), tran.getPurchaseDate(), tran.getVoucherID(),
					tran.getTotalPrice() };
			model.addRow(rowData);
		}

		transactionTable = new JTable(model);
		transactionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Integer tranId = (Integer) transactionTable.getValueAt(transactionTable.getSelectedRow(), 0);
				transactionIdField.setText(tranId.toString());
			}
		});

		JScrollPane sp = new JScrollPane(transactionTable);
		sp.setPreferredSize(new Dimension(775, 400));
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(600, 400));
		centerPanel.add(sp);

		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}

	private void setTableListener() {
		transactionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int idx = transactionTable.getSelectedRow();
				Integer transactionId = (Integer) transactionTable.getValueAt(idx, 0);
				transactionIdField.setText(transactionId.toString());
			}
		});
	}

	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Transcation ID");
		headers.add("Purchase Date");
		headers.add("Voucher ID");
		headers.add("Total Price");
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
		bottomPanel.add(new JLabel("Transaction ID"), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(transactionIdField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 4;
		bottomPanel.add(viewDetailBtn, gbc);

		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ManagerMainView();
			this.dispose();
		} else if (e.getSource() == viewDetailBtn) {
			if (transactionIdField.getText().isEmpty()) {
				showAlert("Please select a transaction first!");
			} else {
				viewTransactionDetail();
			}
		}
	}

	private void viewTransactionDetail() {
		if (JOptionPane.showConfirmDialog(null, "View Transaction Detail?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			TransactionHandler.getInstance().viewTransactionItemManagementView(getSelectedTransaction().getTransactionID());
			this.dispose();
		}

	}

	private Transaction getSelectedTransaction() {
		for (Transaction tran : transactions) {
			if (tran.getTransactionID() == Integer.parseInt(transactionIdField.getText())) {
				return tran;
			}
		}
		return null;
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
