package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TransactionItem extends BaseModel {

	private Integer transactionID;
	private Integer productID;
	private Integer quantity;

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public TransactionItem(Integer transactionID, Integer productID, Integer quantity) {
		super("transaction_items");
		this.transactionID = transactionID;
		this.productID = productID;
		this.quantity = quantity;
	}
	
	public TransactionItem() {
		super("transaction_items");
	}

	public TransactionItem insertNewTransactionItem() {
		try {
			String query = String.format("INSERT INTO %s (transaction_id, product_id, quantity) VALUES (?, ?, ?)",
					tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);

			ps.setInt(1, transactionID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.execute();

			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Vector<TransactionItem> getAllTransactionItems(int transactionID) {
		String query = String.format("SELECT * FROM %s WHERE transaction_id = %d", tableName, transactionID);
		return mapMany(db.executeQuery(query));
	}

	@Override
	protected TransactionItem map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new TransactionItem(rs.getInt("transaction_id"), rs.getInt("product_id"), rs.getInt("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Vector<TransactionItem> mapMany(ResultSet rs) {
		Vector<TransactionItem> transactionItems = new Vector<>();

		try {
			while (rs.next()) {
				transactionItems.add(new TransactionItem(rs.getInt("transaction_id"), rs.getInt("product_id"),
						rs.getInt("quantity")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return transactionItems;
	}
}
