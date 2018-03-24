
package Utilities;

import Temp.Camera;
import org.lwjgl.opengl.Display;
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
        Matrix4f.rotate((float)Math.toRadians(rx),e1,matrix,matrix);
        Matrix4f.rotate((float)Math.toRadians(ry),e2,matrix,matrix);
        Matrix4f.rotate((float)Math.toRadians(rz),e3,matrix,matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }
    
    public static Matrix4f createProjectionMatrix(){
        return createProjectionMatrix(70, 0.1f, 1000);
    }
    
    public static Matrix4f createProjectionMatrix(float fov, float nearPlane ,float farPlane){
        Matrix4f projectionMatrix = new Matrix4f();
        
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
        projectionMatrix.m33 = 0;
        return projectionMatrix;
    }
    
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f matrix = createViewMatrix(
                camera.getPos(),
                camera.getPitch(),
                camera.getYaw(),
                camera.getRoll(), 1);
        return matrix;
    }
    
    public static Matrix4f createViewMatrix(Vector3f pos, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(new Vector3f(-1*pos.x,-1*pos.y,-1*pos.z),matrix,matrix);
        Matrix4f.rotate((float)Math.toRadians(rx),e1,matrix,matrix);
        Matrix4f.rotate((float)Math.toRadians(ry),e2,matrix,matrix);
        Matrix4f.rotate((float)Math.toRadians(rz),e3,matrix,matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }
}
