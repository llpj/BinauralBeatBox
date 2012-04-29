package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import container.Session;

public class GlobalSettingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2975740453896369874L;
	private JTextField	sessionNameEdt;
	private JList		categoryList;
	private JList		soundList;
	private JSlider		volumeSlider;
	
	public GlobalSettingPanel() {
		setLayout( new GridLayout(4,2,5,10) );
		
		sessionNameEdt	= new JTextField();
		categoryList	= new JList();
		soundList		= new JList();
		volumeSlider	= new JSlider();
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel p = new JPanel();
		p.setLayout(gbl);
		
		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel("Name der Session:"),	0, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, sessionNameEdt,					1, 0, 1, 1, 2, 0);
		
		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel("Kategorie:"),		0, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, categoryList,					1, 1, 1, 1, 2, 0);
		
		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel("Hintergrundklang:"),	0, 2, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, soundList,						1, 2, 1, 1, 2, 0);
		
		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel("Lautstärke:"),		0, 3, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, volumeSlider,					1, 4, 1, 1, 2, 0);
		
		setLayout( new BorderLayout() );
		add( p, BorderLayout.NORTH );
	}

	public void setDefaultValues(Session s) {
		sessionNameEdt.setText( s.getName() );
	}
	
	public Session getValues(Session s) {
		s.setName( sessionNameEdt.getText() );
//		s.setHintergrundklang(  );
		return s;
	}
}
