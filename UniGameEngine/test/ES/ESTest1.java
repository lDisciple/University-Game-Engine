
package ES;

import ES.Components.StaticModel;
import ES.Components.Transform;
import ES.Systems.StaticRenderer;
import GameEngine.DisplayManager;
import GameEngine.Loader;
import GameEngine.Model;
import Shaders.StaticShader;
import Temp.Camera;
import Temp.Light;
import Utilities.MatrixMath;
import Utilities.ObjectLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class ESTest1 {
    
    public static void main(String[] args) {
        DisplayManager.create("ES Test");
        
        StaticShader shader = new StaticShader();
        shader.start();
        shader.loadProjectionMatrix(MatrixMath.createProjectionMatrix());
        shader.stop();
        StaticRenderer renderer = new StaticRenderer(shader);
        
        
        Model model = ObjectLoader.loadGoxelOBJ("cupVoxel.obj");
        model.setReflectivity(0.1f);
        model.setShineDamper(10);
        
        EntitySystem es = new MappedEntitySystem();
        MetaEntity entity = new MetaEntity(es);
        entity.add(new Transform(0, 0, -5, 0, 0, 0, 0.1f));
        entity.add(new StaticModel(model));
        
        Light light = new Light(new Vector3f(0,0,9), new Vector3f(1,1,1));
        Camera camera = new Camera();
        while (!Display.isCloseRequested()) {
            //Game logic
            camera.move();
            entity.getAs(Transform.class).changeRotationY(1);
            entity.getAs(Transform.class).changeRotationZ(1);
            //Render
            renderer.prepare();
            shader.start();
            //Load camera
            shader.loadCamera(camera);
            shader.loadLight(light);
            //END
            renderer.render(es);
            shader.stop();
            DisplayManager.update();
        }
        shader.cleanUp();
        Loader.cleanUp();
        DisplayManager.close();
    }
}
