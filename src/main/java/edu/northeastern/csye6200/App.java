package edu.northeastern.csye6200;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.service.CurrencyAPICacheService;
import edu.northeastern.csye6200.service.CurrencyService;
import edu.northeastern.csye6200.service.ErrorHandlingService;
import edu.northeastern.csye6200.service.impl.CurrencyAPICacheServiceImpl;
import edu.northeastern.csye6200.service.impl.CurrencyServiceImpl;
import edu.northeastern.csye6200.service.impl.ErrorHandlingServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	private static ErrorHandlingService errorHandlingService;
	private static CurrencyAPICacheService currencyAPICacheService;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		LOGGER.info("starting the primaryStage: {}", primaryStage);
		
		try {
			Thread.setDefaultUncaughtExceptionHandler(App::showError);
			
			String javafxVersion = SystemInfo.javafxVersion();
	        LOGGER.info("javafxVersion: {}", javafxVersion);

	        String javaVersion = SystemInfo.javaVersion();
	        LOGGER.info("javaVersion: {}", javaVersion);
			
	        String configKey = "log4j.configurationFile";
	        LOGGER.info("configKey: {}", configKey);
	        
	        String configProperty = System.getProperty(configKey);
	        LOGGER.info("configProperty: {}", configProperty);
	        
	        CurrencyService currencyService = CurrencyServiceImpl.getInstance();
	        currencyService.diagnostic();
	        
//	        final String FILE_NAME = "/pages/MainPage.fxml";
	        final String FILE_NAME = "/pages/DashboardPage.fxml";
//	        final String FILE_NAME = "/GraphTest.fxml";
	        LOGGER.info("FILE_NAME: {}", FILE_NAME);
	        
	        Class<?> clazz = getClass();
	        LOGGER.trace("clazz: {}", clazz);
	        
			URL url = clazz.getResource(FILE_NAME);
	        LOGGER.trace("url: {}", url);

			Parent root = FXMLLoader.load(url);
			
//			final double height = 720;
//			final double width = 720;
//			Scene scene = new Scene(root, height, width);
			
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add("stylesheets/application.css");
			
			primaryStage.setTitle(Constants.APP_NAME);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			errorHandlingService = ErrorHandlingServiceImpl.getInstance();
			
			currencyAPICacheService = CurrencyAPICacheServiceImpl.getInstance();
			currencyAPICacheService.rebuildCache();
		} catch (Exception e) {
			LOGGER.error("Exception in start()", e.getMessage(), e);
			System.exit(1);
		}
	}

	@Override
	public void stop() throws Exception {
		LOGGER.info("Application has stopped");
		currencyAPICacheService.saveCache();
		super.stop();
	}
	
	private static void showError(Thread t, Throwable e) {
		LOGGER.error("Something went wrong: {}", e.getMessage(), e);
		
		/*
		boolean isFXApplicationThread = Platform.isFxApplicationThread();
		LOGGER.debug("isFXApplicationThread: {}", isFXApplicationThread);
		
		if (isFXApplicationThread) {
			showErrorDialog(e);
		}
		*/
		
		errorHandlingService.handleError(e);
	}
	
    public static void main(String[] args) {
    	launch();
    }

}