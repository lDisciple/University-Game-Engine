
package Shaders;

import GameEngine.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author jonathan
 */
public class StaticShader extends ShaderProgram{
    private static final String VERTEX_FILE = StaticShader.class.getResource("/Shaders/Static.vshade").getFile();
    private static final String FRAGMENT_FILE = StaticShader.class.getResource("/Shaders/Static.fshade").getFile();
    
    private int loc_tranMatrix;
    
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
        loc_tranMatrix = getUniformLocation("transMatrix");
    }
    
    public void loadTransformationMatrix(Matrix4f mat){
        loadMatrix4f(loc_tranMatrix, mat);
    }
    
}
