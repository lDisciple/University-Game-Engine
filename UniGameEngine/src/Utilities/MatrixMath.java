
package Utilities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class MatrixMath {
    private static final Vector3f e1 = new Vector3f(1, 0, 0);
    private static final Vector3f e2 = new Vector3f(0, 1, 0);
    private static final Vector3f e3 = new Vector3f(0, 0, 1);
    
    public static Matrix4f createTransformationMatrix(Vector3f pos, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(pos,matrix,matrix);
        Matrix4f.rotate((float)Math.toDegrees(rx),e1,matrix,matrix);
        Matrix4f.rotate((float)Math.toDegrees(ry),e2,matrix,matrix);
        Matrix4f.rotate((float)Math.toDegrees(rz),e3,matrix,matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }
}
