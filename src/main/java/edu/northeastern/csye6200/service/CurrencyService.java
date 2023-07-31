package edu.northeastern.csye6200.service;

import java.util.List;

import edu.northeastern.csye6200.model.Currency;

public interface CurrencyService {
	
	/**
	 * Will run a diagnostic check to see which country flags are not present.
	 * 
	 * <br><br>
	 * 
	 * This was done to help developers add icons for all those currencies
	 * for which it didn't exist in the project
	 * */
	void diagnostic();

	/**
	 * Will create all the available currencies.
	 * 
	 * <br><br> 
	 * 
	 * This will only create the list when it is invoked for
	 * the first time. If it is already created then it
	 * will return the same object
	 * */
	List<Currency> getAllCurrencies();
	
	/**
	 * Will return the {@link Currency} for the
	 * given code
	 * */
	Currency getByCode(String code);

}
