
package Utilities;

import GameEngine.Loader;
import GameEngine.Mesh;
import GameEngine.Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author jonathan
 */
public class ObjectLoader {
    
    public static Model loadGoxelOBJ(String file){
        try {
            FileReader fr = new FileReader(new File(ObjectLoader.class.getResource("/Assets/"+file).getFile()));
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            List<Vector3f> vertices = new ArrayList();
            List<Integer> colorIndices = new ArrayList();
            HashMap<Color,Integer> colorIds = new HashMap();
            List<Vector3f> normals = new ArrayList();
            List<Integer> indices = new ArrayList();
            float[] vertArr, texArr, normArr;
            int[] indicesArr;
            boolean foundFaces = false;
            int colorId = 0;
            while(!foundFaces && (line = br.readLine()) != null){
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "v":
                        Vector3f vertex = new Vector3f(
                        Float.parseFloat(parts[1]),
                        Float.parseFloat(parts[2]),
                        Float.parseFloat(parts[3])
                        );
                        vertices.add(vertex);
                        //Tex coords
                        Color color= new Color(
                        Float.parseFloat(parts[4]),
                        Float.parseFloat(parts[5]),
                        Float.parseFloat(parts[6])
                        );
                        if(!colorIds.containsKey(color)){
                            colorIndices.add(colorId);
                            colorIds.put(color, colorId++);
                        }else{
                            colorIndices.add(colorIds.get(color));
                        }
                        break;
                    case "vn":
                        Vector3f normal = new Vector3f(
                        Float.parseFloat(parts[1]),
                        Float.parseFloat(parts[2]),
                        Float.parseFloat(parts[3])
                        );
                        normals.add(normal);
                        break;
                    case "f":
                        foundFaces = true;
                        break;
                    default:
                }
            }
            vertArr = new float[vertices.size()*3];
            normArr = new float[vertices.size()*3];
            texArr = new float[vertices.size()*2];
            
            while((line = br.readLine()) != null){
                if(line.startsWith("f ")){
                    String[] parts = line.split(" ");
                    String[] v1 = parts[1].split("/");
                    String[] v2 = parts[2].split("/");
                    String[] v3 = parts[3].split("/");
                    String[] v4 = parts[4].split("/");
                    //No UV coords
                    //Create triangles
                    processGoxelVertex(v1, normArr, indices, normals);
                    processGoxelVertex(v2, normArr, indices, normals);
                    processGoxelVertex(v3, normArr, indices, normals);
                    processGoxelVertex(v4, normArr, indices, normals);
                    processGoxelVertex(v1, normArr, indices, normals);
                    processGoxelVertex(v3, normArr, indices, normals);
                }
            }
            br.close();
            
            //Generate Texture map
            HashMap<Integer, Vector2f> colorIdsToTexCoords = new HashMap();
            int imgSize = 2;
            while (imgSize << 1 < colorIds.size()) {                
                imgSize = imgSize << 1;
            }
            BufferedImage img = new BufferedImage(imgSize*2, imgSize*2, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = img.createGraphics();
            for (Color col : colorIds.keySet()) {
                int index = colorIds.get(col);
                g.setColor(col);
                int x = (index*2)%(imgSize*2);
                int y = (index*2)/(imgSize*2);
                g.fillRect(x,y, 2, 2);
                colorIdsToTexCoords.put(index, new Vector2f((x+1)/(imgSize*2f),(y+1)/(imgSize*2f)));
            }
            g.dispose();
            img.flush();
            String[] parts = file.split("/");
            String filename = parts[parts.length-1].replace(".obj", "");
            File imageFile = new File("Goxel/"+filename+".png");
            imageFile.mkdirs();
            imageFile.createNewFile();
            ImageIO.write(img, "PNG", imageFile);
            
            for (int i = 0; i < vertices.size(); i++) {
                Vector3f v = vertices.get(i);
                vertArr[i*3] = v.x;
                vertArr[i*3+1] = v.y;
                vertArr[i*3+2] = v.z;
                //Temp
                int texId = colorIndices.get(i);
                Vector2f uv = colorIdsToTexCoords.get(texId);
                texArr[i*2] = uv.x;
                texArr[i*2+1] = uv.y;
            }
            
            indicesArr = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                indicesArr[i] = indices.get(i);
            }
            
            Mesh mesh = Mesh.createMesh(vertArr, indicesArr);
            return new Model(mesh, Loader.loadExternalTexture(imageFile.getCanonicalPath()), texArr);
        } catch (FileNotFoundException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        }
        return null;
    }
    
    private static void processGoxelVertex(String[] v, float[] normArr, List<Integer> indices, List<Vector3f> normals){
        int vertPointer = Integer.parseInt(v[0])-1;
        int normalPointer = Integer.parseInt(v[2])-1;
        indices.add(vertPointer);
        normArr[vertPointer*3] = normals.get(normalPointer).x;
        normArr[vertPointer*3+1] = normals.get(normalPointer).y;
        normArr[vertPointer*3+2] = normals.get(normalPointer).z;
    }
}
