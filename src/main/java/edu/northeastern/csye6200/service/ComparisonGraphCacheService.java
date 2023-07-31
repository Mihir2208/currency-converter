package edu.northeastern.csye6200.service;

public interface ComparisonGraphCacheService {

	/**
	 * Will reset the cache
	 * */
	void resetCache();

	/**
	 * Will set the value in the cache
	 * */
	void setCache(int index, String value);

	/**
	 * Will get the value from the cache
	 * */
	String getCache(int index);
}
