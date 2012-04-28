package logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
	private long cuDuration = 0;
	private int balance;
	private AudioFormat playme;
	private byte sampleSize;
	private byte[] totalBeat;
	private File file;
	private Clip clip1 = null;
	private Clip clip2 = null;
	private AudioInputStream ais = null;
    private AudioInputStream ais2 = null;
    private SourceDataLine sourceDataLine;
    
    // Testvariablen
    static int i= 0;

    
	public SessionWiedergabe(Session session) {
		this.session = session;
		
		AudioFormat playme = getAudioFormat();
		byte sampleSize = (byte) (playme.getSampleSizeInBits() / 8);
		
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
	  * 
	  */
	public void playSession(int freqLinks, int  freqRechts) {
		i++;
		System.out.println("Funktion aufgerufen: "+i);
		System.out.println("Links: "+freqLinks+ " Rechts: "+freqRechts);
		file = new File("./src/resources/wav/amsel.wav");
		AudioFormat playme = getAudioFormat();
		byte[] data = getStereoSinusTone(freqLinks, freqRechts, playme);
		
		
		// Hintergrundmusik (clip1)
		try {
			clip1 = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(file);
			clip1.open(ais);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Frequenzton (clip2)
		try {
		clip2 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			clip2.open(playme, data, 0, data.length);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
		// Abspielen (clip1 + clip2)
	        
        // Dauerschleife
        clip1.loop(-1);
        clip2.loop(-1);
        while(clip2.isRunning()) {
            try {
            	System.out.println("clip2 spielt "+ clip2.getMicrosecondPosition());
                // Thread.sleep(50);
            	
            } 
            catch (Exception ex) {}
        }

             
        // -------------- IDEE --------------- 
        // http://www.java-forum.org/allgemeine-java-themen/14532-mehrere-audioclips-gleichzeitig-wiedergeben.html
       

        System.out.println("Ende...");
    }
	
	
	//Berechnung, um die Frequenzen auf die Boxen zu verteilen
//	public byte[] getStereoSinusTone(int frequency1, int frequency2, AudioFormat playme, int duration) {
//        byte[] data = new byte[(int) playme.getSampleRate() * sampleSize  * duration];
//        double stepWidth = (2 * Math.PI) / playme.getSampleRate();
//        int sample_max_value = (int) Math.pow(2, playme.getSampleSizeInBits()) / 2 - 1;
//        double x = 0;
//        
//    	for (int i = 0; i < data.length; i += sampleSize * 2) {
//            
//            int value = (int) (sample_max_value * Math.sin(frequency1 * x));
//            for (int j = 0; j < sampleSize; j++) {
//                byte sampleByte = (byte) ((value >> (8 * j)) & 0xff);
//                data[i + j] = sampleByte;
//            }
//            
//            value = (int) (sample_max_value * Math.sin(frequency2 * x));
//            for (int j = 0; j < sampleSize; j++) {
//                byte sampleByte = (byte) ((value >> (8 * j)) & 0xff);
//                int index = i + j + sampleSize;
//                data[index] = sampleByte;
//            }
//            
//            
//            x += stepWidth;
//        }
//        return data;
//    }	
	
    public byte[] getStereoSinusTone(int frequency1, int frequency2, AudioFormat af) {
        byte sampleSize = (byte) (af.getSampleSizeInBits() / 8);
        byte[] data = new byte[(int) af.getSampleRate() * sampleSize  * 2];
        double stepWidth = (2 * Math.PI) / af.getSampleRate();
        double x = 0;
        for (int i = 0; i < data.length; i += sampleSize * 2) {
            
        	int sample_max_value = (int) Math.pow(2, af.getSampleSizeInBits()) / 2 - 1;
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
    
    // Audioformat bestimmen (Durch eigene funktion werden Fehler behoben :)
    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        //return new AudioFormat( AudioFormat.Encoding.PCM_SIGNED, 8000.0f, 8, 1, 1,
        //8000.0f, false );

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

	
	/**
	  *	pauseSession()
	  *
	  *	Session Pausieren. Nur moeglich, wenn die Session schon einmal abgespielt worden ist, d.h. wenn die aktuelle Zeit bei 0
	  * steht, dann soll der Pause Knopf nix tun		
	  * 
	  */
	public void pauseSession() {
		System.out.println("Pausenbuttontest");
		cuDuration = clip1.getMicrosecondPosition();
		System.out.println("Aktuelle Position: "+cuDuration);
		if (clip1.isRunning()) {
			clip1.stop();
		} else {
			System.out.println("Pause Fehler: Clip c wurde nicht abgespielt.");
		}		
	}
	
	/**
	 * public static void continueSession()
	 * 
	 * setzt die Session Wiedergabe da fort, wo pause gedrueckt worden ist
	 */
	
	public long getCuDuration() {
		return cuDuration;
	}
	
	public void continueSession() {
		System.out.println("Continuebuttontest");
		clip1.setMicrosecondPosition(cuDuration);
		System.out.println("Continue at: "+cuDuration);
		clip1.start();
	}	
	
	public void stopSession() {
		System.out.println("Stopbuttontest");
		if (clip1.isRunning()) {
			clip1.stop();
			clip2.stop();
			clip1.close();
			clip2.close();
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
			//byte[] data = getStereoSinusTone(b.getFreq1_start(), b.getFreq2_start(), playme, s.getDuration());
			//System.arraycopy(data, 0, totalBeat, destPos, data.length);
			//destPos += data.length;
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
		FloatControl gainControl = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volumn); //  veringert die Lautsärke um 10 Decibel
	}
	
	private void changeBalance(int balance) {
		this.balance = balance;
	}

}
