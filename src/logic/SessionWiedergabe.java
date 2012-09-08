package logic;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import container.Session;
import gui.playerGui.PlayerPanel;
import interfaces.Mood;
import management.BinauralBeatBox;


/**
 * @author Boris Beck and Felix Pistorius
 * @return This class load and play a Session
 * 
 */


public class SessionWiedergabe implements Runnable{

	private Session				session;
	private PlayerPanel 		playerPnl;
	private AudioFormat			beatFormat;
	private double				posX;			//Zaehlvariable fuer die Berechnung des Tons
	private double				stepWidth;
	private int					sample_max_value;
	private SourceDataLine		beatLine;
	private DataLine.Info		beatInfo;
	private AudioInputStream	ais = null;
	private Clip				clip;
	private long 				clipDuration = 0;
	private File				fileBg;
	public int[]				tmpFreq = {0,0,0};
	public int[]				curFreq = {0,0,0};
	float 						balc1, balc2;
	int FreqLeft, FreqRight, FreqBeat = 0;
	double volumn;
	float dB;
	Mood curMood;
	
	private Thread	t;
	private boolean pause;
	private boolean animation; //true, wenn SessionWiedergabe mit Animation zusammenarbeitet

	private final static int BUFFER = 4096;

	public SessionWiedergabe(Session session) {
		this(session, true);
	}

	public SessionWiedergabe(Session session, boolean animation) {
		this.session = session;
		this.animation	= animation;
		
		this.posX				= 0;
		this.pause				= false;
		this.beatFormat 		= new AudioFormat(44100, 16, 1, true, false);
		this.stepWidth			= (2 * Math.PI) / beatFormat.getSampleRate();
		this.sample_max_value 	= (int) Math.pow(2, beatFormat.getSampleSizeInBits()) / 2 - 1;
		this.fileBg				= new File(session.getHintergrundklang());
		
		if (!fileBg.exists()) {
			System.out.println("File not found: " + fileBg);
		 	return;
		}
		
		beatInfo = new DataLine.Info(SourceDataLine.class, beatFormat);
        try {
			beatLine	= (SourceDataLine) AudioSystem.getLine(beatInfo);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playSession() {
		t = new Thread(this);
		
		try {
			if (session != null) {
				System.out.println("Ausgewaehlte Session in den Player geladen");
			} else
				System.err.println("SessionWiedergabe: Keine Session vorhanden.");
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		
		//Hintergrundmusik (clip)
		try {
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(fileBg);
			clip.open(ais);
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
		
		clip.loop(-1);		
		t.start();
	}
	
	/**
	 * public void continueSession()
	 * 
	 * setzt die Session Wiedergabe da fort, wo pause gedrueckt worden ist
	 */
	public void continueSession()	{
		System.out.println("Continuebuttontest");
		pause = false; 
		clip.setMicrosecondPosition(clipDuration);
		System.out.println("Continue at: " + clipDuration);
		beatLine.start();
		clip.start();
		
	}
	
	public long getClipDuration() {
		return clipDuration;
	}
	
	
	/**
	 * public void pauseSession()
	 * 
	 * Session Pausieren. Nur moeglich, wenn die Session schon einmal abgespielt
	 * worden ist, d.h. wenn die aktuelle Zeit bei 0 steht, dann soll der Pause
	 * Knopf nix tun
	 * 
	 */
	public void pauseSession() {
		pause = true;
		System.out.println("Pausenbuttontest");
		clipDuration = clip.getMicrosecondPosition();
		System.out.println("Aktuelle Position: " + clipDuration);
		
		if (clip.isRunning()) {
			beatLine.stop();
			clip.stop();
		}
	}
	
	public void stopSession(boolean click) {
		System.out.println("Stopbuttontest");
		
		if (click) {
			playerPnl.setValueOfTimBar(0);
			System.out.println("Click");
		} else {
			playerPnl.setValueOfTimBar(session.getDuration());
		}
		
		
		if (beatLine.isOpen()) {
			beatLine.stop();
			beatLine.close();
		}
		if (clip.isOpen()) {
			clip.stop();
			clip.close();
			clipDuration = 0; // Session auf den Anfang setzen
		}
		t.interrupt();
	}

	
	@Override
	public void run() {
		try {
			beatLine.open( beatFormat, 2*BUFFER );
		}
		catch (LineUnavailableException e) { e.printStackTrace(); }
	
		beatLine.start();
		playerPnl.setMaximumofTimeBar(session.getDuration());
		
		while(posX < session.getDuration() ) {						
			synchronized (this) {
				while(pause) {
					try {
						wait(1);
						// TODO fixen, dass wenn Pause gedrückt ist, dann stop, kein Interrupted Exception kommt.
					} catch (InterruptedException e) { }
				}
			}
			
			playerPnl.setValueOfTimBar((int) posX);
			
			if(animation) {
				if (checkFrequence()==true) {
					System.out.println("FREQUENZAENDERUNG");
					BinauralBeatBox.animationUpdateFreq(getCurFreq(), getCurMood());
				}
			}
			
			byte[] data = new byte[BUFFER];
			int numBytesRead = getStereoTon(data, BUFFER);
			
			if(numBytesRead == 0) {
				stopSession(false);
				break;
			}
			beatLine.write(data, 0, numBytesRead);
			
		}
		stopSession(false);
		
	}
	
	// Get Current Time
	public double getCurrentTime() {
		return posX;
	}
	
	public void setCurrentTime(double posNew) { //TODO glaube so geht das nicht :(
		posX=posNew;
	}
	
	// Get complete Session Time
	public int getCompleteDuration() {
		return session.getDuration();
	}
	
	private int getStereoTon(byte[] data, int buffer) {
		int x = 0;
		
		while ( (x < (int)(buffer/4)) && !(posX >= session.getDuration()) ) {
			byte[] l = getTone( session.getFreqAt(posX, true) );
			data[x + 0] = l[0];
			data[x + 1] = l[1];
			
			byte[] r = getTone( session.getFreqAt(posX, false) );
			data[x + 2] = r[0];
			data[x + 3] = r[1];
			
			x += 4;
			posX += stepWidth;
		}

		return x;
	}
	
	private byte[] getTone(double freq) {
		byte[] data = new byte[2];
		int value = (int) (sample_max_value * Math.sin(freq * posX));
		
		//zerlege value (int) in 2 bytes
		data[0]		= (byte)  (value & 0xff);
		data[1]		= (byte) ((value >> 8) & 0xff);
		
		return data;
	}
	
	/**
	 * public getCurFreq
	 * 
	 * gibt die Aktuelle Frequenz in einem int Array zurück
	 * 
	 * @return int[]
	 */
	
	public int[] getCurFreq() {
		
		FreqLeft = (int) session.getFreqAt(posX, true) / 10;
	    FreqRight = (int) session.getFreqAt(posX, false) / 10;
	    
	    if (FreqLeft <= FreqRight) {
	    	FreqBeat =  (int) session.getFreqAt(posX, false) - (int) session.getFreqAt(posX, true);
	    } else {
	    	FreqBeat =  (int) session.getFreqAt(posX, true) - (int) session.getFreqAt(posX, false);
	    }   
	    int [] freq  = { FreqLeft, FreqBeat, FreqRight };
		//System.out.println("Frequenz: " + freq[0] + "   " + freq[1] + "   " + freq[2]);
	    return freq;
	}
	
	/**
	 * Checkt die Frequent auf Aenderungen
	 * false:	keine Aenderung
	 * true:	Frequenzaenderung
	 * 
	 * @return boolean
	 */
	public boolean checkFrequence(){	
		curFreq=getCurFreq();
		if (tmpFreq[0]==curFreq[0] && tmpFreq[1]==curFreq[1] && tmpFreq[2]==curFreq[2]) {
			return false;
		}
		else {
			tmpFreq=curFreq;
			return true;
		}
	}
	
	/**
	 * public getCurMood
	 * 
	 * gibt den aktuelle Mood von der Session wieder
	 * 
	 * @return Mood
	 */
	public Mood getCurMood() {
		curMood = session.getSegments().get(0).getBeat().getMood();
		System.out.println("Current Mood: " + curMood);
		return curMood;
	}
	
	
	/**
	 * 
	 * private void changeVolumn(int volumn)
	 * 
	 * @param volumn
	 *            : float, Wert um wieviel die Lautstärke veringert (Negativer
	 *            Wert) oder erhöht werden soll) zB -10.0f veringert die
	 *            Lautsätke um -10 Decibel
	 */
	public void changeVolumn(float controll) {
		volumn = controll / 100; 
		dB = (float) (Math.log(volumn) / Math.log(10.0) * 20.0);
		FloatControl gainControl1 = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		FloatControl gainControl2 = (FloatControl) beatLine.getControl(FloatControl.Type.MASTER_GAIN);
		
		gainControl1.setValue(dB); // veringert / erhoeht die Lautsärke um x // Decibel
		gainControl2.setValue(dB); // veringert / erhoeht die Lautsärke um x // Decibel
	}
	
	public void changeBalance(float balance, boolean clipBeatLine) { //clipBeatLine bedeutet, wenn true dann veringere lautsärke von Clip  (und andersrum)
		volumn = balance / 100; 
		dB = (float) (Math.log(volumn) / Math.log(10.0) * 20.0);

		FloatControl gainControl1 = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		//FloatControl gainControl2 = (FloatControl) beatLine.getControl(FloatControl.Type.MASTER_GAIN);
		System.out.println("dB :" + -dB);		
		
//		if (clipBeatLine) {
//			gainControl2.setValue(-dB);
//			gainControl1.setValue(dB);
//		} else {	
//			gainControl2.setValue(dB);
//			gainControl1.setValue(-dB);
//		}
		gainControl1.setValue(dB);
		
		

		
	}
	
	public void setPlayerPanel(PlayerPanel pp) {
		playerPnl = pp;
	}

}