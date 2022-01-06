package controllers;

import java.util.Vector;

public class BaseController {
	protected Vector<String> errorMessages; 
	
	public BaseController() {
		errorMessages = new Vector<>();
	}
	
	public Vector<String> getErrorMessages() {
		return errorMessages;
	}
	
	public String getFirstErrorMsg() {
		return errorMessages.get(0);
	}
	
	public void clearError() {
		errorMessages.clear();
	}
	
	protected boolean isNumeric(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
