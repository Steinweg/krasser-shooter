/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.weapons;

import com.jme3.font.BitmapText;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Spatial;

import baseClasses.Weapon;

/**
 *
 * @author eikes
 */
public class BasicRifle extends Weapon{
    
    public BasicRifle(Spatial spatial){
        super(spatial);
    }
    
   @Override
   public BitmapText getCrossHairs(BitmapFont guiFont, int settingsWidth,
           int settingsHeight) {
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("   |\n--  --\n   |");
    ch.setLocalTranslation(settingsWidth / 2 - (ch.getLineWidth())/2, 
            settingsHeight / 2 + (ch.getLineHeight()*3)/2, 0);
    return ch;
   }
   
   
   
}
