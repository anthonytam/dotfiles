import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class PrimeTesterActionListener implements ActionListener {
	
	PrimeTesterActionListener() {
	
	}

	public void actionPerformed(ActionEvent e) {
		String output = PrimeTester.getTestFieldValue();
		try { 
			int numberToTest = Integer.parseInt(output); 
			if (isPrime(numberToTest)) 
				PrimeTester.setResultValue("yes");
			else
				PrimeTester.setResultValue("no");
				
		} catch(NumberFormatException nfe) {
			PrimeTester.setResultValue("invalid number");
		}
		
	}
	
	private boolean isPrime(int n) {
		for (int i = 2; i < n;i++)
			if(n % i == 0)
				return false;
		return true;
	}
}
