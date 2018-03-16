
package Shaders;

import GameEngine.ShaderProgram;
import Temp.Camera;
import Utilities.MatrixMath;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author jonathan
 */
public class StaticShader extends ShaderProgram{
    private static final String VERTEX_FILE = StaticShader.class.getResource("/Shaders/Static.vshade").getFile();
    private static final String FRAGMENT_FILE = StaticShader.class.getResource("/Shaders/Static.fshade").getFile();
    
    private int loc_transMatrix;
    private int loc_projMatrix;
    private int loc_viewMatrix;
    
    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoords");
    }

    @Override
    public void getUniformLocations() {
        loc_transMatrix = getUniformLocation("transMatrix");
        loc_projMatrix = getUniformLocation("projMatrix");
        loc_viewMatrix = getUniformLocation("viewMatrix");
    }
    
    public void loadTransformationMatrix(Matrix4f mat){
        loadMatrix4f(loc_transMatrix, mat);
    }
    
    public void loadProjectionMatrix(Matrix4f mat){
        loadMatrix4f(loc_projMatrix, mat);
    }
    
    public void loadCamera(Camera camera){
        loadMatrix4f(loc_viewMatrix, MatrixMath.createViewMatrix(camera));
    }
    
}
