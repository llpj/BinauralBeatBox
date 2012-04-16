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
	
	
	private JList			categoryList;
	private JList			sessionList;
	private JPanel			menuPnl;
	private JButton			addBtn;
	private	JButton			editBtn;
	private	JButton			removeBtn;
	private ToggleButton	openBtn;
	
	private DefaultListModel	categoryListModel;
	private DefaultListModel	sessionListModel;

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
	
}
