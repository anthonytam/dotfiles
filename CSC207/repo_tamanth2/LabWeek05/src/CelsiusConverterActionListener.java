import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CelsiusConverterActionListener implements ActionListener {
	JLabel lblFarenheight;
	JTextField tfCelsius;
	
	CelsiusConverterActionListener(JTextField tfCelsius, JLabel lblFarenheight) {
		this.lblFarenheight=lblFarenheight;
		this.tfCelsius=tfCelsius;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			double celsius=Double.parseDouble(this.tfCelsius.getText());
			double farenheight=(celsius*9/5)+32;
			this.lblFarenheight.setText(""+farenheight);
		} catch (NumberFormatException exception) {
			
		}
		
	}
}
