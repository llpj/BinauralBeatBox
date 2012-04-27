/**
 * 
 */
package container;

import java.util.ArrayList;

/**
 * @author Magnus Bruehl
 * 
 */
public class Category {
	// Attribute
	String name;
	ArrayList<Session> sessions;

	// Konstruktoren
	public Category(String name) {
		this.name = name;
		sessions = new ArrayList<Session>();
	}

	public Category(String name, Session session) {
		this(name);
		sessions.add(session);
	}

	// Getter & Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}

	// Andere Methoden
	/**
	 * Fuegt der Kategorie eine Session hinzu. Die Session wird ans ende der
	 * Liste angehaengt.
	 * 
	 * @param session
	 */
	public void addSession(Session session) {
		this.sessions.add(session);
	}

	/**
	 * Loescht die Session an der angegebenen Stelle in der Liste.
	 * 
	 * @param index
	 */
	public void removeSession(int index) {
		try {
			this.sessions.remove(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public int size() {
		return this.sessions.size();
	}

	public String toString() {
		return name;
//		return "Name: " + name + "\nContained Sessions:\n"
//				+ sessions.toString();
	}
}
