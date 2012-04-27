package management;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import container.Segment;
import container.Session;

public class xmlwriter {
 
	public static void main(String argv[]) throws FileNotFoundException {
	
		//test session
		Segment steadySegment = new Segment(60, 155, 160);
		Session exportableSession = new Session(steadySegment);
		
		//creating xstream object
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("Session", Session.class);
		String xml = xstream.toXML(exportableSession);
		//writes string in file
		FileWriter writer;
	    try {
	      writer = new FileWriter();
	      writer.write(xml);
	      writer.close();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
		
	    }
	    
	    
	    
	    
	    
	}
}