import javax.swing.*;  
import java.awt.*;

class GraphicPanel extends JPanel {
	int i=0;
	public GraphicPanel(){
		// Method of JComponent
		setBackground(Color.blue);
	}

	// Use g to draw on the JPanel, lookup java.awt.Graphics in
	// the javadoc to see more of what this can do for you!!
	
	public void paintComponent(Graphics g) {
        	super.paintComponent(g); //paint background

		// methods of Applet
		// setBackground (Color.blue); 
		// Origin is at the top left of the applet 50 over, 75 down
		g.drawString ("i="+i, 50, 75);
		i=i+1;
	}
}