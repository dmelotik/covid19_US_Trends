/**
 * This JavaFX11 application shows Covid-19 
 * trends in the US. The data comes from Johns 
 * Hopkins University and is updated daily. The 
 * user can filter the data and export it into 
 * a *.txt file in an easy to digest format.
 * 
 * @project covid19_US_Trends (Covid-19 Data Trends)
 * @author Dylan Melotik
 * @email dmelotik@wisc.edu
 * @lecture CS400 002
 */

package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class creates the exportScene from the template BorderPane.
 */
public class ExportScene {

	/**
	 * This method is responsible for building the exportScene defined in
	 * Main.java
	 * 
	 * @return the exportScene Scene
	 */
	public static Scene buildExportScene(BorderPane root, DataStructure data) {
		// adding onto the template built in BorderPaneTemplate.java

		// creating the VBox for the radio buttons
		Label dataLabel = new Label("Select Data to Export:");
		RadioButton deathButton = new RadioButton("Total Deaths");
		RadioButton caseButton = new RadioButton("Total Confirmed Cases");
		RadioButton testsGivenButton = new RadioButton(
				"Total Tests Administered");
		RadioButton mortalityButton = new RadioButton("Mortality Rate");
		RadioButton percentOfTestsButton = new RadioButton(
				"Percent of Positive Tests");
		RadioButton rateButton = new RadioButton("Rate of Infection");
		VBox radioVBox = new VBox(deathButton, caseButton, testsGivenButton,
				mortalityButton, percentOfTestsButton, rateButton);
		radioVBox.setPadding(new Insets(10));
		VBox dataVBox = new VBox(dataLabel, radioVBox);
		dataVBox.setPadding(new Insets(5));

		// creating the VBox for the state selector
		Label stateLabel = new Label("Select a State: ");
		ComboBox<String> stateMenu = new ComboBox<String>();
		stateMenu.getItems().add("United States");
		for (States state : States.values()) {
			stateMenu.getItems().add(state.toString());
		}
		stateMenu.getSelectionModel().selectFirst();
		VBox stateVBox = new VBox(stateLabel, stateMenu);
		stateVBox.setPadding(new Insets(5));

		// creating text box for file name
		TextField fileName = new TextField();
		fileName.setPromptText("Enter file name");
		fileName.setPrefColumnCount(8);
		VBox nameVBox = new VBox(fileName);
		nameVBox.setPadding(new Insets(5));

		// add create button
		Button createButton = new Button("Create export file");
		createButton.setOnAction(e -> {
			// error process first
			DataCalculations calculator = new DataCalculations(data);
			String name = fileName.getText().trim();
			String state = stateMenu.getValue();
			if (!calculator.isFileNameValid(name)) { // used invalid chars
				// do nothing, errors handled in
				// DataCalculations.isFileNameValid()
			} else { // make calculations and write the file TODO

				if (deathButton.isSelected()) { // calculate deaths

				}
				if (caseButton.isSelected()) { // calculate cases

				}
				if (testsGivenButton.isSelected()) { // calculate tests

				}
				if (percentOfTestsButton.isSelected()) {
					// calculate percent of tests returned positive
				}
				if (rateButton.isSelected()) {
					// calculate the rate of infection
				}

			}
		});
		VBox buttonVBox = new VBox(createButton);
		buttonVBox.setPadding(new Insets(5));

		// adding selectors to the left panel
		VBox leftPanel = new VBox(dataVBox, stateVBox, nameVBox, buttonVBox);
		leftPanel.setPadding(new Insets(35));
		leftPanel.setSpacing(7);
		leftPanel.setAlignment(Pos.CENTER);

		root.setLeft(leftPanel);

		// set center panel to display of the export.txt file
		// TODO replace with example.txt
		Label exampleLabel = new Label("Example export.txt:");
		String text = "This section in the center panel of "
				+ "the BorderPanel will show a preview of "
				+ "what the export.txt file will look like. "
				+ "I would like to make have it update based "
				+ "on the users selections, but if I cannot "
				+ "figure that out it will show an example.";
		Text replace = new Text(text);
		replace.setWrappingWidth(250);
		replace.setTextAlignment(TextAlignment.JUSTIFY);
		replace.setLineSpacing(3.0);
		replace.setFont(Font.font("sans-serif", FontPosture.REGULAR, 14));
		VBox centerVBox = new VBox(exampleLabel, replace);
		centerVBox.setSpacing(5);
		centerVBox.setAlignment(Pos.CENTER);

		root.setCenter(centerVBox);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}
}
