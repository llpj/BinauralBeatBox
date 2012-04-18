/**
 * 
 */
package management;

import java.util.ArrayList;

import container.Category;

/**
 * Liest und schreibt XML-Dateien. Exportiert komplette Sessions als WAVE-Datei.
 * Stellt Beat-Templates fuer den Casual-Modus zur Verfuegung
 * 
 * @author Magnus Bruehl, Ulrich Ahrendt
 * 
 */
// TODO: XML-Encoding, WAVE-Encoding
public class FileManager {
	// Attribute
	private ArrayList<Category> categories;

	// Konstruktor
	public FileManager() {
		categories = new ArrayList<Category>();
	}

	// Getter & Setter
	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	// Weitere Methoden
	public void addCategory(Category category) {
		this.categories.add(category);
	}

	public void removeCategory(int index) {
		this.categories.remove(index);
	}

}
