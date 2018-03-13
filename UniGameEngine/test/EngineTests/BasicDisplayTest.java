
package EngineTests;

import GameEngine.DisplayManager;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jonathan
 */
public class BasicDisplayTest {
    public static void main(String[] args) {
        DisplayManager.create("Display Test");
        while (!Display.isCloseRequested()) {
            //Game logic
            
            //Render
            DisplayManager.update();
        }
    }
}
