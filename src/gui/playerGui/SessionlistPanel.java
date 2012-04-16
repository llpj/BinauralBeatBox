package gui.playerGui;

import gui.GuiFunctionLib;
import gui.ToggleButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SessionlistPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1413877908493049019L;

	private static final int Elements = 0;

	private JList			categoryList;
	private JList			sessionList;
	private JPanel			menuPnl;
	private JButton			addBtn;
	private	JButton			editBtn;
	private	JButton			removeBtn;
	private ToggleButton	openBtn;
	
	private DefaultListModel	categoryListModel;
	private DefaultListModel	sessionListModel;
	

	public static final int ADD_BUTTON 		= 0;
	public static final int EDIT_BUTTON 	= 1;
	public static final int REMOVE_BUTTON 	= 2;
//	public static final int CATEGORY_LIST 	= 3;
//	public static final int SESSION_LIST 	= 4;
//	public static final int OPEN_BTN	 	= 5;
	
//	public enum Elements {
////		CATEGORY_LIST,
////		SESSION_LIST,
////		OPEN_BTN,
//		ADD_BUTTON,
//		EDIT_BUTTON,
//		REMOVE_BUTTON
//	}

	public SessionlistPanel() {
		initMenuPnl();
		openBtn				= new ToggleButton("^", "v", true);
		
		categoryListModel	= new DefaultListModel();
		categoryList		= new JList(categoryListModel);

		sessionListModel	= new DefaultListModel();
		sessionList			= new JList(sessionListModel);
		
		changeLayout();
		
		openBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLayout();
			}
		});
		
		setMinimumSize( new Dimension(800,200) );
	}
	
	private void initMenuPnl() {
		addBtn 			= new JButton("+");
		editBtn 		= new JButton("0");
		removeBtn 		= new JButton("-");
		
		menuPnl = new JPanel( new GridLayout(3,1) );
		menuPnl.add(addBtn);
		menuPnl.add(editBtn);
		menuPnl.add(removeBtn);
	}
	
	private void changeLayout() {
		if(openBtn.isSelected()) {
			maximize();
		} else {
			minimize();
		}
	}

	private void maximize() {
		removeAll();
		GridBagLayout gbl = new GridBagLayout();
		setLayout( gbl );
		GuiFunctionLib.addGridBagContainer(this, gbl, openBtn,							0, 0, 3, 1, 0, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JScrollPane(categoryList),	0, 1, 1, 2, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, new JScrollPane(sessionList),		1, 1, 1, 2, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, menuPnl,							2, 1, 1, 1, 0, 0);
		
		updateUI();
	}
	
	private void minimize() {
		removeAll();
		BorderLayout bl = new BorderLayout();
		setLayout( bl );
		add(openBtn, BorderLayout.NORTH);
		
		updateUI();
	}
	
	public void addActionListenerToElement(ActionListener al, int element) {
		switch(element) {
			case ADD_BUTTON:
				addBtn.addActionListener(al);
				break;
			case EDIT_BUTTON:
				editBtn.addActionListener(al);
				break;
			case REMOVE_BUTTON:
				removeBtn.addActionListener(al);
				break;
		}
	}
	
}
