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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the initializer of the application. It brings together all of
 * the elements.
 */
public class Main extends Application {

	protected static final int WINDOW_WIDTH = 768;
	protected static final int WINDOW_HEIGHT = 432;
	private static final String APP_TITLE = "US Covid-19 Trends";
	protected static Stage window; // easier to call the stage window
	protected static Scene mainScene, dataScene, exportScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		// creating the mainScene from MainScene.java
		mainScene = MainScene.buildMainScene(BorderPaneTemplate.buildTemplate());

		// creating the dataScene from DataScene.java
		dataScene = DataScene.buildDataScene(BorderPaneTemplate.buildTemplate());

		// creating the exportScene from ExportScene.java
		exportScene = ExportScene.buildExportScene(BorderPaneTemplate.buildTemplate());

		// add necessities and set the new window to mainScene (Home)
		window.setTitle(APP_TITLE);
		window.setScene(mainScene);
		window.show();
		
		// clone repo for data
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}