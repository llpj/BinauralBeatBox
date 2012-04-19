/**
 * @author Fabian Schäfer
 *
 */

package logic;
import container.Session;
import gui.*;
import java.applet.*;
import java.awt.*;

import javax.swing.JPanel;

public class AnimationFreq extends Animation {
/**
 * Klassenname wurde geändert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
	Color colors[] = { Color.RED, Color.GREEN,Color.BLUE}; // ersetzt das Attribut String color, da weniger code und einfacheres Handle
//	private double [][] values; 
//	private int count;   //Anzahl der Bildpunkte
	private Thread animation;
	private Graphics2D animationPnl;
	//Koordinaten auf der Sinuskurve
	int[] x = new int[720];
	int[] y = new int[720];
	   
	public AnimationFreq ()
	{
		
	}
	
	public AnimationFreq (int [] freq)//TODO Session session
	{
		super.setFreq(freq);
		//Initialisierung
		init();//TODO session
	
		
	}
	
	protected void sin ()
	{	 
		// TODO in: freq
		
		for( int j = 0; j < 3; j++)
		{
			for( int i = 0; i < y.length; i++ )
			{
				y[i]=(100-(int)Math.round(Math.sin(Math.toRadians(i))*super.getFreq()[j]));
				x[i]=i;
				if(j == 0)y[i]-=20;
				else if (j==1) y[i]+=30;
				else y[i]+=80;
				
				//animationPnl.draw();
				//animationPnl. (colors[j]);
				//animationPnl.drawLine(0,sin,0,sin);

					
			}
			animationPnl.drawPolyline(x, y, x.length);
		}
//		for (int j=0; j<3; j++)
//		{
//		for(int i=0;i<y.length;i++)
//	      {
//	         y[i]=100-(int)Math.round(Math.sin(Math.toRadians(i))*super.getFreq()[j]);
//	         x[i]=i;
//	      }
//		}
	}
	
	
	 public void start () {
	        animation = new Thread (this);
	        animation.start ();
	    }

	
	//Vererbte Methoden
	
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		// Initialisierung der Werte
		
//		this.count = 50;
//		this.values = new double [3][count];
		MainFrame mf = new MainFrame();
		setHandle((Graphics2D)mf.getGraphicsForVirtualization()); 
		animation = new Thread();
		start();
	}

	@Override
	public boolean pause (boolean state) {
		// TODO Auto-generated method stub
		if(state == false)return false;
		else 	
		{
			try {
				animation.sleep(0); //TODO pause
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean finish (boolean state) {
		// TODO Auto-generated method stub
		if(state == false)
		{
			return false;
		}
		else 	
		{
			animation.stop();
			return true;
		}
		
	}

	@Override
	public void setHandle (Graphics2D animationPnl) {
		// TODO Auto-generated method stub
		this.animationPnl = animationPnl;
	}
//getter & setter
	public Color [] getColor ()
	{
		return colors;
	}
	
	public void setColor (Color[] color)
	{
		this.colors = color;
	}
	
//	public void setValues(double [][] values) {
//		this.values = values;
//	}
//
//	public double [][] getValues() {
//		return values;
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		while (true) {
            try {
                Thread.sleep (30);
            }
            catch (InterruptedException e) {
            }
            sin();
//            animationPnl.drawPolyline(x, y, x.length);
       // }

	}

	
}
