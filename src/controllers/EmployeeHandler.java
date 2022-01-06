package controllers;

import java.util.Vector;

import models.Employee;
import models.Position;
import views.BaseView;
import views.hrd.EmployeeManagementView;

public class EmployeeHandler extends BaseController{
	private static EmployeeHandler instance = null;
	
	public static EmployeeHandler getInstance() {
		if (instance == null) {
			instance = new EmployeeHandler();
		}
		
		return instance;
	}
	
	private EmployeeHandler() {
		super();
	}
	
	public Employee getEmployee(String username) {
		Employee model = new Employee();
		return model.getEmployee(username);
	}
	
	public Vector<Employee> getAllEmployees() {
		Employee model = new Employee();
		return model.getAllEmployees();
	}
	
	public Employee insertEmployee(String name, Position position, String salary, String username, String password) {
		if (name.isEmpty() || salary.isEmpty() || username.isEmpty() || salary.isEmpty() || password.isEmpty()) {
			errorMessages.add("All field must be fillled!");
			return null;
		} else if (!isNumeric(salary)) {
			errorMessages.add("Salary must be numeric!");
			return null;
		} else if (Integer.parseInt(salary) < 1) {
			errorMessages.add("Salary must be greater than 1!");
			return null;
		}
		
		Employee model = new Employee(-1, position.getPositionID(), name, "ACTIVE", Integer.parseInt(salary), username, password);
		Employee emp = model.insertEmployee();
		
		if (emp == null) {
			errorMessages.add("Failed when inserting employee to the database :/");
			return null;
		}
		
		return emp;
	}
	
	public Employee updateEmployee(int employeeID, String name, String salary, String username, String password) {
		if (name.isEmpty() || salary.isEmpty() || username.isEmpty() || salary.isEmpty() || password.isEmpty()) {
			errorMessages.add("All field must be fillled!");
			return null;
		} else if (!isNumeric(salary)) {
			errorMessages.add("Salary must be numeric!");
			return null;
		} else if (Integer.parseInt(salary) < 1) {
			errorMessages.add("Salary must be greater than 1!");
			return null;
		}
		
		Employee model = new Employee();
		model.setEmployeeID(employeeID);
		model.setName(name);
		model.setSalary(Integer.parseInt(salary));
		model.setUsername(username);
		model.setPassword(password);
		
		Employee emp = model.updateEmployee();
		
		if (emp == null) {
			errorMessages.add("Failed when updating employee to the database :/");
			return null;
		}
		
		return emp;
	}
	
	public boolean fireEmployee(int ID) {
		Employee model = new Employee();
		
		if (!model.fireEmployee(ID)) {
			errorMessages.add("Failed when deleting employee from database :/");
			return false;
		}
		
		return true;
	}
	
	public boolean deleteEmployee(int employeeID) {
		Employee model = new Employee();
		
		if (!model.fireEmployee(employeeID)) {
			errorMessages.add("Failed when deleting employee from database :/");
			return false;
		}
		
		return true;
	}
	
	public BaseView viewEmployeeManagementForm() {
		return new EmployeeManagementView();
	}
	
	public views.manager.EmployeeManagementView viewEmployeeManagerManagementView() {
		return new  views.manager.EmployeeManagementView();
	}
}
