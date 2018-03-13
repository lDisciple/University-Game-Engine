
package Shaders;

import GameEngine.ShaderProgram;

/**
 *
 * @author jonathan
 */
public class TestShader extends ShaderProgram{
    private static final String VERTEX_FILE = TestShader.class.getResource("/Shaders/Test.vshade").getFile();
    private static final String FRAGMENT_FILE = TestShader.class.getResource("/Shaders/Test.fshade").getFile();

    public TestShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void getUniformLocations() {
    }
    
}
