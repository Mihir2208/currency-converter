package edu.northeastern.csye6200.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.ExchangeRate;
import edu.northeastern.csye6200.service.CurrencyAPICacheService;
import edu.northeastern.csye6200.service.CurrencyAPIService;
import edu.northeastern.csye6200.service.ExchangeRateService;

public class CurrencyAPIServiceImpl implements CurrencyAPIService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyAPIServiceImpl.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static CurrencyAPIService instance;

	// https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/<YYYY-MM>-DD>/currencies/<FROM-Currency-code>/<To-Currency-code>.json
	private static final String API_URL_TEMPLATE = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/%s/currencies/%s/%s.min.json";
	
	// https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/<YYYY-MM>-DD>/currencies/<Base-currency-code>.json
	private static final String COMPARE_API_URL_TEMPLATE = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/%s/currencies/%s.min.json";
	
	private ExchangeRateService exchangeRateService;
	private CurrencyAPICacheService currencyAPICacheService;
	
	private CurrencyAPIServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		
		exchangeRateService = ExchangeRateServiceImpl.getInstance();
		currencyAPICacheService = CurrencyAPICacheServiceImpl.getInstance();
	}
	
	public static CurrencyAPIService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CurrencyAPIServiceImpl();
		return instance;
	}
	
	@Override
	public ExchangeRate getLatestExchangeRate(
			Currency fromCurrency, 
			Currency toCurrency, 
			float amount) throws UnsupportedOperationException, IOException, ParseException {
		LOGGER.trace(
				"getting the latest exchange rate from: {}, to: {}, amount: {}",
				fromCurrency,
				toCurrency,
				amount);
		
		LocalDate date = LocalDate.now();
		
		String dateString = DateTimeFormatter.ofPattern(DATE_FORMAT).format(date);
		String fromCountryCode = fromCurrency.getCountryCode();
		String toCountryCode = toCurrency.getCountryCode();
		
		String apiUrl = String.format(
				API_URL_TEMPLATE, 
				dateString, 
				fromCountryCode, 
				toCountryCode);
		LOGGER.trace("apiUrl: {}", apiUrl);
		
		String responseString = callApiUrl(apiUrl);
		LOGGER.trace("responseString: {}", responseString);
		
		ExchangeRate exchangeRate = exchangeRateService.adapt(
				amount,
				fromCurrency, 
				toCurrency, 
				responseString, 
				dateString,
				date);
		
		return exchangeRate;
	}

	@Override
	public ExchangeRate getExchangeRate(
			Currency fromCurrency, 
			Currency toCurrency, 
			LocalDate date, 
			float amount)
			throws UnsupportedOperationException, IOException, ParseException {
		LOGGER.trace(
				"getting the historical exchange rate from: {}, to: {}, date: {}, amount: {}",
				fromCurrency,
				toCurrency,
				date,
				amount);
		
		String dateString = date.toString(); 
		String fromCountryCode = fromCurrency.getCountryCode();
		String toCountryCode = toCurrency.getCountryCode();
		
		String apiUrl = String.format(
				API_URL_TEMPLATE, 
				dateString, 
				fromCountryCode, 
				toCountryCode);
		LOGGER.trace("apiUrl: {}", apiUrl);
		
		String responseString = callApiUrl(apiUrl);
		LOGGER.trace("responseString: {}", responseString);
		
		if (responseString == null) {
			return null;
		}
		
		ExchangeRate exchangeRate = exchangeRateService.adapt(
				amount,
				fromCurrency, 
				toCurrency, 
				responseString, 
				dateString,
				date);
		
		return exchangeRate;
	}
	
	@Override
	public List<ExchangeRate> getMultipleCurrencies(
			Currency baseCurrency,
			List<Currency> comparisonCurrencies,
			LocalDate date,
			float amount) throws UnsupportedOperationException, IOException, ParseException {
		List<String> comparisonCurrencyCodes = comparisonCurrencies
				.stream()
				.map(Currency::getCountryCode)
				.collect(Collectors.toList());
		// Not logging comparison Currencies as it becomes to verbose
		LOGGER.trace(
				"getting the multiple exchange rate from: {}, date: {}, amount: {}, comparisonCurrencyCodes: {}",
				baseCurrency,
				date,
				amount,
				comparisonCurrencyCodes);
		
		String dateString = date.toString(); 
		String baseCurrencyCode = baseCurrency.getCountryCode();
		
		String apiUrl = String.format(
				COMPARE_API_URL_TEMPLATE, 
				dateString, 
				baseCurrencyCode);
		LOGGER.trace("apiUrl: {}", apiUrl);
		
		String responseString = callApiUrl(apiUrl);
//		TOO verbose
//		LOGGER.trace("responseString: {}", responseString);
		
		if (responseString == null) {
			return Collections.emptyList();
		}
		
		List<ExchangeRate> exchangeRate = exchangeRateService.adaptMultiple(
				amount,
				baseCurrency,
				comparisonCurrencies, 
				responseString, 
				dateString,
				date);
		
		return exchangeRate;
	}
	
	@Override
	public String callApiUrl(String url) throws UnsupportedOperationException, IOException {
		LOGGER.trace("call API for url: {}", url);
		
		String cacheData = currencyAPICacheService.get(url);
		if (cacheData != null) {
			
			if (cacheData.equals(CurrencyAPICacheService.CACHE_NULL_ENTRY)) {
				LOGGER.debug("Null entry in cache for: {}", url);
				return null;
			}
			
			LOGGER.debug("Found entry in cache for: {}", url);
			return cacheData;
		}
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse httpResponse = client.execute(httpGet);
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			currencyAPICacheService.set(url, CurrencyAPICacheService.CACHE_NULL_ENTRY);
			return null;
		}
		
		InputStream inputStream = httpResponse.getEntity().getContent();

		StringBuilder builder = new StringBuilder();
		try (Scanner scanner = new Scanner(inputStream)) {
			while(scanner.hasNext()) {
				builder.append(scanner.nextLine());
			}
		}
		
		String responseString = builder.toString();
		LOGGER.debug("Setting entry in cache for: {}", url);
		currencyAPICacheService.set(url, responseString);
		
		// TOO Verbose
//		LOGGER.trace("responseString: {}", responseString);
		return responseString;
	}
}
