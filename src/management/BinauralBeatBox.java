package management;

import interfaces.Mood;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import container.BinauralBeat;
import container.Segment;
import container.Session;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox{

	private MainFrame			mf; 
	private Animation			animation;
	private SessionWiedergabe	sw;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) { 

		new BinauralBeatBox();
	}
	
	private BinauralBeatBox() {
		mf = new MainFrame();
		
		Session session = new Session();
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		sw = new SessionWiedergabe(session);
		
		initActionListenerForPlayerPanel();
	}
	
	private void initActionListenerForPlayerPanel() {
		mf.getPlayerPanel().addListenerToElement( new ActionListener() {
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
//						int [] freq={-30,0,30};
//						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
						//animationFrakFarbverlauf: true = frak, false = nur farbverlauf
						animation = new FrakFarbverlauf (Mood.THETA,false,mf.getVirtualizationPnl());
					
				} else {
					//PAUSE:
					animation.pause(true);
					SessionWiedergabe.pauseSession();
				}
			}
		}, PlayerPanel.PLAY_BUTTON);

		mf.getPlayerPanel().addListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				sw.stopSession();
				animation.finish(true);
				SessionWiedergabe.stopSession();
			}
		}, PlayerPanel.STOP_BUTTON);
		
		mf.getPlayerPanel().addListenerToElement(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JProgressBar b = (JProgressBar)ce.getSource();
				System.out.println( b.getValue() );
			}
		}, PlayerPanel.MUTE_BAR);
	}

}
