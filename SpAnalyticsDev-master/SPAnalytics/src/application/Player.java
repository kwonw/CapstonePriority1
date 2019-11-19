package application;

import java.util.HashMap;
import java.util.Map;

public class Player {
public int jerseyNo;
public String name;
public String height;
public String weight;
public String birthDate;
public String homeTown;
public Map<String, PlayerStats> stats= new HashMap<>();
public Map<String, Clip> clips= new HashMap<>();


/**
 * Generate player with no fields
 */
public Player() {}

/**
 * Generate player for all fields
 * @param jerseyNo
 * @param name
 * @param position
 * @param height
 * @param weight
 * @param birthDate
 * @param homeTown
 * @param season
 * @param gP
 * @param w
 * @param l
 * @param t
 * @param gA
 * @param gAA
 * @param sA
 * @param sV
 * @param sVpercent
 * @param sO
 */
public Player(int jerseyNo, String name, String height, String weight, String birthDate,
		String homeTown, Map<String, PlayerStats> stats) {
	this.jerseyNo = jerseyNo;
	this.name = name;
	this.height = height;
	this.weight = weight;
	this.birthDate = birthDate;
	this.homeTown = homeTown;
	this.stats = stats;
	
}


//add player in database
public Player(int jerseyNo, String name, String height, String weight, String birthDate,
		String homeTown) {
	this.jerseyNo = jerseyNo;
	this.name = name;
	this.height = height;
	this.weight = weight;
	this.birthDate = birthDate;
	this.homeTown = homeTown;	
}

//remove player from database
public Player(int jerseyNo) {
	this.jerseyNo = jerseyNo;
}





}
