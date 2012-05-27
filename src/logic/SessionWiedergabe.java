package logic;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import container.Session;



public class SessionWiedergabe implements Runnable{

	private Session				session;
	private AudioFormat			beatFormat;
	private double				posX;			//Zaehlvariable fuer die Berechnung des Tons
	private double				stepWidth;
	private int					sample_max_value;
	private SourceDataLine		beatLine;
	private DataLine.Info		beatInfo;
	private AudioInputStream	audioInputStream;
	
	private Thread	t;
	private boolean pause;

	private final static int BUFFER = 4096;


	public SessionWiedergabe(Session session) {
		this.session = session;
		
		this.posX		= 0;
		this.pause		= false;
		this.beatFormat 	= new AudioFormat(44100, 16, 1, true, false);
		this.stepWidth	= (2 * Math.PI) / beatFormat.getSampleRate();
		this.sample_max_value = (int) Math.pow(2, beatFormat.getSampleSizeInBits()) / 2 - 1;
		
		
		beatInfo = new DataLine.Info(SourceDataLine.class, beatFormat);
        try {
			beatLine	= (SourceDataLine) AudioSystem.getLine(beatInfo);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playSession() {
		t = new Thread(this);
		t.start();
	}
	
	public void continueSession()	{	pause = false; }
	
	public void pauseSession()		{ pause = true; }

	
	@Override
	public void run() {
		try {
			beatLine.open( beatFormat, 2*BUFFER );
		}
		catch (LineUnavailableException e) { e.printStackTrace(); }
		
		beatLine.start();
		
		while(posX < session.getDuration() ) {
			
			synchronized (this) {
				while(pause) {
					try {
						wait(1);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			
			
			byte[] data = new byte[BUFFER];
			int numBytesRead = getStereoTon(data, BUFFER);
			
			if(numBytesRead == 0) break;
			beatLine.write(data, 0, numBytesRead);
		}
	}

	public void stopSession() {
		beatLine.close();
		beatLine.stop();
		t.interrupt();
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

}