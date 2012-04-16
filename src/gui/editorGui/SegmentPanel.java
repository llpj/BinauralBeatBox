package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

public class SegmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208218652817209244L;

	private String		title;
	
	private JList		startMood;
	private JList		targetModd;
	private JSpinner	durationSpin;
	
	private JButton		removeBtn;
	private	JButton		editBtn;
	
	private JButton		moveUpBtn;
	private JButton		moveDownBtn;
	
	public SegmentPanel(String title) {
		this.title = title;
		setBorder(  new TitledBorder(this.title) );
		
		startMood		= new JList();
		targetModd		= new JList();
		durationSpin	= new JSpinner();
		
		removeBtn		= new JButton("-");
		editBtn			= new JButton("0");
		
		moveUpBtn		= new JButton("^");
		moveDownBtn		= new JButton("v");
		
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Start Stimmug:"),		0, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Ziel Stimmug:"),		0, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, startMood,						1, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, targetModd,						1, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments"),	2, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,						2, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,						3, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,							3, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,						4, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,						4, 1, 1, 1, 1, 0);
	}
}
