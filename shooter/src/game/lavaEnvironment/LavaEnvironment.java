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
import com.jme3.math.Matrix3f;
import com.jme3.scene.Spatial;
import com.jme3.light.DirectionalLight;

import baseClasses.InGameObject;

import game.weapons.BasicRifle;

/**
 *
 * @author eikes
 */
public class LavaEnvironment extends Environment{
    private ArrayList<Zahnrad> zahnraeder;
    private BasicRifle basicRifle;
    private final float renderRadius = 1500;
    private DirectionalLight sun;
    private Node attached;
    
    public LavaEnvironment(HashMap<String,Spatial> initializeLevel, Vector3f position){
        basicNode = new Node();
        zahnraeder = new ArrayList();
        attached = new Node();
        environmentObjects = new HashMap();
        for(int i = -20; i < 20; i++){
            Zahnrad zahnrad = new Zahnrad(((Spatial) initializeLevel
                    .get("Zahnrad").clone()));
            Vector3f translation = new Vector3f(0.0f,-25,-i * 8 *7.9f);
            zahnrad.getSpatial().scale(8);
            zahnrad.getSpatial().setLocalTranslation(position.add(translation));
            zahnraeder.add(zahnrad);
            if(i % 2 == 0){
                zahnrad.getSpatial().rotate(0, 200, 0);
            }
            zahnrad.initializeRigidBodyControl(0);
            environmentObjects.put(position.add(translation),zahnrad);
        }
        basicRifle = new BasicRifle((Spatial) initializeLevel.get("BasicRifle"));
        basicRifle.getSpatial().setLocalTranslation(position.add(new Vector3f(0,-20,20f)));
        basicRifle.getSpatial().scale((float)0.3);
        basicRifle.getSpatial().rotate(0, 0,(float)(3.14159265358979*0.5));
        environmentObjects.put(position.add(new Vector3f(0,-20,20f)),basicRifle);
        weapons.attachChild(basicRifle.getSpatial());
        sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        basicNode.addLight(sun);
    }
    
    @Override
    public void updateEnvironment(float tpf){
        rotateZahnraeder(tpf);
        makeWeaponMoves(tpf);
    }
    
    @Override
    public Node getBasicNode(){
        return basicNode;
    }
    
    
    public Collection<InGameObject> getObjects(Vector3f position){
        return environmentObjects.values();
    }
        
    public void translateZahnraeder(Vector3f position){
        for (Zahnrad zahnrad: zahnraeder){
            Vector3f zahnradPosition = zahnrad.getRigidBodyControl().getPhysicsLocation();
            if(zahnradPosition.distance(position) > renderRadius){
                if(zahnradPosition.x < position.x){
                    zahnrad.getRigidBodyControl().setPhysicsLocation(zahnradPosition
                        .add(new Vector3f(0.0f,0.0f,-40 * 8 *7.9f)));
                }
                else{
                    zahnrad.getRigidBodyControl().setPhysicsLocation(zahnradPosition
                        .add(new Vector3f(0.0f,0.0f,40 * 8 *7.9f)));
                }
            }
        }
    } 
    
    private void rotateZahnraeder(float tpf){
        for(int i = 0; i < zahnraeder.size(); i++){
            Vector3f angularVelocity = new Vector3f(0,(float)0.5*tpf,0);
            zahnraeder.get(i).getRigidBodyControl().setFriction(500);
            if(i % 2 == 0){
                zahnraeder.get(i).getRigidBodyControl().setAngularVelocity(angularVelocity);
                zahnraeder.get(i).getSpatial().rotate(0,(float) 0.25*tpf,0);
            }
            else{
                zahnraeder.get(i).getSpatial().rotate(0, -(float) 0.25*tpf, 0);
            }
        }
    }    
}
