import java.awt.Graphics;

/**
 * Solution for Beads Interface, part of the Strategy pattern for Mancala Project
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * States all necessary requirements for any class that wants to implement bead must have
 */

public interface Beads {
	
	/**
	 * Instructions for how this bead should be painted
	 * @param g - a Graphics object
	 */
	void paintComponent(Graphics g);
	
	/**
	 * Returns a string that helps other designs match the bead design based off the string
	 * @return a string stating the design of this bead
	 */
	String getStyle();
	
	/**
	 * Method to allow this bead to return a copy of itself so more of the same style can be used
	 * @return a new bead of this same type
	 */
	Beads copy();
}
