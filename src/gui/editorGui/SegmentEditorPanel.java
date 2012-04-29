package gui.editorGui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import container.Segment;
import container.Session;

public class SegmentEditorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5333016067647810660L;
	
	private JPanel					segmentPane;
	private ArrayList<SegmentPanel>	segmentList;

	public SegmentEditorPanel() {
		segmentPane	= new JPanel();
		segmentList = new ArrayList<SegmentPanel>();
		
		setLayout( new BorderLayout() );
		add( new JScrollPane(segmentPane), BorderLayout.CENTER );
		
		segmentPane.setLayout( new GridLayout(1,1,0,10) );
		SegmentPanel p = new SegmentPanel("Segment 1");
		segmentList.add(p);
		segmentPane.add( p );
		
//		segmentDia	= new JPanel();
//		segmentDia.setSize( new Dimension(0, 150) );
//		segmentDia.setMinimumSize( new Dimension(0, 150) );
//		segmentDia.setBackground( Color.GRAY );
		
//		GridBagLayout gbl = new GridBagLayout();
//		setLayout(gbl);
//																					x, y, w, h, wx,wy
//		GuiFunctionLib.addGridBagContainer(this, gbl, new JScrollPane(segmentPane),	0, 1, 1, 1, 1, 1);
//		GuiFunctionLib.addGridBagContainer(this, gbl, segmentDia,					0, 0, 1, 1, 1, 1);
	}
	
	public void setDefaultValues(Session session) {
		SegmentPanel p;
		int i = 0;
		for(Segment seg : session.getSegments() ) {
			if(i < segmentList.size()) {
				p = segmentList.get(i);
			} else {
				p = new SegmentPanel("Segment"+ (segmentList.size() + 1) );
				segmentList.add(p);
			}
			p.setDefaultValues(seg);
			i++;
		}
		
		addSegmentPanelsToPane();
	}
	
	public Session getValues(Session s) {
		for(SegmentPanel segPnl : segmentList) {
			s.addSegment( segPnl.getValues() );
		}
		
		return s;
	}
	
	private void addSegmentPanelsToPane() {
		segmentPane.removeAll();
		segmentPane.setLayout( new GridLayout( segmentList.size() ,1,0,10) );
		
		for( SegmentPanel p : segmentList ) {
			segmentPane.add(p);
		}
	}
}