
package GameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author jonathan
 */
public class Loader {
    private static final List<Integer> vaoList = new ArrayList();
    private static final List<Integer> vboList = new ArrayList();
    private static final List<Integer> textureList = new ArrayList();
    
    public static int createVAO(){
        int vaoId = GL30.glGenVertexArrays();
        vaoList.add(vaoId);
        return vaoId;
    }
    
    public static int loadTexture(String filename){
        Texture texture =  null;
        try {
            InputStream stream = Loader.class.getResourceAsStream("/Assets/"+filename);
            if(stream == null){
                throw new FileNotFoundException("/Assets/"+filename);
            }
            texture = TextureLoader.getTexture("PNG", stream);
        } catch (IOException ex) {
            System.err.println("Could not load texture: /Assets/"+filename);
            ex.printStackTrace();
            //TODO Add default texture here
            System.exit(0);
        }
        int textureId = texture.getTextureID();
        textureList.add(textureId);
        return textureId;
    }
    
    public static int loadExternalTexture(String filename){
        Texture texture =  null;
        try {
            InputStream stream = new FileInputStream(new File(filename));
            if(stream == null){
                throw new FileNotFoundException(filename);
            }
            texture = TextureLoader.getTexture("PNG", stream);
        } catch (IOException ex) {
            System.err.println("Could not load texture:"+filename);
            ex.printStackTrace();
            //TODO Add default texture here
            System.exit(0);
        }
        int textureId = texture.getTextureID();
        textureList.add(textureId);
        return textureId;
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
        for (Integer tex : textureList) {
            GL30.glDeleteVertexArrays(tex);
        }
        for (Integer vbo : vboList) {
            GL30.glDeleteVertexArrays(vbo);
        }
    }
}
