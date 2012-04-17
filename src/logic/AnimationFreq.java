package logic;

import java.awt.Graphics2D;

import container.Session;

class AnimationFreq extends Animation {
/**
 * Klassenname wurde geändert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
	private String [] color = new  String [3];
	
	public AnimationFreq ()
	{
		
	}
	
	public AnimationFreq (int [] freq)
	{
		super.setFreq(freq);
		this.color [1] = "blue";
		this.color [2] = "red";
		this.color [3] = "green";
		
	}
	
	public String [] getColor ()
	{
		return color;
	}
	
	public void setColor (String [] color)
	{
		this.color = color;
	}
	
	protected void sin ()
	{
		// TODO in: freq + color
	}
	
	
	//Vererbte Methoden
	@Override
	public void init (Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pause () {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finish () {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHandle (Graphics2D animationpnl) {
		// TODO Auto-generated method stub

	}

}
