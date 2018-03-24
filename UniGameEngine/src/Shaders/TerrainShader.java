
package Shaders;

import GameEngine.ShaderProgram;
import Temp.Camera;
import Temp.Light;
import Utilities.MatrixMath;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author jonathan
 */
public class TerrainShader extends ShaderProgram{
    private static final String VERTEX_FILE = TerrainShader.class.getResource("/Shaders/Terrain.vshade").getFile();
    private static final String FRAGMENT_FILE = TerrainShader.class.getResource("/Shaders/Terrain.fshade").getFile();
    
    private int loc_transMatrix;
    private int loc_projMatrix;
    private int loc_viewMatrix;
    private int loc_lightPos;
    private int loc_lightColor;
    private int loc_reflectivity;
    private int loc_shineDamper;
    
    public TerrainShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    public void getUniformLocations() {
        loc_transMatrix = getUniformLocation("transMatrix");
        loc_projMatrix = getUniformLocation("projMatrix");
        loc_viewMatrix = getUniformLocation("viewMatrix");
        loc_lightPos = getUniformLocation("lightPos");
        loc_lightColor = getUniformLocation("lightColor");
        loc_reflectivity = getUniformLocation("reflectivity");
        loc_shineDamper = getUniformLocation("shineDamper");
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
    
    public void loadLight(Light light){
        loadVector3f(loc_lightPos, light.getPosition());
        loadVector3f(loc_lightColor, light.getColor());
    }
    
    public void loadMaterialShine(float ref, float shineDamper){
        loadFloat(loc_reflectivity, ref);
        loadFloat(loc_shineDamper, shineDamper);
    }
    
}
