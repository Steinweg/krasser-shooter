/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.lavaEnvironment;

import baseClasses.Environment;

import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.light.DirectionalLight;

import baseClasses.InGameObject;
import baseClasses.Player;

import game.weapons.BasicRifle;

/**
 *
 * @author eikes
 */
public class LavaEnvironment extends Environment{
    private ArrayList<Zahnrad> zahnraeder;
    private BasicRifle basicRifle;
    private InGameObject scene;
    private final float renderRadius = 1500;
    private DirectionalLight sun;
    private Node attached;
    
    public LavaEnvironment(HashMap<String,Spatial> initializeLevel, Vector3f position){
        zahnraeder = new ArrayList();
        attached = new Node();
        
        environmentObjects = new HashMap();
        for(int i = -20; i < 20; i++){
            Zahnrad zahnrad = new Zahnrad(((Spatial) initializeLevel
                    .get("Zahnrad").clone()));
            Vector3f translation = new Vector3f(0.0f,-25,i * 8 *7.9f);
            zahnrad.getSpatial().scale(8);
            zahnrad.getSpatial().setLocalTranslation(position.add(translation));
            zahnraeder.add(zahnrad);
            if(i % 2 == 0){
                zahnrad.getSpatial().rotate(0, 200, 0);
            }
            zahnrad.initializeRigidBodyControl(0);
            environmentObjects.put(position.add(translation),zahnrad);
        }
        
        scene = new InGameObject(
                initializeLevel.get("sceneTerrain").clone().scale(10));
        scene.getSpatial().setLocalTranslation(0,-35,0);
        scene.initializeRigidBodyControl(0);
        
        basicRifle = new BasicRifle((Spatial) initializeLevel.get("BasicRifle"));
        basicRifle.getRigidBodyControl().setPhysicsLocation(position
                .add(new Vector3f(0,-20,20f)));
        basicRifle.getSpatial().setLocalTranslation(position
                .add(new Vector3f(0,-20,20f)));
        basicRifle.getSpatial().scale((float)0.3);
        basicRifle.getSpatial().rotate(0, 0,(float)(3.14159265358979*0.5));
        
        environmentObjects.put(position.add(new Vector3f(0,-20,20f)),basicRifle);
        weapons.add(basicRifle);
    }
    
    @Override
    public void updateEnvironment(float tpf){
        rotateZahnraeder(tpf);
        makeWeaponMoves(tpf);
    }
   
    @Override
    public void reactToPlayer(Player player){
        super.reactToPlayer(player);
        translateZahnraeder(player.getPosition());
        translateScene(player.getPosition());
    }
    
    public InGameObject getScene(){
        return scene;
    }
  
    public Collection<InGameObject> getObjects(){
        return environmentObjects.values();
    }
    
    private void translateScene(Vector3f playerPosition){
        if(playerPosition.distance(scene.getPosition()) > renderRadius-1000){
            float x = playerPosition.x;
            float z = playerPosition.z;
            scene.getRigidBodyControl().setPhysicsLocation(new Vector3f(x,-35,z));
        }
    }
        
    private void translateZahnraeder(Vector3f position){
        for (Zahnrad zahnrad: zahnraeder){
            Vector3f zahnradPosition = zahnrad.getRigidBodyControl().getPhysicsLocation();
            if(zahnradPosition.distance(position) > renderRadius){
                if(zahnradPosition.z > position.z + 20 * 8 *7.9){
                    zahnrad.getRigidBodyControl().setPhysicsLocation(zahnradPosition
                        .add(new Vector3f(0.0f,0.0f,-40 * 8 *7.9f)));
                }
                else if(zahnradPosition.z < position.z + 20 * 8 *7.9){
                    zahnrad.getRigidBodyControl().setPhysicsLocation(zahnradPosition
                        .add(new Vector3f(0.0f,0.0f,40 * 8 *7.9f)));
                }
            }
        }
    } 
    
    private void rotateZahnraeder(float tpf){
        for(int i = 0; i < zahnraeder.size(); i++){
            if(i % 2 == 0){
                zahnraeder.get(i).getSpatial().rotate(0,(float) 0.25*tpf,0);
            }
            else{
                zahnraeder.get(i).getSpatial().rotate(0, -(float) 0.25*tpf, 0);
            }
        }
    }    
}