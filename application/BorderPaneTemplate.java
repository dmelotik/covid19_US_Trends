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

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The purpose of this class is to create a template BorderPane to use on each
 * Scene. The template sets the top and bottom panels.
 */
public class BorderPaneTemplate {

	public static BorderPane buildTemplate() {
		// creating the new template for each scene
		BorderPane template = new BorderPane();

		// formatting the BorderPane
		template.setPadding(new Insets(10));

		// adding the navigation bar as an HBox
		HBox topHBox = new HBox(10);

		// creating buttons for navigation between scenes
		Button homePageButton = new Button("Home");
		homePageButton.setOnAction(e -> Main.window.setScene(Main.mainScene));
		Button dataPageButton = new Button("US Data");
		dataPageButton.setOnAction(e -> Main.window.setScene(Main.dataScene));
		Button exportPageButton = new Button("Export Data");
		exportPageButton
				.setOnAction(e -> Main.window.setScene(Main.exportScene));

		// adding buttons to hBox and formatting
		topHBox.getChildren().addAll(homePageButton, dataPageButton,
				exportPageButton);
		topHBox.setAlignment(Pos.CENTER_LEFT);

		// setting to the top panel
		template.setTop(topHBox);

		// creating center bottom hbox
		HBox bottomCenter = new HBox();

		// adding label for the copyright
		Label copyright = new Label("© Dylan Melotik 2020");

		// adding quit button
		Button exit = new Button("Quit");
		exit.setOnAction(e -> Platform.exit());

		// creating bottom hbox
		HBox bottomRight = new HBox(exit);
		bottomRight.setAlignment(Pos.CENTER_RIGHT);

		HBox.setHgrow(bottomRight, Priority.ALWAYS);

		// adding label to center hbox
		bottomCenter.getChildren().addAll(copyright, bottomRight);

		// setting bottom hbox to the bottom panel
		template.setBottom(bottomCenter);

		return template;
	}

}
