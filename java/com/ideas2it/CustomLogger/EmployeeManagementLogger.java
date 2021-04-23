package com.ideas2it.CustomLogger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Logger for Employee management project
 * @author Sathvika
 *
 */
public class EmployeeManagementLogger {
	private Logger logger;
	
	/**
	 * Creating logger object for a class
	 * @param className name of class
	 */
	public EmployeeManagementLogger(Class<?> className) {
		logger = LogManager.getLogger(className);
	}
	
	/**
	 * Logs INFO 
	 * @param info object of exception class
	 */
	public void logInfo(Object info) {
		logger.info(info);
	}	
	
	/**
	 * Logs Error
	 *@param info object of exception class
	 */
	public void logError(Object info) {
		logger.error(info);
	}

}

