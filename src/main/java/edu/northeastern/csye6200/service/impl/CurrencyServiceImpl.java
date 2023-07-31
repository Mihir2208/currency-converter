package edu.northeastern.csye6200.service.impl;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.service.CountryFlagService;
import edu.northeastern.csye6200.service.CurrencyService;

public class CurrencyServiceImpl implements CurrencyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

	private static final String CURRENCY_DATA_LOCATION = "/data/currencies.json";
	
	private static final String LINE_PATTERN = "----------------------------------------------";
	private static final String DIAGNOSTIC_HEADER = "Currency flag icon does not exist for following: ";
	private static final String DIAGNOSTIC_SEPERATOR = " - ";
	
	private static final String NEW_LINE_PROPERTY_KEY = "line.separator";
	private static String NEW_LINE;
	
	private static CurrencyService instance;
	private static CountryFlagService countryFlagService;
	
	private List<Currency> currencies;
	private Map<String, Currency> currencyMap;
	
	private CurrencyServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		initNewLineCharacter();
		initCountryFlagService();
		constructAllCurrencies();
	}
	
	public static CurrencyService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CurrencyServiceImpl();
		return instance;
	}
	
	private void initNewLineCharacter() {
		NEW_LINE = System.getProperty(NEW_LINE_PROPERTY_KEY);
		LOGGER.trace("NEW_LINE_PROPERTY_KEY: {}, NEW_LINE: {}", NEW_LINE_PROPERTY_KEY, NEW_LINE);
	}
	
	private void initCountryFlagService() {
		countryFlagService = CountryFlagServiceImpl.getInstance();
	}
	
	private void constructAllCurrencies() {
		LOGGER.debug("constructing all currency objects");
		
		try {
			currencyMap = new HashMap<>();
			currencies = new ArrayList<>();
			
			LOGGER.debug("CURRENCY_DATA_LOCATION: {}", CURRENCY_DATA_LOCATION);
			
			Class<?> clazz = getClass();
	        LOGGER.trace("clazz: {}", clazz);
	        
			URL url = clazz.getResource(CURRENCY_DATA_LOCATION);
	        LOGGER.trace("url: {}", url);
	        
	        URI uri = url.toURI();
	        LOGGER.trace("uri: {}", uri);
	        
			Path filePath = Path.of(uri);
			String content = Files.readString(filePath);
			LOGGER.debug("content: {}", content);
			
			JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
			Set<String> keySet = jsonObject.keySet();
			for (String key: keySet) {
				String name = jsonObject.get(key).getAsString();
				
				String flagIconPath = countryFlagService.getFlagIconPath(key);
				
				Currency currency = new Currency();
				currency.setCountryCode(key);
				currency.setName(name);
				currency.setFlagIconPath(flagIconPath);
				
				currencies.add(currency);
				currencyMap.put(key, currency);
			}
		
			LOGGER.debug("number of currencies created: {}", currencies.size());
		} catch (Exception e) {
			LOGGER.error("Exception in constructAllCurrencies: {}", e.getMessage(), e);
		}
	}
	
	@Override
	public void diagnostic() {
		LOGGER.info("Running diagnostic");
		
		try {
			LOGGER.debug("CURRENCY_DATA_LOCATION: {}", CURRENCY_DATA_LOCATION);
			
			Class<?> clazz = getClass();
	        LOGGER.trace("clazz: {}", clazz);
	        
			URL url = clazz.getResource(CURRENCY_DATA_LOCATION);
	        LOGGER.trace("url: {}", url);
	        
	        URI uri = url.toURI();
	        LOGGER.trace("uri: {}", uri);
	        
			Path filePath = Path.of(uri);
			String content = Files.readString(filePath);
			LOGGER.debug("content: {}", content);
			
			JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
			Set<String> keySet = jsonObject.keySet();
			
			StringBuilder builder = new StringBuilder();
			int missingIconCurrencies = 0;
			
			builder.append(LINE_PATTERN);
			builder.append(NEW_LINE);
			builder.append(DIAGNOSTIC_HEADER);
			for (String key: keySet) {
				String name = jsonObject.get(key).getAsString();
				
				boolean iconExist = countryFlagService.isFlagIconExist(key);
				if (!iconExist) {
					missingIconCurrencies++;
					LOGGER.error("icon does not exist for country: {}", key);
					
					builder.append(NEW_LINE);
					builder.append(key);
					builder.append(DIAGNOSTIC_SEPERATOR);
					builder.append(name);
				}
			}
			
			LOGGER.info("missingIconCurrencies: {}/{}", missingIconCurrencies, keySet.size());

			builder.append(NEW_LINE);
			builder.append(LINE_PATTERN);
			
			LOGGER.info(builder.toString());
		} catch (Exception e) {
			LOGGER.error("Exception in diagnostic: {}", e.getMessage(), e);
		}
	}
	
	@Override
	public List<Currency> getAllCurrencies() {
		LOGGER.trace("currencies size: {}", currencies.size());
		return currencies;
	}
	
	@Override
	public Currency getByCode(String code) {
		Currency currency = currencyMap.get(code);
		return currency;
	}

}
