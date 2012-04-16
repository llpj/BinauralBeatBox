package gui;

import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionlistPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5636677183953711899L;
	
	
	private PlayerPanel			playerPnl;
	private JPanel				virtualizationPnl;
	private SessionlistPanel	listPnl;
//	private SessioneditorPanel	editorPnl;


	public MainFrame() {
		playerPnl			= new PlayerPanel();
		listPnl				= new SessionlistPanel();
		virtualizationPnl	= new JPanel();
		
		virtualizationPnl.setBackground(Color.BLUE);

		virtualizationLayout();
		
		pack();
		setMinimumSize( new Dimension(getSize().width,0) );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	private void virtualizationLayout() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, virtualizationPnl,0, 0, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, playerPnl,		0, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, listPnl,			0, 2, 1, 1, 1, 0);
		
		repaint();
	}
	
}
