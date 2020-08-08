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
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class makes the data calculations for the program
 */
public class DataCalculations {

	private DataStructure data; // 2d array containing the data nodes

	/**
	 * Constructor instantiates a new DataCalculations class with the data in a
	 * 2d array.
	 * 
	 * @param dataArr -the 2d array containing nodes of each chunk of data
	 */
	public DataCalculations(DataStructure data) {
		this.data = data;
	}

	/**
	 * Makes the correct calculations for all of the operations in the
	 * DataScene.java
	 * 
	 * @param operation -the different data to display onto the chart
	 * @param state     -the state being asked to display data onto
	 * @param startDate -the beginning date of the data to be displayed
	 * @param endDate   - the last date of the data to be displayed
	 * @return an ArrayList containing the values of each data point
	 */
	public ArrayList<Integer> dataSceneCalculations(String operation,
			String state, LocalDate startDate, LocalDate endDate) {
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException(
					"Start date comes after the end date");
		}

		// get the state enum of this.state
		int stateIndex = -1; // index of the state in the data arr
		for (int z = 0; z < States.values().length; z++) {
			if (state.equals(States.values()[z].toString())) {
				stateIndex = z;
				break;
			}
		}

		// find the indices of the start and end dates in the data array
		int indexOfStart = -1;
		int indexOfEnd = -1;
		for (int i = 0; i < data.getDataArr().length; i++) {
			// error handling for incorrect format
			if (data.getDataArr()[i][0] == null) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(
						"Please fix the formatting issue and restart the program");
				a.show();
				return null;
			}

			if (data.getDataArr()[i][0].getDate().equals(startDate)) {
				indexOfStart = i;
			}
			if (data.getDataArr()[i][0].getDate().equals(endDate)) {
				indexOfEnd = i;
				break;
			}
		}

		// make calculations based off operation selected
		ArrayList<Integer> dataPoints = new ArrayList<Integer>();

		for (int c = indexOfStart; c <= indexOfEnd; c++) {
			if (operation.contains("Cases")) { // calculate daily cases

				if (stateIndex == -1) { // united states was selected
					dataPoints.add(calculateUSTotals(operation, c));
				} else { // a specific state was selected
					int dailyNewCases = data.getDataArr()[c][stateIndex]
							.getNumOfConfirmed()
							- data.getDataArr()[c - 1][stateIndex]
									.getNumOfConfirmed();
					dataPoints.add(dailyNewCases);
				}

			} else if (operation.contains("Deaths")) { // calculate daily deaths

				if (stateIndex == -1) { // united states was selected
					dataPoints.add(calculateUSTotals(operation, c));
				} else { // a specific state was selected
					int dailyNewCases = data.getDataArr()[c][stateIndex]
							.getNumOfDeaths()
							- data.getDataArr()[c - 1][stateIndex]
									.getNumOfDeaths();
					dataPoints.add(dailyNewCases);
				}

			} else { // calculate daily tests administered

				if (stateIndex == -1) { // united states was selected
					dataPoints.add(calculateUSTotals(operation, c));
				} else { // a specific state was selected
					int dailyNewCases = data.getDataArr()[c][stateIndex]
							.getNumOfTest()
							- data.getDataArr()[c - 1][stateIndex]
									.getNumOfTest();
					dataPoints.add(dailyNewCases);
				}
			}
		}

		return dataPoints;
	}

	/**
	 * This method calculates the total change in this operation for the entire
	 * United states on the given date
	 * 
	 * @param operation   -the data point being collected. i.e. Deaths, Cases,
	 *                    Tests
	 * @param indexOfDate -the outer index of the date to calculate the daily
	 *                    operation total
	 * @return the new operation data value for the given date
	 */
	private int calculateUSTotals(String operation, int indexOfDate) {
		int dataPoint = 0; // the general data bit being returned

		if (operation.contains("Cases")) { // get all cases of whole us

			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				// calculate the num of cases for i States on this and prev date
				int thisDayCases = data.getDataArr()[indexOfDate][i]
						.getNumOfConfirmed();
				int prevDayCases = data.getDataArr()[indexOfDate - 1][i]
						.getNumOfConfirmed();

				dataPoint += (thisDayCases - prevDayCases); // add difference to
															// the running total
			}

		} else if (operation.contains("Deaths")) { // get all us deaths

			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				// calculate the num of deaths for i States on this & prev date
				int thisDayDeaths = data.getDataArr()[indexOfDate][i]
						.getNumOfDeaths();
				int prevDayDeaths = data.getDataArr()[indexOfDate - 1][i]
						.getNumOfDeaths();

				dataPoint += (thisDayDeaths - prevDayDeaths); // add to running
																// total
			}

		} else { // get all tests administered

			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				// calculate the num of tests for i States on this and prev date
				int thisDayTests = data.getDataArr()[indexOfDate][i]
						.getNumOfTest();
				int prevDayTests = data.getDataArr()[indexOfDate - 1][i]
						.getNumOfTest();

				dataPoint += (thisDayTests - prevDayTests); // add difference to
															// the running total
			}

		}

		return dataPoint;
	}

	/**
	 * This method checks if the file name the user entered meets the criteria
	 * of a legitimate file name. The method is only called from
	 * ExportScene.java
	 * 
	 * @param name -the file name that the user enters
	 * @return false when name contains invalid chars or the file already
	 *         exists, otherwise true
	 */
	public boolean isFileNameValid(String name) {
		// first check for invalid characters
		String[] invalidChars = new String[] { "<", ">", ":", "\"", "/", "\\",
				"|", "?", "*", " ", ".txt", "." };
		for (String invalidChar : invalidChars) {
			if (name.contains(invalidChar)) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("File name contained invalid character(s): \""
						+ invalidChar + "\"");
				a.show();
				return false;
			}
		}

		// check if the file already exists
		String pathname = "exports/" + name + ".txt";
		File tempFile = new File(pathname);
		if (tempFile.exists()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("A file with that name already exists");
			a.show();
			return false;
		}

		return true;
	}

	/**
	 * This method calculates the total number of deaths for state in States
	 * enum.
	 * 
	 * @param state -the string value of a state in enum, or the "United States"
	 * @return the total number of deaths as an int
	 */
	public int calculateTotalDeaths(String state) {
		// find the index of the state in States.java
		int stateIndex = -1;
		for (int i = 0; i < States.values().length; i++) {
			if (States.values()[i].toString().equals(state)) {
				stateIndex = i;
				break;
			}
		}

		// Calculating the total number of covid deaths for the given state
		int totalDeaths = 0;

		if (stateIndex == -1) {
			// calculate united states totals
			for (DataNode node : data.getDataArr()[data.getNumOfDays() - 1]) {
				totalDeaths += node.getNumOfDeaths();
			}
		} else { // calculate for given state index
			totalDeaths = data.getDataArr()[data.getNumOfDays() - 1][stateIndex]
					.getNumOfDeaths();
		}

		return totalDeaths;
	}

	/**
	 * This method returns the total number of confirmed covid-19 cases for the
	 * given state
	 * 
	 * @param state -the string value of a state in States enum or the "United
	 *              States" as a whole
	 * @return the total number of cases for state
	 */
	public int calculateTotalConfirmedCases(String state) {
		// find the index of the state in States.java
		int stateIndex = -1;
		for (int i = 0; i < States.values().length; i++) {
			if (States.values()[i].toString().equals(state)) {
				stateIndex = i;
				break;
			}
		}

		// calculate total confirmed cases
		int totalConfirmedCases = 0;

		if (stateIndex == -1) {
			// calculate united states totals
			for (DataNode node : data.getDataArr()[data.getNumOfDays() - 1]) {
				totalConfirmedCases += node.getNumOfConfirmed();
			}
		} else { // calculate for given state index
			totalConfirmedCases = data.getDataArr()[data.getNumOfDays()
					- 1][stateIndex].getNumOfConfirmed();
		}

		return totalConfirmedCases;
	}

	/**
	 * This method returns the total number of tests administered in the given
	 * state
	 * 
	 * @param state -the string representation of a state from States enum or
	 *              the "United States" as a whole
	 * @return the total number of tests administered in state
	 */
	public int calculateTestsAdministered(String state) {
		// find the index of the state in States.java
		int stateIndex = -1;
		for (int i = 0; i < States.values().length; i++) {
			if (States.values()[i].toString().equals(state)) {
				stateIndex = i;
				break;
			}
		}

		// calculate total tests Administered
		int totalTests = 0;

		if (stateIndex == -1) {
			// calculate united states totals
			for (DataNode node : data.getDataArr()[data.getNumOfDays() - 1]) {
				totalTests += node.getNumOfTest();
			}
		} else { // calculate for given state index
			totalTests = data.getDataArr()[data.getNumOfDays() - 1][stateIndex]
					.getNumOfTest();
		}

		return totalTests;
	}

	/**
	 * This method calculates the mortality rate for covid-19 in the given
	 * state. The mortality rate is the percentage of deaths caused by covid-19
	 * to the number of confirmed cases.
	 * 
	 * @param state -the strinf representation of a value in States enum, or the
	 *              "United States" as a whole
	 * @return the mortality rate of state as a percentage
	 */
	public double calculateMortalityRate(String state) {
		return ((double) calculateTotalDeaths(state)
				/ (double) calculateTotalConfirmedCases(state)) * 100;
	}

	/**
	 * This method calculates the percentage of positive tests in the given
	 * state. This percentage is based off of the number of tests administered.
	 * 
	 * @param state -the string representation of the state value found in
	 *              States enum, or the "United States" as a whole
	 * @return the percentage of positive tests as a double
	 */
	public double calculatePercentOfPositiveTests(String state) {
		return ((double) calculateTotalConfirmedCases(state)
				/ (double) calculateTestsAdministered(state)) * 100;
	}

	/**
	 * This private method checks to see if the state is a state listed in
	 * States enum, otherwise it is the "United States"
	 * 
	 * @param state -the string representation of the state in States enum, or
	 *              the "United States"
	 * @return true if state is "United States" and not in States.java,
	 *         otherwise false
	 */
	private boolean isStateUnitedStates(String state) {
		for (States stateInEnum : States.values()) {
			if (state.equals(stateInEnum.toString())) {
				return false; // found a state :. not the US
			}
		}
		return true; // no state found :. US
	}

	/**
	 * This method calculates the states population based off of the data file
	 * application/populations/population.txt
	 * 
	 * @param state -the string representation of the States enum, or the entire
	 *              "United States"
	 * @return the population of state as an int
	 */
	public int calculateStatePopulation(String state) {
		File populationData = new File(
				"application/population/populations.txt");
		boolean isStateUS = isStateUnitedStates(state);
		int statePopulation = 0;
		try {
			Scanner fileScnr = new Scanner(populationData);
			fileScnr.nextLine(); // skip first line because it is the label

			while (fileScnr.hasNextLine()) {
				String[] line = fileScnr.nextLine().split(",");
				if (isStateUS) { // whole us is being calculated
					statePopulation += Integer.parseInt(line[1]);
				} else { // search for a state
					if (state.equals(line[0])) {
						statePopulation = Integer.parseInt(line[1]);
					}
				}
			}

			fileScnr.close();
		} catch (FileNotFoundException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(
					"population/populations.txt does not exist. Please make sure that file is in the directory.");
			a.show();
			return -1; // bad, error occured
		}

		return statePopulation;
	}

	/**
	 * This method calculates the rate of infection in the given state. The rate
	 * of infection is a fraction between the number of people with covid-19 and
	 * the number of people in that state.
	 * 
	 * @param state -the string representation of the state value in States
	 *              enum, or the "United States" as a whole
	 * @return the rate of infection as a percentage
	 */
	public double calculateRateOfInfection(String state) {
		return ((double) calculateTotalConfirmedCases(state)
				/ (double) calculateStatePopulation(state)) * 100;
	}
}