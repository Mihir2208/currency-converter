package edu.northeastern.csye6200.constant;

public interface Constants {
	String APP_NAME = "Currency Converter";
	String APP_VERSION = "Version - 1.0.0";
	
	String BLANK_STRING = "";
	
	// Reference: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	String NUMBER_REGEX = "-?\\d+";
	
	String SOMETHING_WENT_WRONG = "Something Went Wrong!!";
	
	String DEFAULT_ADMIN_PASSWORD = "admin1234";
	
	String TEST_API_URL = "Test API URL";
	String TEST_CACHE = "Cache";
	
	String CATEGORY_CONVERT = "CONVERT";
	String CATEGORY_HISTORY = "HISTORY";
	String CATEGORY_COMPARE = "COMPARE";
	String CATEGORY_SETTINGS = "SETTINGS";
	String CATEGORY_COMING_SOON = "COMING-SOON";

	String ERROR_TITLE = "Error";
	String PROGRESS_TITLE = "Progress";
	String COMPARISON_GRAPH_TITLE = "Comparison Graph";
	
	String DATA_LOADING_1 = "Data Loading.";
	String DATA_LOADING_2 = "Data Loading..";
	String DATA_LOADING_3 = "Data Loading...";
	String DATA_LOADING_4 = "Data Loading....";
	String DATA_LOADING_5 = "Data Loading.....";
	
	float CURRENCY_COMPARE_DEFAULT_AMOUNT = 1;
	int COMPARISON_LIMIT = 10;
	int DAYS_LIMIT = 1000;
	
	int MAX_GRAPH_TICK_MARKS = 10;
	
	String CACHE_ENABLED = "Cache Enabled";
	String CACHE_DISBLED = "Cache Disabled";
	
	// ERROR MESSAGES
	String ERROR_AMOUNT_INVALID = "Please enter valid amount";
	String ERROR_FROM_CURRENCY_NOT_SELECTED = "Please select a currency to convert from";
	String ERROR_TO_CURRENCY_NOT_SELECTED = "Please select a currency to convert to";
	String ERROR_HISTORY_DATE_NOT_SELECTED= "Please select a history date";
	
	String ERROR_BUG_DELETE_ARRAY_LIST = "Bug in handling delete operation, report it to the developers";
	
	String ERROR_FROM_DATE_INVALID = "Please select from date";
	String ERROR_TO_DATE_INVALID = "Please select to date";
	String ERROR_FROM_DATE_BEFORE_TO_DATE = "From Date must be before To Date";
	String ERROR_BASE_CURRENCY_INVALID = "Please select a base currency";
	String ERROR_COMPARISON_CURRENCIES_INVALID = "Please add atleast one currency to compare";
	String ERROR_COMPARISON_CURRENCY_LIMIT_TEMPLATE = "Not allowed to add more than %s currencies";
	String ERROR_COMPARISON_DAY_LIMIT_TEMPLATE = "Not allowed to compare more than %s days";
}
