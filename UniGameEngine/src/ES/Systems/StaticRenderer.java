
package ES.Systems;

import ES.Components.StaticModel;
import ES.Components.Transform;
import ES.EntitySystem;
import GameEngine.Mesh;
import GameEngine.Model;
import Shaders.StaticShader;
import Utilities.MatrixMath;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author jonathan
 */
public class StaticRenderer {
    private HashMap<Model,LinkedList<UUID>> modelHolder = new HashMap();
    private StaticShader shader;
    
    public StaticRenderer(StaticShader shader){
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.4f, 0.4f, 0.8f, 1);
    }
    
    public void render(EntitySystem es){
        for (LinkedList<UUID> list : modelHolder.values()) {
            list.clear();
        }
        for (UUID uuid : es.getEntitiesWith(StaticModel.class, Transform.class)) {//Skip checking entities.
            Model model = es.getComponent(uuid, StaticModel.class).getModel();
            if(modelHolder.get(model) == null){
                modelHolder.put(model, new LinkedList());
            }
            if(es.getComponent(uuid, StaticModel.class).isVisible()){
                modelHolder.get(model).add(uuid);
            }
        }
        for (Model model : modelHolder.keySet()) {
            LinkedList<UUID> entities = modelHolder.get(model);
            if(entities.isEmpty()){
                modelHolder.remove(model);
                continue;
            }
            Mesh mesh = model.getMesh();
            GL30.glBindVertexArray(mesh.getVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            shader.loadMaterialShine(model.getReflectivity(), model.getShineDamper());
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureId());
            for (UUID uuid : entities) {
                Transform position = es.getComponent(uuid, Transform.class);
                shader.loadTransformationMatrix(MatrixMath.createTransformationMatrix(
                        position.getPosition(),
                        position.getRotationX(),
                        position.getRotationY(),
                        position.getRotationZ(),
                        position.getScale()));
                GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);
        }
        
    }
    
    public void cleanUp(){
        shader.cleanUp();
    }
}
