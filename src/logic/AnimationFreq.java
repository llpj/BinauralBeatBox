/**
 * @author Fabian Schï¿½fer
 *
 */

package logic;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class AnimationFreq extends Animation {
/**
 * Klassenname wurde geï¿½ndert von Frequenz zu AnimationFreq - Grund: Somit ist der Bezeichner eindeutig
 */
	// ersetzt das Attribut String color, da weniger code und einfacheres Handle
	Color colors[] = {Color.BLUE, Color.RED, Color.GREEN}; 
	//Koordinaten auf der Sinuskurve
	int[] x;
	int[] y;
	BufferedImage img;
	
	double xPos;
	
	@SuppressWarnings("static-access")
	public AnimationFreq (int [] freq, JPanel pnl)//TODO Session session
	{
		super(pnl);
		super.setFreq(freq);
		width = pnl.getSize().width; // muss über Dimension gemacht werden, da Punkte und Pixel nicht vergleichbar wären
		height = pnl.getSize().height;
		y = new int[360]; 
		x = new int[360];
		//Initialisierung
		init();//TODO session
	}

	protected void sin (boolean set)
	{	
		// TODO in: freq
		Color[] c = new Color[3];
		if(set == false)
		{
			c[0] = Color.GRAY;
			c[1] = Color.GRAY;
			c[2] = Color.GRAY;
		}
		else
		{
			c[0] = colors[0];
			c[1] = colors[1];
			c[2] = colors[2];
		}
		Graphics2D animationPnlBuffer;
		animationPnlBuffer = (Graphics2D) img.createGraphics();
		int nextPos = 0;
		int i,j;
		int q = height/3;
		while(nextPos < width)
		{
			for( j = 0; j < 3; j++)
			{
				for( i = 0; i < y.length; i++ )
				{
					y[i]=(100-(int)Math.round(Math.sin(Math.toRadians(i)+xPos)*super.getFreq()[j]));
					x[i]=i;
					if(j == 0)y[i]-=q;//20
					else if (j==1) y[i]+=0;//30
					else y[i]+=q;//80
				}
				animationPnlBuffer.setColor(c[j]);
				animationPnlBuffer.setStroke(new BasicStroke(5.0f));
				animationPnlBuffer.drawPolyline(x, y, x.length);
			}
			animationPnlBuffer.translate((int)x[x.length-1],0);
			nextPos += (int)x[x.length-1];
		}
		//animationPnl.drawImage(img, null, 0, this);
		
		
	}

	
	
	
	//Vererbte Methoden
	
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		// Initialisierung der Werte
		animationPnl = (Graphics2D) pnl.getGraphics();
		img = animationPnl.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.BITMASK);
		xPos = 0;
//		pnl.setDoubleBuffered(true);
		super.start();
	}

	@Override
	public boolean pause (boolean state) {
		// TODO Auto-generated method stub
		if(state == false)return false;
		else 	
		{
			try {
				Thread.sleep(0); //TODO pause
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
			xPos += 0.1f;
			sin(true);
			animationPnl.drawImage(img, null, 0, 0);
			sin(false);
			try 
			{
				Thread.sleep (20);	
			}
			catch (InterruptedException e) 
			{
				//nichts
			}
			
        }

	}
}
