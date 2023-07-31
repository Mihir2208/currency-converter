package edu.northeastern.csye6200.service;

public interface ProgressBarService {

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
	 * Will update the total
	 * number of steps in the progress
	 * */
	void updateProgressTotal(int total);
	
	/**
	 * Will update the progress
	 * */
	void updateProgress(int progress);
	
	/**
	 * Will increase the progress by one
	 * */
	void incrementProgress();
}
