package application;

import java.util.ArrayList;

public class TeamScore {
public String teamName;
public int goalsFor;
public int goalsAgainst;
public int shotsFor;
public int shotsAgainst;
public ArrayList<DrawnObject> netChart;
public ArrayList<DrawnObject> scoringChart;

public TeamScore() {}

public TeamScore(String teamName, int goalsFor, int goalsAgainst, int shotsFor, int shotsAgainst) {
	this.teamName = teamName;
	this.goalsFor = goalsFor;
	this.goalsAgainst = goalsAgainst;
	this.shotsFor = shotsFor;
	this.shotsAgainst = shotsAgainst;
	
}

public String getTeamName() {
	return teamName;
}

public void setTeamName(String teamName) {
	this.teamName = teamName;
}

public int getGoalsFor() {
	return goalsFor;
}

public void setGoalsFor(int goalsFor) {
	this.goalsFor = goalsFor;
}

public int getGoalsAgainst() {
	return goalsAgainst;
}

public void setGoalsAgainst(int goalsAgainst) {
	this.goalsAgainst = goalsAgainst;
}

public int getShotsFor() {
	return shotsFor;
}

public void setShotsFor(int shotsFor) {
	this.shotsFor = shotsFor;
}

public int getShotsAgainst() {
	return shotsAgainst;
}

public void setShotsAgainst(int shotsAgainst) {
	this.shotsAgainst = shotsAgainst;
}

public ArrayList<DrawnObject> getNetChart() {
	return netChart;
}

public void setNetChart(ArrayList<DrawnObject> netChart) {
	this.netChart = netChart;
}

public ArrayList<DrawnObject> getScoringChart() {
	return scoringChart;
}

public void setScoringChart(ArrayList<DrawnObject> scoringChart) {
	this.scoringChart = scoringChart;
}

}
