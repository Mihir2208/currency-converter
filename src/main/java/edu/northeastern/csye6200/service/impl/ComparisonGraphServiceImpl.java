package edu.northeastern.csye6200.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.Main;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.controller.ComparisonGraphPageController;
import edu.northeastern.csye6200.model.CurrencyCompareResponse;
import edu.northeastern.csye6200.service.ComparisonGraphService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ComparisonGraphServiceImpl implements ComparisonGraphService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonGraphServiceImpl.class);

	private static ComparisonGraphService instance;
	
	private Stage stage;
	private ComparisonGraphPageController controller;
	
	private ErrorHandlingService errorHandlingService;

	private ComparisonGraphServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		initializeStage();
	}
	
	public static ComparisonGraphService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ComparisonGraphServiceImpl();
		return instance;
	}
	
	private void initializeStage() {
		LOGGER.info("Initializing the stage");
		
		try {
			final String FILE_NAME = "/pages/ComparisonGraphPage.fxml";
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(FILE_NAME));
			Parent root = loader.load();
			controller = loader.getController();
			
			Scene scene = new Scene(root);
			
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setTitle(Constants.COMPARISON_GRAPH_TITLE);
		} catch (Exception e) {
			LOGGER.error("Exception occured during stage initialization: {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
	}
	
	@Override
	public void show() {
		LOGGER.debug("show the window");
		stage.show();
	}
	
	@Override
	public void close() {
		LOGGER.debug("close the window");
		stage.close();
	}
	
	@Override
	public void drawGraph(CurrencyCompareResponse response) {
		LOGGER.debug("drawing the graph");
		controller.drawGraph(response);
	}
}
