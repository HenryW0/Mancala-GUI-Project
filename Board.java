import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for Model Class in MVC pattern for Mancala Project 
 * @author Gary Crabtree and Henry Wahhab
 * @version 1.0 4/8/23
 */

/**
 * Represents the board for a Mancala game 
 */

public class Board {
	
	public static final int MANCALA_A = 6;
	public static final int MANCALA_B = 13;
	
	public static final int[] PITS_A = {0, 1, 2, 3, 4, 5};
	public static final int[] PITS_B = {7, 8, 9, 10, 11, 12};
	
	private int[] arr; //In the form of [A1, A2, A3, A4, A5, A6, Mancala A, B1, B2, B3, B4, B5, B6, Mancala B]
	private int[] prevArray; //Copy of the previous turn of the board
	private ArrayList<ChangeListener> listeners;
	private char player;
	
	public Board(int beads)
	{
		arr = new int[14];
		player = 'A'; //First player is Player A
		
		for (int i = 0; i < arr.length; i ++)
		{
			arr[i] = beads;
		}
		
		arr[MANCALA_A] = 0;
		arr[MANCALA_B] = 0;
		
		prevArray = null;
		
		listeners = new ArrayList<ChangeListener>();
	}
	
	//Accessors
	
	/**
	 * Returns whole board array
	 * @return
	 */
	public int[] getBoard()
	{
		return arr;
	}
	
	/**
	 * Returns the board from the previous turn
	 * @return
	 */
	public int[] getPreviousBoard()
	{
		return prevArray;
	}
	
	/**
	 * Returns the current player
	 * @return
	 */
	public char getCurrPlayer()
	{
		return player;
	}
	
	/**
	 * Returns the character referring to the next player
	 * @return
	 */
	public char nextPlayer()
	{
		if (player == 'A') {return 'B';}
		
		else if (player =='B') {return 'A';} 
		
		else {return 'Z';} //Not valid
	}
	
	/**
	 * Returns the index of the mancala pit corresponding to the player character parameter
	 * @param c
	 * @return
	 */
	public int getPlayerPit(char c)
	{
		if (c == 'A') {return MANCALA_A;}
		
		else if (c == 'B') {return MANCALA_B;}
		
		else {return -1;} //Not Valid
	}
	
	/**
	 * Returns the indices of all pits on that side of this player
	 * @param index
	 */
	public int[] getPlayerSide(char c)
	{
		if (c == 'A') {return PITS_A;}
		
		else if (c == 'B') {return PITS_B;} 
		
		else {return new int[0];} //Not valid
	}
	
	/**
	 * Returns the amount of beads in a specific pit given the index
	 * @param i
	 * @return
	 */
	public int getBeads(int i)
	{
		return arr[i];
	}
	
	/**
	 * Returns the amount of beads in a player's mancala given a character (A or B)
	 * @param c
	 * @return
	 */
	public int getMancalaPlayer(char c)
	{
		if (c == 'A') {return arr[MANCALA_A];}
		
		else if (c == 'B') {return arr[MANCALA_B];} 
		
		else {return -1;} //Not valid
	}
	
	/**
	 * Returns the amount of beads in Player A's Mancala
	 * @return
	 */
	public int getMancalaA()
	{
		return arr[MANCALA_A];
	}
	
	/**
	 * Returns the amount of beads in Player B's Mancala
	 * @return
	 */
	public int getMancalaB()
	{
		return arr[MANCALA_B];
	}
	
	//Mutators
	
	public void playTurn(int index)
	{	
		try 
		{
			System.out.println(getCurrPlayer());
		
			prevArray = Arrays.copyOf(arr, arr.length); //For undo functionality
			int total = arr[index];
			arr[index] = 0;
			
			if (index == MANCALA_A || index == MANCALA_B)
			{
				throw new Exception("Cannot choose a player's pit.");
			}

			if (total == 0)
			{
				throw new Exception("No beads in pit.");
			}
			
			if (!contains(getPlayerSide(player), index))
			{
				throw new Exception("Cannot choose a pit on your opponents side.");
			}
				
			int currIndex = index + 1; 
			
			for (int beads = 0; beads < total; currIndex ++)
			{
				if (currIndex % arr.length == getPlayerPit(nextPlayer()))
				{
					continue;
				}

				//Last Bead of Turn
				if (beads == total - 1) //Can be removed from inside loop but would need to check if the next index is the opposite player's mancala instead
				{
					//Free Turn Functionality
					if (currIndex == getPlayerPit(player))
					{
						player = nextPlayer(); //Call next player since it will be called again after the method ends to return back to this player
					}
					
					//Empty Mancala Pit for Last Bead Rule
					else if (contains(getPlayerSide(player), currIndex) && arr[currIndex % arr.length] == 0) //If the last stone dropped is empty AND on your side
					{
						//Put all beads on the opposite player's side into this pit
						int opIndex = (12 - currIndex) % arr.length;
						arr[currIndex % arr.length] += arr[opIndex];
						arr[opIndex] = 0;
						beads ++;
					}
				}
				
				arr[currIndex % arr.length] += 1; 
				beads ++;
			}
		}
				
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println();
		}
		
		
		if(gameOver())
		{
            winner();
        }
		
		player = nextPlayer(); 
		update();
	}
	
	/**
	 * Sets the board back to the previous turn.
	 */
	public void undo() //For Undo Functionality, make button not pressable if prevArray is null
	{
		try 
		{
			arr = prevArray;
			prevArray = null; //No multiple undo's in a row allowed. (3 allowed per turn, this might have to be part of the controller to implement
																	 // or a countUndo instance variable can be implements and reset for each player each turn. 
			
			player = nextPlayer(); //Need to call nextPlayer to bring back to the original player who undo'd their turn
		}
		
		catch (Exception e)
		{
			System.out.println("Cannot do multiple undo's in a row.");
		}
	}
	
	/**
    * Attach a ChangeListener to the Board (model)
    * @param l - the ChangeListener to be attached
	*/
	public void attach(ChangeListener l)
	{
	    listeners.add(l);
	}
	
	/**
    * Helper method to notify listeners after using the mutator to change data in the board (model)
	*/
	private void update()
	{
		for (ChangeListener l : listeners)
		{
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Determines if the game is over based on the rules and current board, if so adds remaining stones to where they belong
	 * @return
	 */
	public boolean gameOver()
	{
        boolean returnValue = false;
        int A = 0;
        int B = 0;
        
        //add remaining stones in A side
        for (int i = 0; i < MANCALA_A; i++)
        {
            A += arr[i];
        }

        //add remaining stones in B side
        for (int i = 7; i < MANCALA_B; i++)
        {
            B += arr[i];
        }

        //Figure out if game has ended and add corresponding beads to player pit
        if (A == 0)
        {
            returnValue = true;
            arr[MANCALA_B] += B;
            
            for (int i = 7; i < MANCALA_B; i++)
            {
                arr[i] = 0;
            }
        }
        
        else if (B == 0)
        {
            returnValue = true;
            arr[MANCALA_A] += A;
            
            for (int i = 0; i < MANCALA_A; i++)
            {
                arr[i] = 0;
            }
        }

        return returnValue;
    }
	
	private char winner()
	{
		if(arr[MANCALA_A] > arr[MANCALA_B])
		{
			System.out.println("Player A has won!");
			return 'A';
		}
            
        else if (arr[MANCALA_A] < arr[MANCALA_B])
        {
        	System.out.println("Player B has won!");
        	return 'B';
        }   
        	
        else
        {
        	System.out.println("Tie!");
        	return 'T';
        }
	}
	
	
	/**
	 * Helper method to check if an array contains a certain value
	 * @param a
	 * @param v
	 * @return
	 */
	private static boolean contains(int[] a, int v)
	{
		for (int n : a)
		{
			if (n == v) {return true;}
		}
		
		return false;
	}
	
	//Below methods are just for debugging/visualization
	
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < arr.length; i ++)
		{
			s += arr[i] + " ";
		}
		
		return s;
	}
	
	public void printBoard()
	{
		System.out.print("   ");
		for (int i = 12; i > 6; i --)
		{
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
		
		System.out.print(arr[MANCALA_B] + "  ");
		for (char c = 'a'; c < 103; c ++)
		{
			System.out.print(c + "  ");
		}
		System.out.println(arr[MANCALA_A] + "  ");
		
		System.out.print("   ");
		for (int i = 0; i < 6; i ++)
		{
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
		
		System.out.println();
	}
	
}
