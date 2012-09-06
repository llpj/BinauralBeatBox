package gui;

import gui.editorGui.EditorController;
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
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

public class MainFrame extends JFrame {
	
	/**
	 * Stellt alle GUI-Elemente im Hauptfenster dar und ist Schnittstelle zw. der BinauralBeatBox Management-Klasse und den GUI Elementen.
	 */
	private static final long serialVersionUID = -5636677183953711899L;
	
	
	private PlayerPanel			playerPnl;
	private PlayerPanel			editorPlayer;	//soll nur im Editor verwendet werden, 
												//und wird immer beim Wechsel zur Editor-Ansicht neu erstellt
	private JPanel				virtualizationPnl;
	private SessionListPanel	listPnl;
	private SessionEditorPanel	editorPnl;
	private ToggleButton		openBtn;	//Anzeige von Animationsflaeche an/aus
	
	public MainFrame() {
		playerPnl			= new PlayerPanel();
		listPnl				= new SessionListPanel();
		virtualizationPnl	= new JPanel();
		virtualizationPnl.setBackground(Color.GRAY);
		openBtn				= new ToggleButton("^", "v", true);
		
		openBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLayout();
			}
		});

		playerLayout();

		setSize( new Dimension(getSize().width,500) );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	/**
	 * Laedt und initialisiert das Player Layout (Playerleiste, Animationsflaeche, Liste der Kategorien und Sessions)
	 */
	private void playerLayout() {
		this.getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, virtualizationPnl,0, 0, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, openBtn,			0, 1, 3, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, playerPnl,		0, 2, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, listPnl,			0, 3, 1, 1, 1, 0);
		
		repaint();
		pack();
	}
	
	/**
	 * Layout für die Anzeige ohne Animationsflaeche 
	 */
	private void miniPlayerLayout() {
		this.getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, openBtn,			0, 0, 3, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, playerPnl,		0, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, listPnl,			0, 2, 1, 1, 1, 0);
		
		repaint();
		pack();
	}
	
	private void changeLayout() {
		if(openBtn.isSelected()) {
			playerLayout();
			//groesse festlegen, damit animationsflaeche gross genug ist
			setSize( new Dimension(getSize().width,500) );
		} else {
			miniPlayerLayout();
		}
	}
	
	/**
	 * Laedt und inintialisiert das Editor Layout
	 */
	private void editorLayout() {
		getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(this, gbl, editorPnl,	0, 0, 1, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, editorPlayer,	0, 1, 1, 1, 1, 0);
		
		repaint();
		pack();
	}
	
	/**
	 * Getter: Gibt das JPanel fuer die Animation zurueck
	 * @return JPanel
	 */
	public JPanel getVirtualizationPnl() {
		return virtualizationPnl;
	}
	
	/**
	 * Getter
	 * @return PlayerPanel
	 */
	public PlayerPanel getPlayerPanel() {
		return playerPnl;
	}
	
	/**
	 * Getter fuer SessionListPanel
	 * @return SessionListPanel
	 */
	public SessionListPanel getSessionListPnl() {
		return listPnl;
	}
	
	/**
	 * Wechselt zum Editor Layout
	 */
	public void setEditorLayout(PlayerPanel playerPnl, SessionEditorPanel editorPnl) {
		this.editorPnl		= editorPnl;
		this.editorPlayer	= playerPnl;
		editorLayout();
	}
	
	/**
	 * Wechsel zum Player Layout
	 */
	public void setPlayerLayout() {
		playerLayout();
	}
	
	/**
	 * Getter
	 * @return SessionEditorPanel
	 */
	public SessionEditorPanel getSessionEditorPnl() {
		return editorPnl;
	}
}
