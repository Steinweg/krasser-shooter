/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses.control;

import com.jme3.input.controls.ActionListener;

/**
 *
 * @author eikes
 */
public class ActionListenerSnipe implements ActionListener{

    boolean shoot;
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch(name){
            case ("Shoot"):
                shoot = isPressed;
        }
    }
    
}
