/**
 * @author Fabian Schaefer
 *	Oberklasse der Animationen
 */

package logic;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class Animation implements Runnable{
	
	//Grafikattribute
	private int [] freq = new int[3];
	protected Thread animation;
	protected Graphics2D animationPnl;
	protected JPanel pnl;
	protected int width;
	protected int height;
	//Zur Abstimmung der Animationsgeschwindigkeit bei Resizing Window
	protected int checkResize;
	//Animationsattribute
	protected boolean pause;
	protected int tempo;

	
	//Konstruktor
	public Animation(JPanel pnl) {
		this.pnl = pnl;
		pause = false;
	}
	
	//Thread Initialisierung
	 public void start () {
		 if(animation == null)
		{
	        animation = new Thread (this);
	        animation.start ();
		}
	}
	 
	//AnimationPnl size
	public void setSize(Dimension size)
	{
		width = size.width;
		height = size.height;
	}
	
	//Zur Ueberpruefung, ob das AnimationPnl max- bzw. minimiert wurde 
	public void setCheckResize(int checkResize) {
		this.checkResize = checkResize;
	}
	public int getCheckResize() {
		return checkResize;
	}
	
	//getter & setter
	public boolean getPause()
	{
		return pause;
	}
	public int [] getFreq ()
	{
		return freq;
	}
	public void setFreq (int [] freq)
	{
		this.freq = freq;
	}

	//abstrakte Methoden
	public abstract void init ();
	public abstract void pause (boolean state); //"pause" an Stelle von "break", da Java bereits break benutzt
	public abstract boolean finish (boolean state);
	// Die Methode setHandle wurde durch direkte Zuweisung bei Objekt Instanzierung ersetzt
}
