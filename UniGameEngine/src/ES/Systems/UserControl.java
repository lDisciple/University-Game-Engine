
package ES.Systems;

import ES.Components.Transform;
import ES.Components.UserControlled;
import ES.EntitySystem;
import java.util.LinkedList;
import java.util.UUID;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author jonathan
 */
public class UserControl {
    private static final int UP = 0; 
    private static final int DOWN = 1; 
    private static final int LEFT = 2; 
    private static final int RIGHT = 3; 
    private static final int FORWARD = 4; 
    private static final int BACKWARD = 5;
    
    private float speedX, speedY, speedZ; 
    private float rotationSpeedX, rotationSpeedY, rotationSpeedZ; 
    private int[] keybinds;
    
    public UserControl(){
        speedX = 0.8f;
        speedY = 0.8f;
        speedZ = 0.8f;
        rotationSpeedX = 1f;
        rotationSpeedY = 1f;
        rotationSpeedZ = 1f;
        keybinds = new int[]{
            Keyboard.KEY_R,//UP
            Keyboard.KEY_F,//DOWN
            Keyboard.KEY_A,//LEFT
            Keyboard.KEY_D,//RIGHT
            Keyboard.KEY_W,//FORWARD
            Keyboard.KEY_S,//BACKWARD
        };
    }
    
    public void update(EntitySystem es, long timePassed){
        LinkedList<Transform> positions = new LinkedList(); 
        for (UUID uuid : es.getEntitiesWith(UserControlled.class, Transform.class)) {
            positions.add(es.getComponent(uuid, Transform.class));
        }
        float delta;
        for (int i = 0; i < keybinds.length; i++) {
            if(Keyboard.isKeyDown(keybinds[i])){
                switch (i) {
                    case UP:
                        delta = speedY*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeY(delta);
                        }
                        break;
                    case DOWN:
                        delta = -speedY*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeY(delta);
                        }
                        break;
                    case LEFT:
                        delta = -speedX*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeX(delta);
                        }
                        break;
                    case RIGHT:
                        delta = speedX*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeX(delta);
                        }
                        break;
                    case FORWARD:
                        delta = -speedZ*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeZ(delta);
                        }
                        break;
                    case BACKWARD:
                        delta = speedZ*timePassed/1000f;
                        for (Transform position : positions) {
                            position.changeZ(delta);
                        }
                        break;
                }
            }
        }
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedZ() {
        return speedZ;
    }

    public void setSpeedZ(float speedZ) {
        this.speedZ = speedZ;
    }

    public float getRotationSpeedX() {
        return rotationSpeedX;
    }

    public void setRotationSpeedX(float rotationSpeedX) {
        this.rotationSpeedX = rotationSpeedX;
    }

    public float getRotationSpeedY() {
        return rotationSpeedY;
    }

    public void setRotationSpeedY(float rotationSpeedY) {
        this.rotationSpeedY = rotationSpeedY;
    }

    public float getRotationSpeedZ() {
        return rotationSpeedZ;
    }

    public void setRotationSpeedZ(float rotationSpeedZ) {
        this.rotationSpeedZ = rotationSpeedZ;
    }
    
    
}
