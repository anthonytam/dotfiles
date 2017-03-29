import javax.swing.*; // For JFrame
import java.awt.*; // For BorderLayout
class BorderFrame {
	public static void createAndShowGUI() {
		// JFrame has a default content pane with a border layout
		JFrame f=new JFrame("My First Frame"); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new JButton("North"),BorderLayout.NORTH);
		f.getContentPane().add(new JButton("South"),BorderLayout.SOUTH);
		f.getContentPane().add(new JButton("East"),BorderLayout.EAST);
		f.getContentPane().add(new JButton("West"),BorderLayout.WEST);
		f.getContentPane().add(new JTextField("Center"),BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
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