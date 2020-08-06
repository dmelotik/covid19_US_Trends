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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is the data structure that stores and holds all of the information
 * from the *.csv data files from the cloned GitHub COVID-19 repo.
 */
public class DataStructure implements DataStructureADT {

	private int days; // num of days in the ADT
	private DataNode[][] dataArr; // array containing all of the data

	/**
	 * Constructor that instantiates the Data Structure with the number of
	 * different days in the data files
	 * 
	 * @param days -the number of days represented in the data directory
	 */
	public DataStructure(int days) {
		this.days = days;
		dataArr = new DataNode[days][];
	}

	/**
	 * This method returns the DataNode associated with the given state on that
	 * date.
	 * 
	 * @param date  -The LocalDate of the chunk of information being looked for
	 * @param state -The state to find the data from
	 * 
	 * @return the corresponding DataNode
	 */
	@Override
	public DataNode get(LocalDate date, States state)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method sets a new data node in the given position
	 * 
	 * @param outerIndex -the outer index of the 2d DataNode array
	 * @param innerIndex -the inner index of the 2d array
	 * @param node       -the DataNode containing the info
	 */
	@Override
	public void setDataArr(int outerIndex, int innerIndex, DataNode node) {
		dataArr[outerIndex][innerIndex] = node;
	}

	/**
	 * This method inserts the inner array of the 2d data structure array
	 * 
	 * @param index -the outer index location of arr to be placed
	 * @param arr   -the inner part of the 2d array
	 */
	@Override
	public void setDataArr(int index, DataNode[] arr) {
		dataArr[index] = arr;
	}

	/**
	 * Getter method returns Data Structure 2d DataNode array
	 * 
	 * @return the 2d array containing the data
	 */
	@Override
	public DataNode[][] getDataArr() {
		return dataArr;
	}

	/**
	 * Getter method returns the number of different days represented in the
	 * Data Strcuture
	 * 
	 * @return the number of days in the DS
	 */
	@Override
	public int getNumOfDays() {
		return days;
	}

	/**
	 * This method returns the last date accounted for in this data structure.
	 * This date will always be the last element in the 1st layer of the 2d
	 * array.
	 * 
	 * @return LocalDate of the last day in the 2d array
	 */
	@Override
	public LocalDate getLastDate() {
		if (days <= 0) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(
					"Please load data into the program. Follow instructions.txt");
			a.show();
		}

		// find the last date
		int index = days - 1;
		while (dataArr[index][0] == null
				|| dataArr[index][0].getDate() == null) {
			if (index > 0) {
				index--;
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(
						"Files are incorrectly named. Please use the same name as the GitHub repo");
				a.show();
			}
		}

		return dataArr[index][0].getDate();
	}

	/**
	 * This method returns the first date in the data structure.
	 * 
	 * @return LocalDate of the first date, or pop up an error message if the
	 *         dataArr is empty
	 */
	@Override
	public LocalDate getFirstDate() {
		if (days <= 0) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(
					"Please load data into the program. Follow instructions.txt");
			a.show();
		}

		// find the first date in the data structure
		int index = 0;

		while (dataArr[index][0] == null
				|| dataArr[index][0].getDate() == null) {
			if (index < days) {
				index++;
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(
						"The files are incorrectly named, please use the same name as the GitHub repo.");
				a.show();
			}
		}

		return dataArr[index][0].getDate();
	}

}
