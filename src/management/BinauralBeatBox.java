package management;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import container.BinauralBeat;
import container.Segment;
import container.Session;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox{

	private MainFrame	mf; 
	private Animation	animation;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) { 

		new BinauralBeatBox();
		
		Session session = new Session();
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		
		new SessionWiedergabe(session);
	}
	
	private BinauralBeatBox() {
		mf = new MainFrame();
		
		initActionListenerForPlayerPanel();
	}
	
	private void initActionListenerForPlayerPanel() {
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if( ( (ToggleButton)ae.getSource() ).isSelected() ) {
					
					//PLAY
					if (SessionWiedergabe.getCuDuration()==0) {
						SessionWiedergabe.playSession(500,1000);
					} else {
						SessionWiedergabe.continueSession();
					}
					
						//animationfreq
						int [] freq={-30,0,30};
						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
					
					
				} else {
					//PAUSE:
					animation.pause(true);
					SessionWiedergabe.pauseSession();
				}
			}
		}, PlayerPanel.PLAY_BUTTON);
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				animation.finish(true);
				SessionWiedergabe.stopSession();
			}
		}, PlayerPanel.STOP_BUTTON);
	}

}