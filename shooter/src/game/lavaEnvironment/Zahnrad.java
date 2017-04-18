/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.lavaEnvironment;

import com.jme3.scene.Spatial;

import baseClasses.InGameObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;

/**
 *
 * @author eikes
 */
public class Zahnrad extends InGameObject{
    
    private RigidBodyControl rigitBodyControl;
    
    protected Zahnrad(Spatial zahnrad){
        super(zahnrad);
        
    }

    
}
