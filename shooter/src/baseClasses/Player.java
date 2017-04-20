/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import com.jme3.scene.Spatial;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import java.util.ArrayList;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;


import game.weapons.Fist;
/**
 *
 * @author eikes
 */
public class Player{
    
    private int health;
    private int currentWeapon;
    private Vector3f position;
    private StringProperty name;
    private ArrayList<Weapon> weapons;
    private CharacterControl characterControl;
    
    public Player(String name){
        this.name = new ReadOnlyStringWrapper(name);
        currentWeapon = 0;        
    }

    public void initWeapons(Spatial fist){
        weapons  = new ArrayList();
        weapons.add(new Fist(fist));
    }
    
    public void shot(int damage){
        health -= damage;
    }

    public StringProperty nameProperty(){
        return name;
    }
    
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    
    public Weapon getCurrentWeapon(){
        return weapons.get(currentWeapon);
    }
    
    public Weapon changeWeapon(){
        if(currentWeapon < weapons.size() - 1){
            currentWeapon++;
        }
        else{
            currentWeapon = 0;
        }
        System.out.println(weapons.size());
        for(Weapon weapon: weapons){
            System.out.println(weapon.getPathToHitMarkTexture());
        }
        return weapons.get(currentWeapon);
    }
    
    public final String getPathToModel(){
        return "Models/Character/Character.j3o";
    }
    
    public void initCharacterControl(Vector3f position){
        CapsuleCollisionShape capsuleShape = 
                new CapsuleCollisionShape(1.5f, 6f, 1);
        characterControl = 
                new CharacterControl(capsuleShape, 0.05f);
        characterControl.setJumpSpeed(20);
        characterControl.setFallSpeed(30);
        characterControl.setGravity(30);
        characterControl.setPhysicsLocation(position);
    }
    
    public CharacterControl getCharacterControl(){
        return characterControl;
    }
    
    public Vector3f getPosition(){
        return characterControl.getPhysicsLocation();
    }
    
}