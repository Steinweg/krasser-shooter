package game;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.font.BitmapText;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.math.Ray;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import com.jme3.terrain.heightmap.AbstractHeightMap; //delete later
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.terrain.geomipmap.TerrainLodControl;

import java.util.HashMap;
import javafx.application.Application;

import startupScreen.StartupScreen;

import baseClasses.Player;
import baseClasses.Spielstand;
import baseClasses.InGameObject;
import baseClasses.Weapon;

import game.lavaEnvironment.LavaEnvironment;

public class Main extends SimpleApplication{

    protected Geometry hitMark;
    private LavaEnvironment lavaLevel;
    private Node shootables;
    private static Player myself;
    private Spatial sceneModel;
    private Spatial character;
    private BulletAppState bulletAppState;
    private RigidBodyControl landscape;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private BitmapText crossHair;
    
    private TerrainQuad terrain; //delete later
    private Material mat_terrain;
    
    private boolean left = false, right = false, up = false, down = false;
    private boolean snipeState = false;
   
    
    
    public static void main(String[] args) {        
        Application.launch(StartupScreen.class ,args);
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initMyself();

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
               
        HashMap<String,Spatial> initializeLavaEnvironment = new HashMap();
        initializeLavaEnvironment.put("Zahnrad" , 
                assetManager.loadModel("Models/LavaLevel/"
                    + "Zahnrad/ogre/Cylinder.mesh.j3o"));
        initializeLavaEnvironment.put("BasicRifle",assetManager.loadModel(
                "Models/Weapons/ogre/Cylinder.mesh.j3o"));
        lavaLevel = new LavaEnvironment(initializeLavaEnvironment,
                new Vector3f(0.0f, -5f, 0.0f));
        sceneModel = lavaLevel.getBasicNode();
        shootables = new Node();
        for(InGameObject levelObject : lavaLevel.getObjects()){
            shootables.attachChild(levelObject.getSpatial().clone());
            rootNode.attachChild(levelObject.getSpatial());
            bulletAppState.getPhysicsSpace().add(levelObject.getRigidBodyControl());
        }

        shootables.attachChild(sceneModel.clone());        

        flyCam.setMoveSpeed(100);
        initNormState();
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape(sceneModel);
        landscape = new RigidBodyControl(sceneShape, 0);
        sceneModel.addControl(landscape);
        rootNode.attachChild(sceneModel);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        bulletAppState.getPhysicsSpace().add(myself.getCharacterControl());
        initTerrain();
    }

    private void initTerrain(){
        //delete later
    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(assetManager,
            "Common/MatDefs/Terrain/Terrain.j3md");

    
       /*
    mat_terrain.setTexture("Alpha", assetManager.loadTexture(
            "Textures/Terrain/splat/alphamap.png"));

 
    Texture grass = assetManager.loadTexture(
            "Textures/Terrain/splat/grass.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 64f);

    Texture dirt = assetManager.loadTexture(
            "Textures/Terrain/splat/dirt.jpg");
    dirt.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", dirt);
    mat_terrain.setFloat("Tex2Scale", 32f);

    Texture rock = assetManager.loadTexture(
            "Textures/Terrain/splat/road.jpg");
    rock.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", rock);
    mat_terrain.setFloat("Tex3Scale", 128f); 
    */
    
    AbstractHeightMap heightmap = null;
    Texture heightMapImage = assetManager.loadTexture(
            "Textures/LavaLevel/HeightMapImage.jpg");
    heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
    heightmap.load();

    /** 3. We have prepared material and heightmap.
     * Now we create the actual terrain:
     * 3.1) Create a TerrainQuad and name it "my terrain".
     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
     * 3.5) We supply the prepared heightmap itself.
     */
    int patchSize = 65;
    terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, -100, 0);
    terrain.setLocalScale(2f, 1f, 2f);
    rootNode.attachChild(terrain);

    /** 5. The LOD (level of detail) depends on were the camera is: */
    TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
    terrain.addControl(control);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        lavaLevel.updateEnvironment(tpf);
        lavaLevel.reactToPlayer(myself);
        if(lavaLevel.weaponInRadius(myself.getPosition())){
            myself.addWeapon(lavaLevel.getWeaponInRadius());
            rootNode.detachChild(lavaLevel.getWeaponInRadius().getSpatial());
        }
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        myself.getCharacterControl().setWalkDirection(walkDirection);
        character.setLocalTranslation(myself.getCharacterControl()
                .getPhysicsLocation());
        updateCamLocation();
        Spielstand.getInstance().update();

        if(myself.died()){
            myself.respawn();
        }
    }
    
    @Override
    public void simpleRender(RenderManager rm){
       
    }
    
    private void initMyself(){
        myself = Spielstand.getInstance().getMyself();
        //
        Vector3f characterPosition = new Vector3f(0, 30, 0);
        myself.initCharacterControl(characterPosition);
        myself.initWeapons(assetManager.loadModel("Models/Weapons/Fist/Fist.j3o"));
        // character is a Spatial on physicsLocation of controler
        character = assetManager.loadModel(myself.getPathToModel());
        character.setLocalTranslation(myself.getCharacterControl()
                .getPhysicsLocation());
        myself.getCharacterControl().setSpatial(character);
        
        rootNode.attachChild(character);
    }
  
   private void updateCamLocation(){
       if(snipeState){
            cam.setLocation(myself.getCharacterControl().getPhysicsLocation());
        }
        else{
           // vector translates
           Vector3f translation = cam.getDirection().mult(-5).add(0,3,0);
           cam.setLocation(myself.getCharacterControl().
                   getPhysicsLocation().add(translation));
        }
   }
   
  private void initSnipeState(){
        snipeState = true;
        initCrossHairs();
        initSnipeStateKeys();
        initHitMark();      
  }
  
  protected void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt"); 
        crossHair = myself.getCurrentWeapon().getCrossHairs(guiFont,
                    settings.getWidth(),settings.getHeight());
        guiNode.attachChild(crossHair);
   }
  
  protected void initHitMark() {
    Sphere sphere = new Sphere(30, 30, 0.2f);
    hitMark = new Geometry("HitMark", sphere);
    Material mark_mat = new Material(assetManager,               
             myself.getCurrentWeapon().getPathToHitMarkTexture());
    hitMark.setMaterial(mark_mat);
  }
  
  private void initSnipeStateKeys() {
            // trigger: left-click
    inputManager.addMapping("Shoot",
            new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            // triger: right-click
    inputManager.addMapping("switchToNormState",
            new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    inputManager.addListener(actionListenerSnipeState, "Shoot");
    inputManager.addListener(actionListenerSnipeState, "switchToNormState");
  }
  
    private ActionListener actionListenerSnipeState = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Shoot") && !keyPressed) {
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        shootables.collideWith(ray, results);
//        if()
        if (results.size() > 0) {
          CollisionResult closest = results.getClosestCollision();
          Geometry newMark = hitMark.clone();
          newMark.setLocalTranslation(closest.getContactPoint());
          rootNode.attachChild(newMark);
        }
      }
      else if (name.equals("switchToNormState") && keyPressed){
          inputManager.deleteTrigger("Shoot", 
                  new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
          inputManager.deleteTrigger("switchToNormState",
                  new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
          guiNode.detachChild(crossHair);
          initNormState();
      }
    } 
    };
  
    private void initNormState(){
        snipeState = false;
        initKeysNormState();
    }
    
  private void initKeysNormState() {
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("changeWeapon", new KeyTrigger(KeyInput.KEY_C));
    inputManager.addMapping("switchToSnipeState", 
            new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    inputManager.addListener(actionListenerNormState, "Left");
    inputManager.addListener(actionListenerNormState, "Right");
    inputManager.addListener(actionListenerNormState, "Up");
    inputManager.addListener(actionListenerNormState, "Down");
    inputManager.addListener(actionListenerNormState, "Jump");
    inputManager.addListener(actionListenerNormState, "changeWeapon");
    inputManager.addListener(actionListenerNormState, "switchToSnipeState");
  }

  private ActionListener actionListenerNormState = new ActionListener(){
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left")) {
            left = isPressed;
        } else if (name.equals("Right")) {
            right = isPressed;
        } else if (name.equals("Up")) {
            up = isPressed;
        } else if (name.equals("Down")) {
            down = isPressed;
        } else if (name.equals("Jump") && isPressed) {
            myself.getCharacterControl().jump();
        }
        else if (name.equals("changeWeapon") && isPressed){
            myself.changeWeapon();
        }
        else if(name.equals("switchToSnipeState") && isPressed){
            inputManager.deleteTrigger("Left",new KeyTrigger(KeyInput.KEY_A));
            inputManager.deleteTrigger("Right", new KeyTrigger(KeyInput.KEY_D));
            inputManager.deleteTrigger("Up", new KeyTrigger(KeyInput.KEY_W));
            inputManager.deleteTrigger("Down", new KeyTrigger(KeyInput.KEY_S));
            inputManager.deleteTrigger("Jump",
                        new KeyTrigger(KeyInput.KEY_SPACE));
            inputManager.deleteTrigger("switchToSnipeState", 
                        new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
            initSnipeState();
        }
    }
  };
}