
/**
 * Write a description of class Player here.
 *
 * @author Josh Staples
 * @version 20221214
 */
public class Player
{
    private String playerName;
    private int point = -1; // default value
    
    public Player(String playerName) {
        this.playerName = playerName;
    }
    
    public int getPoint() {
        return point;
    }
    
    public String getName() {
        return playerName;
    }
    
    public void setPoint(int newPoint) {
        point = newPoint;
    }
    
    public void setName(String newName) {
        playerName = newName;
    }
 }
