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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

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
			} else { // make calculations and write the file

				try {
					File export = new File("exports/" + name + ".txt");
					if (!export.createNewFile()) {
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText(
								"File already exists, use a different name");
						a.show();
					}

					// create file writer for export
					FileWriter exportWriter = new FileWriter(export);

					// add header to file
					exportWriter.write("Projet:\t\tCovid-19 US Trends\n");
					exportWriter.write("Date Created:\t"
							+ LocalDate.now().toString() + "\n");
					exportWriter.write("Data Since:\t"
							+ data.getFirstDate().toString() + "\n\n\n");
					exportWriter.write("Statistical Calculations of " + state
							+ " Covid-19 Data:\n\n");

					// write stats based on radio button selections
					if (deathButton.isSelected()) { // calculate deaths
						String deaths = NumberFormat
								.getNumberInstance(Locale.US)
								.format(calculator.calculateTotalDeaths(state));
						exportWriter.write("\tTotal number of deaths:\t\t\t"
								+ deaths + "\n");
					}
					if (caseButton.isSelected()) { // calculate cases
						String cases = NumberFormat.getNumberInstance(Locale.US)
								.format(calculator
										.calculateTotalConfirmedCases(state));
						exportWriter
								.write("\tTotal number of confirmed cases:\t"
										+ cases + "\n");
					}
					if (testsGivenButton.isSelected()) { // calculate tests
						String tests = NumberFormat.getNumberInstance(Locale.US)
								.format(calculator
										.calculateTestsAdministered(state));
						exportWriter
								.write("\tTotal number of tests administered:\t"
										+ tests + "\n");
					}
					if (mortalityButton.isSelected()) {
						// calculate mortality rate
						String mortalityRate = String.format("%.2f",
								calculator.calculateMortalityRate(state));
						exportWriter.write("\tMortality rate:\t\t\t\t"
								+ mortalityRate + "%\n");

					}
					if (percentOfTestsButton.isSelected()) {
						// calculate percent of tests returned positive
						String percentOfTests = String.format("%.2f", calculator
								.calculatePercentOfPositiveTests(state));
						exportWriter.write("\tPercent of positive tests:\t\t"
								+ percentOfTests + "%\n");
					}
					if (rateButton.isSelected()) {
						// calculate the rate of infection
						String rateOfInfection = String.format("%.2f",
								calculator.calculateRateOfInfection(state));
						String population = NumberFormat
								.getNumberInstance(Locale.US).format(calculator
										.calculateStatePopulation(state));
						exportWriter
								.write("\tRate of infection per population:\t"
										+ rateOfInfection + "%\n");
						exportWriter.write("\t\t" + stateMenu.getValue()
								+ " Population:\t" + population + "\n");
					}

					// add the returning comments line by line
					exportWriter.write("\n\n");
					exportWriter.write("Notes:\n\n");
					exportWriter.write(
							"This information is compiled from multiple publicly available\n");
					exportWriter.write(
							"sources, and the data is not always 100% accurate. To have the\n");
					exportWriter.write(
							"most up to date information please follow the repository update\n");
					exportWriter.write("instructions in README.txt.\n\n");
					exportWriter.write(
							"Data Source:\t\tgithub.com/CSSEGISandData/COVID-19\n");
					exportWriter.write(
							"Population Data Source:\thttps://www.census.gov/data/tables\n");
					exportWriter.write("Program Author:\t\tDylan Melotik\n");
					exportWriter.write("email:\t\t\tdmelotik@wisc.edu\n");

					exportWriter.close();
					Alert a = new Alert(AlertType.CONFIRMATION);
					a.setContentText(name + ".txt was successfully created!");
					a.show();
				} catch (IOException e1) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText(
							"Error " + e1.getMessage() + " exception thrown");
					a.show();
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
		Label exampleLabel = new Label("Example export.txt:");
		String text = "Statistical Calculations of *State* Covid-19 Data:\r\n\n"
				+ "	Total number of deaths:				*num*\n"
				+ "	Total number of confirmed cases: 		*num*\n"
				+ "	Total number of tests administered:	*num*\n"
				+ "	Mortality Rate:						*num*%\n"
				+ "	Percent of positive tests:				*num*%\n"
				+ "	Rate of infection per population:		*num*%\n"
				+ "		*State* Population:				*num*";
		Text replace = new Text(text);
		replace.setWrappingWidth(350);
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
