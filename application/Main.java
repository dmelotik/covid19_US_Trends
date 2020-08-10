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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This class is the initializer of the application. It brings together all of
 * the elements.
 */
public class Main extends Application {

	protected static final int WINDOW_WIDTH = 777;
	protected static final int WINDOW_HEIGHT = 460;
	private static final String APP_TITLE = "US Covid-19 Trends";
	protected static Stage window; // easier to call the stage window
	protected static Scene mainScene, dataScene, exportScene;
	protected DataStructure data;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		// creating the mainScene from MainScene.java
		mainScene = MainScene
				.buildMainScene(BorderPaneTemplate.buildTemplate());

		// fill data structure with the values from application/data
		int numOfDays; // number of days there is data for
		String dirPath; // the path of the directory storing the data
		File dataDir; // the directory folder of the data
		String[] fileList; // string array containing the name of each file in
							// the directory

		dirPath = "application/data/csse_covid_19_data/csse_covid_19_daily_reports_us";
		dataDir = new File(dirPath);
		if (dataDir.isDirectory()) {
			fileList = dataDir.list();
			numOfDays = fileList.length - 1; // minus 1 for the readme file

			data = new DataStructure(numOfDays);

			// loop through the dataDir until the data structure is filled
			for (int i = 0; i < numOfDays; i++) {
				String tempFilePath = dirPath + "/" + fileList[i];
				FileReader tempReader = new FileReader(new File(tempFilePath));
				DataNode[] tempArr = tempReader.getDataArrayFromFile();
				if (tempArr == null) { // if the files are formatted incorrectly
					break; // skip iteration
				}
				data.setDataArr(i, tempArr);
			}
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(
					"The COVID-19 data is not in the correct spot. Please follow README.txt.");
			a.show();
		}

		// creating the dataScene from DataScene.java
		dataScene = DataScene.buildDataScene(BorderPaneTemplate.buildTemplate(),
				data);

		// creating the exportScene from ExportScene.java
		exportScene = ExportScene
				.buildExportScene(BorderPaneTemplate.buildTemplate(), data);

		// adding the stylesheet to each scene
		mainScene.getStylesheets().add("application/application.css");
		exportScene.getStylesheets().add("application/application.css");
		dataScene.getStylesheets().add("application/application.css");

		// add necessities and set the new window to mainScene (Home)
		window.setTitle(APP_TITLE);
		window.setScene(mainScene);
		window.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}