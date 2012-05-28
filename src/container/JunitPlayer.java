package container;

import static org.junit.Assert.*;

import org.junit.Test;

public class JunitPlayer {

	@Test
	public void test() {
		String s = " HelloWorld " ;
		assertEquals ( " Just a test to see if everything works ... " , " HelloWorld " , s ) ;
		// fail("Not yet implemented");
	}

}

