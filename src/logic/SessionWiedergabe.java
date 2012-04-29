package logic;


import java.io.File;
import java.io.IOException;

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
	
	private Session 			session;
	private long 				cuDuration = 0;
	float 						balc1;
	float						balc2;
	private AudioFormat 		playme;
	private byte 				sampleSize;
	private byte[] 				totalBeat;
	private File 				file;
	private Clip 				clip1 = null;
	private Clip 				clip2 = null;
	private AudioInputStream 	ais = null;
    private AudioInputStream 	ais2 = null;
    private SourceDataLine 		sourceDataLine;
	private SessionWiedergabe	sw;
	private byte[]			 	data;
    
    // Testvariablen
    static int i= 0;

    
	public SessionWiedergabe(Session session) {
		this.session = session;
		
		AudioFormat playme = getAudioFormat();
		//byte sampleSize = (byte) (playme.getSampleSizeInBits() / 8);
		
		//totalBeat = new byte[(int) playme.getSampleRate() * sampleSize  * session.getDuration()];
		//transition();
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
	public void playSession() {
		i++;
		System.out.println("Funktion aufgerufen: "+i);
		
		// Teste ob Session ergolgreich geladne wurde
		try{
			if(session!=null){
				int duration = session.getDuration();
				System.out.println("Ausgewaehlte Session in den PLayer geladen");
			}else System.err.println("SessionWiedergabe: Keine Session vorhanden.");
		}catch(Exception e){
			System.err.println(e.getStackTrace());
		}

		AudioFormat playme = getAudioFormat();
		
		// // Hintergrundmusik (clip1)
		file = new File(session.getHintergrundklang());
		
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
		
        clip1.loop(-1);
		
		// Frequenzton (clip2)
		
		try {
			clip2 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		// Loop ueber alle Segmente
		for (int curSeg = 0; curSeg < session.getNumerOfSegments(); curSeg++) {

			// Hole das aktuelle Segment
			Segment activeSegment = session.getSegments().get(curSeg);
			int freq1 = activeSegment.getBeat().getFreq1_start();
			int freq2 = activeSegment.getBeat().getFreq2_start();
			
			System.out.println("Links: "+freq1+ " Rechts: "+freq2);
			
			// generiere Stereo Frequenz
			byte[] data = getStereoSinusTone(freq1, freq2, playme, 10);			
			
			try {	
				clip2.open(playme, data, 0, data.length);
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			System.out.println(session.getDuration());
			
			clip2.loop(activeSegment.getDuration());
			//clip2.loop(5);

			// Warteschleife
			long j = 10000000;
			for(int i = 0; i < j ; i++) {
				System.out.println(i);
			}
			

			clip2.stop();
			clip2.close();

			System.out.println("Loop Ende");
			
			// session.getSegments() gibt ne arraylist zurück
			// und mit session.getSegments().get(segmentNumber) kriegst du ein segment
			
		}
		
		// Balance geandert (damit clip1 leiser ist als Clip2)
		FloatControl gainControl = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-20); //  veringert / erhoeht die Lautsärke um x Decibel


        System.out.println("Ende...");
    }

	//Berechnung, um die Frequenzen auf die Boxen zu verteilen
    public byte[] getStereoSinusTone(int frequency1, int frequency2, AudioFormat af, double duration) {
        byte sampleSize = (byte) (af.getSampleSizeInBits() / 8);
        byte[] data = new byte[(int) ((int) af.getSampleRate() * sampleSize  * duration)];
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
			clip2.stop();
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
		clip2.setMicrosecondPosition(cuDuration);
		System.out.println("Continue at: "+cuDuration);
		clip1.start();
		clip2.start();
	}	
	
	public void stopSession() {
		System.out.println("Stopbuttontest");
		if (clip1.isRunning()) {
			clip1.stop();
			clip2.stop();
			clip1.close();
			clip2.close();
			cuDuration=0; // Session auf den Anfang setzen
		} else {
			System.out.println("Stop Fehler: Clip wurde nicht abgespielt.");
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
		gainControl.setValue(volumn); //  veringert / erhoeht die Lautsärke um x Decibel
	}
	
	
	private void changeBalance(float balance, boolean c1c2) { //c1c2 bedeutet, wenn true dann setzen lautsärke von Clip1 um - balance/2 und clip2 um + balance/2, und andersrum	
		float hier = balance /2;		
		FloatControl gainControl1 = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
		FloatControl gainControl2 = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
				
		if (c1c2) {
			balc1 = balc1 - hier;
			balc2 = balc2 + hier;	
		} else {
			balc1 = balc1 + hier;
			balc2 = balc2 - hier;
		}

		gainControl1.setValue(balc1);
		gainControl2.setValue(balc2);
	}
}