
package GameEngine;

/**
 *
 * @author jonathan
 */
public class Mesh {
    private int vaoId;
    private int vertexCount;

    private Mesh(int vaoId, int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }
    
    public static Mesh createMesh(float[] vertices, int[] indices){
        int vaoId = Loader.createVAO();
        Loader.storeDataInAttributeList(vaoId, 0, vertices,3);
        Loader.bindIndicesBuffer(vaoId, indices);
        Mesh mesh = new Mesh(vaoId, indices.length);
        return mesh;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
    
}
