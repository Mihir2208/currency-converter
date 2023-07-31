package edu.northeastern.csye6200.service;

import java.net.URISyntaxException;

public interface CountryFlagService {

	/**
	 * Depending on the country code will return
	 * the path of the icon of the country flag.
	 * 
	 * <br><br>
	 * Will also validate that a country flag exists for the
	 * given country code or else will return the
	 * rectangular_fallback.png icon path
	 * 
	 * <br><br>
	 * 
	 * For Argentine Peso<br>
	 * ars - icons/rectangular-flags/ars.png
	 * @throws URISyntaxException 
	 * 
	 * */
	String getFlagIconPath(String countryCode);
	
	/**
	 * Depending on the country code will check if the
	 * flag icon for that country exists or not
	 *
	 * @return true if the icon exists or else will return false
	 * */
	boolean isFlagIconExist(String countryCode);
	
}
