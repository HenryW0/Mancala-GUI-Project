import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.util.Random;

/**
 * Solution for GamePanel, one of the View Classes in MVC pattern for Mancala Project 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Represents the Mancala Board, this is one of the View/Controllers in the MVC Pattern
 * This is also part of the context for the strategy pattern as it uses/has the beads interface as a parameter in the constructor
 */

public class GamePanel extends JPanel {

	/**
	 * Constructor for GamePanel which draws all game components inside it and attaches corresponding ChangeListeners to the model
	 * @param g - the board object that will be used as reference as the model
	 * @param b - the bead style that will be used in the board (strategy pattern)
	 * precondition: all parameters are valid
	 */
	public GamePanel(Board g, Beads b)
	{
        Board game = g;
        Beads beadStyle = b;
        
        setLayout(new GridLayout(1, 4));
        setSize(1050, 400);
        
        
        if (beadStyle.getStyle().equals("random"))
        {
        	Random rand = new Random();
            int r = rand.nextInt(10, 210);
            int gC = rand.nextInt(10, 210);
            int bC = rand.nextInt(10, 210);
        	
        	setBorder(BorderFactory.createLineBorder(new Color(r, gC, bC), 10, true));
        }
        
        else if (beadStyle.getStyle().equals("red"))
        {
        	setBorder(BorderFactory.createLineBorder(Color.RED, 10, true));
        }
        
        else
        {
        	setBorder(BorderFactory.createLineBorder(new Color(102, 51, 0), 10, true));
        }
        
        JPanel leftPitss = new JPanel();
        JPanel rightPitss = new JPanel();
        leftPitss.setLayout(new GridLayout(2, 3));
        rightPitss.setLayout(new GridLayout(2, 3));


        //for Mancalas
        Mancala mancalaB = new Mancala(game, b, 90, 280, 'B');
        Mancala mancalaA = new Mancala(game, b, 90, 280, 'A');
        
        game.attach(mancalaA);
        game.attach(mancalaB);

        // Add CirclePanels to the frame for each pit
        //B pits
         
        for (int i = 1; i < 7; i++) {
            CirclePanel circlePanel = new CirclePanel(game, b, 40, 7 - i, "B");
        	
            game.attach(circlePanel);
            if (i < 4)
                leftPitss.add(circlePanel);
            else
                rightPitss.add(circlePanel);
        }

        //A pits
        for (int i = 1; i < 7; i++) {
            CirclePanel circlePanel = new CirclePanel(game, b, 40, i, "A");
        	
        	game.attach(circlePanel);
            if (i < 4)
                leftPitss.add(circlePanel);
            else
                rightPitss.add(circlePanel);
        }

        add(mancalaB);
        add(leftPitss);
        add(rightPitss);
        add(mancalaA);
	}
	
}
