/**
 * 
 */
package management;

import interfaces.wavFile.WavFile;

import java.io.File;
/* (Currently) unused imports
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 */
import java.util.ArrayList;
/* (Currently) unused Imports
 import javax.sound.sampled.AudioFileFormat;
 import javax.sound.sampled.AudioInputStream;
 import javax.sound.sampled.AudioSystem;

 import com.sun.media.sound.WaveFileWriter;
 */

import container.Category;
import container.Segment;
import container.Session;

/**
 * Liest und schreibt XML-Dateien. Exportiert komplette Sessions als WAVE-Datei.
 * Stellt Beat-Templates fuer den Casual-Modus zur Verfuegung
 * 
 * @author Magnus Bruehl, Ulrich Ahrendt
 * 
 */
// TODO: XML-Encoding
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
	 * Exportiert die aktuelle Session als WAVE-File. Die Samplerate betraegt
	 * 44.1KHz, die Bittiefe betraegt 16.
	 */
	public void exportAsWav() {
		try {
			int sampleRate = 44100; // Samples pro Sekunde
			double duration = activeSession.getDuration(); // Sekunden

			// Berechne die Anzahl Frames, die fuer die angegebene Dauer
			// benoetigt wird
			long numFrames = (long) (duration * sampleRate);

			// Erstelle ein wav-file mit dem Namen, der durch den Benutzer
			// spezifiziert wurde
			// TODO: Hier den User-spezifizierten Filename angeben
			WavFile wavFile = WavFile.newWavFile(new File("test.wav"), 2,
					numFrames, 16, sampleRate);

			// Erstelle einen grosszuegigen Buffer von 100 frames
			double[][] buffer = new double[2][100];

			// Loop ueber alle Segmente
			for (int curSeg = 0; curSeg < activeSession.getNumerOfSegments(); curSeg++) {

				// Hole das aktuelle Segment
				Segment activeSegment = activeSession.getSegments().get(curSeg);

				// Berechne die Anzahl Frames, die fuer das aktuelle Segment
				// benoetigt wird
				numFrames = (long) (activeSegment.getDuration() * sampleRate);

				// Initialisiere lokalen Frame-Zaehler
				long frameCounter = 0;

				// Loop, bis alle Frames geschrieben wurden
				while (frameCounter < numFrames) {
					// Bestimme die maximal zu schreibende Anzahl an Frames
					long remaining = numFrames - frameCounter;
					int toWrite = (remaining > 100) ? 100 : (int) remaining;

					// Fuelle den Buffer. Ein Ton pro Stereokanal
					for (int s = 0; s < toWrite; s++, frameCounter++) {
						// Hole die aktuelle Frequenz aus dem aktuellen Segment
						// TODO: Funktion, die die Frequenz ueber einen Zeitraum
						// anpasst, also slowdown oder wakeup
						int freq1 = activeSegment.getBeat().getFreq1_start();
						int freq2 = activeSegment.getBeat().getFreq2_start();

						buffer[0][s] = Math.sin(2.0 * Math.PI * freq1
								* frameCounter / sampleRate);
						buffer[1][s] = Math.sin(2.0 * Math.PI * freq2
								* frameCounter / sampleRate);

					}

					// Schreibe den Buffer
					wavFile.writeFrames(buffer, toWrite);
				}
			}

			// Schliesse das wavFile
			wavFile.close();
		} catch (Exception e) {
			System.err.println(e);
		}

		/*
		 * Ueber AudioStream InputStream inputStream = "musch"; // Hole
		 * Audioinformationen, um einen AudioInputStream zu generieren
		 * AudioInputStream audioInputStream = AudioSystem
		 * .getAudioInputStream(inputStream); // Neue WAVE-Datei im Filesystem
		 * erstellen File fileOut = new File("test.wav"); // Hier muss der Pfad
		 * rein, // den der User spezifiziert WaveFileWriter writer = new
		 * WaveFileWriter(); // Check, ob das Hostsystem ueberhaupt WAVE-Dateien
		 * schreiben kann. if
		 * (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE,
		 * audioInputStream)) { writer.write(audioInputStream,
		 * AudioFileFormat.Type.WAVE, fileOut); }
		 */

	}

}