package edu.northeastern.csye6200.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.CurrencyCompareRequest;
import edu.northeastern.csye6200.service.CurrencyAPICacheService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.MonitorService;

public class MonitorServiceImpl implements MonitorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceImpl.class);

	private static MonitorService instance;

	private CurrencyAPICacheService currencyAPICacheService;
	private ErrorHandlingService errorHandlingService;
	
	private static final String MONITOR_FILE_LOCATION = "./monitor.txt";
	
	private static final String NEW_LINE_PROPERTY_KEY = "line.separator";
	private static String NEW_LINE;
	
	private static final int NEWLINE_GAP = 5;
	private static final String LINE_PATTERN = "----------------------------------------------";
	private static final String NAME_HOLDER = "NAME: ";
	private static final String DETAILS_HOLDER = "DETAILS: ";
	private static final String START_TIMESTAMP_HOLDER = "Start Timestamp: ";
	private static final String END_TIMESTAMP_HOLDER = "End Timestamp: ";
	private static final String TIME_TAKEN_HOLDER = "Time Taken (in milliseconds): ";
	private static final String CACHE_ENABLED_HOLDER = "Cache Enabled: ";
	
	private static final String AMOUNT = "Amount: ";
	private static final String FROM_DATE = "From Date: ";
	private static final String TO_DATE = "To Date: ";
	private static final String BASE_CURRENCY = "Base Currency: ";
	private static final String COMPARISON_CURRENCIES = "Comparison Currencies: ";

	private String name;
	private String details;
	
	private long startTimestamp;
	private long endTimestamp;
	
	private MonitorServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		
		currencyAPICacheService = CurrencyAPICacheServiceImpl.getInstance();
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		
		initNewLineCharacter();
	}
	
	public static MonitorService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new MonitorServiceImpl();
		return instance;
	}
	
	private void initNewLineCharacter() {
		NEW_LINE = System.getProperty(NEW_LINE_PROPERTY_KEY);
		LOGGER.trace("NEW_LINE_PROPERTY_KEY: {}, NEW_LINE: {}", NEW_LINE_PROPERTY_KEY, NEW_LINE);
	}
	
	@Override
	public void setCurrencyComparisonOperationDeatils(CurrencyCompareRequest request) {
		LOGGER.trace("building operation details for request: {}", request);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(AMOUNT);
		stringBuilder.append(request.getAmount());
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(FROM_DATE);
		stringBuilder.append(request.getFromDate());
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(TO_DATE);
		stringBuilder.append(request.getToDate());
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(BASE_CURRENCY);
		stringBuilder.append(request.getBaseCurrency());
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(COMPARISON_CURRENCIES);
		List<Currency> comparisonCurrencies = request.getComparisonCurrencies();
		for (Currency currency : comparisonCurrencies) {
			stringBuilder.append(NEW_LINE);
			stringBuilder.append( currency);
		}
		
		setOperationDetails(stringBuilder.toString());
	}
	
	@Override
	public void setOperationName(String name) {
		LOGGER.trace("Setting the name: {}", name);
		this.name = name;
	}

	@Override
	public void setOperationDetails(String details) {
		LOGGER.trace("Setting the details: {}", details);
		this.details = details;
	}

	@Override
	public void start() {
		LOGGER.trace("starting the operation");
		this.startTimestamp = System.currentTimeMillis();
	}

	@Override
	public void stop() {
		LOGGER.trace("stopping the operation");
		this.endTimestamp = System.currentTimeMillis();
		
		logDetails();
		
		reset();
	}
	
	private void logDetails() {
		LOGGER.trace("logging all the details");
		
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < NEWLINE_GAP; ++i) {
			stringBuilder.append(NEW_LINE);
		}
		
		stringBuilder.append(LINE_PATTERN);
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(NAME_HOLDER);
		stringBuilder.append(name);
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(DETAILS_HOLDER);
		stringBuilder.append(details);
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(START_TIMESTAMP_HOLDER);
		stringBuilder.append(startTimestamp);
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(END_TIMESTAMP_HOLDER);
		stringBuilder.append(endTimestamp);
		stringBuilder.append(NEW_LINE);
		
		long millsecondsTaken = endTimestamp - startTimestamp;
		stringBuilder.append(TIME_TAKEN_HOLDER);
		stringBuilder.append(millsecondsTaken);
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(CACHE_ENABLED_HOLDER);
		stringBuilder.append(currencyAPICacheService.isEnabled());
		stringBuilder.append(NEW_LINE);
		
		stringBuilder.append(LINE_PATTERN);
		
		for (int i = 0; i < NEWLINE_GAP; ++i) {
			stringBuilder.append(NEW_LINE);
		}
		
		LOGGER.trace("Logging details: {}", stringBuilder);
		
		try(
				FileWriter fileWriter = new FileWriter(MONITOR_FILE_LOCATION, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
			printWriter.println(stringBuilder);
		} catch (Exception e) {
			LOGGER.error("Exception while writing monitor logs: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
	
	private void reset() {
		LOGGER.trace("resetting all details");
		name = null;
		details = null;
		startTimestamp = 0;
		endTimestamp = 0;
	}

}
