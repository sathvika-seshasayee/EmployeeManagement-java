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
	public EmployeeManagementException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmployeeManagementException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EmployeeManagementException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmployeeManagementException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmployeeManagementException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
