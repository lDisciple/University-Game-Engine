
package GameEngine;

/**
 *
 * @author jonathan
 */
public class Model {
    private int textureId;
    private Mesh mesh;
    private float reflectivity = 0;
    private float shineDamper = 1;

    public Model(Mesh mesh, int textureId, float[] texCoords, float[] normals) {
        this.mesh = mesh;
        this.textureId = textureId;
        Loader.storeDataInAttributeList(mesh.getVaoId(), 1, texCoords, 2);
        Loader.storeDataInAttributeList(mesh.getVaoId(), 2, normals, 3);
    }

    public Model(Mesh mesh, int textureId, float[] texCoords) {
        this(mesh, textureId, texCoords,new float[(texCoords.length/2)*3]);
    }

    public int getTextureId() {
        return textureId;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }
    
}
