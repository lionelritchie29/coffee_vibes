package views.product_admin;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import color.AppColor;
import controllers.ProductHandler;
import controllers.VoucherHandler;
import models.Product;
import models.Voucher;
import views.BaseView;

public class VoucherManagementView extends BaseView {

	private JTextField voucherIdField;
	private Vector<Voucher> vouchers; 
	private JTable voucherTable;
	private JButton deleteBtn;
	
	public VoucherManagementView() {
		super("Voucher Management");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		deleteBtn = new JButton("Delete");
		voucherIdField = new JTextField();
		
		voucherIdField.setPreferredSize(new Dimension(300, 30));
		voucherIdField.setEditable(false);
		
		deleteBtn.setBackground(Color.RED);
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.addActionListener(this);
	}

	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		vouchers = VoucherHandler.getInstance().getAllVouchers();
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		
		for (Voucher v : vouchers) {
			Object[] rowData = {v.getVoucherID(), v.getDiscount(), v.getStatus()};
			model.addRow(rowData);
		}
		
		voucherTable = new JTable(model);		
		setTableListener();
		JScrollPane sp = new JScrollPane(voucherTable);
		sp.setPreferredSize(new Dimension(775, 400));
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(600, 400));
		centerPanel.add(sp);
		
		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void setTableListener() {
		voucherTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int idx = voucherTable.getSelectedRow();
				Integer voucherId = (Integer) voucherTable.getValueAt(idx, 0);
				
				voucherIdField.setText(voucherId.toString());
			}
		});
	}
	
	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Voucher ID");
		headers.add("Discount");
		headers.add("Status");
		return headers;
	}

	@Override
	protected void setSouth() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel label = new JLabel("Voucher ID");
		
		gbc.insets = new Insets(10, 8, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(voucherIdField, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = gbc.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy = 1;;
		bottomPanel.add(deleteBtn, gbc);
		
//		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ProductAdminMainView();
			this.dispose();
		} else if (e.getSource() == deleteBtn) {
			delete();
		}
	}
	
	private void delete() {
		String voucherId = voucherIdField.getText();
		VoucherHandler controller = VoucherHandler.getInstance();
	
		if (!controller.deleteVoucher(voucherId)) {
			showFirstError(controller);
		} else {
			showSuccessMsg(controller, "Voucher succesfully deleted! :D");
			new VoucherManagementView();
			this.dispose();
		}
	}
}
