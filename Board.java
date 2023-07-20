import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for Board, the Model Class in MVC pattern for Mancala Project 
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Represents the board for a Mancala game and the Model in the MVC Pattern
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
	
	private int countUndo;
    private int prevCountUndo;
    private boolean prevFreeTurn;
	
    /**
     * Constructs a Board object
     * @param beads - the amount of beads placed into all the pits at the beginning of the game
     * precondition: beads must be 3 or 4 
     */
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
		
		prevFreeTurn = false;
		countUndo = 0;
        prevCountUndo = 0;
	}
	
	//Accessors
	
	/**
	 * Returns whole board array
	 * @return the array containing the number of beads
	 */
	public int[] getBoard()
	{
		return arr;
	}
	
	/**
	 * Returns the board from the previous turn
	 * @return the array containing the number of beads from the previous turn
	 */
	public int[] getPreviousBoard()
	{
		return prevArray;
	}
	
	/**
	 * Returns the number of undo clicks this player has made
	 * @return the number of undo's
	 */
	public int getCountUndo() 
	{
        return countUndo;
    }

	/**
	 * Returns the current player who is playing this turn
	 * @return the current player
	 */
	public char getCurrPlayer()
	{
		return player;
	}
	
	/**
	 * Returns the character referring to the next player and updates the undo counts
	 * @return a character corresponding to the next player
	 */
	public char nextPlayer()
	{
		prevCountUndo = countUndo;
        countUndo = 0;
        
		return getNextPlayer();
	}
	
	/**
	 * Returns the character referring to the next player
	 * @return a character corresponding to the next player
	 */
	public char getNextPlayer()
	{
		if (player == 'A') {return 'B';}
		
		else if (player =='B') {return 'A';} 
		
		else {return 'Z';} //Not valid
	}
	
	/**
	 * Returns the index of the mancala pit corresponding to the player character parameter
	 * @param c - the character representing the player
	 * @return the index of the players mancala pit
	 */
	public int getPlayerPit(char c)
	{
		if (c == 'A') {return MANCALA_A;}
		
		else if (c == 'B') {return MANCALA_B;}
		
		else {return -1;} //Not Valid
	}
	
	/**
	 * Returns the indices of all pits on that side of this player
	 * @param c - the character representing the player
	 * @return an array of all indices that player has control over
	 */
	public int[] getPlayerSide(char c)
	{
		if (c == 'A') {return PITS_A;}
		
		else if (c == 'B') {return PITS_B;} 
		
		else {return new int[0];} //Not valid
	}
	
	/**
	 * Returns the amount of beads in a specific pit given the index
	 * @param i - the index that will be searched
	 * @return the amount of beads in that index
	 * precondition: i is a valid index in the board
	 */
	public int getBeads(int i)
	{
		return arr[i];
	}
	
	/**
	 * Returns the amount of beads in a player's mancala given a character (A or B)
	 * @param c - a character representing the player
	 * @return the amount of beads in the player's mancala pit
	 */
	public int getMancalaPlayer(char c)
	{
		if (c == 'A') {return arr[MANCALA_A];}
		
		else if (c == 'B') {return arr[MANCALA_B];} 
		
		else {return -1;} //Not valid
	}
	
	/**
	 * Returns the amount of beads in Player A's Mancala
	 * @return the amount of beads that Player A has in their mancala
	 */
	public int getMancalaA()
	{
		return arr[MANCALA_A];
	}
	
	/**
	 * Returns the amount of beads in Player B's Mancala
	 * @return the amount of beads that Player B has in their mancala
	 */
	public int getMancalaB()
	{
		return arr[MANCALA_B];
	}
	
	/**
	 * Returns the previous undo count as an integer
	 * @return the previous undo count
	 */
	public int getPrevCount() 
	{
		return prevCountUndo;
	}
	
	//Mutators
	
	/**
	 * Plays the turn out based on the pit the player moves their beads from, follows all of the rules of the game and has undo functionality
	 * @param index - the index of the pit the player wants to move their beads from
	 * @throws Exception - depends on the rules that were broken, an exception will be thrown
	 */
	public void playTurn(int index)
	{	
		
		try 
		{
			//System.out.println(getCurrPlayer());
			
			prevArray = Arrays.copyOf(arr, arr.length); //For undo functionality
			
			if (index == MANCALA_A || index == MANCALA_B)
			{
				throw new Exception("Cannot choose a player's Mancala.");
			}
			
			if (!contains(getPlayerSide(player), index))
			{
				throw new Exception("Cannot choose a pit on your opponents side.");
			}
			
			if (arr[index] == 0)
			{
				throw new Exception("No beads in pit.");
			}
			
			int currIndex = index + 1; 
			int total = arr[index];
			arr[index] = 0;
			
			for (int beads = 0; beads < total; currIndex ++)
			{
				prevFreeTurn = false;
				
				if (currIndex % arr.length == getPlayerPit(getNextPlayer()))
				{
					continue;
				}

				//Last Bead of Turn
				if (beads == total - 1)
				{
					//Free Turn Functionality
					if (currIndex == getPlayerPit(player))
					{ 
						prevFreeTurn = true;
						
					}
					
					//Empty Mancala Pit for Last Bead Rule
					else if (contains(getPlayerSide(player), currIndex) && arr[currIndex % arr.length] == 0) //If the last stone dropped is empty AND on your side
					{
						int opIndex = (12 - currIndex) % arr.length;
						if (arr[opIndex] != 0)
						{
							//Put all beads on the opposite player's side into this pit
							arr[currIndex % arr.length] += arr[opIndex];
							arr[opIndex] = 0;
							arr[getPlayerPit(player)] += arr[currIndex % arr.length] + 1; //Add one for last bead
							arr[currIndex % arr.length] = -1; //Subtract one since last bead is still being added after so remove it by -1 
							beads ++;
						}
					}
					
				}
				
				arr[currIndex % arr.length] += 1; 
				beads ++;
			}
			
			
			if (!prevFreeTurn)
			{
				player = nextPlayer(); 
			}
			
			//If the previous turn was a free turn and count undo is already 3, make the button unpressable this turn but make the free turn
			//this player has able to be canceled/undo'd for an addition 3 times
			if (prevFreeTurn && countUndo == 3)
			{
				prevArray = null;
		        countUndo = 0;
		        prevCountUndo = 0;
			}
			
			update();
			
			
			if(gameOver())
			{
	            winner();
	        }
			
		}
				
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println();
		}
		
	}
	
	/**
	 * Sets the board back to the previous turn.
	 */
	public void undo() //For Undo Functionality, make button not pressable if prevArray is null
	{
		try 
		{
			arr = prevArray;
			prevArray = null; //No multiple undo's in a row allowed. (3 allowed per turn)
																	 
			//countUndo = prevCountUndo + 1;
			
			if (!prevFreeTurn)
			{
				countUndo = prevCountUndo + 1;
				player = getNextPlayer();
			}
			
			else
			{
				prevCountUndo = countUndo;
				countUndo ++;
			}
			
			update();
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
	 * @return a boolean, true if the game is over, false otherwise
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
	
	/**
	 * Helper method for printing out/bring up a window with the winner of the game shown, returns a character based on the player who won
	 * @return a character corresponding to the player
	 */
	private char winner()
    {
        if (arr[MANCALA_A] > arr[MANCALA_B])
        {
            System.out.println("Player A has won!");
            GameFrame.winnerPane("A");
            return 'A';
        }

        else if (arr[MANCALA_A] < arr[MANCALA_B])
        {
            System.out.println("Player B has won!");
            GameFrame.winnerPane("B");
            return 'B';
        }

        else
        {
            System.out.println("Tie!");
            GameFrame.winnerPane("Tie");
            return 'T';
        }
    }
	
	
	/**
	 * Helper method to check if an array contains a certain value
	 * @param a - the array to be checked
	 * @param v - the value to be searched for
	 * @return boolean whether or not the value was found in the array
	 */
	private static boolean contains(int[] a, int v)
	{
		for (int n : a)
		{
			if (n == v) {return true;}
		}
		
		return false;
	}
	
	//Below methods are just for debugging/visualization, they are not needed
	
	/**
	 * Debugging method to show how many mancalas are in each pit
	 * @return Returns a string that contains the mancalas in the board
	 */
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < arr.length; i ++)
		{
			s += arr[i] + " ";
		}
		
		return s;
	}
	
	/**
	 * Prints out a string that looks like the current mancala board
	 */
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
