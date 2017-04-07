package game;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

import java.util.HashMap;
import javafx.application.Application;

import startupScreen.StartupScreen;
import game.lavaEnvironment.LavaEnvironment;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */

public class Main extends SimpleApplication {

    protected Geometry mark;
    LavaEnvironment lavaLevel;
    
    public static void main(String[] args) {
        Application.launch(StartupScreen.class ,args);
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        HashMap<String,Spatial> initializeLavaEnvironment = new HashMap();
        initializeLavaEnvironment.put("Zahnrad" , assetManager.loadModel("Models/LavaLevel/"
                    + "Zahnrad/ogre/Cylinder.mesh.j3o"));
        lavaLevel = new LavaEnvironment(initializeLavaEnvironment,
                new Vector3f(0.0f, -5f, 0.0f));
        rootNode.attachChild(lavaLevel.getBasicNode());
    }

    @Override
    public void simpleUpdate(float tpf) {
        lavaLevel.updateEnvironment(tpf);
    }
}
