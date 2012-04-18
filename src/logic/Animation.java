/**
 * @author Fabian Schäfer
 *
 */
package logic;

import java.awt.Graphics2D;

import container.Session;

abstract class Animation {
	
	private int [] freq = new int[3];
	
	public int [] getFreq ()
	{
		return freq;
	}
	public void setFreq (int [] freq)
	{
		this.freq = freq;
	}
	
	public abstract void init (Session session);
	public abstract boolean pause (); //"pause" an Stelle von "break", da Java bereits break benutzt
	public abstract boolean finish ();
	public abstract void setHandle (Graphics2D animationpnl); //Klasse Graphics aus java.awt.* könnte auch verwendet werden


}
