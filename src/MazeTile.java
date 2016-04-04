/** @author Daniel Chen
  * @version 1.0, May 16, 2014
  * This program deals with all information concerning maze tiles.
  * This program has the ability to tell other programs whether or
  * not a particular is occupied or not, how many walls that tile 
  * has, and where those walls are located on the tile (top, sides, 
  * bottom).
  * <p>
  * Variable Dictionary<br>
  * Name            Type             Purpose<br>
  * north           boolean          Stores whether or not the north (top) side of a maze tile has a wall or not.<br>
  * south           boolean          Stores whether or not the south (bottom) side of a maze tile has a wall or not.<br>
  * east            boolean          Stores whether or not the east (right) side of a maze tile has a wall or not.<br>
  * west            boolean          Stores whether or not the west (left) side of a maze tile has a wall or not.<br>
  * available       boolean          Stores whether or not the tile is available or not. (Available as in if the player can move onto it or not.)
 */
public class MazeTile
{
  private boolean north, east, south, west, avaliable;
  
  /** The purpose of this method is to toggle whether or not a particular
    * tile has been visited/occupied or not.
   */
  public void toggleVisit ()
  {
    avaliable = !avaliable;
  }
  
  /** The purpose of this method is to check whether or not a particular
    * tile is currently being visited/occupied.
    * @return Whether tile is occupied or not.
   */
  public boolean isVisited ()
  {
    return avaliable;
  }
  
  /** The purpose of this method is to remove a wall. This ensures that
    * the maze path is connected.
    * <p>
    * Loops and Conditions<br>
    * if (n == 1) The purpose of this structure is to check which side of the wall is to be removed.
    * @param n Indicates which wall is to be removed.
   */
  public void removeWall (int n)
  {
    if (n == 1)
      north = false;
    else if (n == 2)
      east = false;
    else if (n == -1)
      south = false;
    else
      west = false;
  }
  
  /** The purpose of this method is to check which walls are existing on a
    * particular tile.
    * @return All four sides of a tile, indicating which ones have walls.
   */
  public boolean [] getWalls ()
  {
    boolean [] result = {north, east, south, west};
    return result;
  }
  
  /** The purpose of this method is to return information (when called)
    * about a particular tile.
    * <p>
    * Loops and Conditions<br>
    * if (!north) The purpose of this structure is to check which sides of that particular tile has walls.
    * @param result This variable stores information about whether or not the tile has walls as a numerical value.
    * @return A numerical value indicating which walls a tile has.
   */
  public String toString ()
  {
    int result = 0;
    if (!north)
      result += 1;
    if (!east)
      result += 2;
    if (!south)
      result += 4;
    if (!west)
      result += 8;
    return "" + result;
  }
  
  /** This is the class constructor. It sets the default
    * tile setting as having all 4 walls be present and to 
    * not be available.
   */
  public MazeTile ()
  {
    north = true;
    south = true;
    east = true;
    west = true;
    avaliable = false;
  }
}