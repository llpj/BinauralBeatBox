/**
 * @author Fabian Schäfer
 *
 */

package logic;

import interfaces.Mood;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import javax.swing.JPanel;



public class FrakFarbverlauf extends Animation {
	//Konstanten für Farbverlaufwechsel
	private final float [] vertGrad = {25,25,15,25};
	private final float [] hozGrad = {5,25,2,2};
	private final float [] smoothGrad = {5,5,20,20};
	
	private int tempo;
	private int bodySize; //random
	private int bodyCount; //random
	private Color [] colors = new Color [4]; 
    private boolean isFraktal;        // isFarbverlauf wird nun durch den Unterschied von true und false mit abgebildet
    private Mood mood;
    private int posColor;
    private float gradPos;
    
    public FrakFarbverlauf (Mood mood, boolean isFraktal, JPanel pnl)
    {
    	super(pnl);
    	this.setMood(mood);
    	this.setFraktal(isFraktal);
    	init();
    }
    
    protected void createBody ()
    {
    	// TODO in bodySize
    	animationPnl = (Graphics2D) pnl.getGraphics();
        
        if(isFraktal == false)
		{
        	
        	animationPnl.setPaint(createGradient (colors[posColor], colors[posColor+1]));
			animationPnl.fillRect(0, 0, width, height);
			animationPnl.drawRect(0, 0, width, height);
		}
		else
		{
			gradPos = 0;
			int i;
			for( i = 0; i < bodyCount; i++)
			{
				posColor = (int) (Math.random()*3);
				animationPnl.setPaint(createGradient (colors[posColor], colors[posColor]));
				int x = width/2+i*10;
				int y = height/2+i;
				int n = (int) (Math.random()*5+1); //Anzahl der Ecken
				Polygon poly = new Polygon();
				for ( int j = 0; j < n; j++ )
				{
					poly.addPoint( (int) (x + bodySize * Math.cos( j * 2 * Math.PI / n )),
							(int) (y + bodySize * Math.sin( j * 2 * Math.PI / n )) );
				}
				animationPnl.fill( poly );
				animationPnl.drawPolygon( poly );
				animationPnl.rotate(bodySize);
				
			}
		}
                  
        
    }
    
    protected GradientPaint createGradient (Color color1, Color color2)
    {
    	// hier sind auch Drehungen möglich
    	Point2D.Float p1 = new Point2D.Float(150.f+gradPos, 75.f+gradPos); // Gradient line start
        Point2D.Float p2 = new Point2D.Float(250.f+gradPos, 75.f+gradPos); // Gradient line end
    	GradientPaint gradient = new GradientPaint(p1, color1, p2, color2,true);//true= cyclic, false = acyclic
		return gradient;
    }
    
    protected void setColor ()
    {
    	// TODO definiere color anhand von mood
    }
    
    protected void setTempo ()
    {
    	// TODO kalkuliere tempo anhand von freq
    }
    
   public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getTempo() {
		return tempo;
	}

	public void setBodySize(int bodySize) {
		this.bodySize = bodySize;
	}

	public int getBodySize() {
		return bodySize;
	}

	public void setBodyCount(int bodyCount) {
		this.bodyCount = bodyCount;
	}

	public int getBodyCount() {
		return bodyCount;
	}

	public void setColors(Color [] colors) {
		this.colors = colors;
	}

	public Color [] getColors() {
		return colors;
	}

	public void setFraktal(boolean isFraktal) {
		this.isFraktal = isFraktal;
	}

	public boolean isFraktal() {
		return isFraktal;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public Mood getMood() {
		return mood;
	}
	
	//Vererbte Methoden
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		 // Gibt 0 zurück --> soll für das Eingrenzen der Animation benutzt werden
		float h = 10.f ;
		while (animation == thisThread) 
			{
				bodyCount = (int) (Math.random()*100+1);
				bodySize = (int) (Math.random()*100+1);
				createBody();
				gradPos += h;
				if(gradPos > 1000)
				{
					h = -10.f;
					posColor = (int) (Math.random()*3);
				}
				else if(gradPos <= 0)
				{
					h = 10.f;
					posColor = (int) (Math.random()*3);
				}
	
				try 
				{
					Thread.sleep (40);	
				}
				catch (InterruptedException e) 
				{
					//nichts
				}
//				pnl.repaint();
	        }

	}
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		switch(mood)
		{
			case ALPHA: //Entspannung, Zustand kurz vor und nach dem Schlaf: Erhöhte Erinnerungs- und Lernfähigkeit
				colors[0] = Color.blue;
				colors[1] = Color.green;
				colors[2] = Color.orange;
				colors[3] = Color.white;
			case BETA:  //Hellwach, geistige Aktivität, Konzentration: Gute Aufnahmefähigkeit und Aufmerksamkeit
				colors[0] = Color.yellow;
				colors[1] = Color.orange;
				colors[2] = Color.red;
				colors[3] = Color.white;
			case GAMMA: //Geistige Höchstleistung, Problemlösung, Angst: Transformation und neurale Reorganisation
				colors[0] = Color.red;
				colors[1] = Color.yellow;
				colors[2] = Color.orange;
				colors[3] = Color.pink;
			case DELTA: //Traumloser Schlaf
				colors[0] = Color.green;
				colors[1] = Color.blue;
				colors[2] = Color.white;
				colors[3] = Color.white;
			case THETA: //Leichter Schlaf, REM-Phase, Träume
				colors[0] = Color.green;
				colors[1] = Color.blue;
				colors[2] = Color.cyan;
				colors[3] = Color.magenta;
		}
		posColor = (int) (Math.random()*3);
		tempo = 1500;
		width = pnl.getSize().width; // muss über Dimension gemacht werden, da Punkte und Pixel nicht vergleichbar wären
		height = pnl.getSize().height;
		super.start();
	}

	@Override
	public boolean pause(boolean state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finish(boolean state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHandle(Graphics2D animationpnl) {
		// TODO Auto-generated method stub

	}

}
