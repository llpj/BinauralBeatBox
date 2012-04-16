package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class ToggleButton extends JToggleButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270513156168691820L;
	private String		selText;
	private String		unselText;
	
	public ToggleButton(String unselectedText, String selectedText) {
		this(unselectedText, selectedText, false);
	}
	
	public ToggleButton(String unselectedText, String selectedText, boolean selected) {
		selText = selectedText;
		unselText = unselectedText;
		
		setSelected(selected);
		changeText();
		
		addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeText();
			}
		});
	}
	
	@Override
	public void setSelected(boolean b) {
		super.setSelected(b);
		changeText();
	}
	
	private void changeText() {
		if(isSelected()) {
			setText( selText );
		} else {
			setText( unselText );
		}
	}
	
}
