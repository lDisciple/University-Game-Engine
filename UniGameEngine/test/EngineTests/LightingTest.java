
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Model;
import GameEngine.Renderer;
import Shaders.StaticShader;
import Temp.Camera;
import Temp.Entity;
import Temp.Light;
import Utilities.MatrixMath;
import Utilities.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class LightingTest {
    
    public static void main(String[] args) {
        DisplayManager.create("Lighting Test");
        
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();
        shader.start();
        shader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        shader.stop();
        
        Model model = ObjectLoader.loadGoxelOBJ("cupVoxel.obj");
        Entity entity = new Entity(model, new Vector3f(0f,0,-5), 0, 0, 0, 0.1f);
        
        Light light = new Light(new Vector3f(0,0,9), new Vector3f(1,1,1));
        
        Camera camera = new Camera();
        while (!Display.isCloseRequested()) {
            //Game logic
            camera.move();
            entity.rotate(0f,1f,1f);
            //Render
            renderer.prepare();
            shader.start();
            //Load camera
            shader.loadCamera(camera);
            shader.loadLight(light);
            //END
            renderer.renderEntity(entity,shader);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
