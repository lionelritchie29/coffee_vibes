package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import database.DatabaseConnection;

public abstract class BaseModel {
	protected String tableName;
	protected DatabaseConnection db;

	public BaseModel(String tableName) {
		super();
		this.tableName = tableName;
		db = DatabaseConnection.getInstance();
	}

	protected abstract <T> T map(ResultSet rs);
	protected abstract <T> Vector<T> mapMany(ResultSet rs);
}
