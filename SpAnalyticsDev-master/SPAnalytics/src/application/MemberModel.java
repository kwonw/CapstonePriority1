package application;

import javafx.beans.property.SimpleObjectProperty;


//used for modifying the table

public class MemberModel {
	private SimpleObjectProperty season = new SimpleObjectProperty();
	private SimpleObjectProperty GP= new SimpleObjectProperty();
	private SimpleObjectProperty G= new SimpleObjectProperty();
	private SimpleObjectProperty A= new SimpleObjectProperty();
	private SimpleObjectProperty PTS= new SimpleObjectProperty();
	private SimpleObjectProperty plusMinus= new SimpleObjectProperty();
	private SimpleObjectProperty SOG= new SimpleObjectProperty();
	private SimpleObjectProperty percent= new SimpleObjectProperty();
	private SimpleObjectProperty PPG= new SimpleObjectProperty();
	private SimpleObjectProperty PPA= new SimpleObjectProperty();
	private SimpleObjectProperty SHG= new SimpleObjectProperty();
	private SimpleObjectProperty GWG= new SimpleObjectProperty();
	private SimpleObjectProperty GTG= new SimpleObjectProperty();
	private SimpleObjectProperty TOI= new SimpleObjectProperty();
	private SimpleObjectProperty PROD= new SimpleObjectProperty();
	
	public MemberModel() {};
	
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

	public Object getG() {
		return G.get();
	}

	public void setG(Object G) {
		this.G = new SimpleObjectProperty(G);
	}

	public Object getA() {
		return A.get();
	}

	public void setA(Object A) {
		this.A = new SimpleObjectProperty(A);
	}

	public Object getPTS() {
		return PTS.get();
	}

	public void setPTS(Object PTS) {
		this.PTS = new SimpleObjectProperty(PTS);
	}

	public Object getPlusMinus() {
		return plusMinus.get();
	}

	public void setPlusMinus(Object plusMinus) {
		this.plusMinus = new SimpleObjectProperty(plusMinus);
	}

	public Object getSOG() {
		return SOG.get();
	}

	public void setSOG(Object SOG) {
		this.SOG = new SimpleObjectProperty(SOG);
	}

	public Object getPercent() {
		return percent.get();
	}

	public void setPercent(Object percent) {
		this.percent = new SimpleObjectProperty(percent);
	}

	public Object getPPG() {
		return PPG.get();
	}

	public void setPPG(Object PPG) {
		this.PPG = new SimpleObjectProperty(PPG);
	}

	public Object getPPA() {
		return PPA.get();
	}

	public void setPPA(Object PPA) {
		this.PPA = new SimpleObjectProperty(PPA);
	}

	public Object getSHG() {
		return SHG.get();
	}

	public void setSHG(Object SHG) {
		this.SHG = new SimpleObjectProperty(SHG);
	}

	public Object getGWG() {
		return GWG.get();
	}

	public void setGWG(Object GWG) {
		this.GWG = new SimpleObjectProperty(GWG);
	}

	public Object getGTG() {
		return GTG.get();
	}

	public void setGTG(Object GTG) {
		this.GTG = new SimpleObjectProperty(GTG);
	}

	public Object getTOI() {
		return TOI.get();
	}

	public void setTOI(Object TOI) {
		this.TOI = new SimpleObjectProperty(TOI);
	}

	public Object getPROD() {
		return PROD.get();
	}

	public void setPROD(Object PROD) {
		this.PROD = new SimpleObjectProperty(PROD);
	}
	
	
}

