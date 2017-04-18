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
public class ActionListenerNorm implements ActionListener{

    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean switchState;
    boolean jump;
    boolean changeWeapon;
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch(name){
            case ("Left"): {
                left = isPressed;
            } 
            case ("Right"): {
                right = isPressed;
            } 
            case ("Up"): {
                up = isPressed;
            }
            case ("Down"): {
                down = isPressed;
            }
            case("Jump"):{
                jump = isPressed;
            }
            case("SwitchState"):{
                changeWeapon = isPressed;
            }
        }
    }
    
}
