
package ES.Systems;

import ES.Components.StaticModel;
import ES.Components.Transform;
import ES.EntitySystem;
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
    private HashMap<Integer,LinkedList<UUID>> modelHolder = new HashMap();
    private StaticShader shader;
    
    public StaticRenderer(StaticShader shader){
        this.shader = shader;
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
            int vaoId = es.getComponent(uuid, StaticModel.class).getModel().getMesh().getVaoId();
            if(modelHolder.get(vaoId) == null){
                modelHolder.put(vaoId, new LinkedList());
            }
            modelHolder.get(vaoId).add(uuid);
        }
        for (Integer vaoId : modelHolder.keySet()) {
            LinkedList<UUID> entities = modelHolder.get(vaoId);
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            for (UUID uuid : entities) {
                Model model = es.getComponent(uuid, StaticModel.class).getModel();
                Transform position = es.getComponent(uuid, Transform.class);
                shader.loadTransformationMatrix(MatrixMath.createTransformationMatrix(
                        position.getPosition(),
                        position.getRotationX(),
                        position.getRotationY(),
                        position.getRotationZ(),
                        position.getScale()));
                shader.loadMaterialShine(model.getReflectivity(), model.getShineDamper());
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureId());
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);
        }
    }
}
