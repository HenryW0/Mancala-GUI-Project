import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for Mancala, one of the View/Controller Classes in MVC pattern for Mancala Project 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Represents the player Mancala, this is one of the Views in the MVC Pattern
 * This is also part of the context for the strategy pattern as it uses/has the beads interface as a parameter in the constructor
 */


public class Mancala extends JPanel implements ChangeListener {
	
        private int width;
        private int height;
        private char player;
        private Board game;
        private Beads beadStyle;
        

        /**
         * Constructor for Mancala which represents a player's Mancala on the board
         * @param g - the board object that will be used as the reference to the model
         * @param b - the type of bead used in the pits (different style)
         * @param width - the width of the Mancala Oval shape
         * @param height- the height of the Mancala Oval shape
         * @param char - the player which this mancala belongs to (A or B)
         * precondition: All parameters are valid
         */
        public Mancala(Board game, Beads b, int width, int height, char player) 
        {
            this.game = game;
            this.width = width;
            this.height = height;
            this.player = player;
            beadStyle = b;    
        }

        @Override
        /*
         * Paints the player's mancala based on the bead style and displays the amount of beads inside it 
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            removeAll();
            
            Font myFont;
            
            if (beadStyle.getStyle().equals("red"))
            {
            	myFont = new Font("Consolas", Font.ITALIC, 20);
            }
            
            else
            {
            	 myFont = new Font("Arial", Font.BOLD, 20);
            }
            
            g.setFont(myFont);
            
            if (beadStyle.getStyle().equals("red"))
            {
            	g.setColor(Color.RED);
            	g.drawString("" + player, 135, 60);
            }
            
            else
            {
                g.setColor(Color.BLACK);
                g.drawString("" + player, 135, 60);
            	
            }
            
            g.setColor(Color.BLACK);
            
            if (beadStyle.getStyle().equals("square"))
        	{
            	g.drawRect(75, 35, (int) (1.5 * width), height);
        	}
            
            else
            {
            	g.drawOval(75, 35, (int) (1.5 * width), height);
            }

            JPanel beadsPanel = new JPanel();
            beadsPanel.setBounds(125, 60, width - 45, height - 45);
            beadsPanel.setLayout(new GridLayout(8, 5));

            for (int i = 0; i < game.getMancalaPlayer(player); i++) {
            	Beads clone = beadStyle.copy();
            	beadsPanel.add((Component) clone);            	
            }
            
            this.add(beadsPanel);
            beadsPanel.validate();
            beadsPanel.repaint();

        }

		@Override
		/**
		 * Repaints the mancala when the data in the model has been changed
		 */
		public void stateChanged(ChangeEvent e) {
			repaint();
			revalidate();
		}
}