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
   private HashMap<Player, Integer> playerDeathCount;
   private static Spielstand ourInstance = new Spielstand();

    public static Spielstand getInstance() {
        return ourInstance;
    }

    public void initialize(List<Player> players){
        playerDeathCount = new HashMap<>();
        for(Player player :players)
            playerDeathCount.put(player,0);
    }

    public void addDeath(Player player){
        try {
            if (playerDeathCount.containsKey(player)) {
                playerDeathCount.put(player, playerDeathCount.get(player) + 1);
            }
            else {
                throw new IllegalArgumentException("Player doesn't exist, or game wasn't initialized");
            }
        }
        
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        
    }

    private Spielstand() {
    }   
    
}
