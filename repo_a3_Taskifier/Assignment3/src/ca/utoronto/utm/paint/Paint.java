package ca.utoronto.utm.paint;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class Paint extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4031525251752065381L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Paint::new);
	}

	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;

	public Paint() {
		super("Paint"); // set the title and do other JFrame init
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());

		Container c = this.getContentPane();

		c.add(this.paintPanel = new PaintPanel(), BorderLayout.CENTER);
		c.add(this.shapeChooserPanel = new ShapeChooserPanel(this), BorderLayout.WEST);
		this.pack();
		this.setVisible(true);
	}

	public PaintPanel getPaintPanel() {
		return paintPanel;
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("File");

		// a group of JMenuItems
		menuItem = new JMenuItem("New");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Open") {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					FileReader fr = new FileReader(file);
					BufferedReader reader = new BufferedReader(fr);
					PaintSaveFileParser parser = new PaintSaveFileParser();
					if (parser.parse(reader)) {
						this.paintPanel.setCommands(parser.getCommands());
					} else System.out.println(parser.getErrorMessage());
					fr.close();
					reader.close();
					this.paintPanel.repaint();
				} catch (FileNotFoundException ex) {
					System.out.println("The file " + file.getName() + " is not found.");
				} catch (IOException ex) {
					System.out.print("IO Exception while closing the file.");
				}
				System.out.println("Opening: " + file.getName() + "." + "\n");
			} else {
				System.out.println("Open command cancelled by user." + "\n");
			}
		} else if (e.getActionCommand() == "Save") {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					PrintWriter writer = new PrintWriter(file);
					this.paintPanel.save(writer);
					writer.close();
				} catch (FileNotFoundException ex) {
					System.out.println("The file " + file.getName() + " does not exist");
				}
				System.out.println("Saving: " + file.getName() + "." + "\n");
			} else {
				System.out.println("Save command cancelled by user." + "\n");
			}
		} else if (e.getActionCommand() == "New") {
			this.paintPanel.reset();
			this.shapeChooserPanel.reset();
		}
		System.out.println(e.getActionCommand());
	}
}
