/**
 * @author Fabian Schäfer
 *
 */

package logic;

import java.awt.Graphics2D;

import container.Session;

class FrakFarbverlauf extends Animation {
	
	private int tempo;
	private int bodySize; //random
	private int bodyCount; //random
	private String [] color = new String [2];
    private boolean isFraktal;        // isFarbverlauf wird nun durch den Unterschied von true und false mit abgebildet
    private String mood;
    
    public FrakFarbverlauf ()
    {
    	
    }
    
    public FrakFarbverlauf (String mood, boolean isFraktal)
    {
    	this.setMood(mood);
    	this.setFraktal(isFraktal);
    }
    
    protected Object setBody ()
    {
    	// TODO in bodySize
    	Object body = null;
    	return body;
    }
    
    protected Object createGradient ()
    {
    	// TODO in color
    	Object gradient = null;
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

	public void setColor(String [] color) {
		this.color = color;
	}

	public String [] getColor() {
		return color;
	}

	public void setFraktal(boolean isFraktal) {
		this.isFraktal = isFraktal;
	}

	public boolean isFraktal() {
		return isFraktal;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getMood() {
		return mood;
	}

	//Vererbte Methoden
	public void run()
	{
		
	}
	@Override
	public void init() {//TODO Session session
		// TODO Auto-generated method stub

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
