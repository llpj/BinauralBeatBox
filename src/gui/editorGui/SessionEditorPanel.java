package gui.editorGui;

import gui.ActionListenerAddable;
import gui.GuiFunctionLib;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import container.Session;

public class SessionEditorPanel extends JPanel implements ActionListenerAddable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961327885810979762L;
	private JTabbedPane			tabPane;
	private GlobalSettingPanel	settingPnl;
	private SegmentEditorPanel	segmentPnl;
	private JButton				saveBtn;
	private JButton				exportBtn;
	private	JButton				cancelBtn;

	public static final int SAVE_BUTTON 	= 0;
	public static final int EXPORT_BUTTON 	= 1;
	public static final int CANCEL_BUTTON 	= 2;
	
	public SessionEditorPanel() {
		saveBtn		= new JButton("Speichern");
		exportBtn	= new JButton("Exportieren");
		cancelBtn	= new JButton("Abbrechen");
		
		settingPnl = new GlobalSettingPanel();
		segmentPnl = new SegmentEditorPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Globale Einstellungen", settingPnl);
		tabPane.addTab("Segmenteditor", segmentPnl);

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
//																	x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, tabPane,		0, 0, 3, 1, 1, 1);
		GuiFunctionLib.addGridBagContainer(this, gbl, saveBtn,		0, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, exportBtn,	1, 1, 1, 1, 1, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, cancelBtn,	2, 1, 1, 1, 1, 0);
	}

	@Override
	public void addListenerToElement(int element, EventListener el) {
		switch(element) {
			case SAVE_BUTTON:
				saveBtn.addActionListener( (ActionListener)el );
				break;
			case EXPORT_BUTTON:
				exportBtn.addActionListener( (ActionListener)el );
				break;
			case CANCEL_BUTTON:
				cancelBtn.addActionListener( (ActionListener)el );
				break;
		}
	}
	
	public void setDefaultValues(Session s) {
		settingPnl.setDefaultValues(s);
		segmentPnl.setDefaultValues(s);
	}
}