import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Solution for RandomBeads which is one of the concrete strategies for the Beads Interface (Strategy Pattern) 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Class for randomly colored beads that can be used as a style in the board (in the form of a JPanel)
 * (Note: the beads do not stay the same color)
 */

public class RandomBeads extends JPanel implements Beads {
	
	/**
	 * Returns a string based on this design style so it can be recognized by other classes
	 * @return a string about this style of bead
	 */
	public String getStyle()
	{
		return "random";
	}
	
	/**
	 * Returns a new bead of the same style so it can be implemented in JPanels that hold beads
	 * @return a bead of the same style as this
	 */
	public RandomBeads copy()
	{
		return new RandomBeads();
	}
	
	@Override
    /*
     * Paints this bead with a random color when called
     */
    public void paintComponent(Graphics g) {    	
        super.paintComponent(g);
        
        Random rand = new Random();
        int r = rand.nextInt(10, 210);
        int gC = rand.nextInt(10, 210);
        int b = rand.nextInt(10, 210);
        
        g.setColor(new Color(r, gC, b));
        g.fillOval(0, 0, 10, 10);
    }
}