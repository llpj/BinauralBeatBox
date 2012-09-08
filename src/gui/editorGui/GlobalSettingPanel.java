package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private Map<String,File> soundMap; // speichert alle Hintergrundgeraesche als Map<Filename (String), File>
	private String defualtHintergrundklang = "";

	public static final int CATEGORY_LIST = 0;
	public static final int SOUND_LIST = 1;

	/**
	 * Initialisiert das Panel, erstellt das Layout (wird nicht gewechselt)
	 */
	public GlobalSettingPanel() {
		this(null);
	}
	
	public GlobalSettingPanel(Session s) {
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
		
		if(s != null) {
			sessionNameEdt.setText(s.getName());
			defualtHintergrundklang = s.getHintergrundklang();
		}
	}

	/**
	 * Gibt die angegebene Kategorie zurueck
	 * 
	 * @return Kategorie als String
	 */
	public String getCategory() {
		ComboBoxModel cbm = categoryList.getModel();
		String catName = cbm.getSelectedItem().toString();
		
		if( catName.isEmpty() ) {
			catName = "uncategorized";
		}

		return catName;
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
		if(sessionNameEdt.getText().equals(""))
			sessionNameEdt.setText( (new Date()).toString() );
		s.setName(sessionNameEdt.getText());
		s.setHintergrundklang( soundMap.get( soundList.getSelectedItem() ).toString() );
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
			soundMap = (Map<String, File>) map;
			for( Object f :  map.values() ) {
				soundList.addItem( ((File)f).getName() );
//				System.out.println(defualtHintergrundklang+"\t"+((File)f).toString());
				if(defualtHintergrundklang.equals(((File)f).toString()))
					soundList.setSelectedIndex( soundList.getItemCount()-1 );
			}
			break;
		}
	}
}
