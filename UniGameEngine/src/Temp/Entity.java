
package Temp;

import GameEngine.Model;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class Entity {
    private Model model;
    private Vector3f pos;
    private float rx,ry,rz;
    private float scale;

    public Entity(Model model, Vector3f pos, float rx, float ry, float rz, float scale) {
        this.model = model;
        this.pos = pos;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void changeX(float delta) {
        this.pos.x += delta;
    }

    public void changeY(float delta) {
        this.pos.y += delta;
    }

    public void changeZ(float delta) {
        this.pos.z += delta;
    }
    public void rotate(float drx,float dry,float drz) {
        this.rx += drx;
        this.ry += dry;
        this.rz += drz;
    }
    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRz() {
        return rz;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    
    
}
