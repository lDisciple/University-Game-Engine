
package Shaders;

import Shaders.*;
import GameEngine.ShaderProgram;

/**
 *
 * @author jonathan
 */
public class TextureShader extends ShaderProgram{
    private static final String VERTEX_FILE = TextureShader.class.getResource("/Shaders/Texture.vshade").getFile();
    private static final String FRAGMENT_FILE = TextureShader.class.getResource("/Shaders/Texture.fshade").getFile();

    public TextureShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoords");
    }

    @Override
    public void getUniformLocations() {
    }
    
}
