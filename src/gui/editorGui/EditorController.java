package gui.editorGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;

import container.Category;
import container.Session;
import management.FileManager;
import gui.MainFrame;
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
			System.out.println("Session wird an SessionEditorPanel uebergeben");
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
			return editorPnl.getValues();
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
	
	private void initActionListener() {
		saveBtnAl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Session s = getTempSession();
				
				if(s != null) {
					addNewSession(s);
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
					mainFrame.setPlayerLayout();
					fileManager.writeCategories(fileManager.getCategories());
					fileManager.exportAsWav();
				}
			}
		};
		
		cancelBtnAl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setPlayerLayout();
			}
		};
	}
}