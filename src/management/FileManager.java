/**
 * 
 */
package management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.sun.media.sound.WaveFileWriter;

import container.Category;
import container.Session;

/**
 * Liest und schreibt XML-Dateien. Exportiert komplette Sessions als WAVE-Datei.
 * Stellt Beat-Templates fuer den Casual-Modus zur Verfuegung
 * 
 * @author Magnus Bruehl, Ulrich Ahrendt
 * 
 */
// TODO: XML-Encoding, WAVE-Encoding
public class FileManager {
	// Attribute
	private ArrayList<Category> categories;
	private Session activeSession;

	// Konstruktor
	public FileManager() {
		categories = new ArrayList<Category>();
	}

	// Getter & Setter
	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public void setActiveSession(Session activeSession) {
		this.activeSession = activeSession;
	}

	public Session getActiveSession() {
		return activeSession;
	}

	// Weitere Methoden
	public void addCategory(Category category) {
		this.categories.add(category);
	}

	public void removeCategory(int index) {
		this.categories.remove(index);
	}

	/**
	 * Oh Shit, das wird schwierig. Wir muessen die Audioinformationen aus der
	 * Session irgendwie auf eine DataLine schreiben, damit daraus ein
	 * AudioInputStream generiert werden kann, der dann in ein WaveFile
	 * exportiert werden kann. Damn!
	 */
	public void exportAsWav() {
		// Hole Audioinformationen, um einen AudioInputStream zu generieren
		AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
		// Neue WAVE-Datei im Filesystem erstellen
		File fileOut = new File("test.wav"); // TODO: Hier muss der Pfad rein,
												// den der User spezifiziert
		WaveFileWriter writer = new WaveFileWriter();
		// Check, ob das Hostsystem ueberhaupt WAVE-Dateien schreiben kann.
		if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE,
				audioInputStream)) {
			writer.write(audioInputStream, AudioFileFormat.Type.WAVE, fileOut);
		}

	}

}
