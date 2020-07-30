package application;

/**
 * List of every state in the US, including the territories. The whole US is
 * first variable.
 */
public enum States { // TODO does this need to be public?
	ALABAMA("Alabama"),
	ALASKA("Alaska"),
	AMERICAN_SOMOA("American Somoa"),
	ARIZONA("Arizona"),
	ARKANSAS("Arkansas"),
	CALIFORNIA("California"),
	COLORADO("Colorado"),
	CONNECTICUT("Connecticut"),
	DELAWARE("Delaware"),
	DIAMOND_PRINCESS("Diamond Princess"),
	DC("District of Columbia"),
	FLORIDA("Florida"),
	GERORGIA("Georgia"),
	GRAND_PRINCESS("Grand Princess"),
	GUAM("Guam"),
	HAWAII("Hawaii"),
	IDAHO("Idaho"),
	ILLINOIS("Illinois"),
	INDIANA("Indiana"),
	IOWA("Iowa"),
	KANSAS("Kansas"),
	KENTUCKY("Kentucky"),
	LOUISIANA("Louisiana"),
	MAINE("Maine"),
	MARYLAND("Maryland"),
	MASSACHUSETTS("Massachusetts"),
	MICHIGAN("Michigan"),
	MINNESOTA("Minnesota"),
	MISSISSIPPI("Mississippi"),
	MISSOURI("Missouri"),
	MONTANA("Montana"),
	NEBRASKA("Nebraska"),
	NEVADA("Nevada"),
	NEW_HAMPSHIRE("New Hampshire"),
	NEW_JERSY("New Jersey"),
	NEW_MEXICO("New Mexico"),
	NEW_YORK("New York"),
	NORTH_CAROLINA("North Carolina"),
	NORTH_DAKOTA("North Dakota"),
	NORTHERN_MARINA_ISLANDS("Northern Marina Islands"),
	OHIO("Ohio"),
	OKLAHOMA("Oklahoma"),
	OREGON("Oregon"),
	PENNSYLVANIA("Pennsylvania"),
	PUERTO_RICO("Puerto Rico"),
	RHODE_ISLAND("Rhode Island"),
	SOUTH_CAROLINA("South Carolina"),
	SOUTH_DAKOTA("South Dakota"),
	TENNESSEE("Tennessee"),
	TEXAS("Texas"),
	UTAH("Utah"),
	VERMONT("Vermont"),
	VIRGIN_ISLANDS("Virgin Islands"),
	VIRGINIA("Virginia"),
	WASHINGTON("Washington"),
	WEST_VIRGINIA("West Virginia"),
	WISCONSIN("Wisconsin"),
	WYOMING("Wyoming");
	
	private String name; // name that matches input files
	
	/**
	 * Places string val of State in name
	 * 
	 * @param name -the String value of the State (or Territory)
	 */
	States(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
