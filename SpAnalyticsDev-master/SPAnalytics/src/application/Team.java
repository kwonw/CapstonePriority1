package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team {
public String teamName;
public int totalGames;
public int goalsFor;
public int goalsAgainst;
public int shotsFor;
public int shotsAgainst;
public String record;
public Map<String, Game> games = new HashMap<>();

public Team() {

}

public Team(String teamName, int totalGames, int goalsFor, int goalsAgainst, int shotsFor, int shotsAgainst,
		String record) {
	super();
	this.teamName = teamName;
	this.totalGames = totalGames;
	this.goalsFor = goalsFor;
	this.goalsAgainst = goalsAgainst;
	this.shotsFor = shotsFor;
	this.shotsAgainst = shotsAgainst;
	this.record = record;
}

public Team(String teamName, int totalGames, int goalsFor, int goalsAgainst, int shotsFor, int shotsAgainst,
		String record, Map<String, Game> games) {
	super();
	this.teamName = teamName;
	this.totalGames = totalGames;
	this.goalsFor = goalsFor;
	this.goalsAgainst = goalsAgainst;
	this.shotsFor = shotsFor;
	this.shotsAgainst = shotsAgainst;
	this.record = record;
	this.games = games;
}


}
