package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListModel;

import container.Session;

/**
 * JPanel fuer globale Einstellungen zu einer Session (Namen, Kategorie,...)
 * 
 * @author felix
 * 
 */
public class GlobalSettingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2975740453896369874L;
	private JTextField sessionNameEdt;
	private JComboBox categoryList;
	private JComboBox soundList; // Liste fuer die Hintergrundgeraesche

	public static final int CATEGORY_LIST = 0;
	public static final int SOUND_LIST = 1;

	/**
	 * Initialisiert das Panel, erstellt das Layout (wird nicht gewechselt)
	 */
	public GlobalSettingPanel() {
		setLayout(new GridLayout(4, 2, 5, 10));

		sessionNameEdt = new JTextField();
		categoryList = new JComboBox();
		categoryList.setEditable(true);
		
		soundList = new JComboBox();

		GridBagLayout gbl = new GridBagLayout();
		JPanel p = new JPanel();
		p.setLayout(gbl);

		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel(
				"Name der Session:"), 0, 0, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, sessionNameEdt, 1, 0, 1, 1,
				2, 0);

		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel("Kategorie:"), 0,
				1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, categoryList, 1, 1, 1, 1, 2,
				0);

		GuiFunctionLib.addGridBagContainer(p, gbl, new JLabel(
				"Hintergrundklang:"), 0, 2, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(p, gbl, soundList, 1, 2, 1, 1, 2, 0);

		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
	}

	/**
	 * Uebernimmt die Werte einer uebergebenen Sesssion und nutzt diese als
	 * default Anzeigewerte
	 * 
	 * @param s
	 *            Session, deren Werte als default angezeigt werden sollen
	 */
	public void setDefaultValues(Session s) {
		sessionNameEdt.setText(s.getName());
	}

	/**
	 * Gibt die angegebene Kategorie zurueck
	 * 
	 * @return Kategorie als String
	 */
	public String getCategory() {
		return categoryList.getModel()
				.getElementAt(categoryList.getSelectedIndex()).toString();
	}

	/**
	 * Fuegt alle Werte der globalen Einstellungen der uebergebenen Session
	 * hinzu und gibt diese wieder zurueck.
	 * 
	 * @param s
	 *            Session, der Werte hinzugefuegt werden sollen
	 * @return Session, mit Werten hinzugefuegten Werten
	 */
	public Session getValues(Session s) {
		s.setName(sessionNameEdt.getText());
		// s.setHintergrundklang( );
		return s;
	}

	/**
	 * Fuegt einer JList ein ListModel hinzu, je nach dem Paramenter element
	 * 
	 * @param lm
	 *            Hinzufuegendes ListModel
	 * @param element
	 *            Konstante fuer das Element
	 */
	public void setListModel(Map<?,?> map, int element) {
		switch (element) {
		case CATEGORY_LIST:
			for( Object c :  map.values() )
				categoryList.addItem(c);
			break;
		case SOUND_LIST:
			for( Object f :  map.values() )
				soundList.addItem( ((File)f).getName() );
			break;
		}
	}
}
