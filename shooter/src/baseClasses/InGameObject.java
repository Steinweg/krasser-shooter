/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import com.jme3.math.Vector3f;

/**
 *
 * @author eikes
 */

public class InGameObject{
    private Vector3f position;

    public InGameObject(){}

    public InGameObject(Vector3f position){
        /**
         *
         * @param Vector3f to determine position of object
         */
        this.position = position;

    }

    public InGameObject copy(){
        return new InGameObject(position);
    }
}