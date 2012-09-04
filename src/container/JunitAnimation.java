package container;
import junit.framework.*;
import logic.*;
public class JunitAnimation extends TestCase {

	public JunitAnimation(String name){
		super(name);
	}
	
	

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JunitAnimation.class);
	}
}
