package gui.editorGui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

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
		
		add( new JLabel("Name der Session:") );
		add( sessionNameEdt );
		add( new JLabel("Kategorie:") );
		add( categoryList );
		add( new JLabel("Hintergrundklang:") );
		add( soundList );
		add( new JLabel("Lautstärke:") );
		add( volumeSlider );
	}
	
}
