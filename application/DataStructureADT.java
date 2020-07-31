package application;

import java.time.LocalDate;

public interface DataStructureADT {


	/**
	 * Inserts a new node into the array in the correct position
	 */
	public void insert(DataNode node) throws NullPointerException;
	
	/**
	 * Retrieves a node containing the information.
	 * 
	 * @return DataNode 
	 */
	public DataNode get(LocalDate date, States state) throws NullPointerException;
	
	/**
	 * Retrieves the last date there is data for in the DataStructure
	 * 
	 * @return the last date as a LocalDate
	 */
	public LocalDate getLastDate();
	
}
