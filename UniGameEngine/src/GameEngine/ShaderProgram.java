
package GameEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author jonathan
 */
public abstract class ShaderProgram {
    private static final int VERTEX_SHADER = GL20.GL_VERTEX_SHADER;
    private static final int FRAGMENT_SHADER = GL20.GL_FRAGMENT_SHADER;
    private int programId, vertexShader, fragmentShader;
    
    public ShaderProgram(String vFile, String fFile){
        vertexShader = loadFile(vFile, VERTEX_SHADER);
        fragmentShader = loadFile(fFile, FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
    }
    
    public void start(){
        GL20.glUseProgram(programId);
    }
    
    public void stop(){
        GL20.glUseProgram(0);
    }
    
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programId, vertexShader);
        GL20.glDetachShader(programId, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        GL20.glDeleteProgram(programId);
    }
    
    protected abstract void bindAttributes();
    
    protected void bindAttribute(int attribNumber, String name){
        GL20.glBindAttribLocation(programId, attribNumber, name);
    }
    
    private static int loadFile(String file, int type){
        //Read in file
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null){
                shaderSource.append(line+"\n");
            }
        }catch(IOException ex){
            System.err.println("Shader file could not be loaded.");
            ex.printStackTrace(System.err);
            System.exit(0);
        }
        //Create shader
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader: " + file);
            System.exit(0);
        }
        return shaderId;
    }
}
