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
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class reads a *.csv data file and returns a DataNode[] array of a whole
 * file that is meant to be one of the inner arrays of the DataStructure array
 */
public class FileReader {

	private File dataFile; // the file being read from
	private LocalDate date; // the date the *.csv file was create
	private final int CONFIRMED_INDEX = 5; // index the confirmed case number is
											// foundin after splitting up a line
	private final int DEATHS_INDEX = 6; // the index of the death count
	private final int TESTS_INDEX = 11; // index of the total tests administered
	private final int STATE_INDEX = 0; // index of the state name

	/**
	 * Constructor method that instantiates with a file and pulls out the date
	 * to make it a new LocalDate
	 * 
	 * @param file -a *.csv file containing the covid-19 data points for a given
	 *             day
	 */
	public FileReader(File file) {
		dataFile = file;

		// read filename to get the date
		String fileName = dataFile.getName();
		fileName = fileName.replaceAll("[^\\d]", " "); // replace all non digits
														// with a space
		fileName = fileName.trim();
		Scanner nameScnr = new Scanner(fileName);

		int month = 0;
		int day = 0;
		int year = 0;

		// create error to throw if the file names are incorrect
		Alert nameError = new Alert(AlertType.ERROR);
		nameError.setContentText("The File, \"" + dataFile.getName()
				+ "\", is incorrect. Do not change file names.");
		// checking to make sure the file names are correct
		if (nameScnr.hasNextInt()) {
			month = nameScnr.nextInt();
		} else {
			nameError.show();
			return;
		}

		if (nameScnr.hasNextInt()) {
			day = nameScnr.nextInt();
		} else {
			nameError.show();
			return;
		}

		if (nameScnr.hasNextInt()) {
			year = nameScnr.nextInt();
		} else {
			nameError.show();
			return;
		}

		nameScnr.close();

		date = LocalDate.of(year, month, day);
	}

	/**
	 * Only method in the class that reads the data from a *.csv file and stores
	 * it in a DataNode array that will be placed in the DataStructure's 2d
	 * array
	 * 
	 * @return an array containing data nodes of each state for a given day,
	 *         (date)
	 */
	public DataNode[] getDataArrayFromFile() {
		DataNode[] nodeArr = new DataNode[States.values().length];

		// create scanner to read the file
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(
					"Please update the data directory and restart the program");
			a.show();
		}

		// skip the first line b/c it has no values
		fileReader.nextLine();

		// now read line by line, inputting the values into the nodeArr
		for (int i = 0; i < States.values().length; i++) {
			String line = "";
			if (fileReader.hasNextLine()) {
				line = fileReader.nextLine(); // returns a whole line
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(
						dataFile.getName() + " is not formatted correctly");
				a.show();
				return null; // end because data will be incorrect
			}

			String[] dataPoints = line.split(",");

			// checking if the line lengths are correct
			if (dataPoints.length < 16 || dataPoints.length > 18) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(
						dataFile.getName() + " is not formatted correctly");
				a.show();
				continue; // skip loop iteration
			}

			// convert strings to ints and find state
			States stateName = null;
			for (States state : States.values()) {
				if (dataPoints[STATE_INDEX].equals("Recovered")) {
					// for 4/12/2020 there is a state after "Recovered"
					line = fileReader.nextLine(); // returns a whole line
					dataPoints = line.split(",");
				}
				if (dataPoints[STATE_INDEX].equals(state.toString())) {
					stateName = state;
					break;
				}
			}
			if (stateName == null) {
				System.out.println("Null state   Date: " + date);
			}

			int confirmed = Integer.parseInt(dataPoints[CONFIRMED_INDEX]);
			int deaths = Integer.parseInt(dataPoints[DEATHS_INDEX]);
			int tests = -1; // only diamond/grand princess
			if (!dataPoints[TESTS_INDEX].equals("")) {
				tests = Integer.parseInt(dataPoints[TESTS_INDEX]);
			}

			// create and insert node into data structure
			nodeArr[i] = new DataNode(confirmed, deaths, tests, date,
					stateName);
		}

		fileReader.close();
		return nodeArr;
	}
}
