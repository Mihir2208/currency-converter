package edu.northeastern.csye6200.service.impl;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.service.CountryFlagService;

public class CountryFlagServiceImpl implements CountryFlagService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryFlagServiceImpl.class);

	private static final String ICONS_PATH_TEMPLATE = "/icons/rectangular-flags/%s.png";
	private static final String RECTANGULAR_FALLBACK_PATH = "/icons/rectangle_fallback.png";
	
	private static CountryFlagService instance;
	
	private CountryFlagServiceImpl() {
		LOGGER.debug("Initiliasing the service");
	}
	
	public static CountryFlagService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CountryFlagServiceImpl();
		return instance;
	}
	
	@Override
	public String getFlagIconPath(String countryCode) {
		LOGGER.trace("will get the icon path for countryCode: {}", countryCode);
		
		String pathString = String.format(ICONS_PATH_TEMPLATE, countryCode);
		
		Class<?> clazz = getClass();
        LOGGER.trace("clazz: {}", clazz);
        
		URL url = clazz.getResource(pathString);
        LOGGER.trace("url: {}", url);
        
        if (url == null) {
        	LOGGER.error("Could not find the URL ICON for countryCode: {}, path: {}", countryCode, pathString);
			LOGGER.trace("for countryCode: {}, will use the fallback", countryCode, RECTANGULAR_FALLBACK_PATH);
			return RECTANGULAR_FALLBACK_PATH;
        }
        
        try {
        	URI uri = url.toURI();
            LOGGER.trace("uri: {}", uri);
    		Path filePath = Path.of(uri);
    		boolean fileExists = Files.exists(filePath);
    		if (!fileExists) {
    			LOGGER.error("Could not find the icon for countryCode: {}, path: {}", countryCode, pathString);
    			LOGGER.trace("for countryCode: {}, will use the fallback", countryCode, RECTANGULAR_FALLBACK_PATH);
    			pathString = RECTANGULAR_FALLBACK_PATH;
    		}
    		
    		LOGGER.debug("countryCode: {}, path: {}", countryCode, pathString);
		} catch (Exception e) {
			LOGGER.error("Exception in getFlagIconPath(): {}", e.getMessage(), e);
		}
        
		return pathString;
	}

	@Override
	public boolean isFlagIconExist(String countryCode) {
		LOGGER.trace("checking if flag exists for countryCode: {}", countryCode);
		
		String pathString = String.format(ICONS_PATH_TEMPLATE, countryCode);
		
		Class<?> clazz = getClass();
        LOGGER.trace("clazz: {}", clazz);
        
		URL url = clazz.getResource(pathString);
        LOGGER.trace("url: {}", url);
        
        if (url == null) {
        	LOGGER.error("Could not find the URL ICON for countryCode: {}, path: {}", countryCode, pathString);
			LOGGER.trace("URL doesn't exist for countryCode: {}", countryCode);
			return false;
        }
        
        boolean flagExists = true;
        
        try {
        	URI uri = url.toURI();
            LOGGER.trace("uri: {}", uri);
    		Path filePath = Path.of(uri);
    		boolean fileExists = Files.exists(filePath);
    		if (!fileExists) {
    			LOGGER.error("Could not find the icon for countryCode: {}, path: {}", countryCode, pathString);
    			LOGGER.trace("File doesn't exist for countryCode: {}", countryCode);
    		}
		} catch (Exception e) {
			LOGGER.error("Exception in getFlagIconPath(): {}", e.getMessage(), e);
		}
        
		return flagExists;
	}

}
