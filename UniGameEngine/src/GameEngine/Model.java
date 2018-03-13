
package GameEngine;

/**
 *
 * @author jonathan
 */
public class Model {
    private int textureId;
    private Mesh mesh;

    public Model(Mesh mesh, int textureId, float[] textCoords) {
        this.mesh = mesh;
        this.textureId = textureId;
        Loader.storeDataInAttributeList(mesh.getVaoId(), 1, textCoords, 2);
    }

    public int getTextureId() {
        return textureId;
    }

    public Mesh getMesh() {
        return mesh;
    }
    
}
