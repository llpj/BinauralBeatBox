package management;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import container.BinauralBeat;
import container.Segment;
import container.Session;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox {

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
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if( ( (ToggleButton)ae.getSource() ).isSelected() ) {
					//PLAY:
					sw.playSession(200, 300);
					int [] freq={-30,0,30};
					//Session ses = new Session ();
					animation = new AnimationFreq (freq);
					animation.setHandle( (Graphics2D)mf.getGraphicsForVirtualization() );
					
				} else {
					//PAUSE:
					animation.pause(true);
				}
			}
		}, PlayerPanel.PLAY_BUTTON);
		
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				sw.stopSession();
				animation.finish(true);
			}
		}, PlayerPanel.STOP_BUTTON);
	}

}