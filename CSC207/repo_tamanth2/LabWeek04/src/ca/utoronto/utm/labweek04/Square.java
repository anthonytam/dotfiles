package ca.utoronto.utm.labweek04;

public class Square extends Rectangle {

	public Square() {
		super("blue", 10, 10, 100, 100);
	}
	
	public Square(String c, int width, int x, int y) {
		super(c, width, width, x, y);
	}	
	
	public void setWidth(int width) {
		super.setWidth(width);
		super.setHeight(width);
	}
	
	public void setHeight(int height) {
		super.setWidth(height);
		super.setHeight(height);
	}
}
