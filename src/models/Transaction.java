package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Vector;

public class Transaction extends BaseModel {
	private Integer transactionID;
	private Date purchaseDate;
	private Integer voucherID;
	private Integer employeeID;
	private Integer totalPrice;
	private Vector<TransactionItem> listTransactionItem;

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(Integer voucherID) {
		this.voucherID = voucherID;
	}

	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Vector<TransactionItem> getListTransactionItem() {
		return listTransactionItem;
	}

	public void setListTransactionItem(Vector<TransactionItem> listTransactionItem) {
		this.listTransactionItem = listTransactionItem;
	}

	public Transaction(Integer transactionID, Date purchaseDate, Integer voucherID, Integer employeeID,
			Integer totalPrice) {
		super("transactions");
		this.transactionID = transactionID;
		this.purchaseDate = purchaseDate;
		this.voucherID = voucherID;
		this.employeeID = employeeID;
		this.totalPrice = totalPrice;
	}

	public Transaction() {
		super("transactions");
	}

	public Transaction insertNewTransaction() {
		try {
			String query = "";
			PreparedStatement ps;
			if (voucherID == 0) {
				query = String.format("INSERT INTO %s (purchase_date, employee_id, total_price) VALUES (?, ?, ?)",
						tableName);
				ps = db.getConnection().prepareStatement(query);
				ps.setInt(2, employeeID);
				ps.setInt(3, totalPrice);

			} else {
				query = String.format(
						"INSERT INTO %s (purchase_date, voucher_id, employee_id, total_price) VALUES (?, ?, ?, ?)",
						tableName);
				ps = db.getConnection().prepareStatement(query);
				ps.setInt(2, voucherID);
				ps.setInt(3, employeeID);
				ps.setInt(4, totalPrice);
			}

			ps.setDate(1, purchaseDate);
			ps.execute();

			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public int getLastTransactionID() {
		String query = String.format("SELECT * FROM %s ORDER BY transaction_id DESC LIMIT 1", tableName);
		Transaction tran = map(db.executeQuery(query));
		return tran.getTransactionID();
	}

	@Override
	protected Transaction map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new Transaction(rs.getInt("transaction_id"), rs.getDate("purchase_date"),
						rs.getInt("voucher_id"), rs.getInt("employee_id"), rs.getInt("total_price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<Transaction> getAllTransactions() {
		String query = String.format("SELECT * FROM %s", tableName);
		return mapMany(db.executeQuery(query));
	}

	public Transaction getTransaction(int transactionID) {
		String query = String.format("SELECT * FROM %s WHERE transaction_id = %d", tableName, transactionID);
		return map(db.executeQuery(query));
	}

	@Override
	protected Vector<Transaction> mapMany(ResultSet rs) {
		Vector<Transaction> transactions = new Vector<>();

		try {
			while (rs.next()) {
				transactions.add(new Transaction(rs.getInt("transaction_id"), rs.getDate("purchase_date"),
						rs.getInt("voucher_id"), rs.getInt("employee_id"), rs.getInt("total_price")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactions;
	}

}
