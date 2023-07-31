package edu.northeastern.csye6200.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.Main;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.controller.ProgressBarPageController;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.ProgressBarService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressBarServiceImpl implements ProgressBarService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressBarServiceImpl.class);

	private static ProgressBarService instance;
	
	private Stage stage;
	private ProgressBarPageController progressBarPageController;
	
	private ErrorHandlingService errorHandlingService;
	
	private ProgressBarServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		initializeStage();
	}
	
	public static ProgressBarService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ProgressBarServiceImpl();
		return instance;
	}
	
	private void initializeStage() {
		LOGGER.info("Initializing the stage");
		
		try {
			final String FILE_NAME = "/pages/ProgressBarPage.fxml";
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(FILE_NAME));
			Parent root = loader.load();
			progressBarPageController = loader.getController();
			
			Scene scene = new Scene(root);
			
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setTitle(Constants.PROGRESS_TITLE);
		} catch (Exception e) {
			LOGGER.error("Exception occured during stage initialization: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
	
	@Override
	public void show() {
		LOGGER.debug(
				"will show the progress bar window on stage: {}, progressBarPageController: {}", 
				stage,
				progressBarPageController);
		stage.show();
	}

	@Override
	public void close() {
		LOGGER.debug(
				"will close the progress bar window on stage: {}, progressBarPageController: {}", 
				stage,
				progressBarPageController);
		progressBarPageController.reset();
		stage.close();
	}

	@Override
	public void updateProgressTotal(int total) {
		LOGGER.debug("updating the total progress to total: {}", total);
		progressBarPageController.setTotalProgress(total);
	}

	@Override
	public void updateProgress(int progress) {
		LOGGER.debug("updating the progress: {}", progress);
		progressBarPageController.setProgress(progress);
	}
	
	@Override
	public void incrementProgress() {
		LOGGER.debug("Will increment the progress");
		progressBarPageController.incrementProgress();
	}

}
