package application;

import java.util.ArrayList;

public class Member {

	public int G;
	public int A;
	public int PTS;
	public int winsOrLosses;
	public int SOG;
	public int percent;
	public int PPG;
	public int PPA;
	public int SHG;
	public int GWG;
	public int GTG;
	public int TOIG;
	public int PROD;
	
	
	public Member() {}


	public Member(int g, int a, int pTS, int winsOrLosses, int sOG, int percent, int pPG, int pPA, int sHG,
			int gWG, int gTG, int tOIG, int pROD) {
		super();
		G = g;
		A = a;
		PTS = pTS;
		this.winsOrLosses = winsOrLosses;
		SOG = sOG;
		this.percent = percent;
		PPG = pPG;
		PPA = pPA;
		SHG = sHG;
		GWG = gWG;
		GTG = gTG;
		TOIG = tOIG;
		PROD = pROD;
	}
	
	
}
