package edu.northeastern.csye6200.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.Main;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.controller.ErrorPageController;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorHandlingServiceImpl implements ErrorHandlingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingServiceImpl.class);

	private static ErrorHandlingService instance;
	
	private ErrorHandlingServiceImpl() {
		LOGGER.debug("Initiliasing the service");
	}
	
	public static ErrorHandlingService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ErrorHandlingServiceImpl();
		return instance;
	}

	@Override
	public void handleError(Throwable throwable) {
		try {
			StringWriter errorMsg = new StringWriter();
			throwable.printStackTrace(new PrintWriter(errorMsg));
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			final String ERROR_FILE_NAME = "/pages/ErrorPage.fxml";
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(ERROR_FILE_NAME));
			Parent root = loader.load();
			ErrorPageController errorController = loader.getController();
			errorController.setErrorText(errorMsg.toString());
			dialog.setScene(new Scene(root, 500, 500));
			dialog.show();
			
			dialog.setTitle(Constants.SOMETHING_WENT_WRONG);
		} catch (Exception e) {
			LOGGER.error("Exception in showErrorDialog(): {}", e.getMessage(), e);
		}
	}

	@Override
	public void handleMessage(String title, String message) {
		try {
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			
			final String ERROR_FILE_NAME = "/pages/ErrorPage.fxml";
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(ERROR_FILE_NAME));
			Parent root = loader.load();
			ErrorPageController errorController = loader.getController();
			errorController.setErrorText(message);
			dialog.setScene(new Scene(root, 500, 500));
			dialog.show();
			
			dialog.setTitle(title);
		} catch (Exception e) {
			LOGGER.error("Exception in handleMessage(): {}", e.getMessage(), e);
		}
	}
}
