package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Magnus Brühl
 *
 */
public class Session implements Serializable {

	//Attributes
	private static final long serialVersionUID = 3318158115697418966L;
	private String Hintergrundklang; //path to background noise
	private ArrayList<Segment> segments;
	private int duration;
	
	//Constructors
	public Session(){
		//TODO: Add correct path to default background noise.
		Hintergrundklang = "Path to default background noise.";
		segments = new ArrayList<Segment>();
		duration = calcDuration();
	}
	public Session(String Hintergrundklang, ArrayList<Segment> segments){
		this.Hintergrundklang = Hintergrundklang;
		this.segments = segments;
	}
	public Session(String Hintergrundklang, Segment segment){
		this.Hintergrundklang = Hintergrundklang;
		this.addSegment(segment);
	}
	
	// Getters and setters
	public void setHintergrundklang(String hintergrundklang) {
		Hintergrundklang = hintergrundklang;
	}
	public String getHintergrundklang() {
		return Hintergrundklang;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getDuration() {
		return duration;
	}
	
	// Calculations and methods
	public void addSegment(Segment segment){
		this.segments.add(segment);
	}
	public void removeSegment(int position){
		this.segments.remove(position);
	}
	/**
	 * Iterates through the list of segments and adds up the durations
	 * @return the duration of the complete Session
	 */
	private int calcDuration(){
		int lDuration = 0;
		if(this.segments.isEmpty()){
			return lDuration;
		}else{
			// iterate through the list of segments and add the durations
			Iterator<Segment> itr = this.segments.iterator();
			while(itr.hasNext()){
				lDuration = itr.next().getDuration();
			}
			return lDuration;
		}
	}
	

}
