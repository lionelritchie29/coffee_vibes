package controllers;

import models.Employee;

public class AuthController extends BaseController {
	private static AuthController instance = null;
	private Employee employee;

	public static AuthController getInstance() {
		if (instance == null) {
			instance = new AuthController();
		}

		return instance;
	}

	private AuthController() {
		super();
	}

	public Employee Login(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			errorMessages.add("Username and password must not be empty!");
			return null;
		}

		Employee emp = EmployeeHandler.getInstance().getEmployee(username);
		if (emp == null) {
			errorMessages.add("Username does not exist!");
			return null;
		}

		if (!emp.getPassword().equals(password)) {
			errorMessages.add("Wrong combination of username and password!");
			return null;
		}

		return emp;
	}
	
	public void setUser(Employee employee) {
		this.employee = employee;
	}
	
	public Employee getUser() {
		return this.employee;
	}
}
