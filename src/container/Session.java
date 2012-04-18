package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Magnus Br�hl
 * 
 */
public class Session implements Serializable {

	// Attribute
	private static final long serialVersionUID = 3318158115697418966L;
	private String Hintergrundklang; // path to background noise
	private ArrayList<Segment> segments;
	private int duration;

	// Konstruktoren
	public Session() {
		// TODO: Korrekten Pfad zum Standard-Hintergrundklang hinzufuegen
		Hintergrundklang = "Pfad";
		segments = new ArrayList<Segment>();
		duration = calcDuration();
	}

	public Session(String Hintergrundklang, ArrayList<Segment> segments) {
		this.Hintergrundklang = Hintergrundklang;
		this.segments = segments;
	}

	public Session(String Hintergrundklang, Segment segment) {
		this.Hintergrundklang = Hintergrundklang;
		this.addSegment(segment);
	}

	// Getter und Setter
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

	// Kalkulationen und Methoden
	public void addSegment(Segment segment) {
		this.segments.add(segment);
	}

	public void removeSegment(int position) {
		this.segments.remove(position);
	}

	/**
	 * Iteriert durch die Liste von Segmenten und addiert die jeweiligen L�ngen
	 * 
	 * @return Die Dauer der kompletten Session
	 */
	private int calcDuration() {
		int lDuration = 0;
		if (this.segments.isEmpty()) {
			return lDuration;
		} else {
			// iterate through the list of segments and add the durations
			Iterator<Segment> itr = this.segments.iterator();
			while (itr.hasNext()) {
				lDuration = itr.next().getDuration();
			}
			return lDuration;
		}
	}

}
