package management;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.*;
import container.*;


import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox {

	private MainFrame	mf; 
	AnimationFreq aniFreq;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// hall0
		new BinauralBeatBox();
		
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
					//PLAY:
					//SessionWiedergabe.playSession(500,1000,10);
					int [] freq={-30,0,30};
					//Session ses = new Session ();
					
						aniFreq = new AnimationFreq (freq); 
					
				} else {
					//PAUSE:
					aniFreq.pause(true);
				}
			}
		}, PlayerPanel.PLAY_BUTTON);
		
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				aniFreq.finish(true);
			}
		}, PlayerPanel.STOP_BUTTON);
	}

}