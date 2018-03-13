
package EngineTests;

import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Renderer;
import GameEngine.ShaderProgram;
import Shaders.UniformShader;
import Utilities.MatrixMath;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class UniformTest {
    public static void main(String[] args) {
        DisplayManager.create("Basic Uniform Test");
        
        Renderer renderer = new Renderer();
        UniformShader shader = new UniformShader();
        
        float[] vertices = new float[]{
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, -0.5f, 0,
            0.5f, 0.5f, 0
        };
        
        int[] indices = new int[]{
            0,1,2,3,0,2
        };
        
        Mesh mesh = Mesh.createMesh(vertices, indices);
        Vector3f pos = new Vector3f(0,0,0);
        boolean left = false;
        while (!Display.isCloseRequested()) {
            //Game logic
            if(pos.x > 0.5f | pos.x < -0.5){
                left = !left;
            }
            if(left){
                pos.x += 0.02f;
            }else{
                pos.x -= 0.02f;
            }
            //Render
            renderer.prepare();
            shader.start();
            shader.loadTransformationMatrix(MatrixMath.createTransformationMatrix(pos, 0, 0, 0, 1));
            renderer.renderMesh(mesh);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
