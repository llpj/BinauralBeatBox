package container;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

//import logic.SessionWiedergabe;
import interfaces.mixing.MixingAudioInputStream;
import interfaces.wavFile.WavFile;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javax.sound.sampled.UnsupportedAudioFileException;


import logic.SessionWiedergabe;
import management.FileManager;

public class ContainerTester {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Testing getMood in connection with the enum
		BinauralBeat beat = new BinauralBeat(12, 14);
		System.out.println("Gewaehlte Stimmung: " + beat.getMood());

		/*
		 * SessionWiedergabe.playSession((int) beat.getFreq1_start(), (int)
		 * beat.getFreq2_start(), 1000);
		 */

//		 Session session = new Session();
//		 session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
//		 session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
//		 session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
//		 SessionWiedergabe sw = new SessionWiedergabe(session);
//		 sw.playSession((int) beat.getFreq1_start(),(int)
//			 beat.getFreq2_start());


		
//		  for(int i = 0; i<1000; i++){
//		  SessionWiedergabe.playSession((int)beat.getFreq1_start(),
//		  (int)beat.getFreq2_start()); if(i%1000 == 0)
//		  System.out.println("Spielt..."); } System.out.println("Fertig.");
		 

		// Test Categories
		Segment segment1 = new Segment(10, beat);
		Segment segment2 = new Segment(10, 30, 33);
		Segment slowdown = new Segment(10, 100, 30, 110, 40);

		Session session1 = new Session(
				"Hier koennte Ihr Hintergrundklang stehen", slowdown);
		session1.addSegment(segment1);

		Session session2 = new Session("Hintergrundklang", slowdown);
		session2.addSegment(segment2);

		Category category = new Category("Testkategorie", session1);
		category.addSession(session2);

		System.out.println(category.toString());

		// Test Wav-Export
		Segment steadySegment = new Segment(60, 155, 160);
		Session exportableSession = new Session(steadySegment);

		exportableSession.addSegment(slowdown);
		exportableSession.addSegment(segment1);
		exportableSession.addSegment(segment2);
		// Erwartetes Resultat: eine Wav-Datei mit Laenge 70

		FileManager fm = new FileManager();
		fm.setActiveSession(exportableSession);
		fm.writeCategories();
		System.out.println("Erstelle Wavefile mit Sinustoenen...");
		fm.exportAsWav();
		System.out.println("Wavefile erfolgreich erstellt.");

// XML Test
//		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
//				new FileOutputStream("Test.xml")));
//		e.writeObject(exportableSession);
		
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("Session", Session.class);
		String xml = xstream.toXML(exportableSession);
		
		FileWriter writer;
	    try {
	      writer = new FileWriter("session.xml");
	      writer.write(xml);
	      writer.close();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
		/*
		 * // Teste Hintergrundklang-Pfad Session standardSession = new
		 * Session(); System.out
		 * .println("----------- Hintergrundklang-Information --------------------"
		 * ); readWav(standardSession.getHintergrundklang()); System.out
		 * .println
		 * ("--------- Hintergrundklang-Information-Ende------------------");
		 */

		/*
		 * Mixing Test. Der Hintergrundklang wird mit dem Sinus-Wavefile
		 * zusammengemischt.
		 */
		System.out.println("Hole Informationen fuer Mix...");
		File fileSin = null;
		AudioFormat formatSin;
		AudioInputStream streamSin = null;
		File fileBg = null;
		AudioFormat formatBg = null;
		AudioInputStream streamBg = null;
		SourceDataLine lineSin = null;
		SourceDataLine lineBg;

		// Hole das bereits erstellte Wavefile mit Sinustoenen
		try {
			fileSin = new File("sineonly.wav");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		// Hole den Hintergrundklang
		try {
			fileBg = new File(exportableSession.getHintergrundklang());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		// Setze die Audioformate
		try {
			streamSin = AudioSystem.getAudioInputStream(fileSin);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		formatSin = streamSin.getFormat();

		try {
			streamBg = AudioSystem.getAudioInputStream(fileBg);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		formatBg = streamBg.getFormat();

		// Erstelle die Datalines
		DataLine.Info infoSin = new DataLine.Info(SourceDataLine.class,
				formatSin);
		try {
			lineSin = (SourceDataLine) AudioSystem.getLine(infoSin);
			lineSin.open(formatSin);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		DataLine.Info infoBg = new DataLine.Info(SourceDataLine.class, formatBg);
		try {
			lineBg = (SourceDataLine) AudioSystem.getLine(infoBg);
			lineBg.open(formatBg);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		// Schreibe die AudioInputStreams in eine ArrayList
		ArrayList<AudioInputStream> dlList = new ArrayList<AudioInputStream>();
		dlList.add(streamSin);
		dlList.add(streamBg);

		// Endlich! Endlich mix das Zeug!
		System.out.println("Mixe die beiden Audiostreams...");
		AudioInputStream mixed = new MixingAudioInputStream(formatSin, dlList);
		
		// Schreib den Mix
		System.out.println("Erstelle das gemixte File...");
		File outputFile = new File("mixed.wav");
		try {
			AudioSystem.write(mixed, AudioFileFormat.Type.WAVE, outputFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Wave-File erfolgreich erstellt.");
	}

	public static void readWav(String path) {
		try {
			// Open the wav file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(new File(path));

			// Display information about the wav file
			wavFile.display();

			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;

			do {
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				// Loop through frames and look for minimum and maximum value
				for (int s = 0; s < framesRead * numChannels; s++) {
					if (buffer[s] > max)
						max = buffer[s];
					if (buffer[s] < min)
						min = buffer[s];
				}
			} while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			// Output the minimum and maximum value
			System.out.printf("Min: %f, Max: %f\n", min, max);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
