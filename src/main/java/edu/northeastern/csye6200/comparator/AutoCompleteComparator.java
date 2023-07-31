package edu.northeastern.csye6200.comparator;

/**
 * Interface to compare the objects in ComboBox based
 * on the typed String value
 * 
 * <br><br>
 * 
 * Reference: https://stackoverflow.com/questions/19010619/javafx-filtered-combobox
 * 
 * */
public interface AutoCompleteComparator<T> {
	
	boolean matches(String typedText, T objectToCompare);
	
}
