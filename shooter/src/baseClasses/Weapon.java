/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseClasses;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Spatial;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;


/**
 *
 * @author eikes
 */
public class Weapon extends InGameObject{
    
    
    public Weapon(Spatial spatial){
        super(spatial);
         CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape(spatial);
        rigitBodyControl = new RigidBodyControl(sceneShape, 0);
    }
    
    public BitmapText getCrossHairs(BitmapFont guiFont, int settingsWidth,
           int settingsHeight){
            BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");
    ch.setLocalTranslation(settingsWidth / 2 - (ch.getLineWidth())/2, 
            settingsHeight / 2 + (ch.getLineHeight())/2, 0);
    return ch;
    }
    
    public final String getPathToHitMarkTexture(){
        return "Common/MatDefs/Misc/Unshaded.j3md";
    }
    
}
