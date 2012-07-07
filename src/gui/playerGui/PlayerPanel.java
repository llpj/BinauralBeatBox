package gui.playerGui;

import gui.ActionListenerAddable;
import gui.ToggleButton;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class PlayerPanel extends JPanel implements ActionListenerAddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160124994382068026L;
	/*
	 * Button für Play und Pause
	 */
	private ToggleButton	playBtn;
	private JButton			stopBtn;
	
	private JProgressBar	timeBar;		// stellt die Zeitleiste dar
	private JSlider			muteSlider;		// stellt die Leiste für Gesamtlautstärke dar
	private JSlider			beatSlider;		// stellt die Leiste für die Beatlautstärke dar
	
	public static final int PLAY_BUTTON 	= 0;
	public static final int STOP_BUTTON 	= 1;
//	public static final int BALANCE_BUTTON	= 2;
	public static final int TIME_BAR		= 3;
	public static final int MUTE_SLIDER		= 4;
	public static final int BEAT_SLIDER		= 5;
	

	public PlayerPanel() {
		initElements();
		
		add(playBtn);
		add(stopBtn);
		add(timeBar);
		
		JPanel p = new JPanel();
		p.setLayout( new GridLayout(2,1) );
		
		JPanel p1 = new JPanel();
		p1.add( new JLabel("Total:") );
		p1.add( muteSlider );
		
		JPanel p2 = new JPanel();
		p2.add( new JLabel("Beat:") );
		p2.add( beatSlider );
		
		p.add( p1 );
		p.add( p2 );
		
		add(p);
	}
	
	/**
	 * Initialisierung der GUI Elemente, des Players (JButtons, JSlieder, JProgressBar).
	 */
	private void initElements() {
		
		playBtn = new ToggleButton(" Play ", "Pause");
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				playBtn.setSelected(false);
			}
		});
		
		timeBar	= new JProgressBar();
		timeBar.setMinimum(0);
		timeBar.setMaximum(0);
		
		muteSlider = new JSlider();
		muteSlider.setPaintTicks(true);
		muteSlider.setPaintLabels(true);
		muteSlider.setMinimum(0);
		muteSlider.setMaximum(100);
		
		beatSlider = new JSlider();
		beatSlider.setPaintTicks(true);
		beatSlider.setPaintLabels(true);
		beatSlider.setMinimum(0);
		beatSlider.setMaximum(100);
	}

	@Override
	public void addListenerToElement(int element, EventListener el) {
		switch(element) {
			case PLAY_BUTTON:
				playBtn.addActionListener( (ActionListener)el );
				break;
			case STOP_BUTTON:
				stopBtn.addActionListener( (ActionListener)el );
				break;
			case TIME_BAR:
				timeBar.addChangeListener( (ChangeListener)el );
				break;
			case MUTE_SLIDER:
				muteSlider.addChangeListener( (ChangeListener)el );
				break;
		}
	}
	
	public void setDuration(int d) {
		// TODO Label anzeige wird nicht aktualisiert
		timeBar.setMaximum(d);
		timeBar.setValue(0);
	}

//	private void calculateProgessBarPos(Point p) {
//		float u = (float) muteBar.getSize().width / muteBar.getMaximum();
//		muteBar.setValue((int)( (float)p.x / u));
//	}
}
