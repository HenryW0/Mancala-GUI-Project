import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for GameFrame, one of the View/Controller Classes in MVC pattern for Mancala Project 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Represents the entirety of the Mancala Board with an undo button, this is one of the View/Controllers in the MVC Pattern
 * This is also part of the context for the strategy pattern as it uses/has the beads interface as a parameter in the constructor
 */

public class GameFrame extends JFrame implements ChangeListener{

	private Board game;
	private JPanel buttonPanel;
	private Beads beadStyle;
	
	/**
	 * Constructs a GameFrame containing the mancala board and undo button 
	 * @param g - the board object that will be used as reference as the model
	 * @param sty - the bead style that will be used in the board (strategy pattern)
	 * precondition: all parameters are valid
	 */
	public GameFrame(Board g, Beads sty)
	{
		super("Mancala Game");
		game = g;
		beadStyle = sty;
		setLayout(new BorderLayout());
		setSize(1100, 450);
        
		buttonPanel = new JPanel(new FlowLayout());
		
        initUndo();
        
        add(buttonPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Helper method to initialize the undo button and attach the action listener to it as well as place the panel that contains it into the frame
	 */
	private void initUndo()
	{
		JButton undo = new JButton("Undo");
        
        undo.addActionListener(e -> {
            game.undo();
            //System.out.println("prevUndo " + game.getPrevCount());
            //System.out.println("undo " + game.getCountUndo());
        });
        
        if (game.getPreviousBoard() == null || (game.getPrevCount() + 1) > 3) 
        {
            undo.setEnabled(false); // Button is now disabled
        }
        
        else
        {
        	undo.setEnabled(true);
        }
        
        buttonPanel.add(undo);
	}
	
	/**
	 * Adds the game panel to the rest of the game frame
	 */
	public void addPanels()
	{
		add(new GamePanel(game, beadStyle), BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

	@Override
	/**
	 * Called when the data in the model has changed, repaints the frame and displays the button correctly again
	 */
	public void stateChanged(ChangeEvent e) {
		buttonPanel.removeAll();
		initUndo();
		add(buttonPanel);
		validate();
	}
	
	/**
	 * Brings up a JOptionPane to notify which player won at the end of the game
	 * @param b - the string that will be used to display who won
	 */
	public static void winnerPane(String b) {
		
		int option;
		
		if (b.equals("Tie"))
		{
			option = JOptionPane.showConfirmDialog(null, "TIE!", "Tie Game", JOptionPane.OK_CANCEL_OPTION);
		}
		
		else
		{
			Object[] message = {"Player " + b + " WINS!!!!"};
	        option = JOptionPane.showConfirmDialog(null, message, "We have a Winner!", JOptionPane.OK_CANCEL_OPTION);
		}

    }
	
}
