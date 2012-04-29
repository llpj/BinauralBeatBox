package gui;

import gui.editorGui.SessionEditorPanel;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5636677183953711899L;
	
	
	private PlayerPanel			playerPnl;
	private JPanel				virtualizationPnl;
	private SessionListPanel	listPnl;
	private SessionEditorPanel	editorPnl;

	public MainFrame() {
		playerPnl			= new PlayerPanel();
		listPnl				= new SessionListPanel();
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				editorLayout();
			}
		};
		
		listPnl.addListenerToElement(SessionListPanel.ADD_BUTTON, al);
		listPnl.addListenerToElement(SessionListPanel.EDIT_BUTTON, al);
		
		virtualizationPnl	= new JPanel();
		virtualizationPnl.setBackground(Color.GRAY);
	
		editorPnl			= new SessionEditorPanel();
		editorPnl.addListenerToElement(SessionEditorPanel.CANCEL_BUTTON, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playerLayout();
				}
			});
		
		playerLayout();

		setMinimumSize( new Dimension(getSize().width,500) );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	
	
	private void playerLayout() {
		this.getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, virtualizationPnl,0, 0, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, playerPnl,		0, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, listPnl,			0, 2, 1, 1, 1, 0);
		
		repaint();
		pack();
	}
	
	private void editorLayout() {
		getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, editorPnl,	0, 0, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, playerPnl,	0, 1, 1, 1, 1, 0);
		
		repaint();
		pack();
	}
	
	public JPanel getVirtualizationPnl() {
		return virtualizationPnl;
	}
	
	public PlayerPanel getPlayerPanel() {
		return playerPnl;
	}
	
	public SessionListPanel getSessionListPnl() {
		return listPnl;
	}
	
	public SessionEditorPanel getSessionEditorPnl() {
		return editorPnl;
	}
}
