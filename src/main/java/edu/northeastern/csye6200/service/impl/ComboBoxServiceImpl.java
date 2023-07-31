package edu.northeastern.csye6200.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.comparator.AutoCompleteComparator;
import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.model.Currency;
import edu.northeastern.csye6200.service.ComboBoxService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ComboBoxServiceImpl implements ComboBoxService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComboBoxServiceImpl.class);
	
	private static ComboBoxService instance;
	
	private ComboBoxServiceImpl() {
		LOGGER.debug("Initializing Combo Box Service");
	}

	public static ComboBoxService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new ComboBoxServiceImpl();
		return instance;
	}
	
	/*
	@Override
	public <T> void autoComplete(ComboBox<T> comboBox, AutoCompleteComparator<T> comparatorMethod) {
		LOGGER.trace(
				"integrating auto-complete into comboBox: {}, with comparatorMethod: {}", 
				comboBox, 
				comparatorMethod);

        comboBox.setEditable(true);
 
        ObservableList<T> data = comboBox.getItems();
        SingleSelectionModel<T> selectionModel = comboBox.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();

        TextField editor = comboBox.getEditor();
        String editorText = editor.getText();

        ReadOnlyBooleanProperty property = editor.focusedProperty();
        property.addListener(observable -> {
        	if (selectedIndex < 0) {
				editor.setText(null);
            }
        });
        
        EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
			
        	private boolean moveCaretToPos = false;
            private int caretPos;
        	
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				
				if (code == KeyCode.UP) {
                    caretPos = -1;
                    
                    if (editorText != null) {
                    	int textLength = editorText.length();
                        moveCaret(textLength);
                    }
                    
                    return;
                } else if (code == KeyCode.DOWN) {
                    
                	if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                	
                    caretPos = -1;
                    if (editorText != null) {
                    	int textLength = editorText.length();
                        moveCaret(textLength);
                    }
                    
                    return;
                } else if (code == KeyCode.BACK_SPACE) {
                	
                    if (editorText != null) {
                        moveCaretToPos = true;
                        caretPos = editor.getCaretPosition();
                    }
                    
                } else if (code == KeyCode.DELETE) {
                	
                    if (editorText != null) {
                        moveCaretToPos = true;
                        caretPos = editor.getCaretPosition();
                    }
                    
                } else if (code == KeyCode.ENTER) {
                    return;
                }

                if (code == KeyCode.RIGHT 
                		|| code == KeyCode.LEFT 
                		|| code.equals(KeyCode.SHIFT) 
                		|| code.equals(KeyCode.CONTROL)
                        || event.isControlDown() 
                        || code == KeyCode.HOME
                        || code == KeyCode.END 
                        || code == KeyCode.TAB) {
                    return;
                }

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T aData : data) {
                    if (aData != null 
                    		&& editorText != null 
                    		&& comparatorMethod.matches(editorText, aData)) {
                        list.add(aData);
                    }
                }
                
                String t = StringConstants.BLANK_STRING;
                if (editorText != null) {
                    t = editorText;
                }

                comboBox.setItems(list);
                editor.setText(t);
                
                if (!moveCaretToPos) {
                    caretPos = -1;
                }
                
                moveCaret(t.length());
                if (!list.isEmpty()) {
                    comboBox.show();
                }
			}
			
			private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    editor.positionCaret(textLength);
                } else {
                	editor.positionCaret(caretPos);
                }
                
                moveCaretToPos = false;
            }
		};
        
        comboBox.addEventHandler(KeyEvent.KEY_PRESSED, t -> comboBox.hide());
        comboBox.addEventHandler(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
		
		LOGGER.trace(
				"integrating DONE with auto-complete for comboBox: {}, with comparatorMethod: {}", 
				comboBox, 
				comparatorMethod);
	}
	*/
	
	public <T> void autoComplete(ComboBox<T> comboBox, AutoCompleteComparator<T> comparatorMethod) {
        ObservableList<T> data = comboBox.getItems();

        comboBox.setEditable(true);
        comboBox.getEditor().focusedProperty().addListener(observable -> {
            if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
                comboBox.getEditor().setText(null);
            }
        });
        comboBox.addEventHandler(KeyEvent.KEY_PRESSED, t -> comboBox.hide());
        comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            private boolean moveCaretToPos = false;
            private int caretPos;

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    if (comboBox.getEditor().getText() != null) {
                        moveCaret(comboBox.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                    caretPos = -1;
                    if (comboBox.getEditor().getText() != null) {
                        moveCaret(comboBox.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (comboBox.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = comboBox.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.DELETE) {
                    if (comboBox.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = comboBox.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.ENTER) {
                    return;
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T aData : data) {
                    if (aData != null && comboBox.getEditor().getText() != null && comparatorMethod.matches(comboBox.getEditor().getText(), aData)) {
                        list.add(aData);
                    }
                }
                String t = "";
                if (comboBox.getEditor().getText() != null) {
                    t = comboBox.getEditor().getText();
                }

                comboBox.setItems(list);
                comboBox.getEditor().setText(t);
                if (!moveCaretToPos) {
                    caretPos = -1;
                }
                moveCaret(t.length());
                if (!list.isEmpty()) {
                    comboBox.show();
                }
            }

            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    comboBox.getEditor().positionCaret(textLength);
                } else {
                    comboBox.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }
        });
    }

	@Override
	public <T> T getValue(ComboBox<T> comboBox) {
		LOGGER.trace("getting value from comboBox: {}", comboBox);
		
		SingleSelectionModel<T> model = comboBox.getSelectionModel();
		int selectedIndex = model.getSelectedIndex();
		LOGGER.trace("selectedIndex: {}", selectedIndex);
		
		if (selectedIndex < 0) {
			LOGGER.trace("No value selected in ComboBox, will return NULL");
			return null;
		}
		
		ObservableList<T> items = comboBox.getItems();
		T selectedItem = items.get(selectedIndex);
		LOGGER.trace("selected item is: {}", selectedItem);
		return selectedItem;
	}
	
	@Override
	public ListCell<Currency> getCurrencyListCell() {
		ListCell<Currency> listcell = new ListCell<>() {
			
			@Override
			protected void updateItem(Currency currency, boolean empty) {
				super.updateItem(currency, empty);
				
				if (currency == null || empty) {
					setGraphic(null);
					setGraphic(null);
				} else {
					String name = currency.getName();
					String flagIconPath = currency.getFlagIconPath();
					
					ImageView imageView = new ImageView(flagIconPath);
					
					setGraphic(imageView);
					setText(name);
				}
			};
		};
		
		return listcell;
	}

	@Override
	public Callback<ListView<Currency>, ListCell<Currency>> getCurrencyCallback() {
		Callback<ListView<Currency>, ListCell<Currency>> callBack = new Callback<>() {
			
			@Override
			public ListCell<Currency> call(ListView<Currency> param) {
				ListCell<Currency> listCell = new ListCell<Currency>() {
					
					@Override
					protected void updateItem(Currency currency, boolean empty) {
						super.updateItem(currency, empty);
						
						if (currency == null || empty) {
							setGraphic(null);
							setText(null);
						} else {
							String name = currency.getName();
							String flagIconPath = currency.getFlagIconPath();
							
							ImageView imageView = new ImageView(flagIconPath);
							
							setGraphic(imageView);
							setText(name);
						}
						
					};
				};
				
				return listCell;
			}
		};
		
		return callBack;
	}
	
	@Override
	public StringConverter<Currency> getCurrencyStringConverter(ComboBox<Currency> comboBox) {
		
		StringConverter<Currency> converter = new StringConverter<Currency>() {
			
			@Override
			public String toString(Currency currency) {
				if (currency == null) {
					return Constants.BLANK_STRING;
				}
				
				String name = currency.getName();
				return name;
			}
			
			@Override
			public Currency fromString(String string) {
				ObservableList<Currency> items = comboBox.getItems();
				
				Currency currency = items
					.stream()
					.filter(object -> object.getName().equals(string))
					.findFirst()
					.orElse(null);
				return currency;
			}
		};
		
		return converter;
	}
	
	@Override
	public AutoCompleteComparator<Currency> getCurrencyAutocompleteComparator() {
		AutoCompleteComparator<Currency> comparator = new AutoCompleteComparator<Currency>() {
			
			@Override
			public boolean matches(String typedText, Currency itemToCompare) {
				String typedTextLowerCase = typedText.toLowerCase();
				String nameLowerCase = itemToCompare.getName().toLowerCase();
				String code = itemToCompare.getCountryCode().toLowerCase();
				
				boolean result = nameLowerCase.contains(typedTextLowerCase) || code.contains(typedTextLowerCase) ;
				return result;
			}
		};
		
		return comparator;
	}
	
	@Override
	public <T> void filter(
			List<T> completeList,
			ComboBox<T> comboBox, 
			AutoCompleteComparator<T> comparatorMethod, 
			String searchText) {
		ObservableList<T> items = FXCollections.observableArrayList(completeList);
		FilteredList<T> filteredItems = new FilteredList<T>(items);
		filteredItems.setPredicate(item -> comparatorMethod.matches(searchText, item));
		comboBox.setItems(filteredItems);
	}
}
