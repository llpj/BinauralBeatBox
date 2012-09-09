package gui.editorGui;

import gui.GuiFunctionLib;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import container.Segment;
import container.Session;

/**
 * Panel zum anzeigen mehrerer Segmente
 * 
 * @author felix
 * 
 */
public class SegmentEditorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5333016067647810660L;

	private JPanel segmentPane;
	private ArrayList<SegmentPanel> segmentList;
	private JButton addSegmentBtn;
	private JScrollPane scrollPane;

	public SegmentEditorPanel() {
		this(null);
	}
	
	public SegmentEditorPanel(Session session) {
		segmentPane = new JPanel();

		scrollPane = new JScrollPane(segmentPane);

		segmentList = new ArrayList<SegmentPanel>();
		addSegmentBtn = new JButton("+");
		addSegmentBtn.addActionListener(getAlForAddBtn());

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		// 																x, y, w, h, wx,wy
		GuiFunctionLib.addGridBagContainer(this, gbl, addSegmentBtn,	0, 0, 1, 1, 2, 0);
		GuiFunctionLib.addGridBagContainer(this, gbl, scrollPane,		0, 1, 1, 1, 2, 2);

		segmentPane.setLayout(new GridLayout(2, 1, 0, 10));

		SegmentPanel p = new SegmentPanel("Segment 1");
		addALsToSegmentPnl(p);
		
		if(session != null)
			setDefaultValues(session); 
	}

	/**
	 * Erstellt ActionListener zum hinzufuegen neuer SegmentPanel.
	 * Soll nur ein mal dem add btn hinzugefuegt werden! Sonst keine Verwendung.
	 * @return ActionListener fuer den add Btn
	 */
	private ActionListener getAlForAddBtn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SegmentPanel p = new SegmentPanel("Segment"+ (segmentList.size() + 1),null);
				addALsToSegmentPnl(p);
			}
		};
	}
	
	/**
	 * aktualisiert die title aller SegmentPanel in segmentList
	 */
	private void updateTitleOfSegmentPnl() {
		int i = 0;
		for(SegmentPanel p: segmentList) {
			p.setTitle("Segment"+ ++i);
		}
	}
	
	/**
	 * Fuegt einem SegmentPanel die ActionListener fuer move up, move down & remove btn hinzu
	 * @param p SegmentPanel dem ActionListener hinzugefuegt werden soll
	 */
	private void addALsToSegmentPnl(SegmentPanel p) {
		p.addListenerToElement( SegmentPanel.MOVE_UP_BUTTON , createAlForMoveUpBtn());
		p.addListenerToElement( SegmentPanel.MOVE_DOWN_BUTTON , createAlForMoveDownBtn());
		p.addListenerToElement( SegmentPanel.REMOVE_BUTTON , createAlForRemoveBtn());
		
		segmentList.add(p);
		updateSegmentPane();
	}
	
	/**
	 * Umsortierung von segmentList, title und GUI werden aktualisiert
	 * @return ActionListener fuer move down Btn von einem SegmentPanel
	 */
	private ActionListener createAlForMoveUpBtn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton)ae.getSource();
				SegmentPanel sp	= (SegmentPanel)b.getParent();//ae.getSource();
				Integer index	= segmentList.indexOf( sp );
				
				if ( index>0 ) {
					SegmentPanel h	= segmentList.get( index-1 );
					h.setTitle("Segment" + (index+1) );
					sp.setTitle("Segment" + (index) );
					segmentList.set(index, h);
					segmentList.set(index-1, sp);
					updateSegmentPane();
				}
				
				TestAndAnalyze.Printer.printSession(getValues(new Session()), "", "SegmentEditorPanel.createAlForMoveUpBtn");
			}
		};
	}
	
	/**
	 * Umsortierung von segmentList, title und GUI werden aktualisiert
	 * @return ActionListener fuer move up Btn von einem SegmentPanel
	 */
	private ActionListener createAlForMoveDownBtn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton)ae.getSource();
				SegmentPanel sp	= (SegmentPanel)b.getParent();
				Integer index	= segmentList.indexOf( sp );
				
				if ( index<(segmentList.size()-1) ) {
					SegmentPanel h	= segmentList.get( index+1 );
					h.setTitle("Segment" + (index+1) );
					sp.setTitle("Segment" + (index+2) );
					segmentList.set(index, h);
					segmentList.set(index+1, sp);
					updateSegmentPane();
				}
				TestAndAnalyze.Printer.printSession(getValues(new Session()), "", "SegmentEditorPanel.createAlForMoveDownBtn");
			}
		};
	}
	
	/**
	 * Erstellt ActionListener, der ein SegmentPanel aus der sessionList entfernt, alle Title der Segment Panels & die GUI aktualisiert
	 * @return	ActionListener fuer Remove Button von einem SegmentPanel
	 */
	private ActionListener createAlForRemoveBtn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton)ae.getSource();
				SegmentPanel sp	= (SegmentPanel)b.getParent();
				int index	= segmentList.indexOf( sp );
				segmentList.remove(index);

				updateTitleOfSegmentPnl();
				updateSegmentPane();
				
				TestAndAnalyze.Printer.printSession(getValues(new Session()), "", "SegmentEditorPanel.createAlForRemoveBtn");
			}
		};
	}

	/**
	 * Nimmt die Segmente aus der uebergebenen Session als default Werte und
	 * zeiget diese an
	 * 
	 * @param session
	 *            Session mit anzuzeigenen defualt Werten
	 */
	private void setDefaultValues(Session session) {
		SegmentPanel p;
		int i = 0;
		segmentList.clear();
		for (Segment seg : session.getSegments()) {
			if (i < segmentList.size()) {
				p = segmentList.get(i);
			} else {
				p = new SegmentPanel("Segment" + (segmentList.size() + 1), seg);
//				segmentList.add(p);
				addALsToSegmentPnl(p);
			}
			i++;
		}

		updateSegmentPane();
	}

	/**
	 * Uebergibt die konfigurierten Segmente der uebergebenen Session und gibt
	 * diese auch wieder zurueck.
	 * 
	 * @param s
	 *            Session der Segmente hinzugefuegt werden sollen
	 * @return Session mit uebergebenen Segmenten
	 */
	public Session getValues(Session s) throws IllegalArgumentException {
		s.removeAllSegments();

		for (SegmentPanel segPnl : segmentList) {
			s.addSegment(segPnl.getValues());
		}

		return s;
	}

	/**
	 * Geht die Liste segmentList durch und fuegt alle beinhaltenden Segmente
	 * der Reihe nach der GUI hinzu
	 */
	private void updateSegmentPane() {
		segmentPane.removeAll();
		segmentPane.setLayout(new GridLayout(segmentList.size(), 1, 0, 10));

		for (SegmentPanel p : segmentList) {
			segmentPane.add(p);
		}

		scrollPane.updateUI();
	}
}