/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import java.util.HashMap;
import java.util.ArrayList;

import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
     

/**
 *
 * @author eikes
 */

public class Environment{
    protected HashMap<Vector3f,InGameObject> environmentObjects;
    protected Node basicNode;
    protected ArrayList<Weapon> weapons = new ArrayList();
    protected Weapon inDistanceToPlayer;
    public Environment(){}
    
    
    /**
     * 
     * @param initializeEnvironment HashMap, Spatials, which represent models 
     * used in the Environment are mapped against their names
     * @param position 
     */
    
    public Environment(HashMap<String,Spatial> initializeEnvironment,
            Vector3f position){
        
    }

    public Node getBasicNode(){
        return basicNode;
    }
    
    public void updateEnvironment(float tpf){
        
    }
    
    /**
     * 
     * method is supposed to be used to verify wether method getWeaponInRadius
     * should be used
     * @param position
     * @return 
     */
    public boolean weaponInRadius(Vector3f position){
        int index = -1;
        for(Weapon weapon:weapons){
            if(!(weapon == null)){
                if(position.distance(weapon.getPosition()) < 5){
                    inDistanceToPlayer = weapon;
                    index = weapons.indexOf(weapon);
                }
            }
        }
        if(!(index == -1)){
            weapons.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @throws IllegalStateException thrown if return value would be null
     * @return 
     */
    public Weapon getWeaponInRadius(){
        try{
            if(!(inDistanceToPlayer == null)){
                return inDistanceToPlayer;
            }
            else{
                throw new IllegalStateException("method was called althow no"
                        + " weapon was in distance to player to be picked up");
            }
        }
        catch(IllegalStateException e){
            e.printStackTrace();
        }
       return null;
    }
    
    protected void makeWeaponMoves(float tpf){
        for(Weapon weapon:weapons){
            weapon.getSpatial().rotate((float)(0.5*tpf), 0, 0);
        }
    }
    
}
