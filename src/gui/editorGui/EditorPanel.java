package gui.editorGui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961327885810979762L;
	private JTabbedPane tabPane;
	private JPanel settingPnl;
	
	public EditorPanel() {
		tabPane = new JTabbedPane();
		tabPane.addTab("Globale Einstellungen", settingPnl);
		tabPane.addTab("Segmenteditor", null);
	}
}