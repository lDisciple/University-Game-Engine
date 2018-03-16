
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Model;
import GameEngine.Renderer;
import Shaders.StaticShader;
import Temp.Camera;
import Temp.Entity;
import Utilities.MatrixMath;
import Utilities.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class LoadGoxelTest {
    
    public static void main(String[] args) {
        DisplayManager.create("Goxel loading Test");
        
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();
        shader.start();
        shader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        shader.stop();
        
        Model model = ObjectLoader.loadGoxelOBJ("cupVoxel.obj");
        Entity entity = new Entity(model, new Vector3f(-0.5f,0,-5), 0, 0, 0, 0.1f);
        
        Camera camera = new Camera();
        while (!Display.isCloseRequested()) {
            //Game logic
            camera.move();
            entity.rotate(1f,2f,0);
            //Render
            renderer.prepare();
            shader.start();
            //Load camera
            shader.loadCamera(camera);
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
