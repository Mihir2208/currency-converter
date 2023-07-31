package edu.northeastern.csye6200.service;

import edu.northeastern.csye6200.model.CurrencyCompareResponse;

public interface ComparisonGraphService {

	/**
	 * Will show the Progress Window
	 * */
	void show();
	
	/**
	 * Will close the Progress Window
	 * and hide all the progress related information
	 * */
	void close();
	
	/**
	 * Will draw the graph using the 
	 * given {@link CurrencyCompareResponse}
	 * */
	void drawGraph(CurrencyCompareResponse response);
	
}
