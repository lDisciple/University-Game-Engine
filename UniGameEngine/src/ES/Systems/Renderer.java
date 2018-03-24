
package ES.Systems;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author jonathan
 */
public abstract class Renderer {

    
    public static void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.4f, 0.4f, 0.8f, 1);
    }
}
