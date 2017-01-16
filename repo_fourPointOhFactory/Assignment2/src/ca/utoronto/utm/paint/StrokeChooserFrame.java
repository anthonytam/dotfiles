package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.events.shapes.ShapeLineWidthSelectedEvent;
import ca.utoronto.utm.paint.shapes.PaintShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Optional;

/**
 * A JPanel generated when the user selects a stroke. When closed, it will return the value
 * of the desired stroke
 */
public class StrokeChooserFrame extends JFrame implements ActionListener, AdjustmentListener {
    private View view;

    private JTextField tSelection;
    private JScrollBar sSelection;

    public StrokeChooserFrame(View view) {
        super("Stroke Selection");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.view = view;

        this.setLayout(new FlowLayout());

        sSelection = new JScrollBar(JScrollBar.HORIZONTAL);
        sSelection.setMinimum(100);
        sSelection.setMaximum(1000);
        sSelection.setUnitIncrement(100);
        sSelection.setValue(view.getOptions().getStrokeWidth() * 100);
        sSelection.addAdjustmentListener(this);
        this.add(sSelection);

        tSelection = new JTextField(15);
        tSelection.setEditable(false);
        tSelection.setText("Thickness: " + (sSelection.getValue() / 100));
        this.add(tSelection);

        JButton bOk = new JButton("Ok");
        bOk.addActionListener(this);
        this.add(bOk);
        JButton bCancel = new JButton("Cancel");
        bCancel.addActionListener(this);
        this.add(bCancel);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        tSelection.setText("Thickness: " + (sSelection.getValue() / 100));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Optional<PaintShape> shape = view.getSelectedShape();
        if (shape.isPresent() && !e.getActionCommand().equals("Cancel")) {
            ShapeLineWidthSelectedEvent event = new ShapeLineWidthSelectedEvent(shape.get(), view.getOptions(), sSelection.getValue() / 100);
            view.getEventDispatcher().dispatchEvent(event);
        }
        this.dispose();
    }
}
