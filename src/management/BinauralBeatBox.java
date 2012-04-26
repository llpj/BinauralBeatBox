package management;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import container.BinauralBeat;
import container.Category;
import container.Segment;
import container.Session;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;


public class BinauralBeatBox{

	private MainFrame			mf; 
	private Animation			animation;
	
	private FileManager			fileManager;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) { 

		new BinauralBeatBox();
	}
	
	private BinauralBeatBox() {
		fileManager	= new FileManager();
		test_Sessions();
		
		mf = new MainFrame();
		
		Session session = new Session();
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		
		initListenerForPlayerPanel();
		initListenerForSessionListPanel();
		

		// F�r Animation-resize
		mf.addComponentListener(new ComponentListener() 
		{  
		        // Diese Methode wird aufgerufen, wenn JFrame wird max-/minimiert
		        public void componentResized(ComponentEvent evt) {
		            if (animation != null) {
		            	Component c = (Component)evt.getSource();
			            
			            // Neue Gr��e
			            Dimension newSize = c.getSize();
			            animation.setSize(newSize);
		            }
		        }
				@Override
				public void componentHidden(ComponentEvent arg0) {	}
				@Override
				public void componentMoved(ComponentEvent arg0) { }
				@Override
				public void componentShown(ComponentEvent arg0) { }
		});
	}
	
	private void initListenerForPlayerPanel() {
		PlayerPanel pnl = mf.getPlayerPanel();
		pnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if( ( (ToggleButton)ae.getSource() ).isSelected() ) {
				
					//PLAY
					if (SessionWiedergabe.getCuDuration()==0) {
						SessionWiedergabe.playSession(500,1000);
					} else {
						SessionWiedergabe.continueSession();
					}
					
						//animationfreq
						int [] freq={-30,0,30};
						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
						//animationFrakFarbverlauf: true = frak, false = nur farbverlauf
//						animation = new FrakFarbverlauf (Mood.THETA,mf.getVirtualizationPnl(),false);
//					
				} else {
					//PAUSE:
//					animation.pause(true);
					animation.finish(true);
					SessionWiedergabe.pauseSession();
				}
			}
		});

		pnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				animation.finish(true);
				SessionWiedergabe.stopSession();
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.TIME_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider s = (JSlider)ce.getSource();
				System.out.println( s.getValue() );
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.MUTE_BAR, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JProgressBar muteBar = (JProgressBar)ce.getSource();
				// TODO Gesamtlautstaerke einbinden  (Wertebereich von muteBar.getValue(): 0-100)
				System.out.println( muteBar.getValue() );
			}
		});
	}
	
	private void initListenerForSessionListPanel() {
		SessionListPanel pnl = mf.getSessionListPnl();
		
		pnl.addListenerToElement(SessionListPanel.EDIT_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO gewählte Session bearbeiten
			}
		});
		
		pnl.addListenerToElement(SessionListPanel.REMOVE_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO gewählte Session löschen
			}
		});
		
		pnl.addListenerToElement(SessionListPanel.CATEGORY_LIST, new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if( e.getValueIsAdjusting() ) return;
				
				Category c = (Category)((JList)e.getSource()).getSelectedValue();
				System.out.println(c);
				
				DefaultListModel sessionModel = new DefaultListModel();
				mf.getSessionListPnl().setListModel(sessionModel, SessionListPanel.SESSION_LIST);
				
				for(Session s : fileManager.getCategories().get(((JList)e.getSource()).getSelectedIndex()).getSessions() ) {
					sessionModel.addElement( s );
				}
			}
		});
		
		pnl.addListenerToElement(SessionListPanel.SESSION_LIST, new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Session s = (Session)((JList)e.getSource()).getSelectedValue();
				if( ! e.getValueIsAdjusting() && s != null) {
					System.out.println(s);
				}
			}
		});
	
		DefaultListModel catModel = new DefaultListModel();
		mf.getSessionListPnl().setListModel(catModel, SessionListPanel.CATEGORY_LIST);
		
		for(Category c : fileManager.getCategories() ) {
			catModel.addElement( c );
		}
	}

	private void test_Sessions() {
		fileManager.addCategory( new Category("Category 1") );
		fileManager.addCategory( new Category("Category 2") );
		fileManager.addCategory( new Category("Category 3") );
		fileManager.addCategory( new Category("Category 4") );
		fileManager.addCategory( new Category("Category 5") );
		
		Session session = new Session();
		session.setName("Session 1");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get(0).addSession(session);
		
		session = new Session();
		session.setName("Session 2");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get(0).addSession(session);
		
		session = new Session();
		session.setName("Session 3");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get(0).addSession(session);
	}
}
