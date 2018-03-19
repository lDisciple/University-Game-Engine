
package ES.Components;

/**
 *
 * @author jonathan
 */
public class Movement extends Component{
    private float velocityX,velocityY,velocityZ;

    public Movement(float velocityX, float velocityY, float velocityZ) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public Movement() {
        this(0,0,0);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void changeVelocityX(float dx) {
        this.velocityX += dx;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void changeVelocityY(float dy) {
        this.velocityY += dy;
    }

    public float getVelocityZ() {
        return velocityZ;
    }

    public void setVelocityZ(float velocityZ) {
        this.velocityZ = velocityZ;
    }

    public void changeVelocityZ(float dz) {
        this.velocityZ += dz;
    }
    
}
