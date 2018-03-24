
package ES.Systems;

import ES.Components.StaticModel;
import ES.Components.Terrain;
import ES.Components.Transform;
import ES.EntitySystem;
import GameEngine.Mesh;
import GameEngine.Model;
import Shaders.TerrainShader;
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
public class TerrainRenderer extends Renderer{
    private HashMap<Terrain,LinkedList<UUID>> modelHolder = new HashMap();
    private TerrainShader shader;
    
    public TerrainRenderer(TerrainShader shader){
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    
    public void render(EntitySystem es){
        for (LinkedList<UUID> list : modelHolder.values()) {
            list.clear();
        }
        for (UUID uuid : es.getEntitiesWith(Terrain.class, Transform.class)) {//Skip checking entities.
            Terrain terrain = es.getComponent(uuid, Terrain.class);
            if(modelHolder.get(terrain) == null){
                modelHolder.put(terrain, new LinkedList());
            }
            modelHolder.get(terrain).add(uuid);
        }
        for (Terrain terrain : modelHolder.keySet()) {
            LinkedList<UUID> entities = modelHolder.get(terrain);
            if(entities.isEmpty()){
                modelHolder.remove(terrain);
                continue;
            }
            Mesh mesh = terrain.getMesh();
            GL30.glBindVertexArray(mesh.getVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTextureId());
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
