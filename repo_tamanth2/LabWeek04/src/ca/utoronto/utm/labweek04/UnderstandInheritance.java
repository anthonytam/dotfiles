package ca.utoronto.utm.labweek04;
public class UnderstandInheritance {

	public static void main(String[] args) {
		// 6) Review class Square, understand what happens when we execute the following:
		// that is, which methods are called and when...
		
		//Print Starting to the console
		System.out.println("Starting");
		//Creates a new instance of square by running the constructor.
		//Selects the constructor requiring no parameters. This constructor
		//calls the super class and creates a new rectangle object the the length 
		//and width equal to each other. The rectangle constructor calls its super 
		//class and creates a new shape object with a color, x, and y coordinate.
		Square s=new Square(); 
		//Calls the toString method of square to print its attributes. The method does
		//not exist in the Square object, so it looks in its super class. The Rectangle object
		//does contain a to string method. This method calls the to string of the Shape object and
		//appends the rectangle attributes at the end.
		System.out.println(s.toString());
		//Calls the setWidth method located in Square. This calls its super class and sets both the
		//height and the width accordingly.
		s.setWidth(20);
		//calls setX in the Square object, which references Rectangle, referencing Shape.
		s.setX(10);
		
	}

}
