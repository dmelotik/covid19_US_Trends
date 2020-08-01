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
import java.util.ArrayList;

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
	 * This method calcuates the total number change for that day in america
	 * 
	 * @param operation
	 * @param indexOfDate
	 * @return
	 */
	private int calculateUSTotals(String operation, int indexOfDate) {
		int dataPoint = 0; // the general data bit being returned

		if (operation.contains("Cases")) { // get all cases of whole us

			// initial data point
			dataPoint += data.getDataArr()[indexOfDate][0].getNumOfConfirmed();
			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				dataPoint += (data.getDataArr()[indexOfDate][i]
						.getNumOfConfirmed()
						- data.getDataArr()[indexOfDate][i - 1]
								.getNumOfConfirmed());
			}
		} else if (operation.contains("Deaths")) { // get all us deaths

			// initial data point
			dataPoint += data.getDataArr()[indexOfDate][0].getNumOfDeaths();
			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				dataPoint += (data.getDataArr()[indexOfDate][i].getNumOfDeaths()
						- data.getDataArr()[indexOfDate][i - 1]
								.getNumOfDeaths());
			}
		} else { // get all tests administered

			// initial data point
			dataPoint += data.getDataArr()[indexOfDate][0].getNumOfTest();
			for (int i = 1; i < data.getDataArr()[indexOfDate].length; i++) {
				dataPoint += (data.getDataArr()[indexOfDate][i].getNumOfTest()
						- data.getDataArr()[indexOfDate][i - 1].getNumOfTest());
			}
		}

		return dataPoint;
	}
}
