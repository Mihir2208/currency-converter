package edu.northeastern.csye6200.service;

public interface CurrencyAPICacheService {

	String CACHE_NULL_ENTRY = "NULL";
	
	/**
	 * Will set the date in the Cache
	 * */
	void set(String key, String data);
	
	/**
	 * Will get the data from the cache
	 * */
	String get(String key);
	
	/**
	 * Will return the size of the
	 * cache
	 * */
	int getCacheSize();
	
	/**
	 * Will save the cache on local file system.
	 * 
	 * <br><br>
	 * 
	 * Will be used by service to build the cache
	 * again on application restart
	 * */
	void saveCache();
	
	/**
	 * Will rebuild the cache from the 
	 * local cache saved in memory
	 * 
	 * */
	void rebuildCache();
	
	/**
	 * Will set the enable flag of the cache.
	 * 
	 *  <br><br>
	 *  
	 *  If it is true than cache will be used
	 *  
	 *  <br><br>
	 *  
	 *  If it is false then cache will not be used
	 * */
	void setEnabled(boolean flag);
	
	/**
	 * Will tell if cache is enabled or not.
	 * 
	 * <br><br>
	 * 
	 * Will return true it enabled otherwise will
	 * return false
	 * */
	boolean isEnabled();
}
