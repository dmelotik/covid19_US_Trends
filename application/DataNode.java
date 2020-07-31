package application;

import java.time.LocalDate;

public class DataNode {

	private LocalDate date;
	private States state;
	private int confirmed;
	private int deaths;
	private int tests;

	public DataNode(int confirmed, int deaths, int tests, LocalDate date, States state) {
		this.date = date;
		this.state = state;
		this.confirmed = confirmed;
		this.deaths = deaths;
		this.tests = tests;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public States getState() {
		return state;
	}
	
	public int getNumOfConfirmed() {
		return confirmed;
	}
	
	public int getNumOfDeaths() {
		return deaths;
	}
	
	public int getNumOfTest() {
		return tests;
	}
}
