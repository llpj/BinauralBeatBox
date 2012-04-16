package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SegmentEditorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5333016067647810660L;

//	private JScrollPane	segmentPane;
	private JPanel		segmentPane;
	private JPanel		segmentDia;

	public SegmentEditorPanel() {
		segmentPane	= new JPanel();
		segmentPane.setBackground( Color.BLUE );
		segmentPane.add( new SegmentPanel("Segment 1") );
		segmentPane.add( new SegmentPanel("Segment 2") );
		segmentPane.add( new SegmentPanel("Segment 3") );
		
		segmentDia	= new JPanel();
		segmentDia.setSize( new Dimension(0, 300) );
		segmentDia.setBackground( Color.GRAY );
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JScrollPane(segmentPane),	0, 0, 1, 1, 1, 2);
		GuiFunctionLib.addGridBagContainer(this, gbl, segmentDia,					0, 1, 1, 1, 1, 1);
	}
	
}