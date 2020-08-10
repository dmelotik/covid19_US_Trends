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
 * This is the interface for the DataStructure
 */
public interface DataStructureADT {

	/**
	 * Overloaded method that inserts a DataNode at a given position based on
	 * the indices.
	 * 
	 * @param outerIndex -the outer index of the 2d DataNode array
	 * @param innerIndex -the inner index of the 2d DataNode array
	 * @param node       -the node to be inserted into the array
	 */
	public void setDataArr(int outerIndex, int innerIndex, DataNode node);

	/**
	 * Overloaded method that inserts a DataNode[] array at the given outer
	 * index positon
	 * 
	 * @param index- the position on the outer side of the 2d array t insert the
	 *               DataNode array
	 * @param arr-   the array of DataNodes to be inserted into the
	 *               DataStructure
	 */
	public void setDataArr(int index, DataNode[] arr);

	/**
	 * This is a getter method to return the 2d array that stores the data
	 * 
	 * @return the data structure's 2d array containing the data points
	 */
	public DataNode[][] getDataArr();

	/**
	 * Getter method for the number of different days in the data structure
	 * 
	 * @return the number of days represented in the data structure
	 */
	public int getNumOfDays();

	/**
	 * This method returns the last date accounted for in this data structure.
	 * This date will always be the last element in the 1st layer of the 2d
	 * array.
	 * 
	 * @return LocalDate of the last day in the 2d array, or pop up an error
	 *         message if the dataArr is empty
	 */
	public LocalDate getLastDate();

	/**
	 * This method returns the first date in the data structure.
	 * 
	 * @return LocalDate of the first date, or pop up an error message if the
	 *         dataArr is empty
	 */
	public LocalDate getFirstDate();
}
