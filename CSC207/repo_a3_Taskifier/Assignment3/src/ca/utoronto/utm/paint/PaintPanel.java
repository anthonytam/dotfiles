package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.exceptions.DeserializationStrategyMissingException;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

class PaintPanel extends JPanel {
	private static final long serialVersionUID = 3277442988868869424L;
	private ArrayList<PaintCommand> commands = new ArrayList<>();
	
	public PaintPanel(){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
	}
	
	public void setCommands(ArrayList<PaintCommand> commands){
		this.commands=commands;
	}
	public void reset(){
		this.commands.clear();
		this.repaint();
	}
	
	public void addCommand(PaintCommand command){
		this.commands.add(command);
	}

	/**
	 * Save the current project to a file. The file is selected in the Paint's view of the application.
	 * This will cycle through the list of commands and generate their relative serials to be saved.
	 * @param writer A write which is connected to the file ehich the user selected.
	 */
	public void save(PrintWriter writer){
		writer.write("PaintSaveFileVersion1.0\n");
		for (PaintCommand c : commands) {
			try {
				writer.write(c.serialize().write());
			} catch (DeserializationStrategyMissingException ex) {
				System.out.println("Error saving the file, missing strategy.");
			}
		}
		writer.write("EndPaintSaveFile\n");
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g;		
		for(PaintCommand c: this.commands){
			c.execute(g2d);
		}
		g2d.dispose();
	}
}
