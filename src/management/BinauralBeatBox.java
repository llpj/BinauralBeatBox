package management;

import interfaces.Mood;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

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
		
		// Für Animation-resize
		mf.addComponentListener(new ComponentListener() 
		{  
		        // Diese Methode wird aufgerufen, wenn JFrame wird max-/minimiert
		        public void componentResized(ComponentEvent evt) {
		            Component c = (Component)evt.getSource();
		            
		            // Neue Größe
		            Dimension newSize = c.getSize();
		            animation.setSize(newSize);
		        }

				@Override
				public void componentHidden(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentMoved(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentShown(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
		});
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
						int [] freq={-30,0,30};
						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
						//animationFrakFarbverlauf: true = frak, false = nur farbverlauf
//						animation = new FrakFarbverlauf (Mood.THETA,mf.getVirtualizationPnl(),false);
//					
				} else {
					//PAUSE:
//					animation.pause(true);
					animation.finish(true);
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
	}

}
