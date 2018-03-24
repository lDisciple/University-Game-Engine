
package EngineTests;

import ES.*;
import ES.Components.StaticModel;
import ES.Components.Terrain;
import ES.Components.Transform;
import ES.Components.UserControlled;
import ES.Systems.StaticRenderer;
import ES.Systems.TerrainRenderer;
import ES.Systems.UserControl;
import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Model;
import GameEngine.Renderer;
import Shaders.StaticShader;
import Shaders.TerrainShader;
import Temp.Camera;
import Temp.Light;
import Utilities.MatrixMath;
import Utilities.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class TerrainTest {
    
    public static void main(String[] args) {
        DisplayManager.create("ES Test");
        //Setup Entities
        
        
        Model model = ObjectLoader.loadGoxelOBJ("cupVoxel.obj");
        model.setReflectivity(0.1f);
        model.setShineDamper(10);
        
        EntitySystem es = new MappedEntitySystem();
        MetaEntity entity = new MetaEntity(es);
        entity.add(new Transform(0, 0, -5, 0, 0, 0, 0.1f));
        entity.add(new StaticModel(model));
        entity.add(new UserControlled());
        
        int terrainTexture = Loader.loadTexture("Textures/beachsand.png");
        MetaEntity terrain1 = new MetaEntity(es);
        terrain1.add(new Terrain(terrainTexture));
        Terrain.prepareTransform(-1, -1, es, terrain1.getId());
        
        MetaEntity terrain2 = new MetaEntity(es);
        terrain2.add(new Terrain(terrainTexture));
        Terrain.prepareTransform(0, -1, es, terrain2.getId());
        
        //Setup systems
        StaticShader staticShader = new StaticShader();
        staticShader.start();
        staticShader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        staticShader.stop();
        StaticRenderer entityRenderer = new StaticRenderer(staticShader);
        
        TerrainShader terrainShader = new TerrainShader();
        terrainShader.start();
        terrainShader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        terrainShader.stop();
        TerrainRenderer terrainRenderer = new TerrainRenderer(terrainShader);
        
        UserControl userControlSystem = new UserControl();
        
        
        Light light = new Light(new Vector3f(0,0,9), new Vector3f(1,1,1));
        Camera camera = new Camera(0,5,0);
        //Game loop
        long lastTime = System.currentTimeMillis();
        while (!Display.isCloseRequested()) {
            //Game logic
            long timePassed = System.currentTimeMillis() - lastTime;
            camera.move();
            //userControlSystem.update(es, timePassed);
            entity.getAs(Transform.class).changeRotationY(1);
            entity.getAs(Transform.class).changeRotationZ(1);
            lastTime = System.currentTimeMillis();
        //Render
            StaticRenderer.prepare();
            //Terrain
            terrainShader.start();
            terrainShader.loadCamera(camera);
            terrainShader.loadLight(light);
            terrainRenderer.render(es);
            terrainShader.stop();
            //Entities
            staticShader.start();
            staticShader.loadCamera(camera);
            staticShader.loadLight(light);
            entityRenderer.render(es);
            
            DisplayManager.update();
        }
        entityRenderer.cleanUp();
        terrainRenderer.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
