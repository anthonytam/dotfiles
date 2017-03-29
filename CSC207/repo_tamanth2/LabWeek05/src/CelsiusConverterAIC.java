import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CelsiusConverterAIC {

	JTextField tfCelsius;
	JLabel lblFarenheight;

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	CelsiusConverterAIC() {
		JFrame frame = new JFrame("Celsius Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new GridLayout(2, 2));

		this.tfCelsius = new JTextField(10);
		frame.getContentPane().add(tfCelsius);

		JLabel lblCelsius = new JLabel("Celsius");
		frame.getContentPane().add(lblCelsius);

		JButton btnConvert = new JButton("Convert");

		frame.getContentPane().add(btnConvert);

		this.lblFarenheight = new JLabel();
		frame.getContentPane().add(lblFarenheight);

		// Using an anonymous inner class, it has access to instance variables
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double celsius = Double.parseDouble(tfCelsius.getText());
					double farenheight = (celsius * 9 / 5) + 32;
					lblFarenheight.setText("" + farenheight);
				} catch (NumberFormatException exception) {

				}
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public static void createAndShowGUI() {
		CelsiusConverterAIC cc = new CelsiusConverterAIC();
	}
}
