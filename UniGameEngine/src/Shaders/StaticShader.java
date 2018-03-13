
package Shaders;

import GameEngine.ShaderProgram;

/**
 *
 * @author jonathan
 */
public class StaticShader extends ShaderProgram{
    private static final String VERTEX_FILE = StaticShader.class.getResource("/Shaders/Static.vshade").getFile();
    private static final String FRAGMENT_FILE = StaticShader.class.getResource("/Shaders/Static.fshade").getFile();

    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
    
}
