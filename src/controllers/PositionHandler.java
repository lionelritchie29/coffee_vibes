package controllers;

import java.util.Vector;

import models.Employee;
import models.Position;

public class PositionHandler extends BaseController{

	private static PositionHandler instance = null;
	
	public static PositionHandler getInstance() {
		if (instance == null) {
			instance = new PositionHandler();
		}
		
		return instance;
	}
	
	private PositionHandler() {
		super();
	}
	
	public Position getPosition(int positionID) {
		Position model = new Position();
		return model.getPosition(positionID);
	}
	
	public Vector<Position> getAllPositions() {
		Position model = new Position();
		return model.getAllPositions();
	}

}
