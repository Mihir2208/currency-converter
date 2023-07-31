package edu.northeastern.csye6200.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.ExchangeRate;

public interface ExchangeRateService {

	ExchangeRate adapt(
			float value, 
			Currency fromCurrency, 
			Currency toCurrency, 
			String response,
			String dateString,
			LocalDate date) throws ParseException;
	
	List<ExchangeRate> adaptMultiple(
			float value,
			Currency baseCurrency,
			List<Currency> comparisonCurrencies,
			String response,
			String dateString,
			LocalDate date) throws ParseException;
	
}
