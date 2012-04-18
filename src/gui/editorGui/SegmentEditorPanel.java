package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SegmentEditorPanel extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5333016067647810660L;
	private JPanel		segmentPane;
	private JPanel		segmentDia;

	public SegmentEditorPanel() {
		segmentPane	= new JPanel();
		segmentPane.setLayout( new GridLayout(3,1,0,10) );
		segmentPane.add( new SegmentPanel("Segment 1") );
		segmentPane.add( new SegmentPanel("Segment 2") );
		segmentPane.add( new SegmentPanel("Segment 3") );
		
		segmentDia	= new JPanel();
		segmentDia.setSize( new Dimension(0, 150) );
		segmentDia.setMinimumSize( new Dimension(0, 150) );
		segmentDia.setBackground( Color.GRAY );
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
//																					x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, new JScrollPane(segmentPane),	0, 1, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, segmentDia,					0, 0, 1, 1, 1, 1);
	}
	
}