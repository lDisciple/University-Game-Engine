
package GameEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author jonathan
 */
public class DisplayManager {
    
    public static void loadNatives(){
        String nativesPath = "/Natives/windows";
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("linux")){
            nativesPath = "/Natives/linux";
        }else{
            if(os.contains("osx")){
                nativesPath = "/Natives/macosx";
            }
        }
        System.setProperty("org.lwjgl.librarypath", DisplayManager.class.getResource(nativesPath).getFile().replace("%20", " "));
    }
    
    public static void create(String title){
        create(title, 800, 600);
    }
    public static void create(int w, int h){
        create("Game Engine", w, h);
    }
    public static void create(String title,int w, int h){
        loadNatives();
        
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        
        DisplayMode dm = new DisplayMode(w, h);
        try {
            Display.setDisplayMode(dm);
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(title);
            GL11.glViewport(0, 0, w, h);
        } catch (LWJGLException ex) {
            System.err.println("Could not create Display");
            ex.printStackTrace(System.err);
            System.exit(0);
        }
    }
    
    public static void update(){
        update(120);
    }
    public static void update(int fps){
        Display.sync(fps);
        Display.update();
    }
    
    public static void close(){
        Display.destroy();
    }
}
