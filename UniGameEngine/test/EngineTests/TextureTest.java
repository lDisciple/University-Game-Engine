
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Model;
import GameEngine.Renderer;
import Shaders.TextureShader;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jonathan
 */
public class TextureTest {
    public static void main(String[] args) {
        DisplayManager.create("Basic Texture Test");
        
        Renderer renderer = new Renderer();
        TextureShader shader = new TextureShader();
        
        float[] vertices = new float[]{
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, -0.5f, 0,
            0.5f, 0.5f, 0
        };
        
        float[] texCoords = new float[]{
            0,0,
            0,1,
            1,1,
            1,0
        };
        
        int[] indices = new int[]{
            0,1,2,3,0,2
        };
        
        Mesh mesh = Mesh.createMesh(vertices, indices);
        Model model = new Model(mesh, Loader.loadTexture("Textures/pinky.png"),texCoords);
        
        while (!Display.isCloseRequested()) {
            //Game logic
            
            //Render
            renderer.prepare();
            shader.start();
            renderer.renderModel(model);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
