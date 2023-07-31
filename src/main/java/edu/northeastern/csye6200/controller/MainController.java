package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

public class MainController implements Initializable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@FXML
	private ConvertPageController convertPageController;
	
	@FXML
	private HistoryPageController historyPageController;
	
	@FXML
	private ComparePageController comparePageController;
	
	@FXML
	private SettingsPageController settingsPageController;
	
	@FXML
	private Tab settingsTab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		LOGGER.trace("convertPageController: {}", comparePageController);
		LOGGER.trace("historyPageController: {}", historyPageController);
		LOGGER.trace("comparePageController: {}", comparePageController);
		LOGGER.trace("settingsPageController: {}", settingsPageController);
		
		initializeListeners();
	}
	
	private void initializeListeners() {
		LOGGER.trace("Initialzing Listeners");
		
		initializeSettingsTabClickListener();
		
		LOGGER.trace("All Listeners Initialized");
	}

	private void initializeSettingsTabClickListener() {
		LOGGER.trace("Initialzing Settings Tab click listener");
		
		settingsTab.setOnSelectionChanged(event -> handleSettingsTabClick());
		
		LOGGER.trace("Settings Tab click listener initialized");
	}

	private void handleSettingsTabClick() {
		LOGGER.trace("Handle Settings tab click");
		
		boolean settingsTabSelected = settingsTab.isSelected();
		LOGGER.trace("settingsTabSelected: {}", settingsTabSelected);
		
		if (settingsTabSelected) {
			settingsPageController.showLoginPane();
			settingsPageController.hideAdminPane();
		}
	}
}
