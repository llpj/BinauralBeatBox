package management;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import container.Category;
import container.Segment;
import container.Session;

public class xmlwriter {
 
	public static void main(String argv[]) throws FileNotFoundException {
	
		//test session
		Segment steadySegment = new Segment(60, 155, 160);
		Session exportableSession = new Session(steadySegment);
		
		writeSession(exportableSession);
	    
	}
	
	
	/**
	 * Writes a Session into an XML File into the ressources/session directory
	 * @param session
	 */
	
	public static void writeSession(Session session){
		//creating xstream object
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("Session", Session.class);
		String xml = xstream.toXML(session);
		//writes string in file
		FileWriter writer;
	    try {
	      writer = new FileWriter("./src/resources/sessions/"+Session.getName()+"xml");				//get Session Name onClick? wie greife ich auf das lokale object zu?
	      writer.write(xml);
	      writer.close();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
		
	    }
	}
	
	public static void writeCategory(HashMap<String, Category> categories){
		//creating xstream object
		XStream xstream = new XStream(new DomDriver());
		String xml = xstream.toXML(categories);
		//writes string in file
		FileWriter writer;
	    try {
	      writer = new FileWriter("./src/resources/categories.xml");
	      writer.write(xml);
	      writer.close();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
		
	    }
	}
	
}