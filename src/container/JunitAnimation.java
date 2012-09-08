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
import junit.framework.*;
import logic.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class JunitAnimation extends TestCase {

	public JunitAnimation(String name){
		super(name);
	}
	//Test-Methoden
	public void testRun() throws Exception
	{
		MainFrame mf;
		final Animation animation;
		mf = new MainFrame();
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
						//Das Testen der Auswirkungen von Maximierung und Minimierung des Animationsfensters
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
							assertTrue("Ist das Animationsfenster minimiert, soll auch das Tempo niedriger sein", 
									animation.getTempo() == 30);
						}
					}
					break;
			case 2: animation = new FrakFarbverlauf(Mood.BETA, mf.getVirtualizationPnl(), false);
					while(animation.isFinished() == false)
					{
						Color [] c = ((FrakFarbverlauf) animation).getColors();
						assertTrue("Bei Mood BETA sind diese Farben nicht enthalten", c[0] == Color.yellow.darker() &&
								c[1] == Color.orange.brighter() && c[2] == Color.red.brighter() &&
								c[3] == Color.white);
						assertTrue("Farbverlauf Animation",((FrakFarbverlauf)animation).isFraktal() == false);
					}
					break;
			case 3: animation = new FrakFarbverlauf(Mood.ALPHA, mf.getVirtualizationPnl(), true);
					while(animation.isFinished() == false)
					{
						Color [] c1 = ((FrakFarbverlauf) animation).getColors();
							assertTrue("Bei Mood ALPHA sind diese Farben nicht enthalten",
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

