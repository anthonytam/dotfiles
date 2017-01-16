import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class CelsiusConverter {
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
		JFrame frame = new JFrame("Celsius Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new GridLayout(2,2));

		JTextField tfCelsius = new JTextField(10);
		frame.getContentPane().add(tfCelsius);
		
		JLabel lblCelsius=new JLabel("Celsius");
		frame.getContentPane().add(lblCelsius);
		
		JButton btnConvert=new JButton("Convert");

		frame.getContentPane().add(btnConvert);

		JLabel lblFarenheight=new JLabel();
		frame.getContentPane().add(lblFarenheight);
		
		CelsiusConverterActionListener ccal = new CelsiusConverterActionListener(tfCelsius, lblFarenheight);
		btnConvert.addActionListener(ccal);
		
		frame.pack();
		frame.setVisible(true);
	}
}
