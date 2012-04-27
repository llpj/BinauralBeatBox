package gui.playerGui;

import gui.ActionListenerAddable;
import gui.GuiFunctionLib;
import gui.ToggleButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

public class SessionListPanel extends JPanel implements ActionListenerAddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1413877908493049019L;

	private JList			categoryList;
	private JList			sessionList;
	private JPanel			menuPnl;
	private JButton			addBtn;
	private	JButton			editBtn;
	private	JButton			removeBtn;
	private ToggleButton	openBtn;
	

	public static final int ADD_BUTTON 		= 0;
	public static final int EDIT_BUTTON 	= 1;
	public static final int REMOVE_BUTTON 	= 2;
	public static final int CATEGORY_LIST 	= 3;
	public static final int SESSION_LIST 	= 4;
//	public static final int OPEN_BTN	 	= 5;
	
//	public enum Elements {
////		CATEGORY_LIST,
////		SESSION_LIST,
////		OPEN_BTN,
//		ADD_BUTTON,
//		EDIT_BUTTON,
//		REMOVE_BUTTON
//	}

	public SessionListPanel() {
		initMenuPnl();
		openBtn				= new ToggleButton("^", "v", true);
		
		categoryList		= new JList();
		sessionList			= new JList();
		
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

	@Override
	public void addListenerToElement(int element, EventListener el) {
		switch(element) {
			case ADD_BUTTON:
				addBtn.addActionListener( (ActionListener)el );
				break;
			case EDIT_BUTTON:
				editBtn.addActionListener( (ActionListener)el );
				break;
			case REMOVE_BUTTON:
				removeBtn.addActionListener( (ActionListener)el );
				break;
			case CATEGORY_LIST:
				categoryList.addListSelectionListener( (ListSelectionListener)el );
				break;
			case SESSION_LIST:
				sessionList.addListSelectionListener( (ListSelectionListener)el );
				break;
		}
	}
	
	public void setListModel(ListModel lm, int element) {
		switch(element) {
			case CATEGORY_LIST:
				categoryList.setModel(lm);
				break;
			case SESSION_LIST:
				sessionList.setModel(lm);
				break;
		}
	}
	
	public ListModel getListModel(int element) {
		switch(element) {
			case CATEGORY_LIST:
				return categoryList.getModel();
			case SESSION_LIST:
				return sessionList.getModel();
		}
		
		return null;
	}

}
