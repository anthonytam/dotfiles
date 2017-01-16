import javax.swing.Timer;

public class TimerDemo {
	public static void createAndShowGUI() {
		TimerHandler th = new TimerHandler();
		Timer timer = new Timer(1000, th);
		timer.start();

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
