package gui.editorGui;

import gui.ActionListenerAddable;
import gui.GuiFunctionLib;
import gui.MainFrame;
import gui.ToggleButton;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	private JComboBox			startMood;
	private JComboBox			targetMood;
	private JSpinner			durationSpin;
	private SpinnerDateModel	sm;
	
	private JButton			removeBtn;
	private	ToggleButton	editBtn;
	
	private JButton			moveUpBtn;
	private JButton			moveDownBtn;
	
	//zusätzliche profi mode Elemente für linke und rechte Frequenz
	private JPanel				leftFreqPnl;
	private JSpinner			startLeftFreq;
	private SpinnerNumberModel 	snmSL;
	private JSpinner			targetLeftFreq;
	private SpinnerNumberModel 	snmTL;
	
	private JPanel				rightFreqPnl;
	private JSpinner			startRightFreq;
	private SpinnerNumberModel 	snmSR;
	private JSpinner			targetRightFreq;
	private SpinnerNumberModel 	snmTR;
	
	public static final int MOVE_UP_BUTTON		= 0;
	public static final int MOVE_DOWN_BUTTON	= 1;
	public static final int REMOVE_BUTTON		= 2;
	
	/**
	 * Initialisierung eines SegmentPanels
	 * @param title Title des Rahmens vom Panel
	 */
	public SegmentPanel(String title) {
		this(title, null);
	}
	
	public SegmentPanel(String title, Segment s) {
		if(s != null)
			TestAndAnalyze.Printer.printSegment(s, "", "SegmentPanel");
		
		setBorder(  new TitledBorder(title) );

		initBasicElements();
		initProfiModeElements();
		initActionListener();

		if(s != null)
			setDefaultValues(s);
		
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
//																								x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Start Frquenz:"),				0, 1, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Ziel Frequenz:"),				0, 2, 1, 1, 0, 0);

		GuiFunctionLib.addGridBagContainer(this, gbl, leftFreqPnl,								1, 0, 1, 3, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JLabel("Länge des Segments (mm:ss)"),	1, 3, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, rightFreqPnl,								2, 0, 1, 3, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, durationSpin,								2, 3, 1, 1, 0, 0);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, moveUpBtn,								3, 0, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, removeBtn,								3, 1, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, editBtn,									3, 2, 1, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, moveDownBtn,								3, 3, 1, 1, 0, 0);
		
		updateUI();
	}
	
	/**
	 * Initialisierung von allen Elementen des Causual Mode
	 */
	private void initBasicElements() {
//		String[] moods = {"DELTA", "THETA", "ALPHA", "BETA", "GAMMA", "MANUAL"};
		String[] moods = {"Traumloser Schlaf", "Leichter Schlaf", "Entspannung", "Hellwach", "Geistige H�chstleistung", "MANUAL"};
		
		startMood		= new JComboBox(moods);
		targetMood		= new JComboBox(moods);
		
		sm = new SpinnerDateModel(new Date(10000), null, null, Calendar.HOUR_OF_DAY);
		durationSpin = new JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(durationSpin, "mm:ss");
		durationSpin.setEditor(de);
		
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
		leftFreqPnl.setLayout( new GridLayout(0, 1) );
		
		snmSL = new SpinnerNumberModel(1, 1, 20000, 1);
		startLeftFreq	= new JSpinner(snmSL);
		leftFreqPnl.add(startLeftFreq);
		
		snmTL = new SpinnerNumberModel(1, 1, 20000, 1);
		targetLeftFreq	= new JSpinner(snmTL);
		leftFreqPnl.add(targetLeftFreq);


		
		rightFreqPnl	= new JPanel();
		rightFreqPnl.setBorder( new TitledBorder("Rechte Frequenz:") );
		rightFreqPnl.setLayout( new GridLayout(0, 1) );
		
		snmSR = new SpinnerNumberModel(3, 1, 20000, 1);
		startRightFreq	= new JSpinner(snmSR);
		rightFreqPnl.add(startRightFreq);

		snmTR = new SpinnerNumberModel(3, 1, 20000, 1);
		targetRightFreq	= new JSpinner(snmTR);
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
		
		return 1;
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
		
		if (freqLeft < 4 && freqRight < 4) {
//			return "DELTA";
			return 0;
		} else if ((freqLeft >= 4 && freqLeft < 8) && (freqRight >= 4 && freqRight < 8)) {
//			return "THETA";
			return 1;
		} else if ((freqLeft >= 8 && freqLeft <= 13) && (freqRight >= 8 && freqRight <= 13)){
//			return "ALPHA";
			return 2;
		} else if ((freqLeft > 13 && freqLeft <= 30) && (freqRight > 13 && freqRight <= 30)) {
//			return "BETA";
			return 3;
		} else if ((freqLeft > 30) && (freqRight > 30)) {
//			return "GAMMA";
			return 4;
		}
		
		return 5;
//		return null;
	}
	
	/**
	 * Uebernimmt Werte einer Session als default Werte
	 * @param s	Session deren Werte als defualt uebernommen werden
	 */
	private void setDefaultValues(Segment s) {
//		startMood;
//		targetModd;
		Date d = new Date();
		int min = s.getDuration() % 60;
		int sec = s.getDuration() - min*60;
		d.setMinutes( s.getDuration() % 60 );
		d.setSeconds( sec );
		sm.setValue( d );
		System.out.println( s.getDuration() );
//		sm.setValue( s.getDuration()*1000 );
//		durationSpin.setValue(  );		

//		startLeftFreq.setValue( s.getBeat().getFreq1_start() );
		snmSL.setValue( new Integer(s.getBeat().getFreq1_start()) );
//		targetLeftFreq.setValue( s.getBeat().getFreq1_target() );
		snmTL.setValue( new Integer(s.getBeat().getFreq1_target()) );

//		startRightFreq.setValue( s.getBeat().getFreq2_start() );
		snmSR.setValue( new Integer(s.getBeat().getFreq2_start()) );
//		targetRightFreq.setValue( s.getBeat().getFreq2_target() );
		snmTR.setValue( new Integer(s.getBeat().getFreq2_target()) );
	}
	
	/**
	 * Erstellt ein Segment auf Basis der eingegebenen Werten
	 * @return Segment mit den eingegebenen Werten
	 */
	public Segment getValues() throws IllegalArgumentException {
		Date d = (Date)sm.getValue();
		int duration = d.getSeconds() + d.getMinutes()*60;
		System.out.println(duration);
		
		int freq1_start	= getIntValueOfSpinModel(snmSL);
		System.out.println(freq1_start);
		int freq1_target= getIntValueOfSpinModel(snmTL);
		System.out.println(freq1_target);
		
		int freq2_start	= getIntValueOfSpinModel(snmSR);
		System.out.println(freq2_start);
		int freq2_target= getIntValueOfSpinModel(snmTR);
		System.out.println(freq2_target);
		
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


	private void initActionListener() {
		sm.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				Date d = (Date)sm.getValue();
				
				if( d.getSeconds() == 0 && d.getMinutes() == 0) {
					d.setSeconds(1);
					sm.setValue(d);
					durationSpin.updateUI();
				}
			}
		});
		
		ChangeListener clBeatFreq = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				
				int freq1_start		= getIntValueOfSpinModel(snmSL);
				int freq1_target	= getIntValueOfSpinModel(snmTL);
				int freq2_start		= getIntValueOfSpinModel(snmSR);
				int freq2_target	= getIntValueOfSpinModel(snmTR);
				
				MainFrame.cleanMessage();

				try {
					new BinauralBeat(freq1_start, freq1_target, freq2_start, freq2_target);
				} catch (IllegalArgumentException e) {
					MainFrame.showMessage( e.getMessage() );
				}
			}
		};

		snmSL.addChangeListener( clBeatFreq );
		snmSR.addChangeListener( clBeatFreq );
		
		snmTL.addChangeListener( clBeatFreq );
		snmTR.addChangeListener( clBeatFreq );
		
		
		
		
		startMood.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if(ie.getStateChange() == ItemEvent.SELECTED) {
					startLeftFreq.setValue( getMoodFreq( ie.getItem().toString() ) );
					startRightFreq.setValue( (Integer)startLeftFreq.getValue() + 2 );
				}
			}
		});
		
		targetMood.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if(ie.getStateChange() == ItemEvent.SELECTED) {
					targetLeftFreq.setValue( getMoodFreq( ie.getItem().toString() ) );
					targetRightFreq.setValue( (Integer)targetLeftFreq.getValue() + 2 );
				}
			}
		});
	}
	
	private int getIntValueOfSpinModel(SpinnerNumberModel snm) {
		return ((Integer)snm.getValue()).intValue();
	}
	
}