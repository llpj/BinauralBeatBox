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
    Polygon poly;
    
    public FrakFarbverlauf (Mood mood, JPanel pnl, boolean isFraktal)
    {
    	super(pnl);
    	this.setMood(mood);
    	this.setFraktal(isFraktal);
    	init();
    }
    
    public int iteration(double realteil,double imaginaerteil) {   
    	/* Diese Methode enthält die eigentliche Iteration zur Berechnung der Farbe eines Bildpunktes 
    	des Apfelmännchens. Genau genommen wird hier zum einen die Entscheidung getroffen, welcher 
    	Zweig in der Zeichenmethode paint() zu verwenden ist. Zum anderen wird ein Modulo-Faktor für 
    	die tatsächliche Farbwahl berechnet. Dabei werden zwei Parameter benötigt - der Realteil und 
    	der Imaginärteil. */
    	   double x,y,x2,y2,z;
    	   int k=0;
    	   x=0;
    	   y=0;
    	   x2=y;
    	   y2=0;
    	   z=0;
    	   for (k=0;k<1000;k++) {
    	// Der komplexe Apfelmännchen-Grundalgorithmus
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
    	animationPnl = (Graphics2D) pnl.getGraphics();
        
        if(isFraktal == false)
		{
        	
        	animationPnl.setPaint(createGradient (colors[posColor], colors[posColor+1]));
			animationPnl.fillRect(0, 0, width, height);
			animationPnl.drawRect(0, 0, width, height);
		}
		else
		{
			/* In der Zeichenmethode paint() wird der eigentliche Bildaufbau vorgenommen. */
			 /* Startwerte für das Apfelmännchen. Ein Verändern der Startwerte führt zu einer Veränderung 
			der Größe und/oder Position des Apfelmännchens. Die Grundfläche für den Aufbau des 
			Apfelmännchens bleibt allerdings unberührt. */
//			  double restart=-2;
//			  double reend=1;
//			  double imstart=-1;
//			  double imend=1;
			//Alternative Startwerte zum Experimentieren   
			    double restart=-3; 
			   double reend=2;
			   double imstart=-2;
			   double imend=2;
			 
			  double restep,imstep,imquad,repart,impart;
			  int x,y,farbe;
			 /* Veränderung der Schrittweiten bei der Berechnung beeinflusst ebenfalls Größe und Position 
			des Fraktals */
			// Schrittweite für den Realteil 
			  restep=(reend-restart)/200; 
			// Schrittweite für den Imaginärteil 
			  imstep=(imend-imstart)/200; 
			  y=0; // Zählvariable
			// Zuweisung eines Startwertes für den 
			// Imaginärteil in der Rekursion
			  impart=imstart; 
			 /* Beginn der Rekursion. Zwei ineinander verschachtelte for-Schleifen. Die äußere Schleife 
			berechnet den Realteil, die innere Schleife den Imaginärteil. */
			// Jeder y-Wert entspricht einer Bildschirmzeile
			  for (y=0;y<200;y++)  {
			// Zuweisung eines Startwertes für den Realteil 
			// in der Rekursion
			   repart=restart; 
			// Jeder x-Wert entspricht einer Spalte
			   for (x=0;x<200;x++) {
			/* Berechnung der Entscheidungsvariable für die Farbe eines Bildpunktes  */
			    farbe=iteration(repart,impart);
			    if(farbe==1000) {
			// Zeichne an der Position x,y einen schwarzen 
			// Punkt
			        animationPnl.setColor(Color.black);
			   animationPnl.drawLine(x,y,x+1,y);
			    } 
			    else { 
			/* Hier wird die Farbe eines Bildpunktes vom eigentlichen Apfelmännchen explizit berechnet. 
			Die 3 Angaben in der Color-Angabe sind RGB-Werte (Rot-Grün-Blau) und legen die jeweilige 
			Intensität der Farbanteile fest. Nur der erste Parameter wird jeweils neu berechnet. Dabei 
			ist bei Manipulationen des Rotbereichs darauf zu achten, dass das Resultat zwischen 0 und 255 
			bleibt. Hier im Beispiel liegt die Grundfarbwahl im Rotbereich. Sie kann aber jederzeit durch 
			Veränderung der Parameter in einen anderen Farbbereich verlegt werden. */
			  Color jr = new Color(255-(farbe%52*5),255-(farbe%52*5),125);
			/* Alternative Grundfarbe. Dabei wird sowohl der Rotanteil, als auch der Grünanteil 
			manipuliert. */
			/*Color jr = new Color(255-(farbe%26*10), 120, 125); */
			   animationPnl.setColor(jr);  
			// Zeichne an Position x,y einen Punkt mit 
			// dem Farbwert jr
			   animationPnl.drawLine(x,y,x+1,y);} 
			/* Neue Werte für die Iteration*/
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
		// animiert Farbverlauf
		float h = 10.f ;
		while (animation == thisThread) 
			{
				bodyCount = (int) (Math.random()*10+1);
				bodySize = 500;
//				bodySize = (int) (Math.random()*100+1);
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

	@Override
	public boolean finish(boolean state) {
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
	public void setHandle(Graphics2D animationpnl) {
		// TODO Auto-generated method stub

	}
}
