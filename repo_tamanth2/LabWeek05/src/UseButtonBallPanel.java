import javax.swing.*;
class UseButtonBallPanel {
	public static void createAndShowGUI() {
		JFrame jf=new JFrame("Improved Animation Panel");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(new ImprovedAnimationPanel());
		jf.setSize(100,100);
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