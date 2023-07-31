package edu.northeastern.csye6200.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GraphTestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GraphTestController.class);

	private static final String LABEL_TEMPLATE = "X: %s, Y: %s";
	
	@FXML
	private Label dataLabel;
	
	@FXML
	private LineChart<String, Number> lineChart;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button clearButton;
	
	@FXML
	public void initialize() {
		LOGGER.trace("Initialising the Controller");
		initListeners();
		LOGGER.trace("The controller has been initialised");
	}
	
	private void initListeners() {
		LOGGER.trace("Initialising the listeners");
		
		initAddButtonListener();
		initClearButtonListener();
		
		LOGGER.trace("All initialisers have been initialised");
	}
	
	private void initAddButtonListener() {
		LOGGER.trace("Initialising the add button listener");
		
		addButton.setOnAction(event -> addButtonClicked());
		
		LOGGER.trace("add button listener has been initialised");
	}
	
	private void initClearButtonListener() {
		LOGGER.trace("Initialising the clear button listener");
		
		clearButton.setOnAction(event -> clearButtonClicked());
		
		LOGGER.trace("clear button listener has been initialised");
	}
	
	@SuppressWarnings("unchecked")
	private void addButtonClicked() {
		LOGGER.trace("add button has been clicked");
		
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		ObservableList<Data<String, Number>> seriesData = series.getData();
		seriesData.add(new XYChart.Data<String, Number>("Jan", 200));
		seriesData.add(new XYChart.Data<String, Number>("Feb", 500));
		seriesData.add(new XYChart.Data<String, Number>("Mar", 300));
		seriesData.add(new XYChart.Data<String, Number>("Apr", 600));
		series.setName("Pay 1");
		
		ObservableList<Series<String, Number>> data = lineChart.getData();
		data.clear();
		data.addAll(series);
		
		for (final XYChart.Data<String, Number> dataPoint : seriesData) {
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					handleMouseClickEvent(dataPoint);
				}
			};
			
			Node node = dataPoint.getNode();
			node.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
		}
		
		EventHandler<MouseEvent> onMouseEnteredSeriesListener = new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				LOGGER.trace("mouse entered with event: {}", event);
				((Node)(event.getSource())).setCursor(Cursor.HAND);
			}

		};
		
		EventHandler<MouseEvent> onMouseExitedSeriesListener = new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				LOGGER.trace("mouse exited with event: {}", event);
				((Node)(event.getSource())).setCursor(Cursor.DEFAULT);
			}

		};
	    
		Node node = series.getNode();
		node.setOnMouseEntered(onMouseEnteredSeriesListener);
		node.setOnMouseExited(onMouseExitedSeriesListener);
		
		lineChart.setOnMouseDragEntered(onMouseEnteredSeriesListener);
		lineChart.setOnMouseExited(onMouseExitedSeriesListener);
	}
	
	private void clearButtonClicked() {
		LOGGER.trace("clear button has been clicked");
		
		ObservableList<Series<String, Number>> data = lineChart.getData();
		data.clear();
	}

	private void handleMouseClickEvent(XYChart.Data<String, Number> dataPoint) {
		String xValue = dataPoint.getXValue();
		Number yValue = dataPoint.getYValue();
		String text = String.format(LABEL_TEMPLATE, xValue, yValue);
		dataLabel.setText(text);
	}
}
