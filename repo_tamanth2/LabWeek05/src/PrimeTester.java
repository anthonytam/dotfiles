import java.awt.FlowLayout;

import javax.swing.*;

public class PrimeTester {
	
	private static JTextField tNumber;
	private static JTextField tResult;
	
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Prime Tester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the frame layout
		frame.getContentPane().setLayout(new FlowLayout());

		// Create a text field for the user to enter a button
		tNumber = new JTextField(10);
		frame.getContentPane().add(tNumber);
		
		// Create a button to activate the prime testing method
		JButton bTest = new JButton("Is Prime?");
		frame.getContentPane().add(bTest);

		// Create another text field to display the result
		tResult = new JTextField(10);
		tResult.setEditable(false);
		frame.getContentPane().add(tResult);

		// Create a new event handler
		PrimeTesterActionListener listener = new PrimeTesterActionListener();
		bTest.addActionListener(listener);

		// finalize the frame
		frame.pack();
		frame.setVisible(true);
	}
	
	public static String getTestFieldValue() {
		return tNumber.getText();
	}
	
	public static void setResultValue(String result) {
		tResult.setText(result);
	}
	
}
