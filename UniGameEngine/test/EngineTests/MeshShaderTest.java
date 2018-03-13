
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Renderer;
import Shaders.StaticShader;
import ThinMatrixCode.Tut5.renderEngine.RawModel;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jonathan
 */
public class MeshShaderTest {
    public static void main(String[] args) {
        DisplayManager.create("Basic rendering Test (Windows only)");
        
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();
        
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
            renderer.RenderMesh(mesh);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
