
package GameEngine;

import Shaders.StaticShader;
import Temp.Entity;
import Utilities.MatrixMath;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author jonathan
 */
public class Renderer {
    
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.4f, 0.4f, 0.8f, 1);
    }
    
    public void renderMesh(Mesh mesh){
        GL30.glBindVertexArray(mesh.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
    
    public void renderModel(Model model){
        Mesh mesh = model.getMesh();
        GL30.glBindVertexArray(mesh.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
    //Remove later
    public void renderEntity(Entity ent, StaticShader shader){
        Model model = ent.getModel();
        Mesh mesh = model.getMesh();
        shader.loadTransformationMatrix(MatrixMath.createTransformationMatrix(
                ent.getPos(),
                ent.getRx(),
                ent.getRy(),
                ent.getRz(),
                ent.getScale()));
        GL30.glBindVertexArray(mesh.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
