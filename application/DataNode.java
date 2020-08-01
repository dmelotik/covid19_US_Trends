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

/**
 * This class acts as a node that carries the data chunk's information
 */
public class DataNode {

	private LocalDate date; // the day the *.csv file was created
	private States state; // the state or territory of this data
	private int confirmed; // total number of confirmed cases for that state on
							// that date
	private int deaths; // total number of deaths for that state on that date
	private int tests; // tests given for that state up to that date

	/**
	 * Instantiates a new data node containing the following pieces of data
	 * 
	 * @param confirmed -number of confirmed cases
	 * @param deaths    -number of confirmed deaths
	 * @param tests     -number of tests administered
	 * @param date      -the date the *.csv file represents
	 * @param state     -the state represented
	 */
	public DataNode(int confirmed, int deaths, int tests, LocalDate date,
			States state) {
		this.date = date;
		this.state = state;
		this.confirmed = confirmed;
		this.deaths = deaths;
		this.tests = tests;
	}

	/**
	 * Getter for the date
	 * 
	 * @return the date the *.csv file was created as a LocalDate
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Getter for the state
	 * 
	 * @return the Enum.States representation of the state in this.DataNode
	 */
	public States getState() {
		return state;
	}

	/**
	 * Getter for the number of confirmed cases
	 * 
	 * @return an int of the confirmed cases for that state/day
	 */
	public int getNumOfConfirmed() {
		return confirmed;
	}

	/**
	 * Getter for the confirmed deaths
	 * 
	 * @return int containing the total deaths up to this.date for this.state
	 */
	public int getNumOfDeaths() {
		return deaths;
	}

	/**
	 * Getter for the tests
	 * 
	 * @return the number of tests administered up to this.date
	 */
	public int getNumOfTest() {
		return tests;
	}
}
