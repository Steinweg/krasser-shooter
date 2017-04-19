/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author eikes
 */
public class Spielstand {
   private Player myself;
   private HashMap<Player, Integer> playerDeathCount;
   private static Spielstand ourInstance = new Spielstand();

    public static Spielstand getInstance() {
        return ourInstance;
    }

    public void initialize(List<Player> players, Player myself){
        this.myself = myself;
        playerDeathCount = new HashMap<>();
        for(Player player : players){
            playerDeathCount.put(player,0);
        }
    }

    
    /**
     * function handels death of a Player in game
     * @param died player which died 
     * @param killer if player who died killed himself, 
     * just give null as second parameter 
     */
    public void death(Player died, Player killer){
        try {
            if (playerDeathCount.containsKey(died) && 
                    playerDeathCount.containsKey(killer)) {
                playerDeathCount.put(died, playerDeathCount.get(died) + 1);
                playerDeathCount.put(killer, playerDeathCount.get(killer) - 1);
            }
            else if(playerDeathCount.containsKey(died) && killer == null){
                playerDeathCount.put(died, playerDeathCount.get(died) + 1);
            }
            else {
                throw new IllegalArgumentException("Player doesn't exist, or game wasn't initialized");
            }
        }
        
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        
    }

    public Player getMyself(){
        return myself;
    }
    
    private Spielstand() {
    }   
    
}
