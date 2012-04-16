package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class PlaylistPnl extends JPanel {
	
	/*
	 * Playlist anzeigen/verstecken
	 */
	private JButton openBtn;
	/*
	 * toggle zw. Playlist anzeigen/verstecken
	 * true		-> Playlist ist versteckt; Klick auf openBtn -> anzeigen der Playlist
	 * false	-> Playlist wird angezeit; Klick auf openBtn -> vertseckden der playlist
	 */
	private boolean toggleOpen;
	
	private JButton addBtn;
	private JButton removeBtn;
	private JButton editBtn;
	
	private JList categoryList;
	private JList sessionList;
	
	public PlaylistPnl() {
		initElements();
	}
	
	private void initElements() {
		openBtn = new JButton("show");
		toggleOpen = true;
		openBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleOpen = ! toggleOpen;
				if(toggleOpen) {
					openBtn.setText("show");
				} else {
					openBtn.setText("hide");
				}
			}
		});
		
		addBtn		= new JButton("add");
		removeBtn	= new JButton("rem");
		editBtn		= new JButton("edit");
		
		categoryList	= new JList();
		sessionList		= new JList();
	}

}
