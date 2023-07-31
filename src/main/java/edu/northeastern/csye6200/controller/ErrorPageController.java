package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ErrorPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorPageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
	}

	@FXML
	private Label errorMessage;

	public void setErrorText(String text) {
		errorMessage.setText(text);
	}

	@FXML
	private void close() {
		errorMessage.getScene().getWindow().hide();
	}
}
