package edu.northeastern.csye6200.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.model.CurrencyCompareResponse;
import edu.northeastern.csye6200.model.ExchangeRate;
import edu.northeastern.csye6200.service.ComparisonGraphCacheService;
import edu.northeastern.csye6200.service.impl.ComparisonGraphCacheServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.StringConverter;

public class ComparisonGraphPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonGraphPageController.class);

	private ComparisonGraphCacheService graphCacheService;
	
	@FXML
	private LineChart<Number, Number> lineChart;
	
	@FXML
	private NumberAxis xAxis;
	
	@FXML
	private NumberAxis yAxis;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		graphCacheService = ComparisonGraphCacheServiceImpl.getInstance();
		initilizeLineChart();
	}
	
	private void initilizeLineChart() {
		LOGGER.trace("initializing the line chart");
		
//		lineChart.setAnimated(false);
		lineChart.setCreateSymbols(false);
		lineChart.setVerticalGridLinesVisible(false);
		
		final StringConverter<Number> axisFomatter = new StringConverter<Number>() {
			
			@Override
			public String toString(Number axisValue) {
				int index = axisValue.intValue();
				String result = graphCacheService.getCache(index);
				return result;
			}
			
			@Override
			public Number fromString(String string) {
				return null;
			}
		};
		
//		xAxis.setAutoRanging(false);
		xAxis.setMinorTickLength(0);
		xAxis.setTickLabelFormatter(axisFomatter);
		
		LOGGER.trace("Finished initializing the line chart");
	}

	public void drawGraph(CurrencyCompareResponse response) {
		LOGGER.debug("Will start drawing the comparison graph");
		
		Map<String, List<ExchangeRate>> data = response.getData();
		LOGGER.trace("data size: {}", data.size());
		
		Set<Map.Entry<String, List<ExchangeRate>>> entrySet = data.entrySet();
	
		ObservableList<XYChart.Series<Number, Number>> seriesList = FXCollections.observableArrayList();

		for (Map.Entry<String, List<ExchangeRate>> entry : entrySet) {
			String code = entry.getKey();
			List<ExchangeRate> list = entry.getValue();
			LOGGER.trace("Number of entries for code: {} = {}", code, list.size());

			ObservableList<XYChart.Data<Number, Number>> seriesData = FXCollections.observableArrayList();

			int index = 0;
			graphCacheService.resetCache();
			
			for (ExchangeRate exchangeRate : list) {
				String dateString = exchangeRate.getDateString();
				graphCacheService.setCache(index, dateString);
				float convertedValue = exchangeRate.getConvertedValue();
				XYChart.Data<Number, Number> point = new XYChart.Data<>(index, convertedValue);
				seriesData.add(point);
				
				index++;
			}
			
			String seriesName = code.toUpperCase();
			XYChart.Series<Number, Number> series = new XYChart.Series<>(seriesName, seriesData);
			seriesList.add(series);
		}
		
		ObservableList<Series<Number, Number>> observableList = lineChart.getData();
		observableList.clear();
		observableList.addAll(seriesList);
		
		LOGGER.debug("Finished drawing the graph");
	}
	
}
