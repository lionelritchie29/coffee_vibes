package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.DatabaseConnection;

public class Employee extends BaseModel{
	private Integer employeeID;
	private Integer positionID;
	private String name;
	private String status;
	private Integer salary;
	private String username;
	private String password;
	
	public Integer getEmployeeID() {
		return employeeID;
	}
	
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}
	
	public Integer getPositionID() {
		return positionID;
	}
	
	public void setPositionID(Integer positionID) {
		this.positionID = positionID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSalary() {
		return salary;
	}
	
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Employee(Integer employeeID, Integer positionID, String name, String status, Integer salary, String username,
			String password) {
		super("employees");
		this.employeeID = employeeID;
		this.positionID = positionID;
		this.name = name;
		this.status = status;
		this.salary = salary;
		this.username = username;
		this.password = password;
	}
	
	public Employee() { super("employees"); }
	
	public Employee getEmployee(String username) {
		String query = String.format("SELECT * FROM %s WHERE username = '%s'", tableName, username);
		return map(db.executeQuery(query));
	}
	
	public Vector<Employee> getAllEmployees() {
		String query = String.format("SELECT * FROM %s", tableName);
		return mapMany(db.executeQuery(query));
	}
	
	public Employee insertEmployee() {
		try {
			String query = String.format(
					"INSERT INTO %s (position_id, name, status, salary, username, password) VALUES (?, ?, ?, ?, ?, ?)", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, positionID);
			ps.setString(2, name);
			ps.setString(3, status);
			ps.setInt(4, salary);
			ps.setString(5, username);
			ps.setString(6, password);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Employee updateEmployee() {
		try {
			String query = String.format(
					"UPDATE %s SET name = ?, salary = ?, username = ?, password = ? WHERE employee_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setInt(2, salary);
			ps.setString(3, username);
			ps.setString(4, password);
			ps.setInt(5, employeeID);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean fireEmployee(int ID) {
		try {
			String query = String.format(
					"DELETE FROM %s WHERE employee_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, ID);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected Employee map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new Employee(
						rs.getInt("employee_id"), 
						rs.getInt("position_id"), 
						rs.getString("name"), 
						rs.getString("status"), 
						rs.getInt("salary"), 
						rs.getString("username"), 
						rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Vector<Employee> mapMany(ResultSet rs) {
		Vector<Employee> emps = new Vector<>();
		
		try {
			while (rs.next()) {
				emps.add(new Employee(
						rs.getInt("employee_id"), 
						rs.getInt("position_id"), 
						rs.getString("name"), 
						rs.getString("status"), 
						rs.getInt("salary"), 
						rs.getString("username"), 
						rs.getString("password")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emps;
	}
	
}
