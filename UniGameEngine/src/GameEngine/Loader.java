
package GameEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author jonathan
 */
public class Loader {
    private static final List<Integer> vaoList = new ArrayList();
    private static final List<Integer> vboList = new ArrayList();
    
    public static int createVAO(){
        int vaoId = GL30.glGenVertexArrays();
        vaoList.add(vaoId);
        return vaoId;
    }
    
    public static void storeDataInAttributeList(int vaoId, int attribNumber, float[] data){
        storeDataInAttributeList(vaoId, attribNumber, data,3);
    }
    public static void storeDataInAttributeList(int vaoId, int attribNumber,float[] data,int dataSize){
        bindVAO(vaoId);
        int vboId = GL15.glGenBuffers();
        vboList.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribNumber, dataSize, GL11.GL_FLOAT, false, 0, 0);
        unbindVAO();
    }
    
    private static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    public static void bindIndicesBuffer(int vaoId, int[] indices){
        bindVAO(vaoId);
        int vboId = GL15.glGenBuffers();
        vboList.add(vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        unbindVAO();
    }
    
    private static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    public static void bindVAO(int vaoId){
        GL30.glBindVertexArray(vaoId);
    }
    
    public static void unbindVAO(){
        GL30.glBindVertexArray(0);
    }
    
    public static void cleanUp(){
        for (Integer vao : vaoList) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (Integer vbo : vboList) {
            GL30.glDeleteVertexArrays(vbo);
        }
    }
}
