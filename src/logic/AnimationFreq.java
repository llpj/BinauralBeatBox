/**
 * @author Fabian Schäfer
 *
 */

package logic;
import container.Session;
import gui.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class AnimationFreq extends Animation {
/**
 * Klassenname wurde geändert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
	private String [] [] color = new  String [3][3];
	private double [][] values; //Koordinaten auf der Sinuskurve
	private int count;   //Anzahl der Bildpunkte
	
	public AnimationFreq ()
	{
		
	}
	
	public AnimationFreq (int [] freq, int count )//TODO Session session
	{
		super.setFreq(freq);
		//rot
		this.color [0][0] = "ff";
		this.color [0][1] = "00";
		this.color [0][2] = "00"; 
		//grün
		this.color [1][0] = "00";
		this.color [1][1] = "ff"; 
		this.color [1][2] = "00"; 
		//blau
		this.color [2][0] = "00"; 
		this.color [2][1] = "00";
		this.color [2][2] = "ff"; 
		//Sinus-Werte
		this.count = count;
		this.values = new double [count][3];
		//Initialisierung
		init();//TODO session
	
		
	}
	
	protected void sin (Graphics2D animationPnl)
	{	 
		// TODO in: freq
		int h = 0;
		for( int j = 0; j < 3; j++)
		{
			for( double i = 0; i < count; i++ )
			{
				values[j][(int) i] =  Math.sin( 2*Math.PI*i/count )*super.getFreq()[j] ;
				int sin = (int) values[j][(int) i];
				animationPnl.setColor(new Color(sin, sin, sin));
				animationPnl.drawRect((int) i, 0, 1, 200);
				animationPnl.setColor(new Color(0,0,0));
				animationPnl.drawRect((int)i, sin+265, 1, 1);
					
			}
		}
	}
	
	
	//Vererbte Methoden
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
		setHandle((Graphics2D)mf.getGraphicsForVirtualization()); //Felix fragen...wie er sich setHandle vorgestellt hat
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
		sin(animationpnl);
	}
//getter & setter
	public String [][] getColor ()
	{
		return color;
	}
	
	public void setColor (String [][] color)
	{
		this.color = color;
	}
	
	public void setValues(double [][] values) {
		this.values = values;
	}

	public double [][] getValues() {
		return values;
	}

	
}
