package gui;

import gui.editorGui.EditorController;
import gui.editorGui.SessionEditorPanel;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

import management.BinauralBeatBox;

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
	
	private boolean miniPlayerLayout = false;
	
	private static MainFrame staticThis	= null;
	
	public MainFrame() {
		staticThis = this;
		playerPnl			= new PlayerPanel();
		listPnl				= new SessionListPanel();
//		virtualizationPnl	= new JPanel();
//		virtualizationPnl.setBackground(Color.GRAY);
		initVirtPnl();
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
		miniPlayerLayout = false;
		this.getContentPane().removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

//		virtualizationPnl.setSize(400, 500);
		GuiFunctionLib.addGridBagContainer(this, gbl, virtualizationPnl,0, 0, 1, 1, 1, 1);
//		GuiFunctionLib.addGridBagContainer(this, gbl, virtualizationPnl,0, 0, 1, 1, 1, 0, GridBagConstraints.BOTH);
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
		miniPlayerLayout = true;
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
//			initVirtPnl();
			playerLayout();
			//groesse festlegen, damit animationsflaeche gross genug ist
			setSize( new Dimension(getSize().width,500) );
		} else {
			logic.SessionWiedergabe.setAnimation(false);
//			virtualizationPnl = null;
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
		if(virtualizationPnl == null)
			initVirtPnl();
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
	
	public static void packMain() {
		if( staticThis != null ) {
			staticThis.pack();
		
			if(!staticThis.miniPlayerLayout)
				staticThis.setSize( new Dimension(staticThis.getSize().width,500) );
		}
	}
	
	public static void cleanMessage() {
		if( staticThis != null ) {
			JPanel glass = (JPanel)staticThis.getGlassPane();
			glass.removeAll();
			glass.updateUI();
		}
	}
	
	public static void showMessage(String text) {
		JPanel	pnl = new JPanel();
		JLabel	lbl = new JLabel(text);
		JButton	btn	= new JButton("Ok");
	
		pnl.add(lbl);
		pnl.add(btn);
		pnl.setBackground( Color.YELLOW );
		
		if( staticThis != null ) {
			final JPanel glass = (JPanel)staticThis.getGlassPane();
			glass.removeAll();
			glass.updateUI();
			glass.setVisible(true);
			glass.setLayout( new BorderLayout() );
			glass.add(pnl, BorderLayout.SOUTH);
			
			btn.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					glass.removeAll();
					glass.updateUI();
				}
			});
		}
	}
	
	private JPanel initVirtPnl() {
		virtualizationPnl	= new JPanel();
		virtualizationPnl.setBackground(Color.GRAY);
		return virtualizationPnl;
	}
}
