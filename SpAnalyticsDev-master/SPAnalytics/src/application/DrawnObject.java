package application;

import java.util.ArrayList;

import javafx.scene.paint.Paint;

public class DrawnObject {
	private ArrayList<Point> points;
	private String text;
	private double width;
	private boolean hasText;
	
	public DrawnObject() {}
	public DrawnObject(double xPos, double yPos, Paint color, double width) {
		points = new ArrayList<Point>();
		Point p = new Point(xPos, yPos, color);
		points.add(p);
		this.width = width;
		hasText = false;
	}
	
	public DrawnObject(Point p, Paint color, double width) {
		points = new ArrayList<Point>();
		points.add(p);
		this.width = width;
		hasText = false;
	}
	
	public DrawnObject(Point p, Paint color, double width, String text) {
		this(p, color, width);
		this.text = text;
		hasText = true;
	}
	
	public DrawnObject(double xPos, double yPos, Paint color, double width, String text) {
		this(xPos, yPos, color, width);
		this.text = text;
		hasText = true;
	}

	public Point getPoint(int i) {
		return points.get(i);
	}
	
	public void addPoint(double x, double y, Paint color) {
		Point p = new Point(x, y, color);
		points.add(p);
	}
	
	public int size() {
		return points.size();
	}
	
	public Point getLastPoint() {
		return points.get(points.size()-1);
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void removeLast() {
		points.remove(points.size()-1);
	}

	public String getText() {
		if(hasText) {
			return text;
		} else {
			return null;
		}
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public void setHasText(boolean hasText) {
		this.hasText = hasText;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setWidth(double width) {
		this.width = width;
	}
}
