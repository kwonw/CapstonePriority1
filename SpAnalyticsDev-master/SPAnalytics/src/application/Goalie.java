package application;

public class Goalie {
	public int W;
	public int L;
	public int T;
	public int GA;
	public int GAA;
	public int shotsFaced;
	public int shotsSaved;
	public int savePercent;
	public int SO;
	
	public Goalie(){}
	public Goalie(int shotsFaced, int shotsSaved, int savePercent) {
		this.shotsFaced = shotsFaced;
		this.shotsSaved = shotsSaved;
		this.savePercent = savePercent;

	}
	public Goalie(int w, int l, int t, int gA, int gAA, int shotsFaced, int shotsSaved, int savePercent, int sO) {
		super();
		W = w;
		L = l;
		T = t;
		GA = gA;
		GAA = gAA;
		this.shotsFaced = shotsFaced;
		this.shotsSaved = shotsSaved;
		this.savePercent = savePercent;
		SO = sO;
	}
	
	
}
