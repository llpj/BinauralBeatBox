package gui.editorGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import container.Category;
import container.Session;
import logic.SessionWiedergabe;
import management.FileManager;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;

/**
 * 
 * Soll den Ablauf zur Erstellung einer Session unterstuetzen.
 * 
 * Aufgabe:
 * Eventhandling vom Editor
 * Verwaltung benoetiger Komponenten (PlayerPanel, EditorPanel)
 * Verwaltung der temporaeren Session
 * Abspielen temporaerer Sessions
 * 
 * @author felix
 *
 */

public class EditorController {

	private PlayerPanel			playerPnl;
	private SessionEditorPanel	editorPnl;
	
	private FileManager			fileManager;
	private MainFrame			mainFrame;

	private ActionListener		saveBtnAl;
	private ActionListener		exportBtnAl;
	private ActionListener		cancelBtnAl;
	
	
	private int tmpBalance = 0;
	private static SessionWiedergabe sw;
	
	
	/************************************************
	 * 												*
	 * 				CONSTRUCTORS					*
	 * 												*
	 ************************************************/
	public EditorController(FileManager fm, MainFrame mf) {
		this(fm,mf,null);
	}
	
	public EditorController(FileManager fm, MainFrame mf, Session s) {
		fileManager	= fm;
		mainFrame	= mf;
		
		playerPnl	= new PlayerPanel();
		

		if(s != null) {
			TestAndAnalyze.Printer.printSession(s, "", "EditorController");
			editorPnl	= new SessionEditorPanel(s);
		} else {
			editorPnl	= new SessionEditorPanel();
		}
		
		//ActionListener setzen
		initActionListener();
		editorPnl.addListenerToElement(SessionEditorPanel.SAVE_BUTTON, saveBtnAl);
		editorPnl.addListenerToElement(SessionEditorPanel.EXPORT_BUTTON, exportBtnAl);
		editorPnl.addListenerToElement(SessionEditorPanel.CANCEL_BUTTON, cancelBtnAl);
		
		//Layoutwechsel
		mainFrame.setEditorLayout(playerPnl, editorPnl);
		
		//Auswahllisten mit Kategorien und Musikdateien fuellen
		editorPnl.getGlobalSettingPanel().setListModel(fileManager.getCategories(),	GlobalSettingPanel.CATEGORY_LIST);
		editorPnl.getGlobalSettingPanel().setListModel(fileManager.getListOfWav(), GlobalSettingPanel.SOUND_LIST);
		
		initEditorPlayer();
	}

	
	/************************************************
	 * 												*
	 * 					FUNCTIONS					*
	 * 												*
	 ************************************************/
	private void addNewSession(Session s) {
		String catName = editorPnl.getCategory();
			
		if (fileManager.getCategories().containsKey(catName)) {
			fileManager.getCategories().get(catName).addSession(s);
		} else {
			fileManager.addCategory(new Category(catName, s));
		}
		
		fileManager.setActiveSession(s);
		MainFrame.cleanMessage();
		setCategoryListModel();
	}
	
	private Session getTempSession() {
		try {
			MainFrame.cleanMessage();
			
			Session tmp = editorPnl.getValues();
			TestAndAnalyze.Printer.printSession(tmp, "EditorController.getTempSession");
			return tmp;
		} catch (IllegalArgumentException e) {
			MainFrame.showMessage( e.getMessage() );
		}
		return null;
	}
	
	private void setCategoryListModel() {
		DefaultListModel catModel = new DefaultListModel();

		for (Category c : fileManager.getCategories().values()) {
			catModel.addElement(c);
		}
		
		mainFrame.getSessionListPnl().setListModel(catModel, SessionListPanel.CATEGORY_LIST);
	}

	/************************************************
	 * 												*
	 *				ACTION LISTINERS				*
	 * 												*
	 ************************************************/
	private void initActionListener() {
		saveBtnAl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Session s = getTempSession();
				
				if(s != null) {
					addNewSession(s);
					stopPlayer();
					mainFrame.setPlayerLayout();
					fileManager.writeCategories(fileManager.getCategories());
				}
			}
		};
		
		exportBtnAl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Session s = getTempSession();
				
				if(s != null) {
					addNewSession(s);
					stopPlayer();
					mainFrame.setPlayerLayout();
					fileManager.writeCategories(fileManager.getCategories());

					JFileChooser chooser = new JFileChooser();
					
					chooser.setFileFilter(new FileFilter() {
						@Override
		                public boolean accept(File f) {
		                    return f.getName().toLowerCase().endsWith(".wav") || f.isDirectory();
		                }
		                public String getDescription() {
		                    return "Audiodatei (*.wav)";
		                }
		            });

				    int returnVal = chooser.showSaveDialog(null);

				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	String fileStr = chooser.getSelectedFile().toString();
				    	
				    	if(!fileStr.toLowerCase().endsWith(".wav")) {
				    		fileStr = fileStr + ".wav";
				    	}
//				    	System.out.println( new File(fileStr) );
				    	fileManager.exportAsWav( new File(fileStr) );
				    }
				}
			}
		};
		
		cancelBtnAl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopPlayer();
				mainFrame.setPlayerLayout();
			}
		};
	}
	
	
	/************************************************
	 * 												*
	 * 				PLAYER FUNCTIONS				*
	 * 												*
	 ************************************************/
	private void initEditorPlayer() {
		playerPnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {			
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				Session tempSession = getTempSession();
//				TestAndAnalyze.Printer.printSession(tempSession, "initEditorPlayer:PLAY_BUTTON");
				
				if (tempSession != null) {
					if (((ToggleButton) ae.getSource()).isSelected()) {
						// PLAY
						if (sw == null) {
							sw = new SessionWiedergabe(tempSession, false);
							sw.setPlayerPanel( playerPnl );
							sw.playSession();
						} else {
							sw.continueSession();
						}	
					} else {
						// PAUSE
						if (sw != null)
							sw.pauseSession();
					}
				}
			}
		});

		playerPnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopPlayer();
			}
		});
		
		playerPnl.addListenerToElement(PlayerPanel.TIME_BAR, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JProgressBar s = (JProgressBar) ce.getSource();
				if (sw!=null) {
				    s.setStringPainted(true);
				}
			}
		});
		playerPnl.addListenerToElement(PlayerPanel.MUTE_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider muteBar = (JSlider) ce.getSource();
				if(sw != null)
					sw.changeVolumn(muteBar.getValue());
//				System.out.println(muteBar.getValue());
			}
		});
		
		playerPnl.addListenerToElement(PlayerPanel.BEAT_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider balanceBar = (JSlider) ce.getSource();
				
				if (sw != null) {
					if (tmpBalance>=balanceBar.getValue()) {
						sw.changeBalance(balanceBar.getValue(), false);
					}
					else {
						sw.changeBalance(balanceBar.getValue(), true);
					}
				}
				tmpBalance = balanceBar.getValue();
			}
			
		});
	}
	
	private void stopPlayer() {
		if(sw != null) {
			sw.stopSession(true);
			sw = null;
		}
	}
}