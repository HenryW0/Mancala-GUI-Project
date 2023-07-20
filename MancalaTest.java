
/**
 * Solution for MancalaTest, the main method to play the game
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * A class for running the Mancala game to be actually used
 */

public class MancalaTest {
	
	/**
	 * Creates a GameFrame object, brings up a pop up window asking for user selection and a blank mancala board
	 *  Attaches the associated listeners and creates the required panels/frames
	 * @param args - unused
	 */
    public static void main(String[] args) 
    {
    	Board blank = new Board(0);
        GameFrame dontPlay = new GameFrame(blank, new SimpleBeads());
        dontPlay.addPanels();

        String[] userInput = Popup.getFromPopup();
        dontPlay.dispose();

        Board mancala = new Board(Integer.parseInt(userInput[0]));
    	
        GameFrame play;
        
        if (userInput[1].equals("Disco Style")) {
            play = new GameFrame(mancala, new RandomBeads());
        }
        
        else if (userInput[1].equals("Black Style")) {
            play = new GameFrame(mancala, new SimpleBeads());
        }
        
        else if (userInput[1].equals("Red Style")) {
        	play = new GameFrame(mancala, new RedBeads());
        }
        
        else {           
            play = new GameFrame(mancala, new SquareBeads());
        }
        
        mancala.attach(play);
        play.addPanels();
    }
}
