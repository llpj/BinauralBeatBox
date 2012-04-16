package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

public class PlayerPnl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160124994382068026L;
	/*
	 * Button für Play und Pause
	 */
	private ToggleButton	playBtn;
	private JButton			stopBtn;
	private JButton			balanceBtn;
	
	/*
	 * stellt die Zeitleiste dar
	 */
	private JSlider			timeSlider;
	/*
	 * Gesamtlautstärke der Sessionwiedergabe
	 */
	private JProgressBar	muteBar;

	public PlayerPnl() {
		initElements();
		
		add(playBtn);
		add(stopBtn);
		add(timeSlider);
		add(muteBar);
		add(balanceBtn);
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
		
		balanceBtn = new JButton("b");
		
		timeSlider = new JSlider();
		timeSlider.setPaintTicks(true);
		timeSlider.setPaintLabels(true);
		timeSlider.setMinimum(0);
		timeSlider.setMaximum(3);
		
		muteBar = new JProgressBar();
	}

}
