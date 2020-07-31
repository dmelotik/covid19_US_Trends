package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileReader {

	private File dataFile; // the file being read from
	private LocalDate date;
	private final int CONFIRMED_INDEX = 5;
	private final int DEATHS_INDEX = 6;
	private final int TESTS_INDEX = 11;
	private final int STATE_INDEX = 0;

	public FileReader(File file) {
		dataFile = file;

		// read filename to get the date
		String fileName = dataFile.getName();
		fileName = fileName.replaceAll("[^\\d]", " "); // replace all non digits
														// with a space
		fileName = fileName.trim();
		Scanner nameScnr = new Scanner(fileName);
		int month = nameScnr.nextInt();
		int day = nameScnr.nextInt();
		int year = nameScnr.nextInt();
		nameScnr.close();

		date = LocalDate.of(year, month, day);
	}

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
			String line = fileReader.nextLine(); // returns a whole line
			String[] dataPoints = line.split(",");

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
