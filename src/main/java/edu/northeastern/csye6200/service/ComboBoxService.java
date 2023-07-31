package edu.northeastern.csye6200.service;

import java.util.List;

import edu.northeastern.csye6200.comparator.AutoCompleteComparator;
import edu.northeastern.csye6200.model.Currency;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public interface ComboBoxService {
	
	/**
	 * Method to add auto completion to a ComboBox
	 * 
	 * <br><br>
	 * 
	 * Reference: https://stackoverflow.com/questions/19010619/javafx-filtered-combobox
	 * */
	<T> void autoComplete(ComboBox<T> comboBox, AutoCompleteComparator<T> comparatorMethod);
	
	/**
	 * Method to get the selected value in the ComboBox
	 * 
	 * <br><br>
	 * 
	 * Reference: https://stackoverflow.com/questions/19010619/javafx-filtered-combobox
	 * */
	<T> T getValue(ComboBox<T> comboBox);
	
	
	/**
	 * 
	 * Return and format the selected
	 * cell of the dropdowns with
	 * image and the curreny name 
	 */
	ListCell<Currency> getCurrencyListCell();
	
	
	/**
	 * 
	 * Return a callback which formats the dropdown cells
	 * with image and currency name on display
	 */
	
	Callback<ListView<Currency>, ListCell<Currency>> getCurrencyCallback();
	
	/**
	 * Return the String Converter for {@link Currency}
	 * to use for autocomplete in the ComboBox
	 * 
	 * <br><br>
	 * 
	 * Reference: https://stackoverflow.com/questions/19010619/javafx-filtered-combobox
	 * 
	 * */
	StringConverter<Currency> getCurrencyStringConverter(ComboBox<Currency> comboBox);
	
	/**
	 * Builds the AutoCompleteComparator for {@link Currency}
	 * objects to use for ComboBox Auto-complete
	 * */
	AutoCompleteComparator<Currency> getCurrencyAutocompleteComparator();
	
	/**
	 * Filters all the items in ComboBox using
	 * the given {@link AutoCompleteComparator} and 
	 * search Text
	 * */
	<T> void filter(
			List<T> completeList,
			ComboBox<T> comboBox, 
			AutoCompleteComparator<T> comparatorMethod, 
			String searchText);
}
