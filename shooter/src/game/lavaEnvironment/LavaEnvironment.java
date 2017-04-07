/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.lavaEnvironment;

import baseClasses.Environment;

import java.util.HashMap;

import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.light.DirectionalLight;

/**
 *
 * @author eikes
 */
public class LavaEnvironment extends Environment{
    private Node zahnraeder;
   
    public LavaEnvironment(HashMap<String,Spatial> initializeLevel, Vector3f position){
        basicNode = new Node();
        zahnraeder = new Node();
        for(int i = -2; i < 25; i++){
            Spatial zahnrad = ((Spatial) initializeLevel.get("Zahnrad")).clone();
            Vector3f translation = new Vector3f(0.0f,-25,-i * 8 *7.9f);
            zahnrad.scale(8);
            zahnrad.setLocalTranslation(position.add(translation));
            zahnraeder.attachChild(zahnrad);
            if(zahnraeder.getChildIndex(zahnrad) % 2 == 0){
                zahnrad.rotate(0, 200, 0);
            }
        }
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        basicNode.attachChild(zahnraeder);
        basicNode.addLight(sun);
    }
    
    public Node getCollidables(){
        return zahnraeder;
    }
    
    @Override
    public void updateEnvironment(float tpf){
        rotateZahnraeder(tpf);
    }
    
    private void rotateZahnraeder(float tpf){
        for(int i = 0; i < zahnraeder.getQuantity(); i++){
            if(i % 2 == 0){
                zahnraeder.getChild(i).rotate(0,tpf,0);
            }
            else{
                zahnraeder.getChild(i).rotate(0, -tpf, 0);
            }
        }
    }
    
    @Override
    public Node getBasicNode(){
        System.out.println(zahnraeder.getQuantity());
        return basicNode;
    }
}
