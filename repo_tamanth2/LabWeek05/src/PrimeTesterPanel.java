import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;  

class PrimeTesterPanel extends JPanel implements ActionListener {
	
	JTextField tNumber;
	JTextField tResult;
	
	public PrimeTesterPanel(){
		// Method of JComponent
		setBackground(Color.white);
		setMinimumSize(new Dimension(100,100));
		
		tNumber = new JTextField(10);
		
		add(tNumber);
		JButton bTestNumber = new JButton("Is Prime?");
		add(bTestNumber);
		bTestNumber.addActionListener(this);
		
		tResult = new JTextField(10);
		tResult.setEditable(false);
		add(tResult);
	}

	public void actionPerformed(ActionEvent e){
		try { 
			int numberToTest = Integer.parseInt(tNumber.getText()); 
			if (isPrime(numberToTest)) 
				tResult.setText("yes");
			else
				tResult.setText("no");
				
		} catch(NumberFormatException nfe) {
			tResult.setText("invalid number");
		}
	}
	
	private static boolean isPrime(int n) {
		for (int i = 2; i < n;i++)
			if(n % i == 0)
				return false;
		return true;
	}
}