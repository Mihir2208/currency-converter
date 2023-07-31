package edu.northeastern.csye6200.service.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.ExchangeRate;
import edu.northeastern.csye6200.service.ExchangeRateService;
import edu.northeastern.csye6200.util.TimestampUtil;

public class ExchangeRateServiceImpl implements ExchangeRateService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

	private static ExchangeRateService instance;
	
	private ExchangeRateServiceImpl() {
		LOGGER.debug("Initiliasing the service");
	}
	
	public static ExchangeRateService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ExchangeRateServiceImpl();
		return instance;
	}
	
	@Override
	public ExchangeRate adapt(
			float value, 
			Currency fromCurrency, 
			Currency toCurrency, 
			String response,
			String dateString,
			LocalDate date) throws ParseException {
		String toCurrencyCode = toCurrency.getCountryCode();
		
		JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
		float rate = jsonObject.get(toCurrencyCode).getAsFloat();
		float convertedValue = rate * value;

		long timestamp = TimestampUtil.getTimestampFromDateString(dateString);
		
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setFromCurrency(fromCurrency);
		exchangeRate.setToCurrency(toCurrency);
		exchangeRate.setRate(rate);
		exchangeRate.setValue(value);
		exchangeRate.setConvertedValue(convertedValue);
		exchangeRate.setTimestamp(timestamp);
		exchangeRate.setDateString(dateString);
		
		return exchangeRate;
	}
	
	@Override
	public List<ExchangeRate> adaptMultiple(
			float value, 
			Currency baseCurrency, 
			List<Currency> comparisonCurrencies,
			String response, 
			String dateString,
			LocalDate date) throws ParseException {
		String baseCurrencyCode = baseCurrency.getCountryCode();
		
		JsonObject outterJsonObject = new JsonParser().parse(response).getAsJsonObject();
		JsonObject jsonObject = outterJsonObject.getAsJsonObject(baseCurrencyCode);
		Set<String> keySet = jsonObject.keySet();
		
		List<ExchangeRate> exchangeRates = new ArrayList<>();
		
		for (Currency currency : comparisonCurrencies) {
			String code = currency.getCountryCode();
			if (!keySet.contains(code)) {
				continue;
			}
			
			float rate = jsonObject.get(code).getAsFloat();
			float convertedValue = rate * value;

			long timestamp = TimestampUtil.getTimestampFromDateString(dateString);
			
			ExchangeRate exchangeRate = new ExchangeRate();
			exchangeRate.setFromCurrency(baseCurrency);
			exchangeRate.setToCurrency(currency);
			exchangeRate.setRate(rate);
			exchangeRate.setValue(value);
			exchangeRate.setConvertedValue(convertedValue);
			exchangeRate.setDate(date);
			exchangeRate.setTimestamp(timestamp);
			exchangeRate.setDateString(dateString);
			
			exchangeRates.add(exchangeRate);
		}
		
		return exchangeRates;
	}

}
