package container;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import logic.SessionWiedergabe;
import management.BinauralBeatBox;
import management.FileManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class JunitPlayer {
	
	static SessionWiedergabe jSw = null;
	
	public static SessionWiedergabe getSw(boolean click) {	
		
		// Session erstellen
		if (click) {
			if (jSw == null) {
					Session session = new Session();
					session.addSegment(new Segment(10, new BinauralBeat(500, 530)));
					session.addSegment(new Segment(40, new BinauralBeat(800, 830)));
					session.addSegment(new Segment(10, new BinauralBeat(500, 530)));
					jSw = new SessionWiedergabe(session);
				}	
		}
		return jSw;
	}

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
		
		// session laden
		getSw(true);
		assertNotNull("Session erfolgreich geladen");
		
		//session abspielen
		jSw.playSession();
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
		getSw(false);
		assertEquals ( "Sessionwiedergabe nicht ausgewaehlt", null , jSw ) ;	
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

