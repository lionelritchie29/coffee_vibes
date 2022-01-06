package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Product extends BaseModel{

	private Integer productID;
	private String name;
	private String description;
	private Integer price;
	private Integer stock;
	
	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Product(Integer productID, String name, String description, Integer price, Integer stock) {
		super("products");
		this.productID = productID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	
	public Product() { super("products"); }
	
	public Vector<Product> getAllProducts() {
		String query = String.format("SELECT * FROM %s", tableName);
		return mapMany(db.executeQuery(query));
	}
	
	public Product getProduct(int productID) {
		String query = String.format("SELECT * FROM %s WHERE product_id = %d", tableName, productID);
		return map(db.executeQuery(query));
	}
	
	public Product insertNewProduct() {
		try {
			String query = String.format(
					"INSERT INTO %s (name, description, price, stock) VALUES (?, ?, ?, ?)", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setInt(3, price);
			ps.setInt(4, stock);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product updateProduct() {
		try {
			String query = String.format(
					"UPDATE %s SET name = ?, description = ?, price = ? WHERE product_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setInt(3, price);
			ps.setInt(4, productID);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product updateStock() {
		try {
			String query = String.format(
					"UPDATE %s SET stock = ? WHERE product_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, stock);
			ps.setInt(2, productID);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean deleteProduct() {
		try {
			String query = String.format(
					"DELETE FROM %s WHERE product_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, productID);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected Product map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new Product(
						rs.getInt("product_id"), 
						rs.getString("name"), 
						rs.getString("description"), 
						rs.getInt("price"),
						rs.getInt("stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Vector<Product> mapMany(ResultSet rs) {
		Vector<Product> products = new Vector<>();
		
		try {
			while (rs.next()) {
				products.add(new Product(
						rs.getInt("product_id"), 
						rs.getString("name"), 
						rs.getString("description"), 
						rs.getInt("price"),
						rs.getInt("stock")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return products;
	}

}
