
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Renderer;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jonathan
 */
public class MeshRenderingTest {
    public static void main(String[] args) {
        DisplayManager.create("Basic rendering Test (Windows only)");
        
        Renderer renderer = new Renderer();
        
        
        float[] vertices = new float[]{
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, -0.5f, 0,
            0.5f, 0.5f, 0,
        };
        
        int[] indices = new int[]{
            0,1,2,3,0,2
        };
        
        Mesh mesh = Mesh.createMesh(vertices, indices);
        while (!Display.isCloseRequested()) {
            //Game logic
            
            //Render
            renderer.prepare();
            renderer.renderMesh(mesh);
            
            DisplayManager.update();
        }
        Loader.cleanUp();
        DisplayManager.close();
    }
}
