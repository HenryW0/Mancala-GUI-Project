import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for CirclePanel, one of the View/Controller Classes in MVC pattern for Mancala Project 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Represents the pits in the Mancala Board and is one of the View/Controllers in the MVC Pattern
 * Does not directly implement strategy pattern, instead uses beads strategy pattern and matches the design based on the bead style used
 */

public class CirclePanel extends JPanel implements ChangeListener {

        private int radius;
        private int index;    //using this (and get current player) we can know which pit has been clicked
        private String index2;
        private Board game;
        private Beads beadStyle;
        
        /**
         * Constructor for a CirclePanel which represents a pit on the board
         * @param g - the board object that will be used as the reference to the model
         * @param b - the type of bead used in the pits (different style)
         * @param radius - the radius of the pits
         * @param i - the index of this pit
         * @param c - the player who's pit this corresponds to as a string (A or B)
         * precondition: all parameters are valid
         */
        public CirclePanel(Board g, Beads b, int radius, int i, String c) {
        	
        	game = g;
            this.radius = radius;
            index = i;
            index2 = c;
            beadStyle = b;
            
            //Clickable functionality
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println(index2 + index);
                    updateBoard();
                }
            });
        }


        /**
         * This method is the controller part of this class, it mutates the board values based on the indices
         */
		public void updateBoard() {
            //here we can call playTurn using INDEX
            // in model, A1-A6 is 0-5, and B1-B6 is 7-12, hence the modifications to index
			
            if (index2.equals("A")) {
                game.playTurn(index - 1);
            }
            if (index2.equals("B")) {
                game.playTurn(index + 6);
            }
        }

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            removeAll();
            g.setColor(Color.BLACK);
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
            JPanel beadsPanel = new JPanel();
            
            //Draw pit shape around
            
            shapeInit(g, beadsPanel, beadStyle.getStyle());
            
            beadsPanel.setLayout(new GridLayout(5, 4));
            
            int count;
            if (index2 == "A")
            {
            	count = index - 1;
            }
            else //If index2 == "B"
            {
            	count = index + 6;
            }
            
            for (int i = 0; i < game.getBeads(count); i++) {            	
            	Beads clone = beadStyle.copy();
            	beadsPanel.add((Component) clone);
            }
            
            this.add(beadsPanel);
            beadsPanel.validate();
            beadsPanel.repaint();
        }


		/**
		 * Helper method to draw different pit shapes
		 * @param g - the graphics object
		 * @param p - the panel that the drawing will be drawn in
		 * @param s - the string of the bead style used to determine the pit style
		 */
        private void shapeInit(Graphics g, JPanel p, String s)
        {
        	if (index2.equals("B"))
        	{ 		
        		if (s.equals("square"))
        		{
        			g.drawRect(5, radius * 2, radius * 2, radius * 2);
        		}
        		
        		else
        		{
        			g.drawOval(5, radius * 2, radius * 2, radius * 2);
        		}
        		
        		if (s.equals("red"))
        		{
        			g.setColor(Color.RED);
        		}
        		
        		g.drawString(index2 + index, radius - (radius / 4), radius + (radius / 2));
        		g.setColor(Color.BLACK);
        		p.setBounds(18, radius * 2 + 13, radius * 2 - 26, radius * 2 - 26);
        	}
        	
        	else
        	{        		
        		if (s.equals("square"))
        		{
        			g.drawRect(5, 0, radius * 2, radius * 2);
        		}
        		
        		else
        		{
        			g.drawOval(5, 0, radius * 2, radius * 2);
        		}
        		
        		if (s.equals("red"))
        		{
        			g.setColor(Color.RED);
        		}
        		
        		g.drawString(index2 + index, radius - (radius / 4), radius * 3);
        		g.setColor(Color.BLACK);
                p.setBounds(18, 13, radius * 2 - 26, radius * 2 - 26);    
        	}
        }
        
        
		@Override
		/**
		 * Repaints the pit panel when the data in the model has been changed
		 */
		public void stateChanged(ChangeEvent e) {
			repaint();
			revalidate();
		}
		
}