package container;

/**
 * @author Boris Beck
 * @return This class load and play a Session
 *
 */

public class SessionWiedergabe {
	
	// define variables
	private int currentduration = 0;
	private int currentfrequenz;
	private Session current_session;
	private int volumn;
	private int balance;
	
	public Session loadSession() {
		// TODO load session
		return current_session;
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
	
	public int getcurrentduration() {
		return currentduration;
	}
	
	public void setcurrentduration(int currentdurration) {
		this.currentduration = currentdurration;
	}
	
	private void transition(int currentfrequenz, int beginfreq) {
		// TODO Uebergang zwischen den Segmenten berechnen
	}
	
	private void changeVolumn(int volumn) {
		this.volumn = volumn;
	}
	
	private void changeBalance(int balance) {
		this.balance = balance;
	}

}
