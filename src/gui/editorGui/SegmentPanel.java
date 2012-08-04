package gui.editorGui;

import gui.ActionListenerAddable;
import gui.GuiFunctionLib;
import gui.ToggleButton;

import interfaces.Mood;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import container.BinauralBeat;
import container.Segment;

/**
 * Anzeige fuer Einstellmoeglichkeiten der Segmente
 * @author felix
 *
 */
public class SegmentPanel extends JPanel implements ActionListenerAddable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208218652817209244L;
	
	//basic Elemente (casual mode)
	private JComboBox		startMood;
	private JComboBox		targetMood;
	private JSpinner		durationSpin;
	
	private JButton			removeBtn;
	private	ToggleButton	editBtn;
	
	private JButton			moveUpBtn;
	private JButton			moveDownBtn;
	
	//zusätzliche profi mode Elemente für linke und rechte Frequenz
	private JPanel			leftFreqPnl;
	private JSpinner		startLeftFreq;
	private JSpinner		targetLeftFreq;
	
	private JPanel			rightFreqPnl;
	private JSpinner		startRightFreq;
	private JSpinner		targetRightFreq;
	
	public static final int MOVE_UP_BUTTON		= 0;
	public static final int MOVE_DOWN_BUTTON	= 1;
	public static final int REMOVE_BUTTON		= 2;
	
	/**
	 * Initialisierung eines SegmentPanels
	 * @param title Title des Rahmens vom Panel
	 */
	public SegmentPanel(String title) {
		setBorder(  new TitledBorder(title) );

		initBasicElements();
		initProfiModeElements();

		casualModeLayout();
	}

	/**
	 * Aktualisiert das Layout fuer den Causual Mode
	 */
	private void casualModeLayout() {
		removeAll();
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
//																						x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Start Stimmug:"),		0, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Ziel Stimmug:"),		0, 1, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, startMood,						1, 0, 1, 1, 2, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, targetMood,						1, 1, 1, 1, 2, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments"),	2, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,						2, 1, 1, 1, 1, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,						3, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,							3, 1, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,						4, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,						4, 1, 1, 1, 0, 0);
		

		
		Integer moodNameID = getMoodNameID( Integer.parseInt(startLeftFreq.getValue().toString()), Integer.parseInt(startRightFreq.getValue().toString()));
		if(moodNameID != null)
			startMood.setSelectedIndex( moodNameID );

		moodNameID = getMoodNameID( Integer.parseInt(targetLeftFreq.getValue().toString()), Integer.parseInt(targetRightFreq.getValue().toString()));
		if(moodNameID != null)
			targetMood.setSelectedIndex( moodNameID );
		
		updateUI();
	}
	
	/**
	 * Aktualisiert das Layout fuer den Profi Mode
	 */
	private void profiModeLayout() {
		removeAll();
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
//																						x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Ziel Frequenz:"),		0, 1, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Start Frquenz:"),		0, 2, 1, 1, 0, 0);

		GuiFunctionLib.addGridBagContainer(this, gbl, leftFreqPnl,						1, 0, 1, 3, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments"),	1, 3, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, rightFreqPnl,						2, 0, 1, 3, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,						2, 3, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,						3, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,						3, 1, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,							3, 2, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,						3, 3, 1, 1, 0, 0);
		
		startLeftFreq.setValue( getMoodFreq( startMood.getSelectedItem().toString() ) );
		targetLeftFreq.setValue( getMoodFreq( targetMood.getSelectedItem().toString() ) );
		
		startRightFreq.setValue( (Integer)startLeftFreq.getValue() + 2 );
		targetRightFreq.setValue( (Integer)targetLeftFreq.getValue() + 2 );
		
		updateUI();
	}
	
	/**
	 * Initialisierung von allen Elementen des Causual Mode
	 */
	private void initBasicElements() {
		String[] moods = {"DELTA", "THETA", "ALPHA", "BETA", "GAMMA"};
		
		startMood		= new JComboBox(moods);
		targetMood		= new JComboBox(moods);
		durationSpin	= new JSpinner();
		
		removeBtn		= new JButton("-");
		editBtn			= new ToggleButton("P","C");
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( editBtn.isSelected() ) {
					profiModeLayout();
				} else {
					casualModeLayout();
				}
			}
		});
		
		moveUpBtn		= new JButton("^");
		moveDownBtn		= new JButton("v");
	}
	
	/**
	 * Initialisierung von extra Elementen des Profi Mode
	 */
	private void initProfiModeElements() {
		leftFreqPnl		= new JPanel();
		leftFreqPnl.setBorder( new TitledBorder("Linke Frequenz:") );
		leftFreqPnl.setLayout( new GridLayout(2, 1) );
		
		startLeftFreq	= new JSpinner();
		leftFreqPnl.add(startLeftFreq);
		
		targetLeftFreq	= new JSpinner();
		leftFreqPnl.add(targetLeftFreq);



		rightFreqPnl	= new JPanel();
		rightFreqPnl.setBorder( new TitledBorder("Rechte Frequenz:") );
		rightFreqPnl.setLayout( new GridLayout(2, 1) );
		
		startRightFreq	= new JSpinner();
		rightFreqPnl.add(startRightFreq);

		targetRightFreq	= new JSpinner();
		rightFreqPnl.add(targetRightFreq);
	}
	
	private Integer getMoodFreq(String mood) {
		if( mood == "DELTA" )
			return 1;
		if( mood == "THETA" )
			return 4;
		if( mood == "ALPHA" )
			return 8;
		if( mood == "BETA" )
			return 13;
		if( mood == "GAMMA" )
			return 30;
		
		return 0;
	}
	
	private Integer getMoodNameID(Integer freqLeft, Integer freqRight) {
		Integer freq = 0;
//		Test Ausgabe
//		System.out.println("left: "+freqLeft);
//		System.out.println("right: "+freqRight);
		
		if(freqLeft == null || freqRight == null)
			return null;
		
		freq = Math.round( Math.max(freqLeft, freqRight) - Math.abs( (freqLeft-freqRight)/2 ));
//		Test Ausgabe
//		System.out.println("delta freq: "+freq);
		
		if (freq > 0 && freq < 4) {
//			return "DELTA";
			return 0;
		} else if (freq >= 4 && freq < 8) {
//			return "THETA";
			return 1;
		} else if (freq >= 8 && freq <= 13) {
//			return "ALPHA";
			return 2;
		} else if (freq > 13 && freq <= 30) {
//			return "BETA";
			return 3;
		} else if (freq > 30) {
//			return "GAMMA";
			return 4;
		}
		
//		return "UNKNOWN";
		return null;
	}
	
	/**
	 * Uebernimmt Werte einer Session als default Werte
	 * @param s	Session deren Werte als defualt uebernommen werden
	 */
	public void setDefaultValues(Segment s) {
//		startMood;
//		targetModd;
		durationSpin.setValue( s.getDuration() );		

		startLeftFreq.setValue( s.getBeat().getFreq1_start() );
		targetLeftFreq.setValue( s.getBeat().getFreq1_target() );

		startRightFreq.setValue( s.getBeat().getFreq2_start() );
		targetRightFreq.setValue( s.getBeat().getFreq2_target() );
	}
	
	/**
	 * Erstellt ein Segment auf Basis der eingegebenen Werten
	 * @return Segment mit den eingegebenen Werten
	 */
	public Segment getValues() {
		int duration	= Integer.parseInt( durationSpin.getValue().toString() );
		System.out.println(duration);
		int freq1_start	= Integer.parseInt( startLeftFreq.getValue().toString() );
		int freq1_target= Integer.parseInt( targetLeftFreq.getValue().toString() );
		
		int freq2_start	= Integer.parseInt( startRightFreq.getValue().toString() );
		int freq2_target= Integer.parseInt( targetRightFreq.getValue().toString() );
		
		BinauralBeat bb = new BinauralBeat(freq1_start, freq1_target, freq2_start, freq2_target);
		
		return new Segment(duration, bb);
	}
	
	/**
	 * aktualisiert den Title des Rahmens vom Panel
	 * @param title
	 */
	public void setTitle(String title) {
		setBorder(  new TitledBorder(title) );
	}
	
	/**
	 * Fuegt verschiedenen Elementen des SegmentPanels einen
	 * EventListener hinzu.
	 * 
	 * @param element
	 *            Konstante zum Auswehlen des GUI-Elements
	 * @param el
	 *            Hinzuzufuegener EventListener
	 */
	@Override
	public void addListenerToElement(int element, EventListener el) {
		switch (element) {
		case MOVE_UP_BUTTON:
			moveUpBtn.addActionListener((ActionListener) el);
			break;
		case MOVE_DOWN_BUTTON:
			moveDownBtn.addActionListener((ActionListener) el);
			break;
		case REMOVE_BUTTON:
			removeBtn.addActionListener((ActionListener) el);
			break;
		}
	}
}
