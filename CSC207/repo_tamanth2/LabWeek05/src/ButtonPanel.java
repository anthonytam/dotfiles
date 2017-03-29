import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;  

class ButtonPanel extends JPanel implements ActionListener {
	JTextField tf;
	public ButtonPanel(){
		// Method of JComponent
		setBackground(Color.white);
		setMinimumSize(new Dimension(100,100));

		JButton b;
		b=new JButton("Up");    add(b); b.addActionListener(this);
		b=new JButton("Down");  add(b); b.addActionListener(this);
		b=new JButton("Left");  add(b); b.addActionListener(this);
		b=new JButton("Right"); add(b); b.addActionListener(this);
		tf=new JTextField(10);
		add(tf);
	}

	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		tf.setText(e.getActionCommand());
	}
}