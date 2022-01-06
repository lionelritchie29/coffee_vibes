package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Position extends BaseModel{
	private Integer positionID;
	private String name;
	
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
	
	public Position(Integer positionID, String name) {
		super("positions");
		this.positionID = positionID;
		this.name = name;
	}
	
	public Position() { super("positions"); }
	
	public Vector<Position> getAllPositions() {
		String query = String.format("SELECT * FROM %s", tableName);
		return mapMany(db.executeQuery(query));
	}
	
	public Position getPosition(int positionID) {
		String query = String.format("SELECT * FROM %s WHERE position_id = '%s'", tableName, positionID);
		return map(db.executeQuery(query));
	}

	@Override
	protected Position map(ResultSet rs) {
		try {
			if (rs.first()) {
				return new Position(
						rs.getInt("position_id"), 
						rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Vector<Position> mapMany(ResultSet rs) {
		Vector<Position> positions = new Vector<>();
		
		try {
			while (rs.next()) {
				positions.add(new Position(
						rs.getInt("position_id"), 
						rs.getString("name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return positions;
	}
	
}
