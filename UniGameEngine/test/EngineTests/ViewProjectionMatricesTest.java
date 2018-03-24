
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Model;
import GameEngine.Renderer;
import Shaders.StaticShader;
import Temp.Camera;
import Temp.Entity;
import Utilities.MatrixMath;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class ViewProjectionMatricesTest {
    public static void main(String[] args) {
        DisplayManager.create("View/Projection matrix Test");
        
        float[] vertices = {			
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

        };

        float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0

        };

        int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

        };
        
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();
        shader.start();
        shader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        shader.stop();
        
        Mesh mesh = Mesh.createMesh(vertices, indices);
        Model model = new Model(mesh, Loader.loadTexture("Textures/pinky.png"), textureCoords);
        Entity entity = new Entity(model, new Vector3f(-0.5f,0,-5), 0, 0, 0, 1);
        
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
