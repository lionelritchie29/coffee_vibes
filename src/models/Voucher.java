package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Voucher extends BaseModel{
	private Integer voucherID;
	private Integer discount;
	private String status;
	
	public Integer getVoucherID() {
		return voucherID;
	}
	
	public void setVoucherID(Integer voucherID) {
		this.voucherID = voucherID;
	}
	
	public Integer getDiscount() {
		return discount;
	}
	
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Voucher(Integer voucherID, Integer discount, String status) {
		super("vouchers");
		this.voucherID = voucherID;
		this.discount = discount;
		this.status = status;
	}
	
	public Voucher() { super("vouchers"); }
	
	public Voucher generateVoucher() {
		try {
			String query = String.format(
					"INSERT INTO %s (voucher_id, discount, status) VALUES (?, ?, ?)", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, voucherID);
			ps.setInt(2, discount);
			ps.setString(3, status);
			ps.execute();
			
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Voucher getVoucher(int voucherID) {
		String query = String.format("SELECT * FROM %s WHERE voucher_id = %d", tableName, voucherID);
		return map(db.executeQuery(query));
	}
	
	public Vector<Voucher> getAllVouchers() {
		String query = String.format("SELECT * FROM %s", tableName);
		return mapMany(db.executeQuery(query));
	}
	
	public boolean deleteVoucher(int voucherID) {
		try {
			String query = String.format(
					"DELETE FROM %s WHERE voucher_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, voucherID);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateVoucher(int voucherID) {
		try {
			String query = String.format(
					"UPDATE %s SET discount = ?, status = ? WHERE voucher_id = ?", tableName);
			PreparedStatement ps = db.getConnection().prepareStatement(query);
			
			ps.setInt(1, discount);
			ps.setString(2, status);
			ps.setInt(3, voucherID);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected Voucher map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new Voucher(
						rs.getInt("voucher_id"), 
						rs.getInt("discount"), 
						rs.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Vector<Voucher> mapMany(ResultSet rs) {
		Vector<Voucher> v = new Vector<>();
		
		try {
			while (rs.next()) {
				v.add(new Voucher(
						rs.getInt("voucher_id"), 
						rs.getInt("discount"), 
						rs.getString("status")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return v;
	}	
	
}
