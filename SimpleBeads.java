import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Solution for SimpleBeads which is one of the concrete strategies for the Beads Interface (Strategy Pattern) 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Class for simple black colored beads that can be used as a style in the board (in the form of a JPanel)
 */

public class SimpleBeads extends JPanel implements Beads{
	
	/**
	 * Returns a string based on this design style so it can be recognized by other classes
	 * @return a string about this style of bead
	 */
	public String getStyle()
	{
		return "simple";
	}
	
	/**
	 * Returns a new bead of the same style so it can be implemented in JPanels that hold beads
	 * @return a bead of the same style as this
	 */
	public SimpleBeads copy()
	{
		return new SimpleBeads();
	}
	
	@Override
    /*
     * Paints this bead with the color black when called
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, 10, 10);
    }
}