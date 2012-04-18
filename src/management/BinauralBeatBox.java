package management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.SessionWiedergabe;

import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;


public class BinauralBeatBox {

	private MainFrame	mf; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
					SessionWiedergabe.playSession(500,1000);
				} else {
					//PAUSE:
				}
			}
		}, PlayerPanel.PLAY_BUTTON);
		
		mf.getPlayerPanel().addActionListenerToElement( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
			}
		}, PlayerPanel.STOP_BUTTON);
	}

}