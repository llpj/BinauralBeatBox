package logic;

import container.Session;

// Java Sound API
import javax.sound.sampled.*;

/**
 * @author Boris Beck
 * @return This class load and play a Session
 *
 */

public class SessionWiedergabe {
	
	// definiere Variablen
	private int currentFrequenz;
	private Session currentSession;
	private int balance;	
	private static Clip c;
    
    // Testvariablen
    static int i= 0;
	static int j=1;
	
	static AudioFormat playme = new AudioFormat(44100, 16, 2, true, false); // Parameter 1: Samplerate, 2: SampleBits, 3: Kanaele)

    
	public void setSession(Session session) {
		// TODO load session
	}
	
	
	/**
	  * public static void playSession(int freqLinks, int  freqRechts, int freqDauer)
	  * 
	  * @param freqLinks:  linke Frequenz. Wenn 0, dann kein Ton 
	  * @param freqRechts: rechte Frequenz. Wenn 0, dann kein Ton
	  * @param freDsuer: Frequennz Wiederholungen
	  * 
	  * mit Hilfe von c.getFramePosition() ist es moeglich, die aktuelle Zeit zu bestimmen
	  */
	public static void playSession(int freqLinks, int  freqRechts)  {
		i++;
		System.out.print("Funktion aufgerufen: "+i);

        byte[] data = getStereoSinusTone(freqLinks, freqRechts, playme);
        try {
            c = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            c.open(playme, data, 0, data.length);
            c.start();
            System.out.println(" loop: " +j);
            c.loop(Clip.LOOP_CONTINUOUSLY);
            j++;
            while(c.isRunning()) {
                try {
                	System.out.println("spielt "+ c.getFramePosition());
                    //Thread.sleep(50);
                	
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
	public static byte[] getStereoSinusTone(int frequency1, int frequency2, AudioFormat playme) {
        byte sampleSize = (byte) (playme.getSampleSizeInBits() / 8);
        byte[] data = new byte[(int) playme.getSampleRate() * sampleSize  * 2];
        double stepWidth = (2 * Math.PI) / playme.getSampleRate();
        double x = 0;
        for (int i = 0; i < data.length; i += sampleSize * 2) {
            int sample_max_value = (int) Math.pow(2, playme.getSampleSizeInBits()) / 2 - 1;
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
	public void pauseSession() {
		if (c.isRunning()) {
			c.stop();   		
         	c.setFramePosition(c.getFramePosition()); // Session pausieren
		}
		else {
			c.getFramePosition();
			
			if (c.getFramePosition()!=0) {
				c.start();
			}
			else {
				// nix tun
				System.out.println("Pausenbuttontest");
			}
		}
	}
	
	public void stopSession() {
		if (c.isRunning()) {
			c.stop();
		}
         	c.setFramePosition(0); // Session auf den Anfang setzen
	}
	
	private void transition(int endFreq, int beginFreq) {
		// TODO Uebergang zwischen den Segmenten berechnen
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
