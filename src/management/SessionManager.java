/**
 * 
 */
package management;

import java.util.ArrayList;

import container.Category;
import container.Session;

/**
 * !!-- Die Funktionalitaeten dieser Klasse werden in die Klasse FileManager
 * ausgelagert --!! Der SessionManager stellt die Sessions und Kategorien fuer
 * den Controller bereit. Ausserden sendet er Veraenderungen an den Kategorien
 * an den FileManager, damit dieser diese Aenderungen persistieren kann.
 * 
 * @author Magnus Bruehl
 * 
 */
public class SessionManager {
	// Attribute
	private Session activeSession; // private Session, hihi :)
	private Category activeCategory;
	private ArrayList<Category> categories;

	// Konstruktoren
	public SessionManager() {
		categories = new ArrayList<Category>();
	}

	/**
	 * Erstellt einen neuen SessionManager mit bereits vorhandenen Kategorien
	 * 
	 * @param categories
	 *            Die eingelesenen Kategorien
	 */
	public SessionManager(ArrayList<Category> categories) {
		this.categories = categories;
	}

	// Getter & Setter
	public Session getActiveSession() {
		return activeSession;
	}

	public void setActiveSession(Session activeSession) {
		this.activeSession = activeSession;
	}

	public Category getActiveCategory() {
		return activeCategory;
	}

	public void setActiveCategory(Category activeCategory) {
		this.activeCategory = activeCategory;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	// Andere Methoden
	public void openSession(Session session) {
		this.activeSession = session;
	}

	public void openCategory(Category category) {
		this.activeCategory = category;
	}

	// TODO: Uebergabe der veraenderten Kategorien an den FileManager.
	// Implementierbar, sobald Schnittstellen fuer FileManager bekannt.
}
