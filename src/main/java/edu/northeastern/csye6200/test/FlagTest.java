package edu.northeastern.csye6200.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * (Display country flag and flag description)
 *    Listing 16.4, ComboBoxDemo.java, gives a program that
 *    lets the user view a country’s flag image and description
 *    by selecting the country from a combo box.
 *    The description is a string coded in the program.
 *    Rewrite the program to read the text description from a file.
 *    Suppose that the descriptions are stored in the files description0.txt, . . . , and
 *    description8.txt under the text directory for the nine countries Canada,
 *    China, Denmark, France, Germany, India, Norway, United Kingdom,
 *    and United States, in this order.
 *
 * Created by luizsa on 9/29/14.
 */
public class FlagTest extends Application {

    // Declare an array of Strings for flag titles
    private String[] flagTitles = {
    		"Canada", 
    		"China", 
    		"Denmark",
            "Euro", 
            "India", 
            "Pakistan", 
            "United States of America"};

    private static final String FLAG_BASE_LOCATION = "src/main/resources/icons/rectangular-flags/";
    
    // Declare an ImageView array for the national flags of 9 countries
    private ImageView[] flagImage = {
    		new ImageView(FLAG_BASE_LOCATION + "cad.png"),
            new ImageView(FLAG_BASE_LOCATION + "cny.png"),
            new ImageView(FLAG_BASE_LOCATION + "dkk.png"),
            new ImageView(FLAG_BASE_LOCATION + "eur.png"),
            new ImageView(FLAG_BASE_LOCATION + "inr.png"),
            new ImageView(FLAG_BASE_LOCATION + "pkr.png"),
            new ImageView(FLAG_BASE_LOCATION + "usd.png")
           };

    // Declare an array of strings for flag descriptions
    private String[] flagDescription = new String[9];

    // Declare and create a description pane
    private DescriptionPane descriptionPane = new DescriptionPane();

    // Create a combo box for selecting countries
    private ComboBox<String> cbo = new ComboBox<>(); // flagTitles);

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Set text description

        for (int i = 0; i < flagDescription.length; i++) {
            Scanner input;
            String s = "";

            try {
                input = new Scanner(new File("src/Text Files/description" + i + ".txt"));

                while (input.hasNext()) {
                    s += input.nextLine() + "\n";
                }
                flagDescription[i] = s;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        // Set the first country (Canada) for display
        setDisplay(0);

        // Add combo box and description pane to the border pane
        BorderPane pane = new BorderPane();

        BorderPane paneForComboBox = new BorderPane();
        paneForComboBox.setLeft(new Label("Select a country: "));
        paneForComboBox.setCenter(cbo);
        pane.setTop(paneForComboBox);
        cbo.setPrefWidth(400);
        cbo.setValue("Canada");

        ObservableList<String> items =
                FXCollections.observableArrayList(flagTitles);
        cbo.getItems().addAll(items);
        pane.setCenter(descriptionPane);

        // Display the selected country
        cbo.setOnAction(e -> setDisplay(items.indexOf(cbo.getValue())));

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 450, 170);
        primaryStage.setTitle("ComboBoxDemo"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /** Set display information on the description pane */
    public void setDisplay(int index) {
        descriptionPane.setTitle(flagTitles[index]);
        descriptionPane.setImageView(flagImage[index]);
        descriptionPane.setDescription(flagDescription[index]);
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private class DescriptionPane extends BorderPane {
        /** Label for displaying an image and a title */
        private Label lblImageTitle = new Label();

        /** Text area for displaying text */
        private TextArea taDescription = new TextArea();

        public DescriptionPane() {
            // Center the icon and text and place the text under the icon
            lblImageTitle.setContentDisplay(ContentDisplay.TOP);
            lblImageTitle.setPrefSize(200,  100);

            // Set the font in the lbl and the text field
            lblImageTitle.setFont(new Font("SansSerif", 16));
            taDescription.setFont(new Font("Serif", 14));

            taDescription.setWrapText(true);
            taDescription.setEditable(false);

            // Create a scroll pane to hold the text area
            ScrollPane scrollPane = new ScrollPane(taDescription);

            // Place lbl and scroll pane in the border pane
            setLeft(lblImageTitle);
            setCenter(scrollPane);
            setPadding(new Insets(5, 5, 5, 5));
        }

        /** Set the title */
        public void setTitle(String title) {
            lblImageTitle.setText(title);
        }

        /** Set the image view */
        public void setImageView(ImageView icon) {
            lblImageTitle.setGraphic(icon);
        }

        /** Set the text description */
        public void setDescription(String text) {
            taDescription.setText(text);
        }
    }
}

