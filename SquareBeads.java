import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Solution for SquareBeads which is one of the concrete strategies for the Beads Interface (Strategy Pattern) 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Class for cyan, square colored beads that can be used as a style in the board (in the form of a JPanel)
 */

public class SquareBeads  extends JPanel implements Beads {

	/**
	 * Returns a string based on this design style so it can be recognized by other classes
	 * @return a string about this style of bead
	 */
    public String getStyle()
    {
        return "square";
    }

    /**
	 * Returns a new bead of the same style so it can be implemented in JPanels that hold beads
	 * @return a bead of the same style as this
	 */
    public SquareBeads copy()
	{
		return new SquareBeads();
	}
    
    @Override
    /*
     * Paints this bead with the color cyan when called and makes them square shaped
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(50,230,230));
        //g.fillOval(0, 0, 10, 10);
        g.fillRect(0,1,10,10);
    }
}