import java.awt.FlowLayout;

import javax.swing.*;
class UseButtonPanel {
	public static void createAndShowGUI() {
		JFrame jf=new JFrame("Graphics Panel");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new FlowLayout());
		
		jf.add(new ButtonPanel());
		jf.add(new ButtonPanel());
		jf.add(new ButtonPanel());
		
		
		jf.setSize(600,600);
		jf.setResizable(false);
		jf.setVisible(true);
	}
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}