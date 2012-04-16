package gui.editorGui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961327885810979762L;
	private JTabbedPane			tabPane;
	private GlobalSettingPanel	settingPnl;
	
	public EditorPanel() {
		settingPnl = new GlobalSettingPanel();
		JPanel p = new JPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Globale Einstellungen", settingPnl);
		tabPane.addTab("Segmenteditor", p);
		
		setLayout( new BorderLayout() );
		add( tabPane, BorderLayout.CENTER );
	}
}