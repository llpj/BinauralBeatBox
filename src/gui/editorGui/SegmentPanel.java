package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

public class SegmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208218652817209244L;

	private String		title;
	
	//basic Elemente (casual mode)
	private JList		startMood;
	private JList		targetModd;
	private JSpinner	durationSpin;
	
	private JButton		removeBtn;
	private	JButton		editBtn;
	
	private JButton		moveUpBtn;
	private JButton		moveDownBtn;
	
	//zusätzliche profi mode Elemente für linke und rechte Frequenz
	private JPanel		leftFreqPnl;
	private JSpinner	startLeftFreq;
	private JSpinner	targetLeftFreq;
	private JSlider		leftVolume;
	
	private JPanel		rightFreqPnl;
	private JSpinner	startRightFreq;
	private JSpinner	targetRightFreq;
	private JSlider		rightVolume;
	
	public SegmentPanel(String title) {
		this.title = title;
		setBorder(  new TitledBorder(this.title) );

		initBasicElements();
		initProfiModeElements();

//		casualModeLayout();
		profiModeLayout();
	}
	
	private void casualModeLayout() {
		removeAll();
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Start Stimmug:"),		0, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Ziel Stimmug:"),		0, 1, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, startMood,						1, 0, 1, 1, 2, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, targetModd,						1, 1, 1, 1, 2, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments"),	2, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,						2, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,						3, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,							3, 1, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,						4, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,						4, 1, 1, 1, 0, 0);
		
		updateUI();
	}
	
	private void profiModeLayout() {
		removeAll();
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, leftFreqPnl,						0, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, rightFreqPnl,						1, 0, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments"),	0, 1, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,						1, 1, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,						2, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,							2, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,						3, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,						3, 1, 1, 1, 0, 0);
		
		updateUI();
	}
	
	private void initBasicElements() {
		startMood		= new JList();
		targetModd		= new JList();
		durationSpin	= new JSpinner();
		
		removeBtn		= new JButton("-");
		editBtn			= new JButton("0");
		
		moveUpBtn		= new JButton("^");
		moveDownBtn		= new JButton("v");
	}
	
	private void initProfiModeElements() {
		leftFreqPnl		= new JPanel();
		leftFreqPnl.setBorder( new TitledBorder("Linke Frequenz:") );
		leftFreqPnl.setLayout( new GridLayout(3, 2) );
		
		startLeftFreq	= new JSpinner();
		leftFreqPnl.add( new JLabel("Start Frquenz:") );
		leftFreqPnl.add(startLeftFreq);
		
		targetLeftFreq	= new JSpinner();
		leftFreqPnl.add( new JLabel("Ziel Frequenz") );
		leftFreqPnl.add(targetLeftFreq);
		
		leftVolume		= new JSlider();
		leftFreqPnl.add( new JLabel("Lautstärke:") );
		leftFreqPnl.add(leftVolume);



		rightFreqPnl	= new JPanel();
		rightFreqPnl.setBorder( new TitledBorder("Rechte Frequenz:") );
		rightFreqPnl.setLayout( new GridLayout(3, 1) );
		
		startRightFreq	= new JSpinner();
		rightFreqPnl.add( new JLabel("Start Frquenz:") );
		rightFreqPnl.add(startRightFreq);

		targetRightFreq	= new JSpinner();
		rightFreqPnl.add( new JLabel("Ziel Frequenz") );
		rightFreqPnl.add(targetRightFreq);

		rightVolume		= new JSlider();
		rightFreqPnl.add( new JLabel("Lautstärke:") );
		rightFreqPnl.add(rightVolume);
	}
}
