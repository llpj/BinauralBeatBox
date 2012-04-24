/**
 * @author Fabian Sch�fer
 *
 */
package logic;

import java.awt.Graphics2D;
import javax.swing.JPanel;

public abstract class Animation implements Runnable{
	
	private int [] freq = new int[3];
	
	protected JPanel pnl;
	
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
	public abstract void init ();//TODO session 
	public abstract boolean pause (boolean state); //"pause" an Stelle von "break", da Java bereits break benutzt
	public abstract boolean finish (boolean state);
	public abstract void setHandle (Graphics2D animationpnl); //Klasse Graphics aus java.awt.* k�nnte auch verwendet werden

}
