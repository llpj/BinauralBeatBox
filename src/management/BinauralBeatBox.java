package management;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox {

	private MainFrame	mf; 
	private Animation animation;
	
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
				animation.finish(true);
			}
		}, PlayerPanel.STOP_BUTTON);
	}

}