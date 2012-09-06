/**
 * @author Fabian Schäfer
 * Test-Klasse
 * Hier werden die drei Animationen getestet
 * Lessons Learned: JUNIT sollte bereits von Beginn an zum Testen der Funktionen verwendet werden. 
 * Die Verwendung von JUNIT zum Ende eines Softwareprojekts kostet wiederum mehr Aufwand und Zeit. 
 * 
 * Aus diesem Grund sind im Folgenden gegebenenfalls nicht alle möglichen Tests für die
 * Animationsklasse und deren Unterklassen implementiert. 
 */


package container;
import interfaces.Mood;
import gui.MainFrame;
import gui.ToggleButton;
import gui.playerGui.PlayerPanel;
import junit.framework.*;
import logic.*;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class JunitAnimation extends TestCase {

	
	private MainFrame mf;
	private static Animation animation;
	// ueberprueft ob pause gedrueckt wurde
	private boolean isPause;
	// ist resize%2 == 0, so ist das animationPnl in maximiertem Zustand, wenn
	// != 0 in minimiertem Zustand
	private int resize;
	private int animationCounter;
	
	public JunitAnimation(String name){
		super(name);
		mf = new MainFrame();
		animationCounter = 0;
		isPause = false;
		resize = 1;
		mf.addComponentListener(new ComponentListener() {
			// Diese Methode wird aufgerufen, wenn JFrame max-/minimiert wird
			public void componentResized(ComponentEvent evt) {
				if (animation != null) {
					Component c = (Component) evt.getSource();
					// Neuer size
					Dimension newSize = c.getSize();
					animation.setSize(newSize);
					// fuer resize notwendig
					animation.init();
				}
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
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
				/**
				 * Der Animationswechsel soll nur ermöglicht sein, wenn der Sound nicht pausiert
				 */
				if (!isPause) {
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
						int[] freq = { -30, 0, 30 };
						animation = new AnimationFreq(freq, mf
								.getVirtualizationPnl());
						if (resize % 2 == 0) {
							animation.init();
						}
					} else if (animationCounter == 1) {
						// animationFrakFarbverlauf: false = nur farbverlauf
						animation = new FrakFarbverlauf(Mood.ALPHA, mf
								.getVirtualizationPnl(), false);
						if (resize % 2 == 0) {
							animation.init();
						}
					} else {
						// animationFrakFarbverlauf: true = frak
						animation = new FrakFarbverlauf(Mood.BETA, mf
								.getVirtualizationPnl(), true);
						if (resize % 2 == 0) {
							animation.init();
						}
					}
				}
			}
		});
	}
	public void defaultPaint()
	{
		/**
		 * Methode für das Übermalen des Animationsfensters
		 */
		Graphics2D rec = (Graphics2D) mf.getVirtualizationPnl()
		.getGraphics();
		Rectangle2D rectangle = new Rectangle2D.Double(0, 0, mf
		.getVirtualizationPnl().getSize().width, mf
		.getVirtualizationPnl().getSize().height);
		rec.setColor(Color.GRAY);
		rec.fill(rectangle);
	}
	private void initListenerForPlayerPanel() {
		PlayerPanel pnl = mf.getPlayerPanel();

		pnl.addListenerToElement(PlayerPanel.PLAY_BUTTON, new ActionListener() {			
			
			//TODO: Abfangen, dass bei nicht ausgewï¿½hlter Session play button ohn efunktion bleibt
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
							// TODO freq und Mood ï¿½bergabe aus activeSession
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
							animation.pause(true);
							isPause = true;
				}
			}
		});

		pnl.addListenerToElement(PlayerPanel.STOP_BUTTON, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// STOP:
						animation.finish(true);
						// uebermalt alte animation falls mal pause gedrueckt wurde
						defaultPaint();
						isPause = false;
			}
		});
	//Test-Methoden
	public void testRun() throws Exception
	{
		
		int resize = 0;
		int[] freq = { -30, 0, 30 };
		int num;
		String input = null;
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Wählen Sie bitte zwischen folgenden Animationen (1-3):\n 1. " +
				"Frequenz\n 2. Farbverlauf\n 3. Fraktale\n");
		try
		{
			input = console.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		num = Integer.parseInt(input);
		switch(num)
		{
			case 1: animation = new AnimationFreq(freq, mf.getVirtualizationPnl());
			
					assertTrue("Die Frequenz beträgt -30, 0, 30",animation.getFreq() == freq);
					while(animation.isFinished() == false)
					{
						//Das Testen von Maximierung und Minimierung des Animationsfensters
						if(animation.getCheckResize() == 0)
						{
							assertTrue("Wenn checkResize%2 Null ergibt, sollte das tempo = 0 sein",
									animation.getTempo() == 0);
						}
						else 
						{
							assertTrue("Wenn checkResize%2 Null ergibt, sollte das tempo = 0 sein",
									animation.getTempo() == 30);
						}
						
						if(animation.getPnl().getSize().width > 560)
						{
							assertTrue("Ist das Animationsfenster maximiert, soll das Tempo niedriger sein", 
									animation.getTempo() == 0);
						}
						else 
						{
							assertTrue("Ist das Animationsfenster minimiert, soll das Tempo höher sein", 
									animation.getTempo() == 30);
						}
					}
					
					
					break;
			case 2: animation = new FrakFarbverlauf(Mood.BETA, mf.getVirtualizationPnl(), false);
					while(animation.isFinished() == false)
					{
						Color [] c = ((FrakFarbverlauf) animation).getColors();
						assertTrue("Bei Mood BETA sind diese Farben enthalten", c[0] == Color.yellow.darker() &&
								c[1] == Color.orange.brighter() && c[2] == Color.red.brighter() &&
								c[3] == Color.white);
						assertTrue("Farbverlauf Animation",((FrakFarbverlauf)animation).isFraktal() == false);
					}
					break;
			case 3: animation = new FrakFarbverlauf(Mood.ALPHA, mf.getVirtualizationPnl(), true);
					while(animation.isFinished() == false)
					{
						Color [] c1 = ((FrakFarbverlauf) animation).getColors();
							assertTrue("Bei Mood ALPHA sind diese Farben enthalten",
									c1[0] == Color.blue.brighter() && c1[1] == Color.green.darker() &&
									c1[2] == Color.orange.brighter() && c1[3] == Color.white);
							assertTrue("Fraktale Animation",((FrakFarbverlauf)animation).isFraktal() == true);
					}
					break;
		}
		
		
		
		
	}
	
	public void main(String[] args) {
		junit.textui.TestRunner.run(JunitAnimation.class);
		try{
			testRun();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
