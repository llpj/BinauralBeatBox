package container;

import logic.SessionWiedergabe;
import management.FileManager;

public class ContainerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing getMood in connection with the enum
		BinauralBeat beat = new BinauralBeat(12, 14);
		System.out.println("Gewaehlte Stimmung: " + beat.getMood());


//		Session session = new Session();
//		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
//		session.addSegment( new Segment(40, new BinauralBeat(800, 830)) );
//		session.addSegment( new Segment(10, new BinauralBeat(500, 530)) );
//		Sessionww.faWiedergabe sessionWiedergabe = new SessionWiedergabe(session);
//		
//		sessionWiedergabe.playSession((int) beat.getFreq1_start(),(int) beat.getFreq2_start());

		/*
		 * for(int i = 0; i<1000; i++){
		 * SessionWiedergabe.playSession((int)beat.getFreq1_start(),
		 * (int)beat.getFreq2_start()); if(i%1000 == 0)
		 * System.out.println("Spielt..."); } System.out.println("Fertig.");
		 */

		// Test Categories
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

		// Test Wav-Export
		Segment steadySegment = new Segment(40, 155, 160);
		Session exportableSession = new Session("Hintergrundklang", steadySegment);

		FileManager fm = new FileManager();
		fm.setActiveSession(exportableSession);
		System.out.println("Erstelle Wavefile...");
		fm.exportAsWav();
		System.out.println("Wavefile erfolgreich erstellt.");
	}

}