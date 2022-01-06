package views.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import controllers.EmployeeHandler;
import models.Employee;
import views.BaseView;

public class EmployeeManagementView extends BaseView {

	private Vector<Employee> employees;

	private JTable employeeTable;
	private JTextField employeeIdField;
	private JButton fireBtn;

	public EmployeeManagementView() {
		super("Employee Management");
	}

	@Override
	protected void initialize() {
		employeeIdField = new JTextField();
		fireBtn = new JButton("Fire");

		fireBtn.setBackground(Color.RED);
		fireBtn.setForeground(Color.WHITE);

		employeeIdField.setPreferredSize(new Dimension(300, 30));
		employeeIdField.setEditable(false);

		employeeIdField.setBorder(BorderFactory.createCompoundBorder(employeeIdField.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		fireBtn.addActionListener(this);
	}

	@Override
	protected void setCenter() {
		Vector<String> headers = getColumnHeader();
		employees = EmployeeHandler.getInstance().getAllEmployees();
		DefaultTableModel model = new DefaultTableModel(headers, 0);

		for (Employee emp : employees) {
			Object[] rowData = { emp.getEmployeeID(), emp.getPositionID(), emp.getName(), emp.getStatus(),
					emp.getSalary(), emp.getUsername() };
			model.addRow(rowData);
		}

		employeeTable = new JTable(model);
		employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Integer empId = (Integer) employeeTable.getValueAt(employeeTable.getSelectedRow(), 0);
				employeeIdField.setText(empId.toString());
			}
		});

		JScrollPane sp = new JScrollPane(employeeTable);
		sp.setPreferredSize(new Dimension(775, 400));
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(600, 400));
		centerPanel.add(sp);

		centerPanel.setBackground(AppColor.BASE_WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}

	private Vector<String> getColumnHeader() {
		Vector<String> headers = new Vector<>();
		headers.add("Employee ID");
		headers.add("Position ID");
		headers.add("Name");
		headers.add("Status");
		headers.add("Salary");
		headers.add("Username");
		return headers;
	}

	@Override
	protected void setSouth() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel label = new JLabel("EmployeID");

		gbc.insets = new Insets(10, 8, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(employeeIdField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		bottomPanel.add(fireBtn, gbc);

		bottomPanel.setBackground(AppColor.SECONDARY);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ManagerMainView();
			this.dispose();
		} else {
			if (employeeIdField.getText().isEmpty()) {
				showAlert("Please select an employee first!");
			} else {
				if (e.getSource() == fireBtn) {
					fire();
				}
			}
		}
	}

	private Employee getSelectedEmployee() {
		for (Employee emp : employees) {
			if (emp.getEmployeeID() == Integer.parseInt(employeeIdField.getText())) {
				return emp;
			}
		}
		return null;
	}

	private void fire() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			int id = Integer.parseInt(employeeIdField.getText());
			EmployeeHandler controller = EmployeeHandler.getInstance();
			if (!controller.fireEmployee(id)) {
				showFirstError(controller);
			} else {
				showSuccessMsg(controller, "Succesfully fired employee! :D");
				new EmployeeManagementView();
				this.dispose();
			}
		}
	}

}
