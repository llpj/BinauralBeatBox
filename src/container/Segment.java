/**
 * 
 */
package container;

/**
 * @author Magnus Bruehl
 * 
 */
public class Segment {
	// Attribute
	/**
	 * Dauer des Session-Segments in Sekunden.
	 */
	private int duration;
	/**
	 * Die Schwebung, die durch Differenz der eingegebenen Frequenzen entsteht
	 */
	private BinauralBeat beat;

	// Konstruktoren
	/**
	 * Erstellt ein neues Segment mit den Standard-Parametern. Laenge: 60
	 * Sekunden, Schwebungsfrequenz: 21.5Hz
	 */
	public Segment() {
		this.duration = 60;
		this.beat = new BinauralBeat();
	}

	/**
	 * Erstellt ein neues Segment mit den angegebenen Parametern
	 * 
	 * @param duration
	 *            Dauer des Segments
	 * @param freq1_start
	 *            Startfrequenz fuer den Linkskanal
	 * @param freq1_target
	 *            Zielfrequenz fuer den Linkskanal
	 * @param freq2_start
	 *            Startfrequenz fuer den Rechtskanal
	 * @param freq2_target
	 *            Zielfrequenz fuer den Rechtskanal
	 * @throws IllegalArgumentException
	 *             Wird in der Klasse BinauralBeat auf korrekte Frequenzen
	 *             validiert.
	 */
	public Segment(int duration, int freq1_start, int freq1_target,
			int freq2_start, int freq2_target) throws IllegalArgumentException {
		this.duration = duration;
		this.beat = new BinauralBeat(freq1_start, freq1_target, freq2_start,
				freq2_target);
	}

	/**
	 * Erstellt ein neues Segment mit den angegebenen Parametern
	 * 
	 * @param duration
	 *            Dauer des Segments
	 * @param freq1
	 *            Stetige Frequenz fuer den Linkskanal
	 * @param freq2
	 *            Stetige Frequenz fuer den Rechtskanal
	 * @throws IllegalArgumentException
	 *             Wird in der Klasse BinauralBeat auf korrekte Frequenzen
	 *             validiert.
	 */
	public Segment(int duration, int freq1, int freq2)
			throws IllegalArgumentException {
		this.duration = duration;
		this.beat = new BinauralBeat(freq1, freq2);
	}

	/**
	 * Erstellt ein neues Segment aus einem vorkonfigurierten binauralen Beat.
	 * 
	 * @param duration
	 *            Die Dauer des Segments
	 * @param beat
	 *            Ein fertiger binauraler Beat
	 */
	public Segment(int duration, BinauralBeat beat) throws IllegalArgumentException {
		this.duration = duration;
		this.beat = beat;
		
		if(duration<0) {
			throw new IllegalArgumentException("Leider koennen wir die Zeit nicht zurückspulen");
		}
	}

	// Getter & Setter
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public BinauralBeat getBeat() {
		return beat;
	}

	public void setBeat(BinauralBeat beat) {
		this.beat = beat;
	}

}
