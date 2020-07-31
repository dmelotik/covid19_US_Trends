package application;

import java.time.LocalDate;

public class DataStructure implements DataStructureADT {

	private int days; // num of days in the ADT
	private DataNode[][] dataArr; // array containing all of the data

	public DataStructure(int days) {
		this.days = days;
		dataArr = new DataNode[days][];
	}

	/**
	 * This method inserts a the new node into the data structure
	 */
	@Override
	public void insert(DataNode node) throws NullPointerException {
		// TODO Auto-generated method stub

	}

	@Override
	public DataNode get(LocalDate date, States state)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDate getLastDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataArr(int outerIndex, int innerIndex, DataNode node) {
		dataArr[outerIndex][innerIndex] = node;
	}

	public void setDataArr(int index, DataNode[] arr) {
		dataArr[index] = arr;
	}

	public DataNode[][] getDataArr() {
		return dataArr;
	}

	public int getNumOfDays() {
		return days;
	}
	
}
