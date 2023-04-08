import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution for Model Class in MVC pattern for Mancala Project 
 * @author Henry Wahhab
 * @version 1.0 4/6/23
 */

/**
 * Represents the board for a Mancala game 
 */

public class Board {
	
	public static final int MANCALA_A = 6;
	public static final int MANCALA_B = 13;
	
	private int[] arr; //In the form of [A1, A2, A3, A4, A5, A6, Mancala A, B1, B2, B3, B4, B5, B6, Mancala B]
	private ArrayList<ChangeListener> listeners;
	
	public Board(int beads)
	{
		arr = new int[14];
				
		for (int i = 0; i < arr.length; i ++)
		{
			arr[i] = beads;
		}
		
		arr[MANCALA_A] = 0;
		arr[MANCALA_B] = 0;
		
		listeners = new ArrayList<ChangeListener>();
	}
	
	//Accessor
	public int[] getBoard()
	{
		return arr;
	}
	
	//Mutator
	public void playTurn(int index, char p)
	{	
		try 
		{
			int total = arr[index];
			arr[index] = 0;
			int player;
			
			if (index == MANCALA_A || index == MANCALA_B)
			{
				throw new Exception("Cannot choose a player's pit.");
			}

			if (total == 0)
			{
				throw new Exception("No beads in pit.");
			}

			if (p == 'A')
			{
				player = MANCALA_B;
			}
				
			else if (p == 'B') 
			{
				player = MANCALA_A;
			}
				
			else
			{
				throw new Exception("Not a valid player.");
			}
				
			int currIndex = index + 1;
			
			for (int beads = 0; beads < total; currIndex ++)
			{
				if (currIndex % arr.length == player)
				{
					total ++;
					continue;
				}
				
				arr[currIndex % arr.length] += 1; 
				beads ++;
			}
		}
				
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		update();
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
