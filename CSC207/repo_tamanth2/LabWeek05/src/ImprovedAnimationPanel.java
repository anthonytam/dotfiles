import java.awt.*;  //abstract windowing toolkit - contains graphics
import javax.swing.*;  
import java.awt.event.*;

// Note: Could have subclassed Canvas and added the canvas to the frame as well

public class ImprovedAnimationPanel extends JPanel implements ActionListener {

	Timer timer;
	public ImprovedAnimationPanel(){
		// Method of JComponent
		setBackground(Color.blue);
		setMinimumSize(new Dimension(150,200));
		timer = new Timer(100, this);
		timer.start();
	}

	// Use g to draw on the Applet, lookup java.awt.Graphics in
	// the javadoc to see more of what this can do for you!!

	//!! THIS IS NOT THE OO APPROACH!!
	int x=0,y=0;
	public void paintComponent(Graphics g) {
        	super.paintComponent(g); //paint background
		g.fillOval(x+10,y+10,x+3,x+3);
	}

	public void actionPerformed(ActionEvent e){ 
		// Move ball
		x=(x+1)% 100;
		y=(y+1)% 100;
		repaint();  // Schedule a repaint of screen
	}
}
