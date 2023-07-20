import javax.swing.*;

/**
 * Solution for Popup to gather user inputs to initialize the Mancala Board
 * @author Henry Wahhab, Gary Crabtree, Andrii Vlasiuk
 * @version 1.0 5/5/23
 */

/**
 * Creates a JOptionPane to get user input for the number of beads in each pit and the board style
 */

public class Popup {
	
	/**
	 * Static method to create the popup and gather user information in the form of a String array that is returned
	 * @return a String array that will contain the user's selected options
	 */
    public static String[] getFromPopup() {
        JTextField beadsPerPit = new JTextField();
        String[] options = {"Black Style", "Disco Style", "Square Style", "Red Style"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        Object[] message = {"Initial Beads Per Pit (3 or 4):", beadsPerPit, "Select Style:", comboBox};
        String selectedOption = null;
        int bPP = 10;

        while (bPP >4 || bPP < 3) {
            //display popup
            int exited = 0;
            int option = JOptionPane.showConfirmDialog(null, message, "Game Setup", JOptionPane.OK_CANCEL_OPTION);

            //get user input
            if (option == JOptionPane.OK_OPTION) {
                //text filed input
                bPP = Integer.parseInt(beadsPerPit.getText());

                //dropdown input
                selectedOption = (String) comboBox.getSelectedItem();
            }
            
            else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                exited = 1;
            }

            //at this point BPP holds the int beads per pit
            //and selectedOption holds the selected style as a string
            if (bPP < 5 && bPP >2) {
                String[] returnValue = {String.valueOf(bPP), selectedOption};
                return returnValue;
            }
            else if (exited == 1){
                JOptionPane.showConfirmDialog(null, "Try again, start the game", "Cancelled", JOptionPane.OK_CANCEL_OPTION);
            }
            else {
                System.out.println("Invalid Selections");
                JOptionPane.showConfirmDialog(null, "Invalid Selections", "Error", JOptionPane.OK_CANCEL_OPTION);
            }
        }
        
        String[] thisShouldntHappen = {"how", "what"};
        return thisShouldntHappen;
    }
}
