import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;  

class ButtonBallPanel extends JPanel implements ActionListener {
	int x=50,y=50;
	JTextField tf;
	public ButtonBallPanel(){
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
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.fillOval(x,y,10,10);
	}
	
	public void actionPerformed(ActionEvent e){
		String c=e.getActionCommand();
		tf.setText(c);
		if(c.equals("Up"))y--;
		else if(c.equals("Down"))y++;
		else if(c.equals("Left"))x--;
		else if(c.equals("Right"))x++;
		repaint();
	}
}