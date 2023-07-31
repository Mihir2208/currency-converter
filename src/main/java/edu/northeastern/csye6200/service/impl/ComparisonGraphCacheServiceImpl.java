package edu.northeastern.csye6200.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.service.ComparisonGraphCacheService;

public class ComparisonGraphCacheServiceImpl implements ComparisonGraphCacheService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonGraphCacheServiceImpl.class);

	private static ComparisonGraphCacheService instance;
	
	private static Map<Integer, String> graphDataCache;
	
	private ComparisonGraphCacheServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		graphDataCache = new HashMap<>();
	}
	
	public static ComparisonGraphCacheService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ComparisonGraphCacheServiceImpl();
		return instance;
	}
	
	@Override
	public void resetCache() {
		graphDataCache.clear();;
	}
	
	@Override
	public void setCache(int index, String value) {
		graphDataCache.put(index, value);
	}
	
	@Override
	public String getCache(int index) {
		String data = graphDataCache.get(index);
		return data;
	}
}
