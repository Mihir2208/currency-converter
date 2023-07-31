package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.comparator.AutoCompleteComparator;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.CurrencyCompareRequest;
import edu.northeastern.csye6200.service.ComboBoxService;
import edu.northeastern.csye6200.service.CurrencyCompareService;
import edu.northeastern.csye6200.service.CurrencyService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.impl.ComboBoxServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyCompareServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyServiceImpl;
import edu.northeastern.csye6200.service.impl.ErrorHandlingServiceImpl;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;

public class ComparePageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparePageController.class);

	private ComboBoxService comboBoxService;
	private CurrencyService currencyService;
	private CurrencyCompareService currencyCompareService;
	private ErrorHandlingService errorHandlingService;
	
	private AutoCompleteComparator<Currency> currencyComparator;
	private List<Currency> allCurrencies;

	@FXML
	private AnchorPane root;
	
	@FXML
	private DatePicker fromDate;
	
	@FXML
	private DatePicker toDate;
	
	@FXML
	private TextField search;

	@FXML
	private ImageView imageView;
	
	@FXML
	private Label label;
	
	@FXML
	private Button clearButton;

	@FXML
	private ComboBox<Currency> comboBox;
	
	@FXML
	private TextField currencySearch;

	@FXML
	private ImageView currencyImageView;
	
	@FXML
	private Label currencyLabel;
	
	@FXML
	private Button currencyClearButton;
	
	@FXML
	private ComboBox<Currency> currencyComboBox;
	
	@FXML
	private Button compareButton;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private Button clearAllButton;
	
	@FXML
	private ListView<Currency> listView;
	
	private Currency baseCurrency;
	private Currency currency;

	private Set<String> currencyCodes;
	private List<Currency> comparisonCurrenices;
	
	private static String LIMIT_ERROR_MESSAGE;
	private static String DAYS_ERROR_MESSAGE;
	
	public void hide() {
		LOGGER.debug("Hiding the main page");
		root.setVisible(false);
	}
	
	public void show() {
		LOGGER.debug("Showing the main page");
		root.setVisible(true);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		
		LIMIT_ERROR_MESSAGE = String.format(
				Constants.ERROR_COMPARISON_CURRENCY_LIMIT_TEMPLATE, 
				Constants.COMPARISON_LIMIT);
		LOGGER.trace("LIMIT_ERROR_MESSAGE: {}", LIMIT_ERROR_MESSAGE);
		
		DAYS_ERROR_MESSAGE = String.format(
				Constants.ERROR_COMPARISON_DAY_LIMIT_TEMPLATE, 
				Constants.DAYS_LIMIT);
		LOGGER.trace("DAYS_ERROR_MESSAGE: {}", DAYS_ERROR_MESSAGE);
		
		comboBoxService = ComboBoxServiceImpl.getInstance();
		currencyService = CurrencyServiceImpl.getInstance();
		currencyCompareService = CurrencyCompareServiceImpl.getInstance();
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();

		currencyComparator = comboBoxService.getCurrencyAutocompleteComparator();
		
		allCurrencies = currencyService.getAllCurrencies();
		
		initializeListView();
		initializeComboxBoxes();
		initializeListeners();
		
		currencyCodes = new HashSet<>();
		comparisonCurrenices = new ArrayList<>();
	}
	
	private void initializeListView() {
		LOGGER.trace("Initialising the ListView");
		
		Callback<ListView<Currency>, ListCell<Currency>> callback = comboBoxService.getCurrencyCallback();
		listView.setCellFactory(callback);
		
		LOGGER.trace("ListView has been initialized");
	}
	
	private void initializeComboxBoxes() {
		LOGGER.trace("Initialising the Combo Boxes");
	
		ListCell<Currency> listCell = comboBoxService.getCurrencyListCell();
		ListCell<Currency> currencyListCell = comboBoxService.getCurrencyListCell();
		
		comboBox.setButtonCell(listCell);
		currencyComboBox.setButtonCell(currencyListCell);
		
		Callback<ListView<Currency>, ListCell<Currency>> callback = comboBoxService.getCurrencyCallback();
		
		comboBox.setCellFactory(callback);
		currencyComboBox.setCellFactory(callback);
		
		comboBox.getItems().addAll(allCurrencies);
		currencyComboBox.getItems().addAll(allCurrencies);
		
		LOGGER.trace("All combo-boxes have been initialized");
	}
	
	private void initializeListeners() {
		LOGGER.trace("Initialising the listeners");
		
		initializeSearchChangeListener();
		initializeClearButtonClickListener();
		initializeComboBoxSelectedListener();
		
		initializeCurrencySearchChangeListener();
		initializeCurrencyClearButtonClickListener();
		initializeCurrencyComboBoxSelectedListener();
		
		initializeAddButtonClickListener();
		initializeDeleteButtonClickListener();
		initializeClearAllButtonClickListener();
		initializeCompareButtonClickListener();
		
		LOGGER.trace("All listeners initialized");		
	}
	
	private void initializeSearchChangeListener() {
		LOGGER.trace("Initialising the Search listener");

		PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
		search.textProperty().addListener(observable -> {
			pause.setOnFinished(event -> handleSearchChange());
			pause.playFromStart();
		});
		
		LOGGER.trace("Search Listener initialized");
	}
	
	private void initializeCurrencySearchChangeListener() {
		LOGGER.trace("Initialising the Currency Search listener");

		PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
		currencySearch.textProperty().addListener(observable -> {
			pause.setOnFinished(event -> handleCurrencySearchChange());
			pause.playFromStart();
		});
		
		LOGGER.trace("Currency Search Listener initialized");
	}
	
	private void initializeClearButtonClickListener() {
		LOGGER.trace("Initialising the Clearn Button click listener");
		
		clearButton.setOnMouseClicked(event -> handleClearButtonClick());
		
		LOGGER.trace("Clear Button Listener click initialized");
	}
	
	private void initializeCurrencyClearButtonClickListener() {
		LOGGER.trace("Initialising the Currency Clear Button click listener");
		
		currencyClearButton.setOnMouseClicked(event -> handleCurrencyClearButtonClick());
		
		LOGGER.trace("Currency Clear Button Listener click initialized");
	}
	
	private void initializeComboBoxSelectedListener() {
		LOGGER.trace("Initialising the Combo Box Selected listener");
		
		comboBox.setOnAction(event -> handleComboBoxSelect());
		
		LOGGER.trace("Combo Box Selected listener has been initialized");
	}
	
	private void initializeCurrencyComboBoxSelectedListener() {
		LOGGER.trace("Initialising the Currency Combo Box Selected listener");
		
		currencyComboBox.setOnAction(event -> handleCurrencyComboBoxSelect());
		
		LOGGER.trace("Combo Box Selected listener has been initialized");
	}
	
	private void initializeAddButtonClickListener() {
		LOGGER.trace("Initialising the Add Button listener");
		
		addButton.setOnMouseClicked(event -> handleAddButtonClick());
		
		LOGGER.trace("Add Button click listener initialized");
	}

	private void initializeDeleteButtonClickListener() {
		LOGGER.trace("Initialising the Delete Button listener");
		
		deleteButton.setOnMouseClicked(event -> handleDeleteButtonClick());
		
		LOGGER.trace("Delete Button click listener initialized");
	}
	
	private void initializeClearAllButtonClickListener() {
		LOGGER.trace("Initialising the Clear All Button listener");
		
		clearAllButton.setOnMouseClicked(event -> handleClearAllButtonClick());
		
		LOGGER.trace("Clear All Button click listener initialized");
	}
	
	private void initializeCompareButtonClickListener() {
		LOGGER.trace("Initialising the Compare Button listener");
		
		compareButton.setOnMouseClicked(event -> handleCompareButtonClick());
		
		LOGGER.trace("Compare Button click listener initialized");
	}
	
	private void handleSearchChange() {
		LOGGER.trace("Search field has changed");
		
		comboBoxService.filter(
				allCurrencies,
				comboBox, 
				currencyComparator, 
				search.getText());
		
		comboBox.hide();
		comboBox.show();
	}
	
	private void handleCurrencySearchChange() {
		LOGGER.trace("Currency Search field has changed");
		
		comboBoxService.filter(
				allCurrencies,
				currencyComboBox, 
				currencyComparator, 
				currencySearch.getText());
		
		currencyComboBox.hide();
		currencyComboBox.show();
	}
	
	private void handleClearButtonClick() {
		LOGGER.trace("will handle Clear button click");
	
		baseCurrency = null;
		imageView.setImage(null);
		label.setText(Constants.BLANK_STRING);
		
		comboBox.setValue(null);
	}
	
	private void handleCurrencyClearButtonClick() {
		LOGGER.trace("will handle Currency Clear button click");
	
		currency = null;
		currencyImageView.setImage(null);
		currencyLabel.setText(Constants.BLANK_STRING);
		
		currencyComboBox.setValue(null);
	}
	
	private void handleComboBoxSelect() {
		LOGGER.trace("will handle Combo Box select");
		
		Currency currency = comboBox.getValue();
		if (currency == null) {
			LOGGER.debug("No value in Dropdown, will not handle any value change events...");
			return;
		}
		
		updateBaseCurrency(currency);
	}
	
	private void handleCurrencyComboBoxSelect() {
		LOGGER.trace("will handle Currency Combo Box select");
		
		Currency currency = currencyComboBox.getValue();
		if (currency == null) {
			LOGGER.debug("No value in currency Dropdown, will not handle any value change events...");
			return;
		}
		
		updateCurrency(currency);
	}
	
	private void updateBaseCurrency(Currency currency) {
		LOGGER.trace("will update the base currency: {}", currency);
		
		baseCurrency = currency;
		
		label.setText(currency.getName());
		
		Image image = new Image(currency.getFlagIconPath());
		imageView.setImage(image);
	}
	
	private void updateCurrency(Currency currency) {
		LOGGER.trace("will update the currency: {}", currency);
		
		this.currency = currency;
		
		currencyLabel.setText(currency.getName());
		
		Image image = new Image(currency.getFlagIconPath());
		currencyImageView.setImage(image);
	}
	
	private void handleAddButtonClick() {
		LOGGER.trace("will handle Add button click by adding currency: {}", currency);
		
		if (currency == null) {
			LOGGER.debug("currency not selected, will not to anything");
			return;
		}
		
		int length = currencyCodes.size();
		boolean limitReached = (length == Constants.COMPARISON_LIMIT);
		LOGGER.trace("length: {}, limitReached: {}", length, limitReached);
		if (limitReached) {
			LOGGER.debug("Limit has been reached, will not allow to add currency for comparison");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					LIMIT_ERROR_MESSAGE);
			return;
		}
		
		String code = currency.getCountryCode();
		boolean codeAlreadyAdded = currencyCodes.contains(code);
		LOGGER.trace("code: {}, codeAlreadyAdded", code, codeAlreadyAdded);
		if (codeAlreadyAdded) {
			LOGGER.debug("Currency is already added no need to do anything");
			return;
		}

		LOGGER.debug("Adding new currency for comparison: {}", currency);
		
		currencyCodes.add(code);
		comparisonCurrenices.add(currency);
		listView.getItems().add(currency);
	}

	private void handleDeleteButtonClick() {
		LOGGER.trace("will handle Delete button click");
		
		Currency selectedCurrency = listView.getSelectionModel().getSelectedItem();
		LOGGER.trace("selectedCurrency: {}", selectedCurrency);
		
		if (selectedCurrency == null) {
			LOGGER.debug("No currency selected, will not do anything");
			return;
		}
		
		String code = selectedCurrency.getCountryCode();
		currencyCodes.remove(code);
		
		boolean removed = comparisonCurrenices.remove(selectedCurrency);
		if (!removed) {
			// This should never happen but still loggin for Safety Reasons
			LOGGER.error("arrayList failed to remove selectedCurrency: {}", selectedCurrency);
			errorHandlingService.handleMessage(
					Constants.SOMETHING_WENT_WRONG, 
					Constants.ERROR_BUG_DELETE_ARRAY_LIST);
		}
		
		listView.getItems().remove(selectedCurrency);
	}
	
	private void handleClearAllButtonClick() {
		LOGGER.trace("will handle Clear All button click");
		
		currencyCodes.clear();
		comparisonCurrenices.clear();
		listView.getItems().clear();
	}
	
	private void handleCompareButtonClick() {
		LOGGER.trace("will handle Compare button click");
		
		LocalDate from = fromDate.getValue();
		LOGGER.trace("from: {}", from);
		if (from == null) {
			LOGGER.debug("since from date is not selected, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_FROM_DATE_INVALID);
			return;
		}
		
		LocalDate to = toDate.getValue();
		LOGGER.trace("to: {}", to);
		if (to == null) {
			LOGGER.debug("since to date is not selected, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_TO_DATE_INVALID);
			return;
		}
		
		boolean before = from.isBefore(to);
		LOGGER.trace("before: {}", to);
		if (!before) {
			LOGGER.debug("since from date is NOT before to date, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_FROM_DATE_BEFORE_TO_DATE);
			return;
		}
		
		long days = ChronoUnit.DAYS.between(from, to);
		boolean daysToleranceCrossed = (days > Constants.DAYS_LIMIT);
		LOGGER.trace("days: {}, daysToleranceCrossed: {}", days, daysToleranceCrossed);
		if (daysToleranceCrossed) {
			LOGGER.debug("Days Tolearnce has been crossed, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					DAYS_ERROR_MESSAGE);
			return;
		}
		
		LOGGER.trace("baseCurrency: {}", baseCurrency);
		if (baseCurrency == null) {
			LOGGER.debug("since base currency is not selected, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_BASE_CURRENCY_INVALID);
			return;
		}
		
		boolean emptyComparison = currencyCodes.isEmpty();
		LOGGER.trace("emptyComparison: {}", emptyComparison);
		if (emptyComparison) {
			LOGGER.debug("since comparison is empty, will not do anything");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_COMPARISON_CURRENCIES_INVALID);
			return;
		}
		
		CurrencyCompareRequest compareRequest = currencyCompareService.createRequest(
				from, 
				to, 
				baseCurrency, 
				comparisonCurrenices,
				Constants.CURRENCY_COMPARE_DEFAULT_AMOUNT);
		
		try {
			currencyCompareService.computeAsync(compareRequest);
		} catch (Exception e) {
			LOGGER.error("Exception in computing response: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
}
