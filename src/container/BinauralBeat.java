/**
 * 
 */
package container;

import interfaces.Mood;

/**
 * @author Magnus Bruehl
 * 
 */
public class BinauralBeat {
	// Attribute
	private int freq1_start, freq2_start;
	private int freq1_target, freq2_target;

	// Konstruktoren
	/**
	 * Erstellt den kleinstmueglichen huerbaren binauralen Beat von 21.5Hz
	 */
	public BinauralBeat() {
		freq1_start = 21;
		freq1_target = 21;
		freq2_start = 22;
		freq2_target = 22;
	}

	/**
	 * Erstellt einen binauralen Beat mit den angegebenen Parametern. Die
	 * Frequenzen muessen ungleich und nicht negativ sein. Der Unterschied zw.
	 * den Frequenzen darf nicht groesser sein als 30Hz.
	 * 
	 * @param freq1_start
	 *            Startfrequenz fuer den Linkskanal
	 * @param freq1_target
	 *            Zielfrequenz fuer den Linkskanal
	 * @param freq2_start
	 *            Startfrequenz fuer den Rechtskanal
	 * @param freq2_target
	 *            Zielfrequenz fuer den Rechtskanal
	 */
	public BinauralBeat(int freq1_start, int freq1_target, int freq2_start,
			int freq2_target) throws IllegalArgumentException {
		// Inputvalidierung
		if (freq1_start < 0 || freq1_target < 0 || freq2_start < 0
				|| freq2_target < 0) {
			throw new IllegalArgumentException(
					"Frequenzen duerfen nicht negativ sein.");
		}
		if (freq1_start == freq2_start || freq1_target == freq2_target) {
			throw new IllegalArgumentException(
					"Frequenzen duerfen nicht gleich sein.");
		}
		if (freq1_start - freq2_start > 30 || freq2_start - freq1_start > 30
				|| freq1_target - freq2_target > 30
				|| freq2_target - freq1_target > 30) {
			throw new IllegalArgumentException(
					"Der Unterschied zwischen den Frequenzen darf nicht groesser sein als 30Hz");
		}
		this.freq1_start = freq1_start;
		this.freq1_target = freq1_target;
		this.freq2_start = freq2_start;
		this.freq2_target = freq2_target;
	}

	/**
	 * Erstellt einen binauralen Beat, bei dem die Start- und die Endfrequenz
	 * gleich ist, also der kein Uebergang ist.
	 * 
	 * @param freq1
	 *            Linkskanal
	 * @param freq2
	 *            Rechtskanal
	 */
	public BinauralBeat(int freq1, int freq2) throws IllegalArgumentException {
		this(freq1, freq1, freq2, freq2);
	}

	// Getters and Setters
	public int getFreq1_start() {
		return freq1_start;
	}

	public void setFreq1_start(int freq1_start) {
		this.freq1_start = freq1_start;
	}

	public int getFreq2_start() {
		return freq2_start;
	}

	public void setFreq2_start(int freq2_start) {
		this.freq2_start = freq2_start;
	}

	public int getFreq1_target() {
		return freq1_target;
	}

	public void setFreq1_target(int freq1_target) {
		this.freq1_target = freq1_target;
	}

	public int getFreq2_target() {
		return freq2_target;
	}

	public void setFreq2_target(int freq2_target) {
		this.freq2_target = freq2_target;
	}

	// Methoden

	/**
	 * @return Die Schwebungsfrequenz-Klasse, die durch den Unterschied der
	 *         beiden Frequenzen generiert wird. Moegliche Rueckgabewerte sind
	 *         DELTA, THETA, ALPHA, BETA, GAMMA, SLOWDOWN, WAKEUP, INVALID,
	 *         UNKNOWN
	 */
	public Mood getMood() {
		Mood lMood = Mood.UNKNOWN;
		// Wenn kein Uebergang und Links- und Rechtsfrequenz nicht gleich
		if (freq1_start == freq1_target && freq2_start == freq2_target
				&& freq1_start != freq2_start) {
			//double b = (freq1_start + freq2_start) / 2; // berechne gehoerte Frequenz
			double b = freq1_start-freq2_start; // berechne Schwebung
			b = (b < 0 ? b * (-1) : b); // Schnellberechnung des Betrags
			// Cannot switch on a value of type double. Only convertible int
			// values or enum constants are switchable
			if (b >= 0.1 && b < 4) {
				lMood = Mood.DELTA;
			} else if (b >= 4 && b < 8) {
				lMood = Mood.THETA;
			} else if (b >= 8 && b <= 13) {
				lMood = Mood.ALPHA;
			} else if (b > 13 && b <= 30) {
				lMood = Mood.BETA;
			} else if (b > 30) {
				lMood = Mood.GAMMA;
			}
		} else if (freq1_start > freq1_target && freq2_start > freq2_target) {
			lMood = Mood.SLOWDOWN;
		} else if (freq1_start < freq1_target && freq2_start < freq2_target) {
			lMood = Mood.WAKEUP;
		} else {
			lMood = Mood.INVALID;
		}
		return lMood;
	}

}
