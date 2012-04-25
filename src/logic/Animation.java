/**
 * @author Fabian Schï¿½fer
 *
 */
package logic;

import java.awt.Graphics2D;
import javax.swing.JPanel;

public abstract class Animation implements Runnable{
	
	private int [] freq = new int[3];
	protected Thread animation;
	protected Graphics2D animationPnl;
	protected JPanel pnl;
	//TODO animationPnl größe 
	protected int width;
	protected int height;
	
	public int [] getFreq ()
	{
		return freq;
	}
	public void setFreq (int [] freq)
	{
		this.freq = freq;
	}
	
	public Animation(JPanel pnl) {
		this.pnl = pnl;
	}
	
	//Thread Initialisierung
	 public void start () {
		 if(animation == null)
		{
	        animation = new Thread (this);
	        animation.start ();
		}
	}

	
	public abstract void init ();//TODO session 
	public abstract boolean pause (boolean state); //"pause" an Stelle von "break", da Java bereits break benutzt
	public abstract boolean finish (boolean state);
	public abstract void setHandle (Graphics2D animationpnl); //Klasse Graphics aus java.awt.* kï¿½nnte auch verwendet werden

}
