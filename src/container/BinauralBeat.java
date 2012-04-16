/**
 * 
 */
package container;

//Imports to allow UserExceptions
import org.omg.CORBA.UserException;
import interfaces.Mood;

/**
 * @author Magnus Br�hl
 * 
 */
public class BinauralBeat {
	// Attribute
	private double freq1_start, freq2_start;
	private double freq1_target, freq2_target;

	// Konstruktoren
	/**
	 * Erstellt den kleinstm�glichen h�rbaren binauralen Beat von 21.5Hz
	 */
	public BinauralBeat() {
		freq1_start = 21;
		freq1_target = 21;
		freq2_start = 22;
		freq2_target = 22;
	}

	/**
	 * Erstellt einen binauralen Beat mit den angegebenen Parametern. Die
	 * Frequenzen m�ssen ungleich und nicht negativ sein. Der Unterschied zw.
	 * den Frequenzen darf nicht gr��er sein als 30Hz.
	 * 
	 * @param freq1_start
	 *            Startfrequenz f�r den Linkskanal
	 * @param freq1_target
	 *            Zielfrequenz f�r den Linkskanal
	 * @param freq2_start
	 *            Startfrequenz f�r den Rechtskanal
	 * @param freq2_target
	 *            Zielfrequenz f�r den Rechtskanal
	 */
	public BinauralBeat(double freq1_start, double freq1_target,
			double freq2_start, double freq2_target) throws IllegalArgumentException {
		// Inputvalidierung
		if (freq1_start < 0 || freq1_target < 0 || freq2_start < 0
				|| freq2_target < 0) {
			throw new IllegalArgumentException("Frequenzen d�rfen nicht negativ sein.");
		}
		if (freq1_start == freq2_start || freq1_target == freq2_target) {
			throw new IllegalArgumentException("Frequenzen d�rfen nicht gleich sein.");
		}
		if (freq1_start - freq2_start > 30 || freq2_start - freq1_start > 30
				|| freq1_target - freq2_target > 30
				|| freq2_target - freq1_target > 30) {
			throw new IllegalArgumentException(
					"Der Unterschied zwischen den Frequenzen darf nicht gr��er sein als 30Hz");
		}
		this.freq1_start = freq1_start;
		this.freq1_target = freq1_target;
		this.freq2_start = freq2_start;
		this.freq2_target = freq2_target;
	}

	/**
	 * Erstellt einen binauralen Beat, bei dem die Start- und die Endfrequenz
	 * gleich ist, also der kein �bergang ist.
	 * 
	 * @param freq1
	 *            Linkskanal
	 * @param freq2
	 *            Rechtskanal
	 * @throws UserException
	 */
	public BinauralBeat(double freq1, double freq2) throws IllegalArgumentException {
		this(freq1, freq1, freq2, freq2);
	}

	// Getters and Setters
	public double getFreq1_start() {
		return freq1_start;
	}

	public void setFreq1_start(double freq1_start) {
		this.freq1_start = freq1_start;
	}

	public double getFreq2_start() {
		return freq2_start;
	}

	public void setFreq2_start(double freq2_start) {
		this.freq2_start = freq2_start;
	}

	public double getFreq1_target() {
		return freq1_target;
	}

	public void setFreq1_target(double freq1_target) {
		this.freq1_target = freq1_target;
	}

	public double getFreq2_target() {
		return freq2_target;
	}

	public void setFreq2_target(double freq2_target) {
		this.freq2_target = freq2_target;
	}

	// Methoden

	/**
	 * @return Die Schwebungsfrequenz-Klasse, die durch den Unterschied der
	 *         beiden Frequenzen generiert wird. M�gliche R�ckgabewerte sind
	 *         DELTA, THETA, ALPHA, BETA, GAMMA, SLOWDOWN, WAKEUP, INVALID,
	 *         UNKNOWN
	 */
	public Mood getMood() {
		Mood lMood = Mood.UNKNOWN;
		// Wenn kein �bergang und Links- und Rechtsfrequenz nicht gleich
		if (freq1_start == freq1_target && freq2_start == freq2_target
				&& freq1_start != freq2_start) {
			double b = (freq1_start + freq2_start) / 2; // berechne Schwebung
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
