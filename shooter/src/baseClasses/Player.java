/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
/**
 *
 * @author eikes
 */
public class Player {
    
    private int health;
    private StringProperty name;

    public Player(String name){
        this.name = new ReadOnlyStringWrapper(name);
    }

    public void shot(int damage){
        health -= damage;
    }

    public StringProperty nameProperty(){
        return name;
    }
    
}
