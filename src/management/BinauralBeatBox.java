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
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// import com.ibm.keymanager.audit.s;

import container.BinauralBeat;
import container.Category;
import container.Segment;
import container.Session;

import logic.*;
import gui.MainFrame;
import gui.ToggleButton;
import gui.editorGui.GlobalSettingPanel;
import gui.editorGui.SessionEditorPanel;
import gui.playerGui.PlayerPanel;
import gui.playerGui.SessionListPanel;

public class BinauralBeatBox {

	private MainFrame mf;
	private static Animation animation;

	private FileManager fileManager;
	private SessionWiedergabe sw;

	// ueberprueft ob pause gedrueckt wurde
	private boolean isPause;
	// ist resize%2 == 0, so ist das animationPnl in maximiertem Zustand, wenn
	// != 0 in minimiertem Zustand
	private int resize;
	private int animationCounter;
	private Category currentCategory;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new BinauralBeatBox();
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

		Session session = new Session();
		session.addSegment(new Segment(10, new BinauralBeat(500, 530)));
		session.addSegment(new Segment(40, new BinauralBeat(800, 830)));
		session.addSegment(new Segment(10, new BinauralBeat(500, 530)));

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
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

		mf.getVirtualizationPnl().addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent me) {
				animation.finish(true);
				// uebermalt alte animation falls mal pause gedrueckt wurde
				Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl()
						.getGraphics();
				Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf
						.getVirtualizationPnl().getSize().width, mf
						.getVirtualizationPnl().getSize().height);
				rec.setColor(Color.GRAY);
				rec.fill(rectangle);
				if (!isPause) {
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
						// animationFrakFarbverlauf: true = frak,
						animation = new FrakFarbverlauf(sw.getCurMood(), mf
								.getVirtualizationPnl(), true);
						if (resize % 2 == 0) {
							animation.init();
						}
					}
				}
			}
		});
	}
	
	public static void animationUpdateFreq(int[] curFreq, Mood curMood) {
		if (animation.isAniFreq()==false) {
			animation.setFreq(curFreq);
			animation.init();
		}
		if (animation.isAniFreq()==true) {
			((FrakFarbverlauf) animation).setMood(curMood);
			animation.init();
		}
	}

	private void initListenerForPlayerPanel() {
		PlayerPanel pnl = mf.getPlayerPanel();

		pnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {			
			
			//TODO: Abfangen, dass bei nicht ausgew�hlter Session play button ohn efunktion bleibt
			@Override
			public void actionPerformed(ActionEvent ae) {

				if (fileManager.getActiveSession() != null) {
					if (((ToggleButton) ae.getSource()).isSelected()) {
						// PLAY
						if (sw == null) {
							sw = new SessionWiedergabe(fileManager.getActiveSession());
							sw.playSession();
						} else {
							sw.continueSession();
						}
						
						// animation
						if (!isPause) {
							// Auswahl der Animation
							// TODO freq und Mood �bergabe aus activeSession
							if (animationCounter == 0) {
								//int[] freq = { -30, 0, 30 };
								animation = new AnimationFreq(sw.getCurFreq(), mf
										.getVirtualizationPnl());
								if (resize % 2 == 0) {
									animation.init();
								}
							} else if (animationCounter == 1) {
								// animationFrakFarbverlauf: false = nur
								// farbverlauf
								animation = new FrakFarbverlauf(sw.getCurMood(), mf
										.getVirtualizationPnl(), false);
								if (resize % 2 == 0) {
									animation.init();
								}
							} else {
								// animationFrakFarbverlauf: true = frak,
								animation = new FrakFarbverlauf(sw.getCurMood(), mf
										.getVirtualizationPnl(), true);
								if (resize % 2 == 0) {
									animation.init();
								}
							}
						} else {
							animation.pause(false);
						}
						
						
					} else {
						// PAUSE
						if (sw != null)
							sw.pauseSession();
							animation.pause(true);
							isPause = true;
					}
				}
			}
		});

		pnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// STOP:
				if (fileManager.getActiveSession() != null) {
					if (sw != null) {
						animation.finish(true);
						// uebermalt alte animation falls mal pause gedrueckt wurde
						Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl()
								.getGraphics();
						Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf
								.getVirtualizationPnl().getSize().width, mf
								.getVirtualizationPnl().getSize().height);
						rec.setColor(Color.GRAY);
						rec.fill(rectangle);
						isPause = false;
						
						sw.stopSession();
						sw = null;
					}
				}
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.TIME_BAR, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JProgressBar s = (JProgressBar) ce.getSource();
				if (sw!=null) {
				    s.setStringPainted(true);
					s.setMaximum(sw.getCompleteDuration());
					
					System.out.println("CurTime: " + sw.getCurrentTime());
					System.out.println("Complete Time: " + sw.getCompleteDuration());
					
					  if (sw.getCurrentTime() < sw.getCompleteDuration()) {
				           s.setValue((int) sw.getCurrentTime());
				        } else {
				           s.setValue(sw.getCompleteDuration());
				        }
					  s.repaint();
				}
					
				System.out.println("Time Bar: " + s.getValue());
			}
			
			/**
			 * Die Angaben würde ich alle in Sekunden machen. 
			 * (Falls du Mikrosekunden oder was auch immer hast, kannst du das erst mal probieren. 
			 * Aber ich glaube wir bekommen mit der GUI Ausgabe Probleme.)
			 * Schritte:
			 * 1. Gesamtlänge der Session mit SetMaximumOfTimeBar(sessionlänge) festlegen
			 * 2. Aktuellen Stand/Position der Session mit setValueOfTimeBar aktualisieren
			 * Bin mir grad gar nich sicher, ob das so gut gehen wird :/
			 * Könnte sein, dass die TimeBar noch mal in nen Thread müsste, da die GUI wahrscheinlich nicht so flüssi aktualisiert wird....
			 * Egal probier es erst mal so und dann sehen wir weiter ;)
			 */
			//mf.getPlayerPanel().setMaximumofTimeBar(sec)
			//mf.getPlayerPanel().setValueOfTimBar(sec)
			//mf.getPlayerPanel().getValueOfTimBar()
			
		});
		pnl.addListenerToElement(PlayerPanel.MUTE_SLIDER, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider muteBar = (JSlider) ce.getSource();	// TODO MuteBar default Wert ändern
				// FIXME NullPointerException
				// 1. Es wurde noch KEINE Session gewaehlt
				// 2. Lautstärke wurde angepasst
				// Lösung: Wir brauchen etwas einheitliches, um zu erkennen, ob eine Session zurzeit abgespielt wird oder nicht
				// Absprache mit Animation nötig!
				// Quick Fix:
				if(sw != null)
					sw.changeVolumn(muteBar.getValue());
				System.out.println(muteBar.getValue());
			}
		});
		
		pnl.addListenerToElement(PlayerPanel.BEAT_SLIDER, new ChangeListener() {
			int tmpBalance = 0;
			@Override
			public void stateChanged(ChangeEvent ce) {
				JSlider balanceBar = (JSlider) ce.getSource();
				
				if (balanceBar.getValue()<50) {
					tmpBalance=50-balanceBar.getValue();
					sw.changeBalance(tmpBalance, false);
					System.out.println("Links: "+tmpBalance);
				}
				else {
					sw.changeBalance(tmpBalance, true);
					System.out.println("Rechts: "+ tmpBalance);
				}
			}
			
		});
	}

	private void initListenerForSessionListPanel() {
		SessionListPanel pnl = mf.getSessionListPnl();

		pnl.addListenerToElement(SessionListPanel.ADD_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// mf.setEditorLayout();
						changeToSessionEditor();
					}
				});

		pnl.addListenerToElement(SessionListPanel.EDIT_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (fileManager.getActiveSession() != null) {
							// mf.setEditorLayout();
							changeToSessionEditor();
							mf.getSessionEditorPnl().setDefaultValues(
									fileManager.getActiveSession());
						}
					}
				});

		pnl.addListenerToElement(SessionListPanel.REMOVE_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if (fileManager.getCategories().containsKey(
								currentCategory.toString())) {
							fileManager
									.getCategories()
									.get(currentCategory.toString())
									.removeSession(
											fileManager.getActiveSession());
							fileManager.writeCategories(fileManager
									.getCategories());
						}
						setCategoryListModel(0);
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

		setCategoryListModel(0);
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
	private void setCategoryListModel(int list) {
		switch (list) {
		case 0:
			DefaultListModel catModel = new DefaultListModel();

			for (Category c : fileManager.getCategories().values()) {
				catModel.addElement(c);
			}

			mf.getSessionListPnl().setListModel(catModel,
					SessionListPanel.CATEGORY_LIST);
			break;
		case 1:
			mf.getSessionEditorPnl()
					.getGlobalSettingPanel()
					.setListModel(fileManager.getCategories(),
							GlobalSettingPanel.CATEGORY_LIST);
			break;
		}
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

	/**
	 * Sorgt fuer den Wechsel in das Editor Layout, erstellt & setzt alle
	 * notwendigen Listener fuer das Editor Layout und uebergibt alle
	 * notwendigen Daten (Liste der Kategorien und Hintergrundgeraeusche an den
	 * Editor.
	 */
	private void changeToSessionEditor() {
		mf.setEditorLayout();
		SessionEditorPanel editPnl = mf.getSessionEditorPnl();

		editPnl.addListenerToElement(SessionEditorPanel.SAVE_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						addNewSession();
						fileManager.writeCategories(fileManager.getCategories());
					}
				});

		editPnl.addListenerToElement(SessionEditorPanel.EXPORT_BUTTON,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						addNewSession();
						fileManager.exportAsWav();
					}
				});

		setCategoryListModel(1);
		mf.getSessionEditorPnl()
				.getGlobalSettingPanel()
				.setListModel(fileManager.getListOfWav(),
						GlobalSettingPanel.SOUND_LIST);
	}

	private void addNewSession() {
		String catName = mf.getSessionEditorPnl().getCategory();
		Session s = mf.getSessionEditorPnl().getValues();
		if (fileManager.getCategories().containsKey(catName)) {
			fileManager.getCategories().get(catName).addSession(s);
		} else {
			fileManager.addCategory(new Category(catName, s));
		}
		fileManager.setActiveSession(s);
		setCategoryListModel(0);
	}

}
