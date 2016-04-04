import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/** @author Daniel Chen
  * @version 2.0, May 14, 2014
  * This program is the driver program. It takes all the 
  * other code files, puts them together, and runs it. 
  * This program also deals with the menu option selection.
  * However, this program does not generate the buttons.
  * <p>
  * Variable Dictionary <br>
  * Name             Type             Purpose<br>
  * layout           CardLayout       To give the program access to variables and methods of CardLayout - allows programmer to set up screen.<br>
  * panel            JPanel           Allows program to access variables and methods within JPanel. <br>
  * gamePanel        GamePanel        Allows program to have access to variables and methods of GamePanel. <br>
  * highScorePanel   HighScorePanel   Allows program to have access to variables and methods of HighScorePanel. <br>
  * highScoreManager HighScoreManager Allows program to have access to variables and methods of HighScoreManager.
 */
public class Main extends JFrame implements ActionListener
{
  CardLayout layout;
  JPanel panel;
  GamePanel gamePanel;
  HighScorePanel highScorePanel;
  HighScoreManager highScoreManager;
  /** This is the class constructor. This class constructor will create all the panels
    * and will only display the desired ones when the program is running. The layout
    * used is CardLayout.
    * <p>
    * Loops and Conditions<br>
    * try {} catch (InterruptedException... The purpose of this structure is to make sure that the program will not crash due to an InterruptedException.
    * @param e Variable associated to the portion of code preventing program from crashing due to an InterruptedException error.
   */
  public Main ()
  {
    super ("The Chazm");
    layout = new CardLayout ();
    panel = new JPanel (layout);
    panel.setPreferredSize (new Dimension (900, 900));
    add (panel);
    
    setResizable (false);
    pack();
    setDefaultCloseOperation (EXIT_ON_CLOSE); 
    setLocationRelativeTo (null);
    
    highScoreManager = new HighScoreManager ();
    highScorePanel = new HighScorePanel (highScoreManager, this);
    EndPanel endPanel = new EndPanel (highScorePanel, this);
    gamePanel = new GamePanel (endPanel, this);
    MenuPanel mainMenu = new MenuPanel (this);
    SplashPanel splashPanel = new SplashPanel (this);
    InstructionPanel instructionPanel = new InstructionPanel (this);
    CreditPanel creditPanel = new CreditPanel (this);
    LoadingPanel loadingPanel = new LoadingPanel (this);
    
    panel.add (splashPanel, "Splash");
    panel.add (loadingPanel, "Loading");
    panel.add (gamePanel, "Game");
    panel.add (endPanel, "End");
    panel.add (instructionPanel, "Instructions");
    panel.add (highScorePanel, "High Scores");
    panel.add (creditPanel, "Credits");
    panel.add (mainMenu, "Main Menu");
    
    setVisible (true);
    switchPanel ("Splash");
    try
    {
      Thread.sleep (6000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace ();
    }
    switchPanel ("Main Menu");
  }
  
  /** Purpose of this method is to switch from one screen (panel)
    * to another.
    * @param name Contains the name associated to the window to be displayed.
   */
  public void switchPanel (String name)
  {
    layout.show (panel, name);
    panel.revalidate ();
    panel.paintImmediately (0, 0, panel.getWidth (), panel.getHeight ());
    repaint ();
  }
  
  /**The purpose of this method is to check which menu choice the user has made, and to run the proper code for whichever option
    * the user has chosen.
    * <p>
    * Loop/Structure, Purpose <br>
    * if (ae.getActionCommand()..., this structure is set up so that the program can check whether which menu option the user has made,
    * allowing the program to run the correct code for their corresponding menu option. <br>
    * try {} catch (IOExcep... The purpose of this structure is to make sure that the program won't crash when trying to run the .chm file.
    * @param e Variable associated to portion of code that prevents program from crashing due to IOException error.
    * @param  ae  Variable that allows method to receive an action from the user - which, in this case, is choosing a menu option.
    * @exception IOException If the program fails to run the .chm.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if(ae.getActionCommand().equals("Easy Mode"))
    {
      gamePanel.startNewGame (0);
      highScorePanel.update ();
    }
    else if(ae.getActionCommand().equals("Medium Mode"))
    {
      gamePanel.startNewGame (1);
      highScorePanel.update ();
    }
    else if(ae.getActionCommand().equals("Hard Mode"))
    {
      gamePanel.startNewGame (2);
      highScorePanel.update ();
    }
    else if(ae.getActionCommand().equals("Quit"))
      System.exit (0);
    else if (ae.getActionCommand().equals("About"))
    {
      try
      {
        Runtime.getRuntime ( ).exec ("hh.exe " + System.getProperty("user.dir") + "/The Chazm.chm");
      }
      catch (IOException e)
      {
        e.printStackTrace ();
      }
    }    else
      switchPanel (ae.getActionCommand ());
  }
  
  /**This is the main method of the program - puts together all portions of program and runs it.
    * <p>
    * Loops and Conditions<br>
    * try{}catch(Exception... The purpose of this structure is to prevent the entire program from crashing from any Exception.
    * @param e Variable associated to the portion of code that prevents entire program from crashing form any Exception.
    * @param  args  Accepts all the parameters from the class you are running in this main method to make sure the program will not class.
    * @exception Exception If the program fails to run.
    */
  public static void main (String [] args)
  {
    try
    {
      new Main ();
    }
    catch (Exception e)
    {
      e.printStackTrace ();
      System.exit (0);
    }
  }
}