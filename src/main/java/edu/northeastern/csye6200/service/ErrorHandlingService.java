package edu.northeastern.csye6200.service;

public interface ErrorHandlingService {

	/**
	 * Will pass on the Exception Stack trace
	 * to be handled by the Error Box
	 * */
	void handleError(Throwable throwable);
	
	/**
	 * Will pass on the Message
	 * to be handled by the Error Box
	 * */
	void handleMessage(String title, String message);
	
}
