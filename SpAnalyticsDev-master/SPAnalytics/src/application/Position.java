package application;

public class Position {
	public Goalie goalie;
	public Member member;
	public String namePosition;
	
	public Position() {}
	public Position(String namePosition) {
		this.namePosition = namePosition;
	}
	public Goalie goalie(Goalie goalie) {
		
		this.goalie = goalie;
		return this.goalie;
	}
	public Member member(Member member) {

		this.member = member;
		return this.member;
	}
	
	
}
