package management;

import interfaces.Mood;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
import gui.editorGui.SessionEditorPanel;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;


public class BinauralBeatBox{

	private MainFrame			mf; 
	private Animation			animation;
	
	private FileManager			fileManager;
	private SessionWiedergabe	sw;
	
	// ueberprueft ob pause gedrueckt wurde
	private boolean				isPause;
	// ist resize%2 == 0, so ist das animationPnl in maximiertem Zustand, wenn != 0 in minimiertem Zustand
	private int					resize;
	private int					animationCounter;
	
	private Session				activeSession;
	
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
		initListenerForPlayerPanel();
		initListenerForSessionListPanel();
		initListenerForSessionEditor();
		
		Session session = new Session();
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		sw = new SessionWiedergabe(session);

		animationCounter = 0;
		activeSession = new Session();
		isPause = false;
		resize = 1;
		// Animation-resize
		mf.addComponentListener(new ComponentListener() 
		{  
		        // Diese Methode wird aufgerufen, wenn JFrame max-/minimiert wird
		        public void componentResized(ComponentEvent evt) {
		            if (animation != null) {
		            	Component c = (Component)evt.getSource();
			            
			            // Neuer size
			            Dimension newSize = c.getSize();
			            animation.setSize(newSize);
			            //f�r resize notwendig
			            resize++;
			            animation.init();
		            }
		        }
				@Override
				public void componentHidden(ComponentEvent arg0) {	}
				@Override
				public void componentMoved(ComponentEvent arg0) { }
				@Override
				public void componentShown(ComponentEvent arg0) { }
		});
		
		mf.getVirtualizationPnl().addMouseListener( new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent me) {
				animation.finish(true);
				//uebermalt alte animation falls mal pause gedrueckt wurde
				Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl().getGraphics();
				Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf.getVirtualizationPnl().getSize().width, mf.getVirtualizationPnl().getSize().height);
				rec.setColor(Color.GRAY);
				rec.fill(rectangle);
				if(!isPause) {
					//Setzen des Animationscounters
					if(animationCounter > 2)
					{
						animationCounter = 0;
					}
					else
					{
						animationCounter++;
					}
					//Auswahl der Animation
					//TODO freq und Mood �bergabe aus activeSession
					if(animationCounter == 0)
					{
						int [] freq={-30,0,30};
						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
						if(resize%2 == 0)
						{
							animation.init();
						}
					}
					else if (animationCounter == 1)
					{
						//animationFrakFarbverlauf: false = nur farbverlauf
						animation = new FrakFarbverlauf (Mood.THETA,mf.getVirtualizationPnl(),false);
						if(resize%2 == 0)
						{
							animation.init();
						}
					}
					else
					{
						//animationFrakFarbverlauf: true = frak,
						animation = new FrakFarbverlauf (Mood.THETA,mf.getVirtualizationPnl(),true);
						if(resize%2 == 0)
						{
							animation.init();
						}
					}					
				}
			}
		});
	}
	
	private void initListenerForPlayerPanel() {
		PlayerPanel pnl = mf.getPlayerPanel();
		pnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if( ( (ToggleButton)ae.getSource() ).isSelected() ) {
					
					//PLAY
					if (sw.getCuDuration()==0) {
							sw.playSession(100,130);
					} else {
						sw.continueSession();
					}
					
					//animation
					if(!isPause)
					{
						//uebermalt alte animation falls mal pause gedrueckt wurde
						Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl().getGraphics();
						Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf.getVirtualizationPnl().getSize().width, mf.getVirtualizationPnl().getSize().height);
						rec.setColor(Color.GRAY);
						rec.fill(rectangle);
						//TODO Freq-�bergabe aus activeSession
						int [] freq={-30,0,30};
						animation = new AnimationFreq (freq, mf.getVirtualizationPnl());
						if(resize%2 == 0)
						{
							animation.init();
						}
					}
					else
					{
						animation.pause(false);	
					}
				} else {
					//PAUSE:
					animation.pause(true);
					isPause = false;
					sw.pauseSession();
				}
			}
		});

		pnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//STOP:
				animation.finish(true);
				isPause = false;
				sw.stopSession();
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
				if( fileManager.getActiveSession() != null ) {
					mf.setEditorLayout();
					initListenerForSessionEditor();
					mf.getSessionEditorPnl().setDefaultValues( fileManager.getActiveSession() );
				}
			}
		});
		
		pnl.addListenerToElement(SessionListPanel.REMOVE_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

			}
		});
		
		pnl.addListenerToElement(SessionListPanel.CATEGORY_LIST, new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Category c = (Category)((JList)e.getSource()).getSelectedValue();
				if( ! e.getValueIsAdjusting() && c != null ) {
					System.out.println(c);
					
					DefaultListModel sessionModel = new DefaultListModel();
					mf.getSessionListPnl().setListModel(sessionModel, SessionListPanel.SESSION_LIST);

					for(Session s : ((Category)((JList)e.getSource()).getSelectedValue()).getSessions() ) {
						sessionModel.addElement( s );
					}
				}
			}
		});
		
		pnl.addListenerToElement(SessionListPanel.SESSION_LIST, new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Session s = (Session)((JList)e.getSource()).getSelectedValue();
				if( ! e.getValueIsAdjusting() && s != null) {
					// TODO Session laden
					// TODO eventuelle aktuelle Session stoppen
					// TODO Infos in GUI darstellen (duration in timeSlider)
					mf.getPlayerPanel().setDuration( s.getDuration() );
					System.out.println(s);
					fileManager.setActiveSession(s);
				}
			}
		});
		
		System.out.println("asfsad");
		
		setListModel();
	}
	
	private void setListModel() {
		System.out.println("asd");
		DefaultListModel catModel = new DefaultListModel();
		mf.getSessionListPnl().setListModel(catModel, SessionListPanel.CATEGORY_LIST);
		
		for(Category c : fileManager.getCategories().values() ) {
			catModel.addElement( c );
		}
	}

	private void initListenerForSessionEditor() {
		SessionEditorPanel editPnl = mf.getSessionEditorPnl();
		
		ActionListener newSession = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String catName = mf.getSessionEditorPnl().getCategory();
				Session s = mf.getSessionEditorPnl().getValues();
				if( fileManager.getCategories().containsKey( catName ) ){
					fileManager.getCategories().get( catName ).addSession(s);
				} else {
					fileManager.addCategory( new Category(catName, s) );
				}
				setListModel();
			}
		};
		
		editPnl.addListenerToElement(SessionEditorPanel.SAVE_BUTTON,	newSession);
		editPnl.addListenerToElement(SessionEditorPanel.EXPORT_BUTTON,	newSession);
	}
	
	private void test_Sessions() {
		fileManager.addCategory( new Category("Category 1") );
		fileManager.addCategory( new Category("Category 2") );
		fileManager.addCategory( new Category("Category 3") ); 
		fileManager.addCategory( new Category("Category 4") );
		fileManager.writeCategories(fileManager.getCategories()); 
		
		Session session = new Session();
		session.setName("Session 1");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(30, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 1").addSession(session);
		
		session = new Session();
		session.setName("Session 2");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 1").addSession(session);
		
		session = new Session();
		session.setName("Session 3");
		session.addSegment( new Segment(15, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 1").addSession(session);
		

		session = new Session();
		session.setName("sdgsdgg");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(30, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 2").addSession(session);
		
		session = new Session();
		session.setName("sagadfgsdg");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 2").addSession(session);
		
		session = new Session();
		session.setName("sdlkgjhlkasdf");
		session.addSegment( new Segment(15, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 2").addSession(session);
		

		
		session = new Session();
		session.setName("sdgsdgg 3423");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(30, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 3").addSession(session);
		
		session = new Session();
		session.setName("sagadfgsdg 123423");
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 3").addSession(session);
		
		session = new Session();
		session.setName("sdlkgjhlkasdf 123432");
		session.addSegment( new Segment(15, new BinauralBeat(500, 530)) );
		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
		fileManager.getCategories().get("Category 3").addSession(session);
	}
		
}
