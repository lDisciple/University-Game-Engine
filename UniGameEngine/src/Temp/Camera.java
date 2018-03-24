
package Temp;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class Camera {
    private Vector3f pos;
    private float pitch,yaw,roll;
    private float scale;
    
    public Camera(){
        pos = new Vector3f();
    }
    public Camera(float x, float y, float z){
        pos = new Vector3f(x, y, z);
    }
    
    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            pos.z -= 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            pos.z += 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            pos.x -= 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            pos.x += 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            pos.y += 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
            pos.y -= 0.02f;
        }
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public float getScale() {
        return scale;
    }
    
    
}
