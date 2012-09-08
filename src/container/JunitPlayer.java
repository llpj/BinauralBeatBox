package container;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import logic.SessionWiedergabe;
import management.BinauralBeatBox;
import management.FileManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class JunitPlayer {
	
	
	private SessionWiedergabe jSw;

	@Test
	public void test() {
		String s = " HelloWorld " ;
		assertEquals ( "Just a test to see if everything works ... ", " HelloWorld " , s ) ;
	}
	
//	public static junit.framework.Test suite() {
//		return new JUnit4TestAdapter(JunitPlayer.class);
//	}
	
	@Test
	public void testPlay() {	
		
		// get Session
		jSw = BinauralBeatBox.getSw();
		assertNotNull("Session erfolgreich geladen");
		System.out.println(jSw);
		
		// load session
		
		// play session
		//jSw.playSession();
		// TODO Junit
		
		// pause 
		//jSw.pauseSession();
		// TODO Junit
		
		// resume
		//.pauseSession();
		// TODO Junit
		
		// stop
		//jSw.stopSession(true);
		// TODO Junit
		
		
		
		//fail("Not yet implemented");
		
	}
	
	@Test
	public void testPlayWithoutList() {		
		// play session
		jSw = BinauralBeatBox.getSw();
		
		jSw.playSession();
		assertNotNull("Keine Session ausgewaehlt", jSw);
		
		// pause 
		jSw.pauseSession();
		// TODO Junit
		
		// resume
		jSw.pauseSession();
		// TODO Junit
		
		// stop
		jSw.stopSession(true);
		// TODO Junit
		
		//fail("Not yet implemented");
		
	}
	
	
	@Test
	public void testBalance() {
		// load session
		//jSw = new SessionWiedergabe(filemanager.getActiveSession());
		// TODO Junit
		
		// play session
		jSw.playSession();
		// TODO Junit
		
		// change balance between Binaural Beat and Backgroundmusic
		// TODO implement Balance
		// TODO Junit
		
		fail("Not yet implemented");
		
	}

}

