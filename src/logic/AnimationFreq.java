/**
 * @author Fabian Schï¿½fer
 *
 */

package logic;
import container.Session;
import gui.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class AnimationFreq extends Animation {
/**
 * Klassenname wurde geï¿½ndert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
	private Thread animation;
	private Graphics2D animationPnl;
	// ersetzt das Attribut String color, da weniger code und einfacheres Handle
	Color colors[] = {Color.BLUE, Color.RED, Color.GREEN}; 
	//Koordinaten auf der Sinuskurve
	int[] x;
	int[] y;
	//TODO animationPnl größe 
	double width;
	double height;
	
	   
	public AnimationFreq ()
	{
		//Standard Konstruktor
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
		super.paint(animationPnl);
		int i;
		for( int j = 0; j < 3; j++)
		{
			for( i = 0; i < y.length; i++ )
			{
				y[i]=(100-(int)Math.round(Math.sin(Math.toRadians(i))*super.getFreq()[j]));
				x[i]=i;
				if(j == 0)y[i]-=20;
				else if (j==1) y[i]+=30;
				else y[i]+=80;
			}
			animationPnl.setColor(colors[j]);
		    animationPnl.setStroke(new BasicStroke(5.0f));
			animationPnl.drawPolyline(x, y, x.length);
		}
	}
	
	
	//Thread Initialisierung
	 public void start () {
		 if(animation == null)
		{
	        animation = new Thread (this);
	        animation.start ();
		}
	}

	
	//Vererbte Methoden
	
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		// Initialisierung der Werte
		super.paint(animationPnl);
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
			if(animation != null)
			{
				animation = null;
			}
			return true;
		}
		
	}

	@Override
	public void setHandle (Graphics2D animationPnl) {
		// TODO Auto-generated method stub
		this.animationPnl = animationPnl;
		animationPnl.getComposite();
		y = new int[360]; 
		x = new int[360];
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
	Thread thisThread = Thread.currentThread();
	 // Gibt 0 zurück --> soll für das Eingrenzen der Animation benutzt werden
	

	while (animation == thisThread) 
		{
            try 
            {
                Thread.sleep (30);
            }
            catch (InterruptedException e) 
            {
            }
            sin();
            animationPnl.translate((int)x[x.length-1],0);
            repaint();
        }

	}

	
}
