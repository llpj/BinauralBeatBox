/**
 * Placebo-Klasse
 * Für die Animationsklasse sind keine zu testenden Parameter erkennbar und notwendig
 */


package container;
import junit.framework.*;
public class JunitAnimation extends TestCase {

	public JunitAnimation(String name){
		super(name);
	}
	//Test-Methoden
	public void TestRun()
	{
		
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(JunitAnimation.class);
	}
}
