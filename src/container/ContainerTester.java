package container;

import logic.SessionWiedergabe;
import interfaces.Mood;

public class ContainerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing getMood in connection with the enum
		BinauralBeat beat = new BinauralBeat(80, 90);
		System.out.println("Gewaehlte Stimmung: " + beat.getMood());
		for(int i = 0; i<1000; i++){
			SessionWiedergabe.playSession((int)beat.getFreq1_start(), (int)beat.getFreq2_start());
			if(i%1000 == 0) System.out.println("Spielt...");
		}
		System.out.println("Fertig.");

	}

}
