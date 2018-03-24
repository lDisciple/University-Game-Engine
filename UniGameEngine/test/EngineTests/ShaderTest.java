
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Renderer;
import GameEngine.ShaderProgram;
import Shaders.TestShader;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jonathan
 */
public class ShaderTest {
    public static void main(String[] args) {
        DisplayManager.create("Basic Shading Test");
        
        Renderer renderer = new Renderer();
        ShaderProgram shader = new TestShader();
        
        float[] vertices = new float[]{
            -0.5f, 0.3f, 0,
            -0.5f, -0.2f, 0,
            0.5f, -0.5f, 0,
            0.5f, 0.5f, 0
        };
        
        int[] indices = new int[]{
            0,1,2,3,0,2
        };
        
        Mesh mesh = Mesh.createMesh(vertices, indices);
        while (!Display.isCloseRequested()) {
            //Game logic
            
            //Render
            renderer.prepare();
            shader.start();
            renderer.renderMesh(mesh);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
