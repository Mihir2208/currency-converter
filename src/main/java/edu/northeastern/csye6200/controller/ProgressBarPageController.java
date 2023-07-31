package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

public class ProgressBarPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressBarPageController.class);
	
	@FXML
	private Text text;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private ProgressIndicator progressIndicator;

	private List<String> textValues;
	
	private int counter;
	private int totalProgress;
	private int progress;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		
		textValues = new ArrayList<>(5);
		textValues.add(Constants.DATA_LOADING_1);
		textValues.add(Constants.DATA_LOADING_2);
		textValues.add(Constants.DATA_LOADING_3);
		textValues.add(Constants.DATA_LOADING_4);
		textValues.add(Constants.DATA_LOADING_5);
		
		counter = 0;
		totalProgress = 0;
		progress = 0;
		
		progressBar.setProgress(0);
		progressIndicator.setProgress(0);
	}
	
	public void reset() {
		LOGGER.info("Reset all values");
		
		counter = 0;
		totalProgress = 0;
		progress = 0;
		
		progressBar.setProgress(0);
		progressIndicator.setProgress(0);
	}
	
	public void setTotalProgress(int totalProgress) {
		LOGGER.info("setting the total progress to: {}", totalProgress);
		this.totalProgress = totalProgress;
	}
	
	public void setProgress(int progress) {
		LOGGER.info("setting the progress to: {}", progress);
		this.progress = progress;
		updateView();
	}
	
	public void incrementProgress() {
		progress++;
		LOGGER.info("increment progress: {}", progress);
		updateView();
	}
	
	private void updateView() {
		float progressPercentage = (float) progress / totalProgress;
		LOGGER.trace("will update the view {}/{} - {} %", progress, totalProgress, progressPercentage);
		
		counter++;
		int length = textValues.size();
		boolean counterAtEnd = (counter == length);
		LOGGER.trace(
				"counter: {}, length: {}, counterAtEnd: {}",
				counter,
				length,
				counterAtEnd);
		if (counterAtEnd) {
			LOGGER.trace("counter is it add, will reset counter: {}", counter);
			counter = 0;
		}
		
		String loadingText = textValues.get(counter);
		LOGGER.trace("loadingText: {}", loadingText);
		
		text.setText(loadingText);
		progressBar.setProgress(progressPercentage);
		progressIndicator.setProgress(progressPercentage);
	}
	
	/** 
	 * This can be run directly from eclipse and values traced
	 * both in eclipse console and external log file 
	 * */ 
	public static void main(String[] args) {
		int a = 1;
		int b = 100;
		float c = a/b;
		LOGGER.trace("test value: {}", c);
	}
}
