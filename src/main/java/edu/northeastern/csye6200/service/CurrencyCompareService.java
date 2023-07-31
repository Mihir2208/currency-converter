package edu.northeastern.csye6200.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.CurrencyCompareRequest;
import edu.northeastern.csye6200.model.CurrencyCompareResponse;
import edu.northeastern.csye6200.model.ExchangeRate;
import javafx.application.Platform;

public interface CurrencyCompareService {
	
	/**
	 * Will create the {@link CurrencyCompareRequest} according
	 * to the given parameters
	 * */
	CurrencyCompareRequest createRequest(
			LocalDate fromDate,
			LocalDate toDate,
			Currency baseCurrency,
			List<Currency> comparisonCurrencies,
			float amount);
	
	/**
	 * Will create the {@link CurrencyCompareResponse} according to
	 * the given parameters
	 * */
	CurrencyCompareResponse createResponse(
			LocalDate fromDate,
			LocalDate toDate,
			Currency baseCurrency,
			Map<String, List<ExchangeRate>> data);
	
	/**
	 * 
	 * Any heavy processing on the JavaFXApplication
	 * thread can cause issues so it's always better
	 * to delegate heavy processing tasks like batch API calls
	 * to a separate thread or task.
	 * 
	 * <br><br>
	 * 
	 * Will compute the {@link CurrencyCompareResponse} for
	 * the given {@link CurrencyCompareRequest} by delegating
	 * all the calls to a separate thread.
	 * 
	 * <br><br>
	 * 
	 * {@link ProgressBarService} will be updated
	 * using {@link Platform#runLater(Runnable)}
	 * callback
	 * 
	 * <br><br>
	 * 
	 * TODO Need to forward the {@link CurrencyCompareResponse} to
	 * a separate Graph Service in an async manner too.
	 * 
	 * */
	void computeAsync(CurrencyCompareRequest request);
	
}
