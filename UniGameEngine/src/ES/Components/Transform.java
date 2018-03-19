
package ES.Components;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class Transform extends Component{
    private final Vector3f position;
    private float rx,ry,rz;
    private float scale;

    public Transform() {
        position = new Vector3f();
    }

    public Transform(float x, float y, float z) {
        position = new Vector3f(x,y,z);
    }

    public Transform(float x, float y, float z, float rx, float ry, float rz, float scale) {
        position = new Vector3f(x,y,z);
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }
    
    public float getX(){
        return position.x;
    }
    
    public void setX(float x){
        position.x = x;
    }
    
    public void changeX(float dx){
        position.x += dx;
    }
    
    public float getY(){
        return position.y;
    }
    
    public void setY(float y){
        position.y = y;
    }
    
    public void changeY(float dy){
        position.y += dy;
    }
    
    public float getZ(){
        return position.z;
    }
    
    public void setZ(float z){
        position.z = z;
    }
    
    public void changeZ(float dz){
        position.z += dz;
    }

    public float getRotationX() {
        return rx;
    }

    public void setRotationX(float rx) {
        this.rx = rx;
    }

    public void changeRotationX(float drx) {
        this.rx += drx;
    }

    public float getRotationY() {
        return ry;
    }

    public void setRotationY(float ry) {
        this.ry = ry;
    }

    public void changeRotationY(float dry) {
        this.ry += dry;
    }

    public float getRotationZ() {
        return rz;
    }

    public void setRotationZ(float rz) {
        this.rz = rz;
    }

    public void changeRotationZ(float drz) {
        this.rz += drz;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
