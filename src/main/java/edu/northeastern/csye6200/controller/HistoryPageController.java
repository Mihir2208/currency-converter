package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.comparator.AutoCompleteComparator;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.model.ExchangeRate;
import edu.northeastern.csye6200.service.ComboBoxService;
import edu.northeastern.csye6200.service.CurrencyAPIService;
import edu.northeastern.csye6200.service.CurrencyService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.impl.ComboBoxServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyAPIServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyServiceImpl;
import edu.northeastern.csye6200.service.impl.ErrorHandlingServiceImpl;
import edu.northeastern.csye6200.util.StringUtil;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
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

public class HistoryPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertPageController.class);

	private ComboBoxService comboBoxService;
	private CurrencyService currencyService;
	private CurrencyAPIService currencyAPIService;
	private ErrorHandlingService errorHandlingService;
	
	private AutoCompleteComparator<Currency> currencyComparator;
	private List<Currency> allCurrencies;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private ImageView fromCountryImageView;
	
	@FXML
	private Label fromCountryLabel;
	
	@FXML
	private TextField fromCountrySearch;
	
	@FXML
	private Button fromCountryClearButton;
	
	@FXML
	private ComboBox<Currency> fromCountry;
	
	@FXML
	private ImageView toCountryImageView;
	
	@FXML
	private Label toCountryLabel;
	
	@FXML
	private TextField toCountrySearch;
	
	@FXML
	private Button toCountryClearButton;
	
	@FXML
	private ComboBox<Currency> toCountry;
	
	@FXML
	private TextField amountTextField;
	
	@FXML
	private Button swapButton;
	
	@FXML
	private Button convertButton;
	
	@FXML
	private AnchorPane resultAnchorPane;
	
	@FXML
	private ImageView convertedFromCountryImageView;
	
	@FXML
	private Label convertedFromCurrency;
	
	@FXML
	private ImageView convertedToCountryImageView;
	
	@FXML
	private Label convertedToCurrency;

	@FXML
	private Label value;
	
	@FXML
	private Label date;
	
	@FXML
	private Label convertedValue;
	
	@FXML
	private DatePicker historyDatePicker;
	
	private Currency fromCurrency;
	private Currency toCurrency;
	
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
		
		comboBoxService = ComboBoxServiceImpl.getInstance();
		currencyService = CurrencyServiceImpl.getInstance();
		currencyAPIService = CurrencyAPIServiceImpl.getInstance();
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		
		currencyComparator = comboBoxService.getCurrencyAutocompleteComparator();
		
		allCurrencies = currencyService.getAllCurrencies();
		
		initializeComboxBoxes();
		initializeSwapButtonIcon();
		initializeListeners();
		initializeDateFactory();
		
		resultAnchorPane.setVisible(false);
		resultAnchorPane.setVisible(false);
		
        
	}
	
	private void initializeDateFactory() {
		LOGGER.trace("Intialising the date picker");
		Callback<DatePicker, DateCell> callback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) > 0);
                    }

                };
            }

        };
        historyDatePicker.setDayCellFactory(callback);
        LOGGER.trace("Finished initialising date picker by disabling the future dates");
	}
	
	private void initializeComboxBoxes() {
		LOGGER.trace("Initialising the Combo Boxes");

		ListCell<Currency> fromListCell = comboBoxService.getCurrencyListCell();
		ListCell<Currency> toListCell = comboBoxService.getCurrencyListCell();
		
		fromCountry.setButtonCell(fromListCell);
		toCountry.setButtonCell(toListCell);
		
		Callback<ListView<Currency>, ListCell<Currency>> callback = comboBoxService.getCurrencyCallback();
		
		fromCountry.setCellFactory(callback);
		toCountry.setCellFactory(callback);
		
		fromCountry.getItems().addAll(allCurrencies);
		toCountry.getItems().addAll(allCurrencies);
		
		LOGGER.trace("All combo-boxes have been initialized");
	}
	
	private void initializeSwapButtonIcon() {
		LOGGER.trace("Initialising the Swap Button image icon");
		
		String swapIconPath = "/icons/swap-icon.png";
		Image image = new Image(swapIconPath);
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);

		/**
		 * Reference: https://stackoverflow.com/questions/55185856/how-to-set-an-image-to-the-size-of-a-button-in-javafx
		 * */
		imageView.fitWidthProperty().bind(swapButton.widthProperty());
		imageView.fitHeightProperty().bind(swapButton.heightProperty());
		
		swapButton.setGraphic(imageView);
		
		LOGGER.trace("Finished initializing the swap button image icon");
	}
	
	
	private void initializeListeners() {
		LOGGER.trace("Initialising the listeners");

		initializeFromCountrySearchChangeListener();
		initializeFromCountryClearButtonListener();
		initializeFromCountryComboBoxSelectedListener();
		
		initializeToCountrySearchChangeListener();
		initializeToCountryClearButtonListener();
		initializeToCountryComboBoxSelectedListener();
		
		initializeSwapButtonListener();
		initializeConvertButtonListener();
		
		LOGGER.trace("All listeners initialized");		
	}
	
	private void initializeFromCountrySearchChangeListener() {
		LOGGER.trace("Initialising the FromCountrySearch listener");

		PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
		fromCountrySearch.textProperty().addListener(observable -> {
			pause.setOnFinished(event -> handleFromCountrySearchChange());
			pause.playFromStart();
		});
		
		LOGGER.trace("FromCountrySearch initialized");
	}
	
	private void initializeToCountrySearchChangeListener() {
		LOGGER.trace("Initialising the ToCountrySearch listener");

		PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
		toCountrySearch.textProperty().addListener(observable -> {
			pause.setOnFinished(event -> handleToCountrySearchChange());
			pause.playFromStart();
		});
		
		LOGGER.trace("ToCountrySearch initialized");
	}
	
	private void initializeFromCountryClearButtonListener() {
		LOGGER.trace("Initialising the FromCountryClearButton listener");
		
		fromCountryClearButton.setOnMouseClicked(event -> resetFromCurrency());
		
		LOGGER.trace("FromCountryClearButton initialized");
	}
	
	private void initializeToCountryClearButtonListener() {
		LOGGER.trace("Initialising the ToCountryClearButton listener");
		
		toCountryClearButton.setOnMouseClicked(event -> resetToCurrency());
		
		LOGGER.trace("ToCountryClearButton initialized");
	}
	
	private void initializeFromCountryComboBoxSelectedListener() {
		LOGGER.trace("Initialising the FromCountryComboBoxSelectedListener listener");
		
		fromCountry.setOnAction(event -> handleFromCountryValueChange());
		
		LOGGER.trace("FromCountryComboBoxSelectedListener initialized");
	}
	
	private void initializeToCountryComboBoxSelectedListener() {
		LOGGER.trace("Initialising the ToCountryComboBoxSelectedListener listener");
		
		toCountry.setOnAction(event -> handleToCountryValueChange());
		
		LOGGER.trace("ToCountryComboBoxSelectedListener initialized");
	}
	
	private void initializeSwapButtonListener() {
		LOGGER.trace("Initialising the Swap Button listener");
		
		swapButton.setOnMouseClicked(event -> handleSwapButtonClick());
		
		LOGGER.trace("Swap Button initialized");
	}
	
	private void initializeConvertButtonListener() {
		LOGGER.trace("Initialising the Convert Button listener");
		
		convertButton.setOnMouseClicked(event -> handleConvertButtonClick());
		
		LOGGER.trace("Convert Button initialized");
	}
	
	private void handleFromCountrySearchChange() {
		LOGGER.trace("Search field for from country changed");
		
		comboBoxService.filter(
				allCurrencies,
				fromCountry, 
				currencyComparator, 
				fromCountrySearch.getText());
		
		fromCountry.hide();
		fromCountry.show();
	}
	
	private void handleToCountrySearchChange() {
		LOGGER.trace("Search field for to country changed");
		
		comboBoxService.filter(
				allCurrencies,
				toCountry, 
				currencyComparator, 
				toCountrySearch.getText());
		
		toCountry.hide();
		toCountry.show();
	}
	
	private void handleFromCountryValueChange() {
		LOGGER.trace("will handle from country value change");
		
		Currency currency = fromCountry.getValue();
		if (currency == null) {
			LOGGER.debug("No value in From Country Dropdown, will not handle any value change events...");
			return;
		}
		
		updateFromCurrency(currency);
	}
	
	private void handleToCountryValueChange() {
		LOGGER.trace("will handle to country value change");
		
		Currency currency = toCountry.getValue();
		if (currency == null) {
			LOGGER.debug("No value in From Country Dropdown, will not handle any value change events...");
			return;
		}
		
		updateToCurrency(currency);
	}
	
	private void handleSwapButtonClick() {
		LOGGER.trace("will handle Swap button click");
		
		LOGGER.trace("fromCurrency: {}", fromCurrency);
		if (fromCurrency == null) {
			LOGGER.debug("fromCurrency does not exist, so don't need to do anything");
			return;
		}
		
		LOGGER.trace("toCurrency: {}", toCurrency);
		if (toCurrency == null) {
			LOGGER.debug("toCurrency does not exist, so don't need to do anything");
			return;
		}
		
		Currency temp = fromCurrency;
		fromCurrency = toCurrency;
		toCurrency = temp;
		LOGGER.debug("swapped value fromCurrency: {}, toCurrency: {}", fromCurrency, toCurrency);
		
		updateFromCurrency(fromCurrency);
		updateToCurrency(toCurrency);
		
		LOGGER.trace("Finished Swapping the Currency values");
	}
	
	private void handleConvertButtonClick() {
		LOGGER.trace("will handle Convert button click");
		
		String amountText = amountTextField.getText();
		boolean isNumeric = StringUtil.isNumber(amountText);
		if (!isNumeric) {
			LOGGER.debug("amountText is not numeric, so won't do anything else");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_AMOUNT_INVALID);
			return;
		}
		
		float value = Float.parseFloat(amountText);
		
		LOGGER.trace("fromCurrency: {}", fromCurrency);
		if (fromCurrency == null) {
			LOGGER.debug("fromCurrency is not present, so won't do anything else");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_FROM_CURRENCY_NOT_SELECTED);
			return;
		}
		
		LOGGER.trace("toCurrency: {}", toCurrency);
		if (toCurrency == null) {
			LOGGER.debug("toCurrency is not present, so won't do anything else");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_TO_CURRENCY_NOT_SELECTED);
			return;
		}
		
		LOGGER.trace("historyDatePicker: {}", historyDatePicker.getValue());
		if (historyDatePicker.getValue() == null) {
			LOGGER.debug("history date is not present, so won't do anything else");
			errorHandlingService.handleMessage(
					Constants.ERROR_TITLE, 
					Constants.ERROR_HISTORY_DATE_NOT_SELECTED);
			return;
		}
		
		try {
			ExchangeRate exchangeRate = currencyAPIService.getExchangeRate(
					fromCurrency, 
					toCurrency,
					historyDatePicker.getValue(),
					value);
			
			resultAnchorPane.setVisible(true);
			
			handleResponse(exchangeRate);
		} catch (Exception e) {
			LOGGER.error("Exception in handleConvertButtonClick(): {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
		
	}
	
	private void handleResponse(ExchangeRate exchangeRate) {
		LOGGER.trace("will handle exchangeRate: {}", exchangeRate);

		Currency fromCurreny = exchangeRate.getFromCurrency();
		Currency toCurreny = exchangeRate.getToCurrency();
		
		Image fromCountryFlagImage = new Image(fromCurreny.getFlagIconPath());
		Image toCountryFlagImage = new Image(toCurreny.getFlagIconPath());
		
		convertedFromCountryImageView.setImage(fromCountryFlagImage);
		convertedToCountryImageView.setImage(toCountryFlagImage);
		
		convertedFromCurrency.setText(fromCurreny.getName());
		convertedToCurrency.setText(toCurreny.getName());
		
		String valueString = String.valueOf(exchangeRate.getValue());
		String convertedValueString = String.valueOf(exchangeRate.getConvertedValue());
		value.setText(valueString);
		date.setText(exchangeRate.getDateString());
		convertedValue.setText(convertedValueString);
	}
	
	private void updateFromCurrency(Currency currency) {
		LOGGER.trace("will update fromCurrency: {}", currency);
		
		fromCurrency = currency;
		
		fromCountryLabel.setText(currency.getName());
		
		Image image = new Image(currency.getFlagIconPath());
		fromCountryImageView.setImage(image);
	}
	
	private void updateToCurrency(Currency currency) {
		LOGGER.trace("will update toCurrency: {}", currency);
		
		toCurrency = currency;
		
		toCountryLabel.setText(currency.getName());
		
		Image image = new Image(currency.getFlagIconPath());
		toCountryImageView.setImage(image);
	}
	
	private void resetFromCurrency() {
		LOGGER.trace("Resetting the From Currency");
		fromCurrency = null;
		fromCountryImageView.setImage(null);
		fromCountryLabel.setText(Constants.BLANK_STRING);
		
		fromCountry.setValue(null);
	}
	
	private void resetToCurrency() {
		LOGGER.trace("Resetting the To Currency");
		toCurrency = null;
		toCountryImageView.setImage(null);
		toCountryLabel.setText(Constants.BLANK_STRING);
		
		toCountry.setValue(null);
	}
	
	/*
	private void initializeComboxBoxes() {
		LOGGER.trace("Initialising the Combo Boxes");
		
		ComboBoxService comboBoxService = ComboBoxServiceImpl.getInstance();
		
		final Callback<ListView<Currency>, ListCell<Currency>> callback = comboBoxService.getCurrencyCallback();
		
		fromCountry.setCellFactory(callback);
		toCountry.setCellFactory(callback);

		final ListCell<Currency> fromListCell = comboBoxService.getCurrencyListCell();
		final ListCell<Currency> toListCell = comboBoxService.getCurrencyListCell();
		
		fromCountry.setButtonCell(fromListCell);
		toCountry.setButtonCell(toListCell);
		
		final StringConverter<Currency> stringConverter = comboBoxService.getCurrencyStringConverter(fromCountry);
		fromCountry.setConverter(stringConverter);

		CurrencyService currencyService = CurrencyServiceImpl.getInstance();
		final List<Currency> currencies = currencyService.getAllCurrencies();
		
		fromCountry.getItems().addAll(currencies);
		toCountry.getItems().addAll(currencies);
		
		final AutoCompleteComparator<Currency> comparator = comboBoxService.getCurrencyAutocompleteComparator();
		comboBoxService.autoComplete(fromCountry, comparator);
		
//		fromCountry.setEditable(true);
//		TextFields.bindAutoCompletion(fromCountry.getEditor(), fromCountry.getItems());
	}
	*/
}
