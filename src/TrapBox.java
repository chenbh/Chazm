import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**@author Eric Tseng
  * @version 5.0, June 8, 2014
  * This program will create the 'trap box'.
  * This trap box will randomly choose a question 
  * out of the 100 question database and will
  * allow the player to answer the question.
  * If the player answers correctly, this program
  * will return 'true'. If the player answers
  * incorrectly, the program will return 'false'.
  * <p>
  * Variable Dictionary<br>
  * Name                  Type               Purpose<br>
  * d                     JDialog            Gives program access to variables and methods within JDialog - for creating the trap box pop-up screen. <br>
  * questions             String [][]        A string array that will store the questions and their respective answers. <br>
  * boolean               correct            This variable contains the information concerning whether or not the user answered the latest question correctly or not.
 */
public class TrapBox extends JFrame implements ActionListener, WindowListener
{
  private JDialog d;
  private String [][] questions = new String [100][2];
  private String solution, cheat;
  private Main f;
  private JTextField inputField;
  private boolean correct;
  private double chance;
  
  /** This is the class constructor. It sets the
     * Main frame as it's frame and runs the method
     * that obtains the questions and answers from the
     * .txt file.
     * <p>
     * Loops and Conditions<br>
     * if (file.exists()) The purpose of this structure is to check if the cheat file exists or not.<br>
     * try {} catch (IOEx... The purpose of this structure is to make sure the program does not crash due to an IOException or a NumberFormatException.<br>
     * if (in.readLine... The purpose of this structure is to check if the cheats have been enabled.
     * @param e Variable associated to portion of code that prevents program from crashing due to an IOException.
     * @param e Variable associated to portion of code that prevents program from crashing due to a NumberFormatException.
     * @param frame Gives program access to Main frame.
     * @exception IOException If the cheat file was not properly read or does not exist.
     * @exception NumberFormatException If the value entered in for 'chance' is non-numerical.
    */
  public TrapBox (Main frame)
  {
    f = frame;
    getData();
    File file = new File ("Cheat.txt");
    if (file.exists ())
    {
      try
      {
        BufferedReader in = new BufferedReader (new FileReader (file));
        in.readLine (); //Comment/description for allowing cheats
        if (in.readLine ().equals ("allow cheats=true"))
        {
          in.readLine (); //Comment/description for universal answer
          cheat = in.readLine ().substring ("universal answer=".length ());
          in.readLine (); //Comment/description for chance
          chance = Double.parseDouble (in.readLine ().substring ("spawn percentage=".length ()));
        }
        else
        {
          cheat = null;
          chance = 0.20;
        }
      }
      catch (IOException e)
      {
        e.printStackTrace ();
        cheat = null;
        chance = 0.20;
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace ();
        chance = 0.20;
      }
    }
    else
    {
      cheat = null;
      chance = 0.20;
    }
  } 
  
  /** The purpose of this method is to get the questions and their
    * respective answers from the corresponding .txt file. (100 questions)
    * <p>
    * Loops & Conditions<br>
    * try{}catch(IOException...) Purpose is to allow program to read in questions and answers from .txt file without risking a crash.<br>
    * for (int x...) This loop is used to read in all questions and answers from the .txt file into a 2D string array.
    * @param input Variable used to read out data (questions & answers) from the .txt file.
    * @param x Loop variable used as a counter.
    * @param ioe Variable that prevents program from crashing due to an IOException.
    * @exception IOException If the file does not exist.
   */
  public void getData () //Just reads in all the questions and solutions from the .txt file.
  {
    try
    {
      BufferedReader input = new BufferedReader (new InputStreamReader (getClass ().getResource ("/resources/Questions.txt").openStream ()));
      for (int x = 0; x < 100; x++)
      {
        questions [x][0] = input.readLine();
        questions [x][1] = input.readLine();
      }
      input.close ();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace ();
    }
  }
  
  /** The purpose of this method is to generate the trap box and
    * the features surrounding the trap box. (Basically, every time
    * the player moves, there's a 25% chance there will be a trap and
    * if there is a trap, the question obtained will be randomly 
    * selected from the list of 100 questions.) Finally, this method
    * will also return true or false based on whether or not the player
    * answered the most recent question correctly or not.
    * <p>
    * Loops and Conditions<br>
    * if (Math.random() < 0.25) This if structure makes sure that the trap will only appear 25% of the time.
    * @param r This variable is used to get a random number between 1 and 100, and will become the index value of the question & answer that will be selected.
    * @return True if player answered correctly and false if they did not.
   */
  public int createTrap ()
  {
    if (Math.random () < chance)
    {
      int r = (int) (Math.random ()*100.0);
      solution = questions [r][1];
      box (questions [r][0]);
      return correct?0:1;
    }
    return 3;
  }
  
  /** Purpose of this method is to generate the pop up box
    * that contains the question, user input text box, and
    * button that allows them to confirm that they have answered.
    * @param question This variable stores the question.
    * @param d This variable gives the method access to the methods and variables of JDialog, allowing method to create the pop up window.
    * @param answerButton This variable is associated to the button that allows the user to confirm their answer. (Labelled "Answer".)
    * @param inputField This variable is associated to the text field that allows the user to put their solution in.
    * @param questionL This variable is used to create a JLabel with the desired question on it in order to place it on the window.
   */
  public void box (String question)
  {
    d = new JDialog (f, "It's a trap!");
    JButton answerButton = new JButton ("Answer");
    answerButton.setMnemonic (KeyEvent.VK_A);
    inputField = new JTextField (25);
    JLabel questionL = new JLabel (question);
    questionL.setFont (new Font ("Arial", Font.PLAIN, 16));
  
    d.add (questionL);
    d.add (inputField);
    d.add (answerButton);
    
    d.setLayout (new FlowLayout());
    d.pack ();
    
    answerButton.addActionListener (this);
    d.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
    d.setResizable (false);
    d.setLocationRelativeTo (f);
    d.setDefaultCloseOperation (JDialog.DO_NOTHING_ON_CLOSE);
    d.addWindowListener (this);
    d.setVisible (true);
  }
  
  /** The purpose of this method is to check what the user has inputted and to perform the corresponding action based on that user action. 
   * <p>
    * Loops and Conditions<br>
    * if (inputField...(cheat)) Purpose of this structure is to check if the user has entered the cheat code or not.<br>
    * if (!inputField.getText()...("")) Purpose of this structure is to make sure the user has not left their answer blank.
    * @param e This variable gives the method the ability to check what the user has entered - the ability to 'listen'.
    */
  public void actionPerformed (ActionEvent e)
  {
    if (cheat != null && inputField.getText ().equals (cheat))
      correct = true;
    else 
    {
      if (!inputField.getText ().equals (""))
        correct = inputField.getText().equalsIgnoreCase(solution);
      else
      {
        JOptionPane.showMessageDialog (f, "You must enter an answer!", "Question not answered", JOptionPane.ERROR_MESSAGE);
        inputField.requestFocusInWindow ();
        return;
      }
    }
    JOptionPane.showMessageDialog (f, correct?"You answered correctly!":"Wrong! The correct answer was " + solution + ".", correct?"Correct":"Incorrect", JOptionPane.INFORMATION_MESSAGE);
    d.dispose();
  }
  
  /** Purpose of this method is to not allow the user to just close the trap box.
    * @param e This variable gives the method the ability to check if the user has attempted to just close the trap box or not.
   */
  public void windowClosing(WindowEvent e)
  {
    JOptionPane.showMessageDialog (f, "You must answer the question!", "Question not answered", JOptionPane.ERROR_MESSAGE);
  }
  
  /** Follow methods are required for WindowListener interface - does nothing.
    * @param e This variable gives the method the ability to check if the user has attempted to just close the trap box or not.
   */
  public void windowActivated(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}
} 
