/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

/**
 *
 * @author eikes
 */

public class InGameObject{
    
    protected Spatial spatial;
    protected RigidBodyControl rigitBodyControl;

    public InGameObject(Spatial spatial){
        /**
         *
         * @param spatial for the visual representation of the Object
         */
        this.spatial = spatial;

    }

    public void initializeRigidBodyControl(int weight){
        CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape(spatial);
        rigitBodyControl = new RigidBodyControl(sceneShape, weight);
        spatial.addControl(rigitBodyControl);
    }
    
    public InGameObject copy(){
        return new InGameObject(spatial);
       
    }
    
    public Spatial getSpatial(){
        return spatial;
    }
    
    public RigidBodyControl getRigidBodyControl(){
        return rigitBodyControl;
    }
    
    public Vector3f getPosition(){
        return rigitBodyControl.getPhysicsLocation();
    }
}