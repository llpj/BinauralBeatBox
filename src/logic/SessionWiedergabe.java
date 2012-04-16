package logic;

import container.Session;

/**
 * @author Boris Beck
 * @return This class load and play a Session
 *
 */

public class SessionWiedergabe {
	
	// define variables
	private int currentDuration = 0;
	private int currentFrequenz;
	private Session currentSession;
	private int volumn;
	private int balance;
	
	public void setSession(Session session) {
		// TODO load session
	}
	
	public void playSession() {
		// TODO play Session
	}
	
	public void pauseSession() {
		// TODO pause
	}
	
	public void stopSession() {
		// TODO stop Session
	}
	
	public int getCurrentDuration() {
		//würde ich in der Session abfragen lassen
		return currentDuration;
	}
	
	private void transition(int endFreq, int beginFeq) {
		// TODO Uebergang zwischen den Segmenten berechnen
	}
	
	private void changeVolumn(int volumn) {
		this.volumn = volumn;
	}
	
	private void changeBalance(int balance) {
		this.balance = balance;
	}

}
