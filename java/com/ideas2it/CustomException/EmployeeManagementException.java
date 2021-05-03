/**
 * 
 */
package com.ideas2it.CustomException;

/**
 * @author Sathvika Seshasayee
 *
 */
public class EmployeeManagementException extends Exception {

	/**
	 * Constructor
	 */
	public EmployeeManagementException() {}

	/**
	 * This method sets error message to object
	 * @param message
	 */
	public EmployeeManagementException(String message) {
		super(message);
	}

	/**
	 * Sets a new throwable object  
	 * @param cause
	 */
	public EmployeeManagementException(Throwable cause) {
		super(cause);
	}

	/**
	 * This method sets error message and throwable (errors and exceptions) to object
	 * @param message
	 * @param cause
	 */
	public EmployeeManagementException(String message, Throwable cause) {
		super(message, cause);
	}

}
