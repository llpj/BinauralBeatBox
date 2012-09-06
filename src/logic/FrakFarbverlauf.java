/**
 * @author Fabian Schaefer
 * Animation zur Darstellung eines Farbverlaufs und der fraktalen Animation eines Apfelmännchens 
 */

package logic;

import interfaces.Mood;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Transparency;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class FrakFarbverlauf extends Animation {

	private float bodySize; //random
	private int bodyCount; //random
	//Das Attribut Color wird entgegen der Angabe im Architekturdokument mit 4 Farben initialisiert. 
	//Eine Start und End Farbe wird durch die Methode GradientPaint überflüssig
	private Color [] colors = new Color [4]; 
	// isFarbverlauf wird nun durch den Unterschied von true und false mit abgebildet
    private boolean isFraktal;        
    private Mood mood;
    private int posColor;
    private float gradPos;
    Polygon poly;
    BufferedImage img;
    //Attribute zur Manipulation der Fraktalten Animation
    private float counter;
    private double num1;
    private double num2;
    private double num3;
    private double num4;
    
    public FrakFarbverlauf (Mood mood, JPanel pnl, boolean isFraktal)
    {
    	super(pnl);
    	super.setAniFreq(false);
    	this.setMood(mood);
    	this.setFraktal(isFraktal);
    	//Initialisierung - checkSize und posColor müssen im Konstruktor initialisiert sein !!! (wegen resizing)
    	checkResize = 0;
    	counter = 0;
    	posColor = (int) (Math.random()*3);
    	isFinished = false;
    	init();
    }
    
    private void apfelMan (Graphics2D animationPnlBuffer)
    {
    	//Größe des Apfelmännchens
		//Breite: Differenz von restart und reend --> neg = links / pos = rechts
		// restart und reend sollten im Bereich von -10 bis 10 --> optimal bei -4 und -1 liegen
		double restart= num1;	//-3
		double reend= num2;		//0
		//Höhe: Differenz von imstart und imend --> neg = tiefere position / pos = höhere position
		double imstart=num3;	//-2
		double imend=num4;		//0
		double restep,imstep,repart,impart;
		int x,y,farbe;
		// Größe und Position des Fraktals 
		// Schrittweite für den Realteil 
		restep=(reend-restart)/((width/2)); 
		// Schrittweite für den Imaginärteil 
		imstep=(imend-imstart)/((height/2)); 
		// Zuweisung eines Startwertes für den Imaginärteil in der Rekursion
		impart=imstart; 

		for (y=0;y<height-1;y++)  
		{
			// Zuweisung eines Startwertes für den Realteil 
			//counter = 2;
			repart=restart; 
			for (x=0;x<width-1;x++) 
			{
				//Berechnung der Entscheidungsvariable für die Farbe eines Bildpunktes  
				farbe=iteration(repart,impart);
				Color jr;
				switch(farbe)
				{
					case 0: 
							jr = new Color(colors[posColor].getRGB()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
					case 1: 
							jr = new Color(255-(farbe%26*10)+1, 20, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
					case 16: 
							jr = new Color(255-(farbe%26*10)+16, 200, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
					case 32: 
							jr = new Color(255-(farbe%26*10)+32, 80, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
					case 64: 
							jr = new Color(255-(farbe%26*10)+64, 255, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
					case 128: 
							jr = new Color(255-(farbe%26*10)+128, 0, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
						break;
					default:
							jr = new Color(255-(farbe%26*10), 120, colors[posColor].getBlue()); 
							animationPnlBuffer.setColor(jr); 
							animationPnlBuffer.drawLine(x,y,x+1,y);
							break;
				}
				// Neue Werte für die Iteration
				repart=repart+restep;
			}  
			impart=impart+imstep;
		}
    }
    
    private int iteration(double realteil,double imaginaerteil) 
    {   
    	/* Diese Methode enthält die eigentliche Iteration zur Berechnung der Farbe eines Bildpunktes 
    	des Apfelmännchens. Dabei werden zwei Parameter benötigt - der Realteil und der Imaginärteil. */
    	double x,y,x2,y2,z;
    	int k=0;
    	x=0;
    	y=0;
    	x2=x;
    	y2=y;
    	z=0;
    	for (k=0;k<500;k++) 
    	{
    		//Grundalgorithmus: Z(n)= Z(n+1)²+C
    	    y=2*x*y+imaginaerteil;
    	    x=x2-y2+realteil;
    	    x2=x*x+bodySize; 
    	    y2=y*y-bodySize;
    	    z=x2+y2;
    	    if (z>counter) return k; 
    	 }
    	 return k ;
    }
    
    protected void createBody ()
    {
    	// TODO in bodySize
    	Graphics2D animationPnlBuffer;
		animationPnlBuffer = (Graphics2D) img.createGraphics();
        
        if(isFraktal == false)
		{
        	//Farbverlauf Animation
        	animationPnlBuffer.setPaint(createGradient (colors[posColor], colors[posColor+1]));
			animationPnlBuffer.fillRect(0, 0, width, height);
			animationPnlBuffer.drawRect(0, 0, width, height);
		}
		else
		{
			//Fraktale Animation
			apfelMan(animationPnlBuffer);
		}  
    }
    
    protected GradientPaint createGradient (Color color1, Color color2)
    {
    	// hier sind auch Drehungen möglich
    	Point2D.Float p1 = new Point2D.Float(150+gradPos, 75+gradPos); // Gradient line start
    	Point2D.Float p2 = new Point2D.Float(250+gradPos, 75+gradPos); // Gradient line end
    	GradientPaint gradient = new GradientPaint(p1, color1, p2, color2,true);//true= cyclic, false = acyclic
		return gradient;
    }
    
    protected void setColor ()
    {
    	switch(mood)
		{
			case ALPHA: //Entspannung, Zustand kurz vor und nach dem Schlaf: Erhöhte Erinnerungs- und Lernfähigkeit
				colors[0] = Color.blue.brighter();
				colors[1] = Color.green.darker();
				colors[2] = Color.orange.brighter();
				colors[3] = Color.white;
			case BETA:  //Hellwach, geistige Aktivität, Konzentration: Gute Aufnahmefähigkeit und Aufmerksamkeit
				colors[0] = Color.yellow.darker();
				colors[1] = Color.orange.brighter();
				colors[2] = Color.red.brighter();
				colors[3] = Color.white;
			case GAMMA: //Geistige Höchstleistung, Problemlösung, Angst: Transformation und neurale Reorganisation
				colors[0] = Color.red.darker();
				colors[1] = Color.yellow.darker();
				colors[2] = Color.orange.brighter();
				colors[3] = Color.pink.darker();
			case DELTA: //Traumloser Schlaf
				colors[0] = Color.green.brighter();
				colors[1] = Color.blue.brighter();
				colors[2] = Color.white.brighter();
				colors[3] = Color.white.brighter();
			case THETA: //Leichter Schlaf, REM-Phase, Träume
				colors[0] = Color.green.brighter();
				colors[1] = Color.blue.brighter();
				colors[2] = Color.cyan.brighter();
				colors[3] = Color.magenta.brighter();
		}
    }
    
    protected void setTempo ()
    {
    	//Kann bei Bedarf noch variable durch Überabe des Freq-Parameter eingestellt werden
    	//Allerdings wird diese Konstante Einstellung empfohlen bzgl. flüssige Darstellung
    	if(checkResize%2 == 0)
		{
			if(isFraktal)
			{
				tempo = 0;
			}
			else
			{
				tempo = 10;
			}
		}
		else
		{
			tempo = 40;
		}
    }
    
  
	
	//Vererbte Methoden
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		// animiert Farbverlauf
		float h = 3.1f ;
		boolean marker = true;
		boolean marker2 = false;
		int marker3 = 1;
		while (animation == thisThread) 
			{
				synchronized(thisThread)
				{	
					while(pause)
					{  
						try 
						{
							thisThread.wait(90);
		            	} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
				if (isFraktal)
				{
					//Konfigurationen für Animationsdarstellung (fraktal)
					if(counter <= 0 && !marker2)
					{
						marker = true;
					}
					else if (marker2 && counter <= 5)
					{
						marker = true;
						bodyCount = (int) (Math.random()*1+4);
					}	
					else if(counter >= 15)
					{
						marker = false;
					}
					
					if(marker && counter < 15)
					{
						counter += 0.5f;
						if(marker3%2 == 0)
						{
							bodySize += 0.1f;
						}
						else
						{
							bodySize -= 0.1f;
						}
					}
					else
					{
						marker3++;
						counter -= 2;
						marker2 = true;
					}
					
				}
				//System.out.println(bodySize);
				createBody();
				gradPos += h;
				//Definition des Richtungs- und Farbwechsels (Frabverlauf)
				if(gradPos > 750)
				{
					h = -3.1f;
					posColor = (int) (Math.random()*2);
					
				}
				else if(gradPos <= 0)
				{
					h = 3.1f;
					posColor = (int) (Math.random()*2);
				}
				
				try 
				{
					Thread.sleep (tempo);	
				}
				catch (InterruptedException e) 
				{
					//nichts
				}
				animationPnl.drawImage(img, null, 0, 0);
	        }

	}
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub
		setColor();
		bodySize = 1;
		tempo = super.getFreq()[1];
		width = pnl.getSize().width; // muss über Dimension gemacht werden, da Punkte und Pixel nicht vergleichbar wären
		height = pnl.getSize().height;
		animationPnl = (Graphics2D) pnl.getGraphics();
		img = animationPnl.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.BITMASK);
		checkResize++;
		if(isFraktal)
		{
			num1 = -3;
			num2 = 0;
			num3 = -2;
			num4 = 0;
			bodyCount = (int) (Math.random()*1+4);
		}
		super.start();
	}

	@Override
	public void pause (boolean state) {
		if(state == false) pause = false;
		else 	
		{
			pause = true;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean finish (boolean state) {
		if(state == false)
		{
			isFinished = false;
			return false;
		}
		else 	
		{
			//Animation wird übermalt
			Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, height);
			animationPnl.setColor(Color.GRAY);
			animationPnl.fill(rectangle);
			animation.stop();
			super.setAniFreq(true);
			isFinished = true;
			return true;
		}
		
	}

	//getter & setter
	 public void setTempo(int tempo) {
			this.tempo = tempo;
		}

		public int getTempo() {
			return tempo;
		}

		public void setBodySize(int bodySize) {
			this.bodySize = bodySize;
		}

		public float getBodySize() {
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
}
