package edu.northeastern.csye6200.service;

import edu.northeastern.csye6200.model.CurrencyCompareRequest;

/**
 * Using to monitor time taken by operations
 * */
public interface MonitorService {

	String OPERATION_CURRENCY_COMPARE = "Currency Comparison";
	
	/**
	 * Will set the main operation name
	 * */
	void setOperationName(String name);
	

	/**
	 * Will build the details according
	 * to the given {@link CurrencyCompareRequest}
	 * */
	void setCurrencyComparisonOperationDeatils(CurrencyCompareRequest request);
	
	/**
	 * Will set further details related to 
	 * the operation
	 * */
	void setOperationDetails(String details);
	
	/**
	 * Will start the operation
	 * */
	void start();
	
	/**
	 * Will stop the operation and write all operation
	 * related details into a file
	 * */
	void stop();
	
}
