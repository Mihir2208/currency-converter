package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ComingSoonPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComingSoonPageController.class);

	@FXML
	private AnchorPane root;
	
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
	}
}
