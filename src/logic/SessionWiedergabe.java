package logic;

import container.Session;

// Java Sound API
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFormat;

/**
 * @author Boris Beck
 * @return This class load and play a Session
 *
 */

public class SessionWiedergabe {
	
	// definiere Variablen
	private int currentDuration = 0;
	private int currentFrequenz;
	private Session currentSession;
	private int volumn;
	private int balance;
	
	public void setSession(Session session) {
		// TODO load session
	}
	
	
	/**
	  * public static void playSession(int freqLinks, int  freqRechts, int freqDauer)
	  * 
	  * @param freqLinks:  linke Frequenz. Wenn 0, dann kein Ton 
	  * @param freqRechts: rechte Frequenz. Wenn 0, dann kein Ton
	  * @param freDsuer: Frequennz Wiederholungen
	  */
	public static void playSession(int freqLinks, int  freqRechts, int freqDauer)  {
        AudioFormat playme = new AudioFormat(44100, 16, 2, true, false); // Parameter 1: Samplerate, 2: SampleBits, 3: Kanaele)
        byte[] data = getStereoSinusTone(freqLinks, freqRechts, playme);
        try {
            Clip c = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            c.open(playme, data, 0, data.length);
            c.loop(freqDauer);
            while(c.isRunning()) {
                try {
                    Thread.sleep(50);
                	
                } catch (Exception ex) {}
            }
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
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
	
	
	public void pauseSession() {
		// TODO pause
	}
	
	public void stopSession() {
		// TODO stop Session
	}
	
	public int getCurrentDuration() {
		//würde ich in der Session abfragen lassen
		return currentDuration;
	}
	
	private void transition(int endFreq, int beginFeq) {
		// TODO Uebergang zwischen den Segmenten berechnen
	}
	
	private void changeVolumn(int volumn) {
		this.volumn = volumn;
	}
	
	private void changeBalance(int balance) {
		this.balance = balance;
	}

}
