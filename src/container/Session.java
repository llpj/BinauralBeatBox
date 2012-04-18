package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
<<<<<<< HEAD
 * @author Magnus Brï¿½hl
=======
 * Die Session-Klasse. Achtung: bei jedem hinzufuegen eines Segments muss die
 * Laenge der Session (duration) neu berechnet werden.
 * 
 * @author Magnus Brühl
>>>>>>> ae5b5620e132b9d329b77270b90a0af3d6daf0f0
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
		this.duration = 0;
	}

	public Session(String Hintergrundklang, ArrayList<Segment> segments) {
		this.Hintergrundklang = Hintergrundklang;
		this.segments = segments;
		this.duration = this.calcDuration();
	}

	public Session(String Hintergrundklang, Segment segment) {
		this();
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
		this.calcDuration();
	}

	public void removeSegment(int position) {
		this.segments.remove(position);
		this.calcDuration();
	}

	/**
	 * Iteriert durch die Liste von Segmenten und addiert die jeweiligen Lï¿½ngen
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
