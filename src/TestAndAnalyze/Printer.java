package TestAndAnalyze;

import container.BinauralBeat;
import container.Segment;
import container.Session;

public class Printer {
	
	private final static boolean testAndAnalyzeMode = true;

	/**
	 * 
	 * Session Printer
	 * 
	 */
	public static void printSession(Session s) {
		printSession(s, "");
	}
	
	public static void printSession(Session s, String pos) {
		printSession(s, "",pos);
	}
	
	public static void printSession(Session s, String space, String pos) {
		if(testAndAnalyzeMode) {
			if( !pos.isEmpty() )
				System.out.println(space+"----------"+pos+":");
			System.out.println(space+"----------START SESSION PRINT----------");
			
			System.out.println(space+"\tName:\t\t\t"+s.getName());
			System.out.println(space+"\tHintergrundklang:\t"+s.getHintergrundklang());
			System.out.println(space+"\tDauer:\t\t\t"+s.getDuration());
			System.out.println(space+"\tSegmentanzahl:\t\t"+s.getNumerOfSegments());
			System.out.println();
			
			for( Segment seg : s.getSegments() ) {
				printSegment(seg, space+"\t","");
			}
			System.out.println();
			System.out.println(space+"-----------END SESSION PRINT-----------");
		}
	}
	
	/**
	 * 
	 * 	Segment Printer
	 * 
	 */
	public static void printSegment(Segment seg) {
		printSegment(seg, "", "");
	}
	
	public static void printSegment(Segment seg, String space, String pos) {
		if(testAndAnalyzeMode) {
			if( !pos.isEmpty() )
				System.out.println(space+"----------"+pos+":");
			System.out.println(space+"----------START SEGMENT PRINT----------");
			System.out.println(space+"\tDauer:\t"+seg.getDuration());
			printBeat(seg.getBeat(), space+"\t","");
			System.out.println();
			System.out.println(space+"-----------END SEGMENT PRINT-----------");
		}
	}
	
	/**
	 * 
	 * Beat Printer
	 * 
	 */
	public static void printBeat(BinauralBeat beat) {
		printBeat(beat, "", "");
	}
	
	public static void printBeat(BinauralBeat beat, String space, String pos) {
		if(testAndAnalyzeMode) {
			if( !pos.isEmpty() )
				System.out.println(space+"----------"+pos+":");
			System.out.println(space+"----------START BEAT PRINT----------");
			System.out.println(space+"\t Frequ. Links  (Start/Ziel):\t"+beat.getFreq1_start()+"/"+beat.getFreq1_target());
			System.out.println(space+"\t Frequ. Rechts (Start/Ziel):\t"+beat.getFreq2_start()+"/"+beat.getFreq2_target());
			System.out.println(space+"\tMood:\t"+beat.getMood());
			System.out.println();
			System.out.println(space+"-----------END BEAT PRINT-----------");
		}
	}
}
