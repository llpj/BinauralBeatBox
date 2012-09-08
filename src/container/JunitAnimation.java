/**
 * @author Fabian Schäfer
 * Test-Klasse
 * Hier werden die drei Animationen getestet
 * Lessons Learned: JUNIT sollte bereits von Beginn an zum Testen der Funktionen verwendet werden. 
 * Die Verwendung von JUNIT zum Ende eines Softwareprojekts kostet wiederum mehr Aufwand und Zeit. 
 * 
 * Aus diesem Grund ist im Folgenden nur ein beispielhafter JUnitTest illustriert 
 */


package container;

import gui.MainFrame;
import junit.framework.*;
import logic.*;

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
		int[] freq = { -30, 0, 30 };
		animation = new AnimationFreq(freq, mf.getVirtualizationPnl());
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

