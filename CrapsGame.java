
/**
 * Write a description of class CrapsGame here.
 *
 * @author Josh Staples
 * @version 20221214
 */
import java.lang.Math;
import java.util.Scanner;

public class CrapsGame
{    
    private static Scanner systemInput = new Scanner(System.in);
    private static int NUM_PLAYERS;
    private static int playersPlaying;
    private static int currentPlace = 1;
    
    private static Player[] leaderboard;
    // main method
    public static void main(String args[]) {
        System.out.print("How many players do you want to have in the game?: ");
        NUM_PLAYERS = systemInput.nextInt(); // variable initialization based on the player count
        playersPlaying = NUM_PLAYERS; 
        leaderboard = new Player[NUM_PLAYERS];
        
        // initializes the user player along with the other "CPU players"
        Player[] crapsPlayers = new Player[NUM_PLAYERS];
        crapsPlayers[0] = new Player(getUserName());
        
        for(int i = 1; i < NUM_PLAYERS; i++) { // creates CPU players to fill rest of the list
            crapsPlayers[i] = new Player("CPU " + i);
        }
        
        startGame(crapsPlayers); // starts the craps game
    } // end main()
    
    // manages all aspects of the Craps game
    public static void startGame(Player[] crapsPlayers) {
        System.out.print("\nAre you ready to start the game?: ");
        
        if(getYesOrNo().equals("NO")) { // ends program if no
            return;
        }
        
        int roll = 1;
        System.out.println("Roll " + roll + ": "); // manages the first roll for setting points/naturals/craps - rules are managed differently
        for(Player player : crapsPlayers) {
            int point = getSumOfDice();
            System.out.print(" " + player.getName() + ": " + point);
            if(checkRoll(point) == -1) { // if a player rolls craps
                putLastAvailable(player);
                playersPlaying--;
                System.out.print(" | Craps!\n");
            } else if(checkRoll(point) == 1) { // if a player rolls a natural
                putFirstAvailable(player);
                playersPlaying--;
                System.out.print(" | Natural!\n");
            } else { // otherwise, sets to point
                player.setPoint(point);
                System.out.print(" | Point!\n");
            }
        
        }
        
        while(playersPlaying > 1) { // continues all the way to 1 player left
            System.out.print("\nWould you like to continue?: ");
            if(getYesOrNo().equals("NO")) { // ends program if no
                return;
            }
            roll++;
            
            System.out.println("Roll " + roll + ": ");
            for(Player player : crapsPlayers) {
                if(!arrayContains(leaderboard, player)) { // if a player isnt already on the leaderboard (craps/rolled point/natural)
                    int currentRoll = getSumOfDice();
                    System.out.print(" " + player.getName() + ": " + currentRoll); // prints name with current roll
                    if(currentRoll == player.getPoint()) { // adds player to top leaderboard spot if they roll point
                        putFirstAvailable(player);
                        playersPlaying--; // decreases player count
                        System.out.print(" | Rolled Point!");
                    } else if(checkRoll(currentRoll) == -1) {
                        putLastAvailable(player); // adds player to bottom of the leaderboard if they roll craps
                        playersPlaying--; // decreases player count
                        System.out.print(" | Craps!");
                    }
                    System.out.println();
                }
            }    
        }
        
        for(Player player : crapsPlayers) { // puts the last remaining player into the first spot in the leaderboard
            if(!arrayContains(leaderboard, player)) {
                putFirstAvailable(player);
                break;
            }
        }
        
        System.out.println("\nThe game of craps is over.");
        printLeaderboard(); // prints leaderboard after the game is finished
    } // end startGame()
    
    // puts the player input into the first available leaderboard position
    public static void putFirstAvailable(Player crapsPlayer) {
        for(int i = 0; i < leaderboard.length; i++) {
            if(leaderboard[i] == null) {
                leaderboard[i] = crapsPlayer;
                break;
            }
        }
    } // end putFirstAvailable()
    
    // puts the player input into the last available leaderboard spot
    public static void putLastAvailable(Player crapsPlayer) {
        for(int i = leaderboard.length - 1; i >= 0; i--) {
            if(leaderboard[i] == null) { // if the spot isn't taken, set the player to that spot
                leaderboard[i] = crapsPlayer;
                break;
            }
        }
    } // end putLastAvailable()
    
    // prints the leaderboard of players
    public static void printLeaderboard() {
        System.out.println("--- Craps Leaderboard: ---"); // header
        for(int i = 0; i < leaderboard.length; i++) {
            System.out.print(" " + (i+1) + ": " + leaderboard[i].getName());
            if(i == 0) { // puts " | Winner" in front of the first name
                System.out.print(" | Winner!");
            }
            System.out.println();
        }
    } // end printLeaderboard()
    
    // checks if an array contains the object, in this case a player
    public static boolean arrayContains(Player[] playerList, Player crapsPlayer) {
        for(Player player : playerList) {
            if(crapsPlayer.equals(player)) { // if the player in the list is the same as the input
                return true;
            }
        }
        return false;
    } // end arrayContains()
    
    // gets a simple yes/no input from the user
    public static String getYesOrNo() {
        String response;
        do {
            response = systemInput.next().toUpperCase(); // capitalizes for convenience
            if(!(response.equals("YES") || response.equals("NO"))) { // if user puts something other than yes/no
                System.out.print("Your response was invalid. Try \"yes\" or \"no\": ");
            }
        } while(!(response.equals("YES") || response.equals("NO")));
        
        return response;
    } // end getYesOrNo()
    
    // rolls two independent dice and returns the sum of their rolls
    public static int getSumOfDice() {
        int diceOne = 1 + (int)(Math.random() * 6); // rolls a "dice" between 1 and 6
        int diceTwo = 1 + (int)(Math.random() * 6); // rolls a second indepdendent "dice";
        
        return diceOne + diceTwo;
    } // end getSumOfDice()
    
    // returns 1 if the roll is a natural, 0 if its nothing, or -1 if its craps
    public static int checkRoll(int roll) {
        switch(roll) {
            case 2: return -1;
            case 3: return -1;
            case 12: return -1;
            case 7: return 1;
            case 11: return 1;
            default: return 0;
        }
    }
    
    // asks the user for their name and returns the result
    public static String getUserName() {
        System.out.print("What is your name?: ");
        return systemInput.next();
    } // end getUserName()
}
