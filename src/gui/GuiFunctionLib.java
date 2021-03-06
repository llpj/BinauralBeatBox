package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public final class GuiFunctionLib {
	public static final void addGridBagContainer(Container cont, GridBagLayout gbl, Component c,
			int x, int y, int width, int height, double weightx, double weighty) {
		addGridBagContainer(cont, gbl, c, x, y, width, height, weightx, weighty, GridBagConstraints.BOTH);
	}
	
	public static final void addGridBagContainer(Container cont, GridBagLayout gbl, Component c,
			int x, int y, int width, int height, double weightx, double weighty, int fill) {
	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;//GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		
		gbl.setConstraints(c, gbc);
		cont.add(c);
	}
}
