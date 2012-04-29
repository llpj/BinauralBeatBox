/**
 * @author Fabian Schaefer
 * Animation zur Darstellung eines Farbverlaufs und diverser Fraktal 
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

//TODO Fraktale Animation

public class FrakFarbverlauf extends Animation {
	//Konstanten für Farbverlaufwechsel
//	private final float [] vertGrad = {25,25,15,25};
//	private final float [] hozGrad = {5,25,2,2};
//	private final float [] smoothGrad = {5,5,20,20};
	
	private int bodySize; //random
	private int bodyCount; //random
	private Color [] colors = new Color [4]; 
    private boolean isFraktal;        // isFarbverlauf wird nun durch den Unterschied von true und false mit abgebildet
    private Mood mood;
    private int posColor;
    private float gradPos;
    Polygon poly;
    BufferedImage img;
    
    public FrakFarbverlauf (Mood mood, JPanel pnl, boolean isFraktal)
    {
    	super(pnl);
    	this.setMood(mood);
    	this.setFraktal(isFraktal);
    	//Initialisierung - checkSize und posColor müssen im Konstruktor initialisiert sein !!! (wegen resizing)
    	checkResize = 0;
    	posColor = (int) (Math.random()*3);
    	init();
    }
    
    public int iteration(double realteil,double imaginaerteil) {   
    	/* Diese Methode enthält die eigentliche Iteration zur Berechnung der Farbe eines Bildpunktes 
    	des Apfelmännchens. Dabei werden zwei Parameter benötigt - der Realteil und der Imaginärteil. */
    	   double x,y,x2,y2,z;
    	   int k=0;
    	   x=0;
    	   y=0;
    	   x2=y;
    	   y2=0;
    	   z=0;
    	   for (k=0;k<1000;k++) {
    	// Der Apfelmännchen-Grundalgorithmus
    	     y=2*x*y+imaginaerteil;
    	     x=x2-y2+realteil;
    	     x2=x*x;
    	     y2=y*y;
    	     z=x2+y2;
    	     if (z>4) return k; 
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
        	animationPnlBuffer.setPaint(createGradient (colors[posColor], colors[posColor+1]));
			animationPnlBuffer.fillRect(0, 0, width, height);
			animationPnlBuffer.drawRect(0, 0, width, height);
		}
		else
		{
			//Größe der Apfelmännchens
			//Breite: Differenz von restart und reend --> neg = links / pos = rechts
			// restart und reend sollten im Bereich von -10 bis 10 --> optimal bei -4 und -1 liegen
			double restart=-3;
			double reend=0;
			  
			//Höhe: Differenz von imstart und imend --> neg = tiefere position / pos = höhere position
			double imstart=-2;
			double imend=0;
			
			double restep,imstep,repart,impart;
			int x,y,farbe;
			 /* Veränderung der Schrittweiten bei der Berechnung beeinflusst ebenfalls Größe und Position 
			des Fraktals */
			// Schrittweite für den Realteil 
			restep=(reend-restart)/(width/2); 
			// Schrittweite für den Imaginärteil 
			imstep=(imend-imstart)/(height/2); 
			// Zuweisung eines Startwertes für den 
			// Imaginärteil in der Rekursion
			impart=imstart; 
			 
			for (y=0;y<height-1;y++)  
			{
				// Zuweisung eines Startwertes für den Realteil 
				repart=restart; 
				for (x=0;x<width-1;x++) 
				{
					//Berechnung der Entscheidungsvariable für die Farbe eines Bildpunktes  
					farbe=iteration(repart,impart);
					if(farbe==1000) 
					{
						//Farbe für das Apfelmännchen-Innere
						animationPnlBuffer.setPaint(createGradient(colors[posColor], colors[posColor+1]));
						//Malt den Hintergrund in Abhänigkeit von width und height
						animationPnlBuffer.drawLine(x,y,x+1,y);
					} 
					else 
					{ 
			/* Hier wird die Farbe eines Bildpunktes vom eigentlichen Apfelmännchen explizit berechnet. 
			Die 3 Angaben in der Color-Angabe sind RGB-Werte (Rot-Grün-Blau) und legen die jeweilige 
			Intensität der Farbanteile fest. Nur der erste Parameter wird jeweils neu berechnet. Dabei 
			ist bei Manipulationen des Rotbereichs darauf zu achten, dass das Resultat zwischen 0 und 255 
			bleibt. Hier im Beispiel liegt die Grundfarbwahl im Rotbereich. Sie kann aber jederzeit durch 
			Veränderung der Parameter in einen anderen Farbbereich verlegt werden. */
//			  Color jr = new Color(255-(farbe%52*5),255-(farbe%52*5),125);
			/* Alternative Grundfarbe. Dabei wird sowohl der Rotanteil, als auch der Grünanteil 
			manipuliert. */
			    	
						//Farbe der Apfelmännchen-Umgebung
						Color jr = new Color(255-(farbe%26*10), 120, colors[posColor].getBlue()); 
						animationPnlBuffer.setColor(jr); 
						// Zeichne an Position x,y einen Punkt mit 
						// dem Farbwert jr
						animationPnlBuffer.drawLine(x,y,x+1,y);} 
					  	// Neue Werte für die Iteration
					  	repart=repart+restep;}  
				  		impart=impart+imstep;}
			     	}
			  

//			poly = new Polygon();
//			gradPos = 0;
//			int i;
//			int n = (int) (Math.random()*5+1); //Anzahl der Ecken
//			for( i = 0; i < bodyCount; i++)
//			{
//				posColor = (int) (Math.random()*3);
//				animationPnl.setPaint(createGradient (colors[posColor], colors[posColor]));
//				int x = width/2;
//				int y = height/2;
//				
//				for ( int j = 0; j < n; j++ )
//				{
//					poly.addPoint( (int) (x + bodySize * Math.cos( j * 2 * Math.PI / n )),
//							(int) (y + bodySize * Math.sin( j * 2 * Math.PI / n )) );
//				}
////				bodySize -= i*50;
//				animationPnl.fill( poly );
//				animationPnl.drawPolygon( poly );
////				animationPnl.rotate(bodySize);
    	  //animationPnl.rotate(1);
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
    	// TODO definiere color anhand von mood
    }
    
    protected void setTempo ()
    {
    	// TODO kalkuliere tempo anhand von freq
    }
    
  
	
	//Vererbte Methoden
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		// animiert Farbverlauf
		float h = 3.1f ;
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
				bodyCount = (int) (Math.random()*10+1);
				bodySize = 500;
//				bodySize = (int) (Math.random()*100+1);
				createBody();
				gradPos += h;
				if(gradPos > 750)
				{
					h = -3.1f;
					posColor = (int) (Math.random()*3);
					
				}
				else if(gradPos <= 0)
				{
					h = 3.1f;
					posColor = (int) (Math.random()*3);
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
//		posColor = (int) (Math.random()*3);
		tempo = 1500;
		width = pnl.getSize().width; // muss über Dimension gemacht werden, da Punkte und Pixel nicht vergleichbar wären
		height = pnl.getSize().height;
		animationPnl = (Graphics2D) pnl.getGraphics();
		img = animationPnl.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.BITMASK);
		checkResize++;
		if(checkResize%2 == 0)
		{
			tempo = 10;
		}
		else
		{
			tempo = 40;
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
			return false;
		}
		else 	
		{
			//Animation wird übermalt
			Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, height);
			animationPnl.setColor(Color.GRAY);
			animationPnl.fill(rectangle);
			animation.stop();
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
}
