package logic;

import java.lang.reflect.Array;

import container.BinauralBeat;
import container.Segment;
import container.Session;

// Java Sound API
import javax.sound.sampled.*;

/**
 * @author Boris Beck
 * @return This class load and play a Session
 *
 */

public class SessionWiedergabe {
	
	private Session session;
	private static long cuDuration = 0;
	private int balance;	
	private static Clip c;
	private static AudioFormat playme;
	private byte sampleSize;
	private byte[] totalBeat;
    
    // Testvariablen
    static int i= 0;

    
	public SessionWiedergabe(Session session) {
		this.session = session;
		
        playme = new AudioFormat(44100, 16, 2, true, true); // Parameter 1: Samplerate, 2: SampleBits, 3: Kanaele)
		sampleSize = (byte) (playme.getSampleSizeInBits() / 8);
		
		totalBeat = new byte[(int) playme.getSampleRate() * sampleSize  * session.getDuration()];
		
		transition();
	}
	
	
	/**
	  * public static void playSession(int freqLinks, int  freqRechts, int freqDauer)
	  * 
	  * @param	static int j=1; freqLinks:  linke Frequenz. Wenn 0, dann kein Ton 
	  * @param freqRechts: rechte Frequenz. Wenn 0, dann kein Ton
	  * @param freDsuer: Frequennz Wiederholungen
	  * 
	  * mit Hilfe von c.getMicrosecondPosition() ist es moeglich, die aktuelle Zeit zu bestimmen
	  */
	public static void playSession(int freqLinks, int  freqRechts) {
		i++;
		System.out.println("Funktion aufgerufen: "+i);

        byte[] data = getStereoSinusTone(freqLinks, freqRechts, playme,10);
        
        try {
            c = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            c.open(playme, data, 0, data.length);
            c.start();
            //c.loop(Clip.LOOP_CONTINUOUSLY);
            c.loop(1);
            while(c.isRunning()) {
                try {
                	System.out.println("spielt "+ c.getMicrosecondPosition());
                    // Thread.sleep(50);
                	
                } 
                catch (Exception ex) {}
            }
        }
        catch (LineUnavailableException ex) {
        	ex.printStackTrace();         
        }
        System.out.println("Ende...");
    }
	
	//Berechnung, um die Frequenzen auf die Boxen zu verteilen
	private static byte[] getStereoSinusTone(int frequency1, int frequency2, AudioFormat playme, int duration) {
		byte sampleSize = (byte) (playme.getSampleSizeInBits() / 8);
        byte[] data = new byte[(int) playme.getSampleRate() * sampleSize  * duration];
        double stepWidth = (2 * Math.PI) / playme.getSampleRate();
        int sample_max_value = (int) Math.pow(2, playme.getSampleSizeInBits()) / 2 - 1;
        double x = 0;
        
    	for (int i = 0; i < data.length; i += sampleSize * 2) {
            
            int value = (int) (sample_max_value * Math.sin(frequency1 * x));
            for (int j = 0; j < sampleSize; j++) {
                byte sampleByte = (byte) ((value >> (8 * j)) & 0xff);
                data[i + j] = sampleByte;
            }
            
            value = (int) (sample_max_value * Math.sin(frequency2 * x));
            for (int j = 0; j < sampleSize; j++) {
                byte sampleByte = (byte) ((value >> (8 * j)) & 0xff);
                int index = i + j + sampleSize;
                data[index] = sampleByte;
            }
            
            x += stepWidth;
        }
        return data;
    }		   

	
	/**
	  *	pauseSession()
	  *
	  *	Session Pausieren. Nur moeglich, wenn die Session schon einmal abgespielt worden ist, d.h. wenn die aktuelle Zeit bei 0
	  * steht, dann soll der Pause Knopf nix tun		
	  * 
	  */
	public static void pauseSession() {
		System.out.println("Pausenbuttontest");
		cuDuration = c.getMicrosecondPosition();
		System.out.println("Aktuelle Position: "+cuDuration);
		if (c.isRunning()) {
			c.stop();
		} else {
			System.out.println("Pause Fehler: Clip c wurde nicht abgespielt.");
		}		
	}
	
	/**
	 * public static void continueSession()
	 * 
	 * setzt die Session Wiedergabe da fort, wo pause gedrueckt worden ist
	 */
	
	public static long getCuDuration() {
		return cuDuration;
	}
	
	public static void continueSession() {
		System.out.println("Continuebuttontest");
		c.setMicrosecondPosition(cuDuration);
		System.out.println("Continue at: "+cuDuration);
		c.start();
	}	
	
	public static void stopSession() {
		System.out.println("Stopbuttontest");
		if (c.isRunning()) {
			c.stop();
			cuDuration=0; // Session auf den Anfang setzen
		}  	
	}
	
	/**
	 * 
	 */
	private void transition() {
		int destPos = 0;

		for( Segment s : session.getSegments() ) {
			BinauralBeat b = s.getBeat();
			byte[] data = getStereoSinusTone(b.getFreq1_start(), b.getFreq2_start(), playme, s.getDuration());
			System.arraycopy(data, 0, totalBeat, destPos, data.length);
			destPos += data.length;
		}
	}
	
	
	/**
	 * 
	 * private void changeVolumn(int volumn)
	 * 
	 * @param volumn: floet, Wert um wieviel die Lautstärke veringert (Negativer Wert) oder erhöht werden soll)
	 * zB -10.0f veringert die Lautsätke um -10 Decibel
	 */
	private void changeVolumn(float volumn) {
		FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volumn); //  veringert die Lautsärke um 10 Decibel
	}
	
	private void changeBalance(int balance) {
		this.balance = balance;
	}

}
