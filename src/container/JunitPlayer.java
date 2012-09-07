package container;

import static org.junit.Assert.*;
import logic.SessionWiedergabe;
import management.BinauralBeatBox;
import management.FileManager;

import org.junit.Test;

public class JunitPlayer {
	
	
	private SessionWiedergabe sw;
	FileManager filemanager = new FileManager();
	// TODO an filemanager rankommen
	
	@Test
	public void test() {
		String s = " HelloWorld " ;
		assertEquals ( "Just a test to see if everything works ... ", " HelloWorld " , s ) ;
		// fail("Not yet implemented");
	}
	
	@Test
	public void testPlay() {
		
		// check filemanager
		assertNotNull(filemanager);
		
		// load session
		sw = new SessionWiedergabe(filemanager.getActiveSession());
		assertNotNull(sw);
		
		// play session
		sw.playSession();
		// TODO Junit
		
		// pause 
		sw.pauseSession();
		// TODO Junit
		
		// resume
		sw.pauseSession();
		// TODO Junit
		
		// stop
		sw.stopSession(true);
		// TODO Junit
		
		
		
		//fail("Not yet implemented");
		
	}
	
	@Test
	public void testPlayWithoutList() {		
		// play session
		sw.playSession();
		// TODO Junit
		
		// pause 
		sw.pauseSession();
		// TODO Junit
		
		// resume
		sw.pauseSession();
		// TODO Junit
		
		// stop
		sw.stopSession(true);
		// TODO Junit
		
		fail("Not yet implemented");
		
	}
	
	
	@Test
	public void testBalance() {
		// load session
		sw = new SessionWiedergabe(filemanager.getActiveSession());
		// TODO Junit
		
		// play session
		sw.playSession();
		// TODO Junit
		
		// change balance between Binaural Beat and Backgroundmusic
		// TODO implement Balance
		// TODO Junit
		
		fail("Not yet implemented");
		
	}

}

