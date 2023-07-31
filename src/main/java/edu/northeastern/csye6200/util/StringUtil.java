package edu.northeastern.csye6200.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.controller.ConvertPageController;

public class StringUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertPageController.class);

	public static boolean isNumber(String str) {
		boolean result = str.matches(Constants.NUMBER_REGEX);
		LOGGER.trace("str: {}, is number: {}", str, result);
		return result;
	}

}
