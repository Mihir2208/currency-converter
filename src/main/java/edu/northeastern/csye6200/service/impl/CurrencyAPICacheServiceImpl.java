package edu.northeastern.csye6200.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.service.CurrencyAPICacheService;
import edu.northeastern.csye6200.service.ErrorHandlingService;

public class CurrencyAPICacheServiceImpl implements CurrencyAPICacheService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyAPICacheServiceImpl.class);

	private static final String CACHE_FILE_PATH = "./cache.ser";
	
	private ErrorHandlingService errorHandlingService;
	
	private static CurrencyAPICacheService instance;
	
	private Map<String, String> cache;
	private boolean enable;
	
	private CurrencyAPICacheServiceImpl() {
		LOGGER.debug("Initializing Combo Box Service");
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		cache = new HashMap<>();
		enable = true;
	}

	public static CurrencyAPICacheService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CurrencyAPICacheServiceImpl();
		return instance;
	}

	@Override
	public void set(String key, String data) {
		// Don't log the data, it's too verbose
		LOGGER.trace("setting value in cache for key: {}", key);
		
		if (!enable) {
			// Ideally this should not happen for good performance, that's why warning in logs
			LOGGER.warn("Cache flag is disabled will not set value in cache for key: {}", key);
			return;
		}
		
		cache.put(key, data);
	}

	@Override
	public String get(String key) {
		// Don't log the data, it's too verbose
		LOGGER.trace("gettng value from cache for key: {}", key);
		
		if (!enable) {
			// Ideally this should not happen for good performance, that's why warning in logs
			LOGGER.warn("Cache flag is disabled will not get value in cache for key: {}", key);
			return null;
		}
		
		String data = cache.get(key);
		return data;
	}
	
	@Override
	public int getCacheSize() {
		int size = cache.size();
		LOGGER.trace("cache size is: {}", size);
		return size;
	}
	
	@Override
	public void saveCache() {
		LOGGER.info("Saving the cache");
		
		File file = new File(CACHE_FILE_PATH);
		boolean fileExists = file.exists();
		LOGGER.trace("CACHE_FILE_PATH: {}, fileExists: {}", CACHE_FILE_PATH, fileExists);
        if (!fileExists) {
        	LOGGER.info("cache file doesn't exist will create it");
        	try {
				file.createNewFile();
			} catch (Exception e) {
				LOGGER.error("Exception in creating cache file: {}", e.getMessage(), e);
			}
        } else {
        	LOGGER.info("Cache file already exists, will use it to save the data");
        }
		
		try (
				FileOutputStream fileOutputStream = new FileOutputStream(CACHE_FILE_PATH);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			objectOutputStream.writeObject(cache);
		} catch (Exception e) {
			LOGGER.error("Exception occured while saving the cache: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
		
		LOGGER.info("Cache has been saved successfully");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void rebuildCache() {
		LOGGER.info("rebuilding the cache");
		
		Path path = Paths.get(CACHE_FILE_PATH);
		boolean fileExists = Files.exists(path);
		LOGGER.trace("CACHE_FILE_PATH: {}, fileExists: {}", CACHE_FILE_PATH, fileExists);
		if (!fileExists) {
			LOGGER.info("File does not exist so can't rebuild the cache");
			return;
		}
		
		try (
				FileInputStream fileInputStream = new FileInputStream(CACHE_FILE_PATH);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
				
			cache = (Map<String, String>) objectInputStream.readObject();
			LOGGER.info("Finished rebuilding cache with size: {}", cache.size());
		} catch (Exception e) {
			LOGGER.error("Exception occured while saving the cache: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
	
	@Override
	public void setEnabled(boolean flag) {
		this.enable = flag;
		LOGGER.info("updated enabled flag to: {}", this.enable);
	}
	
	@Override
	public boolean isEnabled() {
		LOGGER.trace("cache enable: {}", enable);
		return enable;
	}
}
