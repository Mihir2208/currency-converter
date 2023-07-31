package edu.northeastern.csye6200.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.CurrencyCompareRequest;
import edu.northeastern.csye6200.model.CurrencyCompareResponse;
import edu.northeastern.csye6200.model.ExchangeRate;
import edu.northeastern.csye6200.service.ComparisonGraphService;
import edu.northeastern.csye6200.service.CurrencyAPIService;
import edu.northeastern.csye6200.service.CurrencyCompareService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.MonitorService;
import edu.northeastern.csye6200.service.ProgressBarService;
import javafx.application.Platform;

public class CurrencyCompareServiceImpl implements CurrencyCompareService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyCompareServiceImpl.class);

	private static CurrencyCompareService instance;

	private CurrencyAPIService currencyAPIService;
	private ProgressBarService progressBarService;
	private ComparisonGraphService comparisonGraphService;
	private MonitorService monitorService;
	private ErrorHandlingService errorHandlingService;

	private CurrencyCompareServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		currencyAPIService = CurrencyAPIServiceImpl.getInstance();
		progressBarService = ProgressBarServiceImpl.getInstance();
		comparisonGraphService = ComparisonGraphServiceImpl.getInstance();
		monitorService = MonitorServiceImpl.getInstance();
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
	}
	
	public static CurrencyCompareService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CurrencyCompareServiceImpl();
		return instance;
	}
	
	@Override
	public CurrencyCompareRequest createRequest(
			LocalDate fromDate,
			LocalDate toDate,
			Currency baseCurrency,
			List<Currency> comparisonCurrencies,
			float amount) {
		CurrencyCompareRequest request = new CurrencyCompareRequest();
		request.setAmount(amount);
		request.setFromDate(fromDate);
		request.setToDate(toDate);
		request.setBaseCurrency(baseCurrency);
		request.setComparisonCurrencies(comparisonCurrencies);
		return request;
	}
	
	@Override
	public CurrencyCompareResponse createResponse(
			LocalDate fromDate, 
			LocalDate toDate, 
			Currency baseCurrency,
			Map<String, List<ExchangeRate>> data) {
		
		CurrencyCompareResponse response = new CurrencyCompareResponse();
		response.setFromDate(fromDate);
		response.setToDate(toDate);
		response.setBaseCurrency(baseCurrency);
		response.setData(data);
		return response;
	}
	
	@Override
	public void computeAsync(CurrencyCompareRequest request) {
		monitorService.start();
		monitorService.setOperationName(MonitorService.OPERATION_CURRENCY_COMPARE);
		monitorService.setCurrencyComparisonOperationDeatils(request);
		
		float amount = request.getAmount();
		
		LocalDate fromDate = request.getFromDate();
		LocalDate toDate = request.getToDate();
		Currency baseCurrency = request.getBaseCurrency();
		List<Currency> comparisonCurrencies = request.getComparisonCurrencies();
		
		List<String> comparisonCurrencyCodes = comparisonCurrencies
				.stream()
				.map(Currency::getCountryCode)
				.collect(Collectors.toList());
		
		List<LocalDate> dates = fromDate.datesUntil(toDate).collect(Collectors.toList());

		int dateSize = dates.size();
		int comparisonCurrenciesSize = comparisonCurrencies.size();
		int totalProgress = dateSize * comparisonCurrenciesSize;
		
		progressBarService.show();
		progressBarService.updateProgressTotal(totalProgress);
		
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				
				try {
					Map<String, List<ExchangeRate>> data = new HashMap<>(comparisonCurrenciesSize);
					
					for (String code : comparisonCurrencyCodes) {
						List<ExchangeRate> emptyList = new ArrayList<>();
						data.put(code, emptyList);
					}
					
					for (LocalDate date : dates) {
						List<ExchangeRate> exchangeRates = currencyAPIService.getMultipleCurrencies(
								baseCurrency, 
								comparisonCurrencies, 
								date, 
								amount);

						for (ExchangeRate exchangeRate : exchangeRates) {
							Currency currency = exchangeRate.getToCurrency();
							String code = currency.getCountryCode();
							List<ExchangeRate> list = data.get(code);
							list.add(exchangeRate);
							data.put(code, list);

							Platform.runLater(() -> {
								progressBarService.incrementProgress();
							});
						}
						
						int remainingProgress = comparisonCurrenciesSize - exchangeRates.size();
						for (int i = 0; i < remainingProgress; ++i) {
							Platform.runLater(() -> {
								progressBarService.incrementProgress();
							});
						}
					}
					
					CurrencyCompareResponse response = createResponse(
							fromDate, 
							toDate, 
							baseCurrency, 
							data);
					
					Platform.runLater(() -> {
						progressBarService.close();
						comparisonGraphService.show();
						comparisonGraphService.drawGraph(response);
						monitorService.stop();
					});
				} catch (Exception e) {
					LOGGER.error("Exception in thread run: {}", e.getMessage(), e);
					errorHandlingService.handleError(e);
				}
			};
		};
		
		thread.start();
	}

}
