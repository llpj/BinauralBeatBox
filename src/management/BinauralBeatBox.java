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
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import gui.editorGui.EditorController;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;

public class BinauralBeatBox {

	private MainFrame mf;
	private static Animation animation;

	private static FileManager fileManager;
	private static SessionWiedergabe sw;
	private int tmpBalance = 0;

	// ueberprueft ob pause gedrueckt wurde
	private static boolean isPause;
	// ist resize%2 == 0, so ist das animationPnl in maximiertem Zustand, wenn
	// != 0 in minimiertem Zustand
	private int resize;
	private int animationCounter;
	private Category currentCategory;
	
	private static BinauralBeatBox staticThis = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		staticThis = new BinauralBeatBox();
	}

	public BinauralBeatBox() {
		fileManager = new FileManager();
		try {
			fileManager.setCategories((HashMap<String, Category>) fileManager
					.readCategories());
		} catch (IOException e) {
			e.printStackTrace();
		}

		mf = new MainFrame();
		currentCategory = null;
		initListenerForPlayerPanel();
		initListenerForSessionListPanel();

//		Session session = new Session();
//		session.addSegment(new Segment(10, new BinauralBeat(500, 530)));
//		session.addSegment(new Segment(40, new BinauralBeat(800, 830)));
//		session.addSegment(new Segment(10, new BinauralBeat(500, 530)));

		animationCounter = 0;
		isPause = false;
		resize = 1;
		// Animation-resize
		mf.addComponentListener(new ComponentListener() {
			// Diese Methode wird aufgerufen, wenn JFrame max-/minimiert wird
			public void componentResized(ComponentEvent evt) {
				if (animation != null) {
					Component c = (Component) evt.getSource();

					// Neuer size
					Dimension newSize = c.getSize();
					animation.setSize(newSize);
					// fuer resize notwendig
					resize++;
					animation.init();
				}
			}
			@Override
			public void componentHidden(ComponentEvent arg0) { }
			@Override
			public void componentMoved(ComponentEvent arg0) { }
			@Override
			public void componentShown(ComponentEvent arg0) { }
		});

		mf.getVirtualizationPnl().addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) { }
			@Override
			public void mousePressed(MouseEvent arg0) { }
			@Override
			public void mouseExited(MouseEvent arg0) { }
			@Override
			public void mouseEntered(MouseEvent arg0) { }
			@Override
			public void mouseClicked(MouseEvent me) {
				/**
				 * Der Animationswechsel soll nur erm�glicht sein, wenn der 
				 * Sound nicht pausiert;
				 * Wird keine session abgespielt, so kann auch keine animation visualisiert werden
				 */
				if (!isPause) {
					if(animation != null)
					{
						animation.finish(true);
						// uebermalt alte animation falls mal pause gedrueckt wurde
						defaultPaint();
						// Setzen des Animationscounters
						if (animationCounter > 1) {
							animationCounter = 0;
						} else {
							animationCounter++;
						}
						// Auswahl der Animation
						// TODO freq und Mood uebergabe aus activeSession
						if (animationCounter == 0) {
							// int[] freq = { -30, 0, 30 };
							animation = new AnimationFreq(sw.getCurFreq(), mf
									.getVirtualizationPnl());
							if (resize % 2 == 0) {
								animation.init();
							}
						} else if (animationCounter == 1) {
							// animationFrakFarbverlauf: false = nur farbverlauf
							animation = new FrakFarbverlauf(sw.getCurMood(), mf
									.getVirtualizationPnl(), false);
							if (resize % 2 == 0) {
								animation.init();
							}
						} else {
							// animationFrakFarbverlauf: true = frak
							animation = new FrakFarbverlauf(sw.getCurMood(), mf
									.getVirtualizationPnl(), true);
							if (resize % 2 == 0) {
								animation.init();
							}
						}
					}
					else
					{
						//Falls keine Session ausgew�hlt wurde...
						JOptionPane.showMessageDialog(null, "Bitte w�hlen Sie zuerst eine Session aus (via Category)", null, JOptionPane.OK_OPTION);
					}
				}
				else
				{
					//nichts
				}
			}
		});
	}
	public void defaultPaint()
	{
		/**
		 * Methode f�r das �bermalen des Animationsfensters
		 */
		if(mf.getVirtualizationPnl()!=null)
		{
			Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl()
			.getGraphics();
			Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf
					.getVirtualizationPnl().getSize().width, mf
					.getVirtualizationPnl().getSize().height);
			rec.setColor(Color.GRAY);
			rec.fill(rectangle);
		}
		else
		{
			//nichts
		}
	}
	
	public static void animationUpdateFreq(int[] curFreq, Mood curMood) {
		if( animation != null ) {
			if (animation.isAniFreq()==false) {
				animation.setFreq(curFreq);
				if(staticThis != null)
					staticThis.defaultPaint();
				//defaultPaint();
				animation.init();
			}
			if (animation.isAniFreq()==true) {
				((FrakFarbverlauf) animation).setMood(curMood);
			if(staticThis != null)
				staticThis.defaultPaint();
				//defaultPaint();
				animation.init();
			}
		}
	}
	
	public static void animationFinish(){
			System.out.println("2.\tanimationFinish()");
			if (animation != null)
				animation.finish(true);
			animation = null;
			System.out.println("Animation:\t"+animation);
			sw = null;
	}
	
	private void stopPlayer() {
		if (fileManager.getActiveSession() != null) {
			if (sw != null) {
				sw.stopSession(true);
				sw = null;
			}
			
			if(animation != null)
				animation.finish(true);
				animation = null;
				// uebermalt alte animation 
				defaultPaint();
				isPause = false;
		}
	}

	private void initListenerForPlayerPanel() {
		PlayerPanel pnl = mf.getPlayerPanel();

		pnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {			
			
			//TODO: Abfangen, dass bei nicht ausgew�hlter Session play button ohne funktion bleibt
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				if (fileManager.getActiveSession() != null) {
					if (((ToggleButton) ae.getSource()).isSelected()) {
						// PLAY
						if (sw == null) {
							sw = new SessionWiedergabe(fileManager.getActiveSession());
							sw.setPlayerPanel( mf.getPlayerPanel() );
							sw.playSession();
						} else {
							sw.continueSession();
						}
						
						// animation
						if (!isPause) {
							// Auswahl der Animation
							// TODO freq und Mood �bergabe aus activeSession
							if (animationCounter == 0) 
							{
								//int[] freq = { -30, 0, 30 };
								animation = new AnimationFreq(sw.getCurFreq(), mf
										.getVirtualizationPnl());
								if (resize % 2 == 0) 
								{
									animation.init();
								}
							} 
							else if (animationCounter == 1) 
							{
								// animationFrakFarbverlauf: false = nur
								// farbverlauf
								animation = new FrakFarbverlauf(sw.getCurMood(), mf
										.getVirtualizationPnl(), false);
								if (resize % 2 == 0) 
								{
									animation.init();
								}
							} 
							else 
							{
								// animationFrakFarbverlauf: true = frak,
								animation = new FrakFarbverlauf(sw.getCurMood(), mf
										.getVirtualizationPnl(), true);
								if (resize % 2 == 0) 
								{
									animation.init();
								}
							}
						} 
						else 
						{
							//PAUSE wurde beendet
							animation.pause(false);
							isPause = false;
						}
						
						
					} else {
						// PAUSE
						if (sw != null)
							sw.pauseSession();
							animation.pause(true);
							isPause = true;
					}
				} else {
					//wenn keine Session gewahlt: setze Play/Pause Button zurueck auf Play
					((ToggleButton)ae.getSource()).toggle();
				}
			}
		});

		pnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// STOP:
				stopPlayer();
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.TIME_BAR, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JProgressBar s = (JProgressBar) ce.getSource();
				if (sw!=null) {
				    s.setStringPainted(true);
				}
			}
		});
		pnl.addListenerToElement(PlayerPanel.MUTE_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider muteBar = (JSlider) ce.getSource();	// TODO MuteBar default Wert ändern
				// Lösung: Wir brauchen etwas einheitliches, um zu erkennen, ob eine Session zurzeit abgespielt wird oder nicht
				// Absprache mit Animation nötig!
				// Quick Fix:
				if(sw != null)
					sw.changeVolumn(muteBar.getValue());
				System.out.println(muteBar.getValue());
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.BEAT_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider balanceBar = (JSlider) ce.getSource();
				
				if (sw != null) {
					if (tmpBalance>=balanceBar.getValue()) {
						sw.changeBalance(balanceBar.getValue(), false);
						System.out.println("Links: "+tmpBalance);
					}
					else {
						sw.changeBalance(balanceBar.getValue(), true);
						System.out.println("Rechts: "+ tmpBalance);
					}
				}
				tmpBalance = balanceBar.getValue();
			}
			
		});
	}

	private void initListenerForSessionListPanel() {
		SessionListPanel pnl = mf.getSessionListPnl();

		pnl.addListenerToElement(SessionListPanel.ADD_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						stopPlayer();
						new EditorController(fileManager, mf);
					}
				});

		pnl.addListenerToElement(SessionListPanel.EDIT_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (fileManager.getActiveSession() != null) {
							stopPlayer();
							new EditorController(fileManager, mf, fileManager.getActiveSession());
						}
					}
				});

		pnl.addListenerToElement(SessionListPanel.REMOVE_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if (fileManager.getCategories().containsKey(
								currentCategory.toString())) {
							
							System.out.println(fileManager.getActiveSession());
							
							if(fileManager.getActiveSession() != null) {
								fileManager
										.getCategories()
										.get(currentCategory.toString())
										.removeSession(fileManager.getActiveSession());
							} else {
								if ( fileManager.getCategories().get(currentCategory.toString()).getSessions().isEmpty() ) {
//									System.out.println(fileManager.getCategories().size());
									fileManager.getCategories().remove( currentCategory.toString() );
//									System.out.println(fileManager.getCategories().size());
								}
							}
							
							fileManager.writeCategories(fileManager.getCategories());
							
							fileManager.setActiveSession(null);
						}
						setCategoryListModel();
						setSessionListModel(currentCategory);
					}
				});

		pnl.addListenerToElement(SessionListPanel.CATEGORY_LIST,
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						Category c = (Category) ((JList) e.getSource())
								.getSelectedValue();
						if (!e.getValueIsAdjusting() && c != null) {
							currentCategory = c;
							setSessionListModel(c);
						}
					}
				});

		pnl.addListenerToElement(SessionListPanel.SESSION_LIST,
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						Session s = (Session) ((JList) e.getSource())
								.getSelectedValue();
						if (!e.getValueIsAdjusting() && s != null) {
							// TODO Session laden
							// TODO eventuelle aktuelle Session stoppen
							// TODO Infos in GUI darstellen (duration in
							// timeSlider)
							mf.getPlayerPanel().setMaximumofTimeBar(s.getDuration());
							fileManager.setActiveSession(s);
						}
					}
				});

		setCategoryListModel();
	}

	/**
	 * Erstellt ein ListModel und füllt es mit allen verfügbaren Kategorien. Das
	 * ListModel wird je nach Parameter der Categorylist vom SessionListPanel,
	 * oder der Categorylsit vom GlobalSettingPanel (Sessioneditor) übergeben
	 * 
	 * @param erlaubte
	 *            Werte: {0,1} mit 0 wird die Categorylist vom SesisonListPanel
	 *            gefüllt, mit 1 die Categoryliste vom GlobalSettubgPanel
	 */
	private void setCategoryListModel() {
			DefaultListModel catModel = new DefaultListModel();

			for (Category c : fileManager.getCategories().values()) {
				catModel.addElement(c);
			}

			mf.getSessionListPnl().setListModel(catModel,
					SessionListPanel.CATEGORY_LIST);
	}

	/**
	 * Erstellt ein ListModel und füllt es mit allen Sessions, die der Kategorie
	 * c untergeordnet sind. Das ListModel wird der Sessionliste vom
	 * SessionListPanel übergeben
	 * 
	 * @param c
	 *            Kategorie der Session, die im ListModel angezeigt werden
	 *            sollen
	 */
	private void setSessionListModel(Category c) {
		DefaultListModel sessionModel = new DefaultListModel();

		for (Session s : c.getSessions()) {
			sessionModel.addElement(s);
		}

		mf.getSessionListPnl().setListModel(sessionModel,
				SessionListPanel.SESSION_LIST);
	}
}
