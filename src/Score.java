/**@author Daniel Chen
  * @version 1.0, May 20, 2014
  * This program will store the score
  * and will also return scores, names,
  * and various other pieces of information
  * required for score. This program is 
  * a dataclass.
  * <p>
  * Variable Dictionary <br>
  * Name            Type           Purpose<br>
  * name            String         Stores name of player. <br>
  * score           int            Stores score of player.
 */
public class Score
{
  private String name;
  private int score;
  
  /** Purpose of this method is to return the score of
    * the desired top player when called.
    * @return Score of the desired top player.
   */
  public int getScore ()
  {
    return score;
  }
  
  /** Purpose of this method is to return the name
    * of the desired top player when called.
    * @return The name of the desired top player.
   */
  public String getName ()
  {
    return name;
  }
  
  /** Purpose of this method is to return the name
    * and the score of the desired top player.
    * @return Name of top player and their respective score, separated by a space.
   */
  public String toString ()
  {
    return name + " " + score;
  }
  
  /** This is the class constructor. Whatever name
    * and respective score passed in will be stored.
    * @param name This variable stores the name.
    * @param score This variable stores the score.
   */
  public Score (String name, int score)
  {
    this.name = name;
    this.score = score;
  }
}