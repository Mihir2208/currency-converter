package edu.northeastern.csye6200.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.ExchangeRate;

public interface CurrencyAPIService {

	/**
	 * Will get the latest exchange rate
	 * between the given {@link Currency}
	 * parameters.
	 * */
	ExchangeRate getLatestExchangeRate(
			Currency fromCurrency,
			Currency toCurrency,
			float amount)  throws UnsupportedOperationException, IOException, ParseException;
	
	/**
	 * Will get the exchange rate
	 * between the given {@link Currency}
	 * with specified date
	 * parameters.
	 * */
	ExchangeRate getExchangeRate(
			Currency fromCurrency,
			Currency toCurrency,
			LocalDate date,
			float amount)  throws UnsupportedOperationException, IOException, ParseException;
	
	/**
	 * Will get value of all {@link Currency}
	 * against a Base {@link Currency} for given 
	 * date and amount 
	 * @throws IOException 
	 * @throws UnsupportedOperationException 
	 * */
	List<ExchangeRate> getMultipleCurrencies(
			Currency baseCurrency,
			List<Currency> comparisonCurrencies,
			LocalDate date,
			float amount) throws UnsupportedOperationException, IOException, ParseException;
	
	/**
	 * Will simply call the given URL with a GET Request
	 * 
	 * @return The String response received by Apache HTTP
	 * Client
	 * */
	String callApiUrl(String url) throws UnsupportedOperationException, IOException;
	
}
