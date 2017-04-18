/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import java.util.HashMap;

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
    protected Node weapons = new Node();
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
    
    protected void makeWeaponMoves(float tpf){
        for(int i = 0; i < weapons.getQuantity(); i++){
            weapons.getChild(i).rotate((float)(0.5*tpf), 0, 0);
        }
    }
    
}
