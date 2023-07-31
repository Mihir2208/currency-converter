package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.service.AdminService;
import edu.northeastern.csye6200.service.CurrencyAPICacheService;
import edu.northeastern.csye6200.service.CurrencyAPIService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.ProgressBarService;
import edu.northeastern.csye6200.service.impl.AdminServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyAPICacheServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyAPIServiceImpl;
import edu.northeastern.csye6200.service.impl.ErrorHandlingServiceImpl;
import edu.northeastern.csye6200.service.impl.ProgressBarServiceImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SettingsPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsPageController.class);

	private AdminService adminService;
	private CurrencyAPIService currencyAPIService;
	private ErrorHandlingService errorHandlingService;
	private ProgressBarService progressBarService;
	private CurrencyAPICacheService currencyAPICacheService;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private AnchorPane loginPane;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private AnchorPane adminPane;
	
	@FXML
	private Button testExceptionButton;
	
	@FXML
	private TextField apiTextField;
	
	@FXML
	private Button testAPIButton;
	
	@FXML
	private TextField progressStartTextField;
	
	@FXML
	private TextField progressTotalTextField;

	@FXML
	private TextField progressDelayMillisecondsTextField;
	
	@FXML
	private Button progressSimulateButton;
	
	@FXML
	private TextField testCacheTextField;
	
	@FXML
	private Button testCacheButton;
	
	@FXML
	private Button getCacheSizeButton;
	
	@FXML
	private ToggleButton cacheToggleButton;
	
	public void hide() {
		LOGGER.debug("Hiding the main page");
		root.setVisible(false);
	}
	
	public void show() {
		LOGGER.debug("Showing the main page");
		root.setVisible(true);
		
		boolean cacheEnabled = currencyAPICacheService.isEnabled();
		cacheToggleButton.setSelected(cacheEnabled);
		updateToggleCacheButtonText();
	}
	
	public void showLoginPane() {
		LOGGER.trace("Show the Admin Pane");
		loginPane.setVisible(true);
		updateToggleCacheButtonText();
	}
	
	public void hideLoginPane() {
		LOGGER.trace("Show the Admin Pane");
		loginPane.setVisible(false);
		updateToggleCacheButtonText();
	}
	
	public void showAdminPane() {
		LOGGER.trace("Show the Admin Pane");
		adminPane.setVisible(true);
		updateToggleCacheButtonText();
	}
	
	public void hideAdminPane() {
		LOGGER.trace("Hide the Admin Pane");
		adminPane.setVisible(false);
		updateToggleCacheButtonText();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		
		adminService = AdminServiceImpl.getInstance();
		currencyAPIService = CurrencyAPIServiceImpl.getInstance();
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		progressBarService = ProgressBarServiceImpl.getInstance();
		currencyAPICacheService = CurrencyAPICacheServiceImpl.getInstance();
		
		initializeListeners();
	}
	
	private void initializeListeners() {
		LOGGER.trace("Initializing all the listeners");
		
		initializeLoginButtonClickListener();
		initializeTestExceptionButtonClickListener();
		initializeTestAPIButtonClickListener();
		initializeProgressSimulateButtonClickListener();
		initializeTestCacheButtonClickListener();
		initializeGetCacheSizeButtonClickListener();
		initializeCacheToggleButtonClickListener();
		
		LOGGER.trace("All the listeners have been initialized");
	}
	
	private void initializeLoginButtonClickListener() {
		LOGGER.trace("Initializing the login button click listener");
		
		loginButton.setOnMouseClicked(event -> handleLoginButtonClick());
		
		LOGGER.trace("login button click listener has been initialized");
	}
	
	private void initializeTestExceptionButtonClickListener() {
		LOGGER.trace("Initializing the Test Exception button click listener");
		
		testExceptionButton.setOnMouseClicked(event -> handleTestExceptionButtonClick());
		
		LOGGER.trace("Test Exception button click listener has been initialized");
	}
	
	private void initializeTestAPIButtonClickListener() {
		LOGGER.trace("Initializing the Test Exception button click listener");
		
		testAPIButton.setOnMouseClicked(event -> handleTestAPIButtonClick());
		
		LOGGER.trace("Test Exception button click listener has been initialized");
	}
	
	private void initializeProgressSimulateButtonClickListener() {
		LOGGER.trace("Initializing the Progress Simulate button click listener");
		
		progressSimulateButton.setOnMouseClicked(event -> handleProgressSimulateButtonClick());
		
		LOGGER.trace("Progress Simulate button click listener has been initialized");
	}
	
	private void initializeTestCacheButtonClickListener() {
		LOGGER.trace("Initializing the Test cache button click listener");
		
		testCacheButton.setOnMouseClicked(event -> handleTestCacheButtonClick());
		
		LOGGER.trace("Test Cache button click listener has been initialized");
	}
	
	private void initializeGetCacheSizeButtonClickListener() {
		LOGGER.trace("Initializing the get cache size button click listener");
		
		getCacheSizeButton.setOnMouseClicked(event -> handleGetCacheSizeButtonClick());
		
		LOGGER.trace("get cache size button click listener has been initialized");
	}
	
	private void initializeCacheToggleButtonClickListener() {
		LOGGER.trace("Initializing the cache toggle button click listener");
		
		cacheToggleButton.setOnMouseClicked(event -> handleCacheToggleButtonClick());
		
		LOGGER.trace("cache toggle button click listener has been initialized");
	}
	
	private void handleLoginButtonClick() {
		LOGGER.trace("Handling login button click");
		
		String text = passwordField.getText();
		boolean validAdminPassword = adminService.isValidAdminPassword(text);
		if (!validAdminPassword) {
			LOGGER.debug("will not do anything, invalid admin password given: {}", text);
			return;
		}
		
		LOGGER.debug("Valid Admin Login");
		passwordField.setText(Constants.BLANK_STRING);
		hideLoginPane();
		showAdminPane();
	}
	
	private void handleTestExceptionButtonClick() {
		LOGGER.trace("Handling TestExceptionButton click");
		
		// Doing this on purpose to throw exception and see that error dialog opens
		int x = 1/0;
		
		// Logging on purpose to avoid Eclipse warning, This will never print due to exception in above code
		LOGGER.trace("x: {}", x);
	}
	
	private void handleTestAPIButtonClick() {
		LOGGER.trace("Handling TestAPIButton click");
		String text = apiTextField.getText();
		
		try {
			String response = currencyAPIService.callApiUrl(text);
			errorHandlingService.handleMessage(Constants.TEST_API_URL, response);
		} catch (Exception e) {
			LOGGER.error("Exception in handleTestAPIButtonClick(): {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
	
	private void handleProgressSimulateButtonClick() {
		LOGGER.trace("Handling progress simulate button click");
		
		String progressStartText = progressStartTextField.getText();
		String progressTotalText = progressTotalTextField.getText();
		String progressDelayMillisecondsText = progressDelayMillisecondsTextField.getText();
		LOGGER.trace(
				"progressStartText: {}, progressTotalText: {}, progressDelayMillisecondsText: {}",
				progressStartText,
				progressTotalText,
				progressDelayMillisecondsText);
		
		int progressStart = Integer.parseInt(progressStartText);
		int progressTotal = Integer.parseInt(progressTotalText);
		int progressDelayMilliseconds = Integer.parseInt(progressDelayMillisecondsText);
		LOGGER.trace(
				"progressStart: {}, progressTotal: {}, progressDelayMilliseconds: {}",
				progressStart,
				progressTotal,
				progressDelayMilliseconds);
		
		simulateProgress(
				progressStart,
				progressTotal,
				progressDelayMilliseconds);
	}
	
	private void handleTestCacheButtonClick() {
		LOGGER.trace("Handling test cache button click");
		String key = testCacheTextField.getText();
		String value = currencyAPICacheService.get(key);
		errorHandlingService.handleMessage(Constants.TEST_CACHE, value);
	}
	
	private void handleGetCacheSizeButtonClick() {
		LOGGER.trace("Handling the get cache size button click");
		int size = currencyAPICacheService.getCacheSize();
		String value = String.valueOf(size);
		errorHandlingService.handleMessage(Constants.TEST_CACHE, value);
	}
	
	private void handleCacheToggleButtonClick() {
		LOGGER.trace("Handling the cache toggle button click");
		
		boolean isSelected = cacheToggleButton.isSelected();
		currencyAPICacheService.setEnabled(isSelected);
		updateToggleCacheButtonText();
	}
	
	private void simulateProgress(
			int progressStart, 
			int progressTotal, 
			int progressDelayMilliseconds) {
		LOGGER.info(
				"Simulating Progress flow for progressStart: {}, progressTotal: {}, progressDelayMilliseconds: {}",
				progressStart,
				progressTotal,
				progressDelayMilliseconds);
		
		progressBarService.show();
		
		progressBarService.updateProgressTotal(progressTotal);

		/**
		 * Never use Thread.sleep it will cause many issues with the JavaFX
		 * Thread. Instead use {@link PauseTransition} to delay tasks
		 * 
		 * 
		 * Refernce: https://stackoverflow.com/questions/9966136/javafx-periodic-background-tasks
		 * */
		/*
		try {
			Thread.sleep(progressDelayMilliseconds);
		} catch (InterruptedException e) {
			LOGGER.error("Exception in Simulation Progress Thread Sleep: {}", e.getMessage(), e);
		}
		LOGGER.info("Simulate Progress thread is awake now");
		*/
		
		Duration duration = Duration.millis(progressDelayMilliseconds);
		KeyFrame keyFrame = new KeyFrame(
				duration,
				event -> progressBarService.incrementProgress());

		int cycleCounts = progressTotal - progressStart + 1;
		LOGGER.trace("cycleCounts: {}", cycleCounts);
		
		Timeline timeline = new Timeline(keyFrame);
		timeline.setCycleCount(cycleCounts);
		timeline.setOnFinished(event -> progressBarService.close());
		timeline.play();
	}
	
	private void updateToggleCacheButtonText() {
		LOGGER.trace("updating the text of toggle Cache button");
		boolean cacheEnabled = currencyAPICacheService.isEnabled();
		String text = cacheEnabled ? Constants.CACHE_ENABLED : Constants.CACHE_DISBLED;
		LOGGER.trace("cacheEnabled: {}, text: {}", cacheEnabled, text);
		cacheToggleButton.setText(text);
	}
}
