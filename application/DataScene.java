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

import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * This class creates the dataScene, utilizing the template BorderPane.
 */
public class DataScene {

	/**
	 * This method builds the dataScene from the template.
	 * 
	 * @return the dataScene Scene
	 */
	public static Scene buildDataScene(BorderPane root, DataStructure data) {
		// adding onto template that was built in BorderPaneTemplate.java

		// creating comboBox used to select state
		Label stateLabel = new Label("Select a State: ");
		ComboBox<String> stateMenu = new ComboBox<String>();
		stateMenu.getItems().add("United States"); // TODO different action for
													// the whole US
		// add the whole States enum to the ComboBox
		for (States state : States.values()) {
			stateMenu.getItems().add(state.toString());
		}
		stateMenu.getSelectionModel().selectFirst();
		VBox stateVBox = new VBox(stateLabel, stateMenu);
		stateVBox.setPadding(new Insets(5));

		// creating the date filters start
		Label startDate = new Label("Choose Start Date: ");
		DatePicker startCalendar = new DatePicker(LocalDate.of(2020, 4, 12));

		// block off dates before and after TODO cite this
		final Callback<DatePicker, DateCell> dayCellFactoryStart = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(LocalDate.of(2020, 4, 12))
								|| item.isAfter(LocalDate.of(2020, 4, 12)
										.plusDays(data.getNumOfDays() - 2))) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}

					}
				};
			}
		};
		startCalendar.setDayCellFactory(dayCellFactoryStart);

		VBox startVBox = new VBox(startDate, startCalendar);
		startVBox.setPadding(new Insets(5));

		// creating the date filter end
		Label endDate = new Label("Choose End Date: ");
		DatePicker endCalendar = new DatePicker(LocalDate.now());

		// TODO cite this
		// (https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm#CCHEBIFF)
		// block off dates before and after
		final Callback<DatePicker, DateCell> dayCellFactoryEnd = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isAfter(LocalDate.of(2020, 4, 12)
								.plusDays(data.getNumOfDays() - 1))
								|| item.isBefore(LocalDate.of(2020, 4, 13))) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}

					}
				};
			}
		};
		endCalendar.setDayCellFactory(dayCellFactoryEnd);

		VBox endVBox = new VBox(endDate, endCalendar);
		endVBox.setPadding(new Insets(5));

		// combobox to select the data to show on the graph
		Label dataLabel = new Label("Select Data to Show: ");
		ComboBox<String> dataSelector = new ComboBox<String>();
		dataSelector.getItems().addAll("New Daily Cases",
				"Daily Confirmed Deaths", "Daily Tests Administered");
		dataSelector.getSelectionModel().selectFirst();
		VBox dataVBox = new VBox(dataLabel, dataSelector);
		dataVBox.setPadding(new Insets(5));

		// adding a button to submit the data and generate the graph
		Button submit = new Button("Generate New Graph");
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(
				"Please make sure the end date comes after the start date.");
		submit.setOnAction(e -> {
			if (endCalendar.getValue().isBefore(startCalendar.getValue())) {
				// error calander dates are incorrect
				a.show();
			} else if (endCalendar.getValue().isAfter(LocalDate.of(2020, 4, 12)
					.plusDays(data.getNumOfDays() - 1))) {
				// chose date outside possiblities
				Alert a1 = new Alert(AlertType.ERROR);
				a1.setContentText(
						"Please update your data or choose a date outside of the red");
				a1.show();
			} else if ((stateMenu.getValue().equals("Diamond Princess")
					|| stateMenu.getValue().equals("Grand Princess"))
					&& dataSelector.getValue()
							.equals("Daily Tests Administered")) {
				// error no test info for cruise ships
				Alert a2 = new Alert(AlertType.ERROR);
				a2.setContentText(
						"No test data for Grand Princess or Diamond Princess");
				a2.show();
			} else {
				// TODO call the correct data calculation and update graph
			}
		});
		VBox submitVBox = new VBox(submit);
		submitVBox.setPadding(new Insets(5));

		// creating the VBox for the whole left panel
		VBox leftPanel = new VBox(stateVBox, startVBox, endVBox, dataVBox,
				submitVBox);
		leftPanel.setAlignment(Pos.TOP_LEFT);
		leftPanel.setPadding(new Insets(35));
		root.setLeft(leftPanel);

		// creating the line chart for the center panel
		// TODO changes based on selections
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Day");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("People");
		AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis,
				yAxis);
		ac.setTitle("Covid-19 Trends"); // TODO change based on data selected

		// TODO remove hard coded data - potentially code in data on mouse hover
		XYChart.Series<Number, Number> confirmedCases = new XYChart.Series<Number, Number>();
		confirmedCases.setName("Confirmed Cases"); // TODO changes on data
													// selected
		// hard coding data
		confirmedCases.getData().add(new Data<Number, Number>(0, 17));
		confirmedCases.getData().add(new Data<Number, Number>(1, 23));
		confirmedCases.getData().add(new Data<Number, Number>(2, 40));
		confirmedCases.getData().add(new Data<Number, Number>(3, 35));
		confirmedCases.getData().add(new Data<Number, Number>(4, 67));
		confirmedCases.getData().add(new Data<Number, Number>(5, 80));
		confirmedCases.getData().add(new Data<Number, Number>(6, 76));
		confirmedCases.getData().add(new Data<Number, Number>(7, 81));
		confirmedCases.getData().add(new Data<Number, Number>(8, 54));
		confirmedCases.getData().add(new Data<Number, Number>(9, 98));
		confirmedCases.getData().add(new Data<Number, Number>(10, 89));

		ac.getData().add(confirmedCases);

		root.setCenter(ac);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}
}
