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

import java.net.URI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class creates the mainScene. Utilizing the already created template.
 */
public class MainScene {

	/**
	 * This class creates the main scene on top of the template BorderPane.
	 * 
	 * @return the mainScene
	 */
	public static Scene buildMainScene(BorderPane root) {
		// adding onto the template that was built in BorderPaneTemplate.java

		// adding text to the center panel to explain application
		String paragraph = "This application tracks the spread of "
				+ "Coronavirus (Covid-19) in the United States of "
				+ "America and territories. The 'US Data' page "
				+ "features graph that allows full control to "
				+ "manipulate the data. The 'Export Data' page allows "
				+ "exporting of US Coronavirus data in an easy to "
				+ "digest *.txt file. The data comes from Johns "
				+ "Hopkins University.";
		Text explanation = new Text(paragraph);
		explanation.setWrappingWidth(350);
		explanation.setTextAlignment(TextAlignment.JUSTIFY);
		explanation.setLineSpacing(2.0);
		explanation.setFont(Font.font("sans-serif", FontPosture.REGULAR, 14));

		// adding hyperlink to data
		Hyperlink jhuLink = new Hyperlink("Data");
		ImageView jhuView = new ImageView(new Image("file:application/images/jhu.png"));
		jhuView.setFitHeight(44.9);
		jhuView.setFitWidth(234.5);
		jhuLink.setGraphic(jhuView);

		// event to bring uer to uw cs homepage
		jhuLink.setOnAction(e -> {
			try {
				java.awt.Desktop.getDesktop().browse(
						new URI("https://github.com/CSSEGISandData/COVID-19"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		jhuLink.setAlignment(Pos.CENTER_LEFT);

		// creating a vbox w the nodes
		VBox vbox = new VBox(25, explanation, jhuLink);
		vbox.setAlignment(Pos.CENTER);

		// adding vbox to the borderpane
		root.setCenter(vbox);

		// return new scene we just produced
		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}

}
