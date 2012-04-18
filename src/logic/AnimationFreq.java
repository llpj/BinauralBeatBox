/**
 * @author Fabian Schäfer
 *
 */

package logic;

import java.awt.Graphics2D;

import container.Session;

class AnimationFreq extends Animation {
/**
 * Klassenname wurde geändert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
//	private String [] [] color = new  String [3][3];
	private double [][] values; //Koordinaten auf der Sinuskurve
	private int count;   //Anzahl der Bildpunkte
	
	public AnimationFreq ()
	{
		
	}
	
	public AnimationFreq (int [] freq, int count)
	{
		super.setFreq(freq);
		this.count = count;
		this.values = new double [count][3];
//		this.color [1][] = {"ff", "00", "00"}; //rot
//		this.color [2][] = {"00", "ff", "00"}; //grün
//		this.color [3][] = {"00", "00", "ff"}; //blau
		
	}
	
//	public String [] getColor ()
//	{
//		return color;
//	}
	
//	public void setColor (String [] color)
//	{
//		this.color = color;
//	}
	
	public void setValues(double [][] values) {
		this.values = values;
	}

	public double [][] getValues() {
		return values;
	}

	protected void sin ()
	{
		// TODO in: freq
		for( int j = 0; j < 3; j++)
		{
			for( int i = 0; i < count; i++ )
			{
				values[j][i] =  Math.sin( 2*Math.PI*i/count )+ super.getFreq()[j] ;
			}
		}
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
