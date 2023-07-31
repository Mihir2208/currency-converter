package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class DashboardPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPageController.class);
	
	private String currentCategory;
	
	@FXML
	private Text appVersionText;
	
	@FXML
	private ImageView currencyImageView;
	
	@FXML
	private Button convertButton;
	
	@FXML
	private ImageView convertImageView;
	
	@FXML
	private Button historyButton;
	
	@FXML
	private ImageView historyImageView;
	
	@FXML
	private Button compareButton;
	
	@FXML
	private ImageView compareImageView;
	
	@FXML
	private Button settingsButton;
	
	@FXML
	private ImageView settingsImageView;
	
	@FXML
	private Button comingSoonButton;

	@FXML
	private ImageView comingSoonImageView;

	@FXML
	private Text text;
	
	@FXML
	private AnchorPane convertPage;

	@FXML
	private ConvertPageController convertPageController;
	
	@FXML
	private AnchorPane historyPage;
	
	@FXML	
	private HistoryPageController historyPageController;
	
	@FXML
	private AnchorPane comparePage;
	
	@FXML	
	private ComparePageController comparePageController;
	
	@FXML
	private AnchorPane settingsPage;
	
	@FXML
	private SettingsPageController settingsPageController;
	
	@FXML
	private AnchorPane comingSoonPage;
	
	@FXML
	private ComingSoonPageController comingSoonPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		
		initializeImageViews();
		initializeListeners();
		initializeAppVersion();
		
		LOGGER.trace("convertPage: {}", convertPage);
		LOGGER.trace("convertPageController: {}", convertPageController);
		
		LOGGER.trace("historyPage: {}", historyPage);
		LOGGER.trace("historyPageController: {}", historyPageController);
		
		LOGGER.trace("comparePage: {}", comparePage);
		LOGGER.trace("comparePageController: {}", comparePageController);
		
		LOGGER.trace("settingsPage: {}", settingsPage);
		LOGGER.trace("settingsPageController: {}", settingsPageController);
		
		LOGGER.trace("comingSoonPage: {}", comingSoonPage);
		LOGGER.trace("comingSoonPageController: {}", comingSoonPageController);
		
		currentCategory = Constants.CATEGORY_CONVERT;
		switchCategories();
	}
	
	private void initializeImageViews() {
		LOGGER.trace("Initialising the Image Views");
		
		initCurrencyImageView();
		initConvertImageView();
		initHistoryImageView();
		initCompareImageView();
		initSettingsImageView();
		initComingSoonImageView();
		
		LOGGER.trace("Finished Initialising all the Image Views");
	}
	
	private void initializeListeners() {
		LOGGER.trace("Initialising the listeners");
		
		initializeConvertButtonClickListener();
		initializeHistoryButtonClickListener();
		initializeCompareButtonClickListener();
		initializeSettingsButtonClickListener();
		initializeComingSoonButtonClickListener();
		
		LOGGER.trace("all the listeners have been initialized");
	}
	
	private void initializeAppVersion() {
		LOGGER.trace("Initialising the App Version");
		
		appVersionText.setText(Constants.APP_VERSION);
		
		LOGGER.trace("App Version has been initialized to: {}", Constants.APP_VERSION);
	}

	private void initCurrencyImageView() {
		LOGGER.trace("Initialising the currency image view");
		
		final String IMAGE_PATH = "/icons/currency.jpg"; 
		Image image = new Image(IMAGE_PATH);
		currencyImageView.setImage(image);
		
		LOGGER.trace("Currency image view has been initialized");
	}
	
	private void initConvertImageView() {
		LOGGER.trace("Initialising the convert image view");
		
		final String IMAGE_PATH = "/icons/convert-icon.png"; 
		Image image = new Image(IMAGE_PATH);
		convertImageView.setImage(image);
		
		LOGGER.trace("convert image view has been initialized");
	}
	
	private void initHistoryImageView() {
		LOGGER.trace("Initialising the history image view");
		
		final String IMAGE_PATH = "/icons/history-icon.png"; 
		Image image = new Image(IMAGE_PATH);
		historyImageView.setImage(image);
		
		LOGGER.trace("history image view has been initialized");
	}
	
	private void initCompareImageView() {
		LOGGER.trace("Initialising the compare image view");
		
		final String IMAGE_PATH = "/icons/compare-icon.png"; 
		Image image = new Image(IMAGE_PATH);
		compareImageView.setImage(image);
		
		LOGGER.trace("compare image view has been initialized");
	}
	
	private void initSettingsImageView() {
		LOGGER.trace("Initialising the settings image view");
		
		final String IMAGE_PATH = "/icons/settings-icon.png"; 
		Image image = new Image(IMAGE_PATH);
		settingsImageView.setImage(image);
		
		LOGGER.trace("settings image view has been initialized");
	}
	
	private void initComingSoonImageView() {
		LOGGER.trace("Initialising the coming soon image view");
		
		final String IMAGE_PATH = "/icons/coming-soon-icon.png"; 
		Image image = new Image(IMAGE_PATH);
		comingSoonImageView.setImage(image);
		
		LOGGER.trace("settings image view has been initialized");
	}

	private void initializeConvertButtonClickListener() {
		LOGGER.trace("initializing convert button click listener");
		
		convertButton.setOnMouseClicked(event -> handleConvertButtonClick());
		
		LOGGER.trace("convert button click listener has been initialized");
	}
	
	private void initializeHistoryButtonClickListener() {
		LOGGER.trace("initializing history button click listener");
		
		historyButton.setOnMouseClicked(event -> handleHistoryButtonClick());
		
		LOGGER.trace("history button click listener has been initialized");
	}
	
	private void initializeCompareButtonClickListener() {
		LOGGER.trace("initializing compare button click listener");
		
		compareButton.setOnMouseClicked(event -> handleCompareButtonClick());
		
		LOGGER.trace("compare button click listener has been initialized");
	}
	
	private void initializeSettingsButtonClickListener() {
		LOGGER.trace("initializing settings button click listener");
		
		settingsButton.setOnMouseClicked(event -> handleSettingsButtonClick());
		
		LOGGER.trace("settings button click listener has been initialized");
	}
	
	private void initializeComingSoonButtonClickListener() {
		LOGGER.trace("initializing coming soon button click listener");
		
		comingSoonButton.setOnMouseClicked(event -> handleComingSoonButtonClick());
		
		LOGGER.trace("coming soon button click listener has been initialized");
	}
	
	private void handleConvertButtonClick() {
		LOGGER.trace("Convert button has been clicked");
	
		boolean changed = isCategoryChange(Constants.CATEGORY_CONVERT);
		if (!changed) {
			LOGGER.trace("category is unchanged, so will not do anything");
			return;
		}
		
		text.setText(Constants.CATEGORY_CONVERT);
		
		currentCategory = Constants.CATEGORY_CONVERT;
		
		switchCategories();
	}
	
	private void handleHistoryButtonClick() {
		LOGGER.trace("history button has been clicked");
		
		boolean changed = isCategoryChange(Constants.CATEGORY_HISTORY);
		if (!changed) {
			LOGGER.trace("category is unchanged, so will not do anything");
			return;
		}
		
		text.setText(Constants.CATEGORY_HISTORY);
		
		currentCategory = Constants.CATEGORY_HISTORY;
		
		switchCategories();
	}
	
	private void handleCompareButtonClick() {
		LOGGER.trace("compare button has been clicked");
		
		boolean changed = isCategoryChange(Constants.CATEGORY_COMPARE);
		if (!changed) {
			LOGGER.trace("category is unchanged, so will not do anything");
			return;
		}
		
		text.setText(Constants.CATEGORY_COMPARE);
		
		currentCategory = Constants.CATEGORY_COMPARE;
		
		switchCategories();
	}
	
	private void handleSettingsButtonClick() {
		LOGGER.trace("settings button has been clicked");
		
		boolean changed = isCategoryChange(Constants.CATEGORY_SETTINGS);
		if (!changed) {
			LOGGER.trace("category is unchanged, so will not do anything");
			return;
		}
		
		text.setText(Constants.CATEGORY_SETTINGS);
		
		currentCategory = Constants.CATEGORY_SETTINGS;
		
		switchCategories();
	}
	
	private void handleComingSoonButtonClick() {
		LOGGER.trace("coming soon button has been clicked");
		
		boolean changed = isCategoryChange(Constants.CATEGORY_COMING_SOON);
		if (!changed) {
			LOGGER.trace("category is unchanged, so will not do anything");
			return;
		}
		
		text.setText(Constants.CATEGORY_COMING_SOON);
		
		currentCategory = Constants.CATEGORY_COMING_SOON;
		
		switchCategories();
	}
	
	private boolean isCategoryChange(String category) {
		LOGGER.trace("checking if category changes after chossing category: {}", category);
		boolean changed = !currentCategory.equals(category);
		LOGGER.trace("currentCategory: {}, category: {}, changed: {}", currentCategory, category, changed);
		return changed;
	}
	
	private void switchCategories() {
		LOGGER.debug("switching the categories currentCategory: {}", currentCategory);
		
		convertPageController.hide();
		historyPageController.hide();
		comparePageController.hide();
		settingsPageController.hide();
		comingSoonPageController.hide();
		
		switch (currentCategory) {
			case Constants.CATEGORY_CONVERT:
				convertPageController.show();
				break;
			
			case Constants.CATEGORY_HISTORY:
				historyPageController.show();
				break;
			
			case Constants.CATEGORY_COMPARE:
				comparePageController.show();
				break;
			
			case Constants.CATEGORY_SETTINGS:
				settingsPageController.show();
				settingsPageController.showLoginPane();
				settingsPageController.hideAdminPane();
				break;
				
			case Constants.CATEGORY_COMING_SOON:
				comingSoonPageController.show();
				break;
		
			default:
				// This should never happen but doing it for safety
				LOGGER.error("will throw RuntimeException, cannot handle currentCategory: {}", currentCategory);
				throw new RuntimeException(Constants.SOMETHING_WENT_WRONG);
		}
	}
}
