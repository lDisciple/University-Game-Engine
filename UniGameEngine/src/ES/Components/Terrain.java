
package ES.Components;

import ES.EntitySystem;
import GameEngine.Loader;
import GameEngine.Mesh;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class Terrain extends Component{
    public static final int SIZE = 20; 
    public static final int VERTEX_COUNT = 128;
    
    private Mesh mesh;
    private int textureId;
    private int tiles = 40;
    
    public Terrain(int textureId){
        this.textureId = textureId;
        this.mesh = generateTerrain();
    }
    
    private Mesh generateTerrain(){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
                for(int j=0;j<VERTEX_COUNT;j++){
                        vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                        vertices[vertexPointer*3+1] = 0;
                        vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                        normals[vertexPointer*3] = 0;
                        normals[vertexPointer*3+1] = 1;
                        normals[vertexPointer*3+2] = 0;
                        textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1) * tiles;
                        textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1) * tiles;
                        vertexPointer++;
                }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
                for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                        int topLeft = (gz*VERTEX_COUNT)+gx;
                        int topRight = topLeft + 1;
                        int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                        int bottomRight = bottomLeft + 1;
                        indices[pointer++] = topLeft;
                        indices[pointer++] = bottomLeft;
                        indices[pointer++] = topRight;
                        indices[pointer++] = topRight;
                        indices[pointer++] = bottomLeft;
                        indices[pointer++] = bottomRight;
                }
        }
        Mesh mesh = Mesh.createMesh(vertices, indices);
        Loader.storeDataInAttributeList(mesh.getVaoId(), 1, textureCoords, 2);
        Loader.storeDataInAttributeList(mesh.getVaoId(), 2, normals, 3);
        return mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getTextureId() {
        return textureId;
    }
    
    public static void prepareTransform(int x, int z, EntitySystem es, UUID entity){
        if(!es.entityHas(entity, Transform.class)){
            es.addComponent(entity, new Transform(x*SIZE, 0, z*SIZE));
        }else{
            es.getComponent(entity, Transform.class).setX(x*SIZE);
            es.getComponent(entity, Transform.class).setZ(z*SIZE);
        }
    }
    
}
