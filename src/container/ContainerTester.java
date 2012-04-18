package container;

import logic.SessionWiedergabe;

public class ContainerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing getMood in connection with the enum
		BinauralBeat beat = new BinauralBeat(25, 23);
		System.out.println("Gewaehlte Stimmung: " + beat.getMood());

		for(int i = 0; i<1000; i++){
			System.out.println("Links: " + (int)beat.getFreq1_start() + " Rechts: " + (int)beat.getFreq2_start() + " i: " +i);
			SessionWiedergabe.playSession((int)beat.getFreq1_start(), (int)beat.getFreq2_start(), 1000);
			if(i%1000 == 0) System.out.println("Spielt...");
		}		

		/*
		 * for(int i = 0; i<1000; i++){
		 * SessionWiedergabe.playSession((int)beat.getFreq1_start(),
		 * (int)beat.getFreq2_start()); if(i%1000 == 0)
		 * System.out.println("Spielt..."); } System.out.println("Fertig.");
		 */

		Segment segment1 = new Segment(10, beat);
		Segment segment2 = new Segment(10, 30, 33);
		Segment slowdown = new Segment(10, 100, 30, 110, 40);

		Session session1 = new Session(
				"Hier koennte Ihr Hintergrundklang stehen", slowdown);
		session1.addSegment(segment1);

		Session session2 = new Session("Hintergrundklang", slowdown);
		session2.addSegment(segment2);

		Category category = new Category("Testkategorie", session1);
		category.addSession(session2);

		System.out.println(category.toString());
	}

}
