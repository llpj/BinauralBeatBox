package interfaces;

/**
 * Interface für die verschiedenen möglichen Stimmungen, denn diese sind
 * statisch. Ich bin mir nicht ganz sicher, ob die ein eigenes Package brauchen,
 * aber auf die wird auf jeden Fall von Container, Controller und GUI
 * zugegriffen.
 * 
 * @author Magnus Brühl
 * 
 */
public enum Mood {
	DELTA, THETA, ALPHA, BETA, GAMMA, SLOWDOWN, WAKEUP, INVALID, UNKNOWN
}
