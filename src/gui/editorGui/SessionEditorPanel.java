package gui.editorGui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SessionEditorPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961327885810979762L;
	private JTabbedPane			tabPane;
	private GlobalSettingPanel	settingPnl;
	private SegmentEditorPanel	segmentPnl;
	
	public SessionEditorPanel() {
		settingPnl = new GlobalSettingPanel();
		segmentPnl = new SegmentEditorPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Globale Einstellungen", settingPnl);
		tabPane.addTab("Segmenteditor", segmentPnl);
		
		setLayout( new BorderLayout() );
		add( tabPane, BorderLayout.CENTER );
	}
}