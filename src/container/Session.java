package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Die Session-Klasse. Achtung: bei jedem hinzufuegen eines Segments muss die
 * Laenge der Session (duration) neu berechnet werden.
 * 
 * @author Magnus Bruehl
 * 
 */
public class Session implements Serializable {

	// Attribute
	private static final long serialVersionUID = 3318158115697418966L;
	private String Hintergrundklang; // path to background noise
	private ArrayList<Segment> segments;
	private int duration;

	// Konstruktoren
	/**
	 * Erstellt eine leere Session, mit Standard-Hintergrundklang, einer leeren
	 * Liste von Segmenten und einer Dauer von 0.
	 */
	public Session() {
		Hintergrundklang = "./src/resources/wav/amsel.wav";
		segments = new ArrayList<Segment>();
		this.duration = 0;
	}

	/**
	 * Erstellt eine neue Session mit angegebenem Hintergrundklang und einer
	 * bereits vorhandenen Liste von Segmenten
	 * 
	 * @param Hintergrundklang
	 *            Der Pfad zum Hintergrundklang
	 * @param segments
	 *            Eine Liste von Segmenten
	 */
	public Session(String Hintergrundklang, ArrayList<Segment> segments) {
		this.Hintergrundklang = Hintergrundklang;
		this.segments = segments;
		this.duration = this.calcDuration();
	}

	/**
	 * Erstellt eine neue Session mit angegebenem Hintergrundklang und einem
	 * einzelnen bereits vorhandenen Segment
	 * 
	 * @param Hintergrundklang
	 *            Der Pfad zum Hintergrundklang
	 * @param segment
	 *            Ein einzelnes Segment
	 */
	public Session(String Hintergrundklang, Segment segment) {
		this();
		this.Hintergrundklang = Hintergrundklang;
		this.addSegment(segment);
		this.duration = this.calcDuration();
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
		this.duration = this.calcDuration(); // zur Sicherheit nochmal
												// nachrechnen
		return duration;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
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

	public int getNumerOfSegments() {
		return this.segments.size();
	}

	/**
	 * Iteriert durch die Liste von Segmenten und addiert die jeweiligen Laengen
	 * 
	 * @return Die Dauer der kompletten Session
	 */
	private int calcDuration() {
		int lDuration = 0;
		if (this.segments.isEmpty()) {
			return lDuration;
		} else {
			// iteriere durch die Liste von Segmenten und addiere die Dauer
			Iterator<Segment> itr = this.segments.iterator();
			while (itr.hasNext()) {
				lDuration += itr.next().getDuration();
			}
			return lDuration;
		}
	}

}
