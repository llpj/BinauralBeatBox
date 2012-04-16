package container;

import interfaces.Mood;

public class ContainerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing the enum
		System.out.println(Mood.ALPHA);

		// Testing getMood in connection with the enum
		BinauralBeat beat = new BinauralBeat();
		System.out.println(beat.getMood());

	}

}
