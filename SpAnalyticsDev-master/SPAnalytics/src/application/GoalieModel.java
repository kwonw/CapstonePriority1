package application;

import javafx.beans.property.SimpleObjectProperty;


//used for modifying the table
public class GoalieModel {
	private SimpleObjectProperty season= new SimpleObjectProperty();
	private SimpleObjectProperty GP= new SimpleObjectProperty();
	private SimpleObjectProperty W= new SimpleObjectProperty();
	private SimpleObjectProperty L= new SimpleObjectProperty();
	private SimpleObjectProperty T= new SimpleObjectProperty();
	private SimpleObjectProperty GA= new SimpleObjectProperty();
	private SimpleObjectProperty GAA= new SimpleObjectProperty();
	private SimpleObjectProperty SA= new SimpleObjectProperty();
	private SimpleObjectProperty SV= new SimpleObjectProperty();
	private SimpleObjectProperty SVpercent= new SimpleObjectProperty();
	private SimpleObjectProperty SO= new SimpleObjectProperty();
	
	public GoalieModel() {}
	
	

	public Object getSeason() {
		return season.get();
	}

	public void setSeason(Object season) {
		this.season = new SimpleObjectProperty(season);
	}
	
	public Object getGP() {
		return GP.get();
	}

	public void setGP(Object GP) {
		this.GP = new SimpleObjectProperty(GP);
	}
	
	public Object getW() {
		return W.get();
	}

	public void setW(Object W) {
		this.W = new SimpleObjectProperty(W);
	}
	
	public Object getL() {
		return L.get();
	}

	public void setL(Object L) {
		this.L = new SimpleObjectProperty(L);
	}
	
	public Object getT() {
		return T.get();
	}

	public void setT(Object T) {
		this.T = new SimpleObjectProperty(T);
	}
	public Object getGA() {
		return GA.get();
	}

	public void setGA(Object GA) {
		this.GA = new SimpleObjectProperty(GA);
	}
	public Object getGAA() {
		return GAA.get();
	}

	public void setGAA(Object GAA) {
		this.GAA = new SimpleObjectProperty(GAA);
	}
	public Object getSA() {
		return SA.get();
	}

	public void setSA(Object SA) {
		this.SA = new SimpleObjectProperty(SA);
	}
	public Object getSV() {
		return SV.get();
	}

	public void setSV(Object SV) {
		this.SV = new SimpleObjectProperty(SV);
	}
	public Object getSVpercent() {
		return SVpercent.get();
	}

	public void setSVpercent(Object SVpercent) {
		this.SVpercent = new SimpleObjectProperty(SVpercent);
	}
	public Object getSO() {
		return SO.get();
	}

	public void setSO(Object SO) {
		this.SO = new SimpleObjectProperty(SO);
	}
}
