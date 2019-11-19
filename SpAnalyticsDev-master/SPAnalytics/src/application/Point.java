package application;

import javafx.scene.paint.Paint;

public class Point {
	private double x, y;
	private Paint color;
	
	public Point(double x, double y, Paint color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Paint getColor() {
		return color;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setColor(Paint color) {
		this.color = color;
	}
	
}
