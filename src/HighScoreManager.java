import java.io.*;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Font;;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;

/**@author Daniel Chen
  * @version 1.1, May 11, 2014
  * The program, as the class name suggests, is the high
  * score manager. This program is a dataclass that stores
  * all the current high scores, including name and the
  * corresponding score. There are a variety of methods within
  * this manager that allows the high scores to be cleared,
  * printed, checked for validity, added, accessed, and updated.
  * <p>
  * Variable Dictionary<br>
  * Name      Type                 Purpose<br>
  * list      ArrayList<score>     This variable stores all the top players and their corresponding scores.
 */
public class HighScoreManager
{
  ArrayList <Score> list;
  
  /** The purpose of this method is to clear all the currently stored high scores.
   */
  public void clear ()
  {
    list.clear ();
    update ();
  }
  
  /** The purpose of this method is to allow print the high scores.
    * <p>
    * Loops and Conditions<br>
    * if (list.size() > 0) The purpose of this structure is to check if there is actually something to be printed (instead of just a blank page).<br>
    * try{}catch(IOException... The purpose of this structure is to prevent the program from crashing when obtaining the menu and logo images.<br>
    * for (int i = 0... The purpose of this loop is to send in all data that is to be printed in a properly formatted way.
    * @param p Gives method access to Printer methods and variables.
    * @param e Variable associated to the portion of code that prevents the code from crashing due to an IOException error.
    * @param i Variable used to help the for loop - acts as a loop counter.
    * @return Returns whether or not the data was properly printed or not.
   */
  public boolean print ()
  {
    if (list.size () > 0)
    {
      Printer p = new Printer (new Font ("Serif", Font.PLAIN, 16), 1.5);
      p.println ("", "The Chazm High Scores", "");
      try
      {
        p.printImage (ImageIO.read (getClass ().getResource ("/resources/Menu.png")), new Point (0, 0), 10, true);
        p.printImage (ImageIO.read (getClass ().getResource ("/resources/Logo.png")), new Point (405, 0), 10, true);
      }
      catch (IOException e)
      {
        e.printStackTrace ();
      }
      p.println ();
      p.println ("Name", "", "Score");
      
      for (int i = 0; i < list.size (); i ++)
        p.println ((i+1) + ". " + list.get (i).getName (), "", "" + list.get (i).getScore ());
      return p.printUsingDialog ();
    }
    else
      return false;
  }
  
  /** The purpose of this method is to check if the current player
    * has beaten a high score or not.
    * <p>
    * Loops and Variables<br>
    * if (list.size() < 10) The purpose of this structure is to check if the current list length is under 10 or not.
    * for (int i = 0... The purpose of this loop is to compare the player's current score with the current high scores. (Only if the list currently has 10 stored high scores.)
    * @return True if the player has made it into the high scores and false if the player has not.
   */
  public boolean checkScore (int newScore)
  {
    if (list.size () < 10)
      return true;
    for (int i = 0; i < list.size (); i ++)
    {
      if (newScore > list.get (i).getScore ())
        return true;
    }
    return false;    
  }
  
  /** The purpose of this method is to add a player name and their corresponding
    * score to the list of high scores.
    * @param name Stores the name of the player.
    * @param score Stores the score of the player.
   */
  public void add (String name, int score)
  {
    add (new Score (name, score));
    update ();
  }
  
  /** The purpose of this method is to add a score to the list.
    * (In the correct position.)
    * <p>
    * Loops and Conditions<br>
    * if (list.size()... The purpose of this structure is to check if the newest score can just be added to the end of the list.<br>
    * for (int i = 0... The purpose of this loop is to check the placement of the newest score.<br>
    * if (newScore.getScore... The purpose of this structure is to check if the newest score is greater than any previous high scores. (Helps with placement.)<br>
    * if (list.size() > 10) The purpose of this structure is to remove the 11th score.
    * @param newScore Stores the newest score and is to be compared with old high scores to see if this newest score has made it into the high scores or not.
    * @param i Helps the for loop by acting as a loop counter.
   */
  private void add (Score newScore)
  {
    if (list.size () == 0 || (list.size () < 10 && newScore.getScore () <= list.get (list.size () - 1).getScore ()))
      list.add (newScore);
    else
    {
      for (int i = 0; i < list.size (); i ++)
      {
        if (newScore.getScore () > list.get (i).getScore ())
        {
          list.add (i, newScore);
          break;
        }
      }
    }
    if (list.size () > 10)
      list.remove (10);
  }
  
  /** The purpose of this method is to return a list of all the top players and their scores.
    * @return A list of all the top players and their respective scores.
   */
  public ArrayList <Score> getData ()
  {
    return list;
  }
  
  /** The purpose of this method is to read in all the currently stored high scores that have
    * been saved to a .txt file.
    * <p>
    * Loops and Conditions<br>
    * try {} catch (IO...) catch (Num...) The purpose of this structure is to prevent the program from crashing due to an IOException or a NumberFormatException error.<br>
    * for (int i = Integer.par... The purpose of this loop is to read in all the scores from the .txt file.
    * @param in This variable allows the method to access the .txt file storing the scores.
    * @param file This variable is allows the method to single in on the .txt file containing the Highscores.
    * @param i This variable helps the for loop by acting as a loop counter.
    * @param e This variable is associated to the portion of code that prevents the program from crashing due to an IOException.
    * @param e This variable is associated to the portion of code that prevents the program from crashing due to a NumberFormatException.
    * @param s Score This variable gives the program access to the variables and methods of Score.
    * @return Returns a true/false based on whether or not the program properly read in the data from the .txt file or not.
   */
  public boolean read ()
  {
    try
    {
      BufferedReader in = new BufferedReader (new FileReader (new File ("Highscores.txt")));
      for (int i = Integer.parseInt (in.readLine ()); i > 0; i --)
      {
        Score s = new Score (in.readLine (), Integer.parseInt (in.readLine ()));
        add (s);
      }
      in.close ();
    }
    catch (IOException e)
    {
      return false;
    }
    catch (NumberFormatException e)
    {
      return false;
    }
    return true;
  }
  
  /** The purpose of this method is to update the information in the
    * .txt file.
    * <p>
    * Loops and Conditions<br>
    * try{}catch(IOException... The purpose of this structure is to prevent the program from crashing due to an IOException.<br>
    * for (Score s: list) The purpose of this loop is to read in every single updated score into the .txt file.
    * @param out This variable allows the method to write out the data to the correct .txt file.
    * @param s This variable allows the method to have access to the variables and methods of Score.
    * @param e This variable is associated to the portion of code that disallows the program to crash from an IOException.
    * @return True/false based on whether or not the data could be written to the .txt file or not.
   */
  public boolean update ()
  {
    PrintWriter out;
    try
    {
      out = new PrintWriter (new FileWriter ("Highscores.txt"));
      out.println (list.size ());
      for (Score s: list)
      {
        out.println (s.getName ());
        out.println (s.getScore ());
      }
      out.close ();
    }
    catch (IOException e)
    {
      return false;
    }
    return true;
  }
  
  /** This is the class constructor. This constructor generates a new Arraylist of Scores
    * and also checks if the Highscores.txt file exists or not. If the .txt file does not exist,
    * then this constructor will generate one by calling the update method.
    * <p>
    * Loops and Conditions<br>
    * if (new File... the purpose of this structure is to check if the Highscores.txt file exists or not.
   */
  public HighScoreManager ()
  {
    list = new ArrayList <Score> ();
    if (new File ("Highscores.txt").exists ())
    {
      read ();
      update ();
    }
    else
      update ();
  }
}