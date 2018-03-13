
package Shaders;

import Shaders.*;
import GameEngine.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author jonathan
 */
public class UniformShader extends ShaderProgram{
    private static final String VERTEX_FILE = UniformShader.class.getResource("/Shaders/Uniform.vshade").getFile();
    private static final String FRAGMENT_FILE = UniformShader.class.getResource("/Shaders/Uniform.fshade").getFile();
    
    private int loc_transMatrix;
    
    public UniformShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void getUniformLocations() {
        loc_transMatrix = getUniformLocation("transMatrix");
    }
    
    public void loadTransformationMatrix(Matrix4f mat){
        loadMatrix4f(loc_transMatrix, mat);
    }
    
}
