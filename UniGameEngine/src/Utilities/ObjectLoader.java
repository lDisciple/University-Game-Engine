
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
    //<editor-fold defaultstate="collapsed" desc="Basic loading (No splitting)">
    
    public static Model loadGoxelOBJBasic(String file){
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
                    processGoxelVertexBasic(v1, normArr, indices, normals);
                    processGoxelVertexBasic(v2, normArr, indices, normals);
                    processGoxelVertexBasic(v3, normArr, indices, normals);
                    processGoxelVertexBasic(v4, normArr, indices, normals);
                    processGoxelVertexBasic(v1, normArr, indices, normals);
                    processGoxelVertexBasic(v3, normArr, indices, normals);
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
            return new Model(mesh, Loader.loadExternalTexture(imageFile.getCanonicalPath()), texArr,normArr);
        } catch (FileNotFoundException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        }
        return null;
    }
    
    private static void processGoxelVertexBasic(String[] v, float[] normArr, List<Integer> indices, List<Vector3f> normals){
        int vertPointer = Integer.parseInt(v[0])-1;
        int normalPointer = Integer.parseInt(v[2])-1;
        indices.add(vertPointer);
        normArr[vertPointer*3] = normals.get(normalPointer).x;
        normArr[vertPointer*3+1] = normals.get(normalPointer).y;
        normArr[vertPointer*3+2] = normals.get(normalPointer).z;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Basic loading (No splitting)">
    
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
            int vertexId = 0;
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
            //Get reverse color mappings and texture
            HashMap<Integer, Vector2f> colorIdsToTexCoords = new HashMap();
            String imageFile = generateGoxelTextureImage(file,colorIds, colorIndices,colorIdsToTexCoords);
            
            HashMap<Float[], Integer> trueVerts = new HashMap();
            while((line = br.readLine()) != null){
                if(line.startsWith("f ")){
                    String[] parts = line.split(" ");
                    String[] v1 = parts[1].split("/");
                    String[] v2 = parts[2].split("/");
                    String[] v3 = parts[3].split("/");
                    String[] v4 = parts[4].split("/");
                    //No UV coords
                    //Create triangles
                    processGoxelVertex(v1, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
                    processGoxelVertex(v2, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
                    processGoxelVertex(v3, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
                    processGoxelVertex(v4, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
                    processGoxelVertex(v1, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
                    processGoxelVertex(v3, vertices, colorIndices, colorIdsToTexCoords, normals, indices, trueVerts);
//                    processGoxelVertex(v2, normArr, indices, normals, trueVerts);
//                    processGoxelVertex(v3, normArr, indices, normals, trueVerts);
//                    processGoxelVertex(v4, normArr, indices, normals, trueVerts);
//                    processGoxelVertex(v1, normArr, indices, normals, trueVerts);
//                    processGoxelVertex(v3, normArr, indices, normals, trueVerts);
                }
            }
            br.close();
            
            vertArr = new float[trueVerts.size()*3];
            normArr = new float[trueVerts.size()*3];
            texArr = new float[trueVerts.size()*2];
            for (Float[] component : trueVerts.keySet()) {
                int i = trueVerts.get(component);
                vertArr[i*3] = component[0];
                vertArr[i*3+1] = component[1];
                vertArr[i*3+2] = component[2];
                texArr[i*2] = component[3];
                texArr[i*2+1] = component[4];
                normArr[i*3] = component[5];
                normArr[i*3+1] = component[6];
                normArr[i*3+2] = component[7];
            }
            
            indicesArr = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                indicesArr[i] = indices.get(i);
            }
            
            Mesh mesh = Mesh.createMesh(vertArr, indicesArr);
            return new Model(mesh, Loader.loadExternalTexture(imageFile), texArr,normArr);
        } catch (FileNotFoundException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Could not load Goxel object: " + file);
            ex.printStackTrace();
        }
        return null;
    }
    
    private static String generateGoxelTextureImage(String file,HashMap<Color,Integer> colorIds, List<Integer> colorIndices, HashMap<Integer, Vector2f> colorIdsToTexCoords) throws IOException{
        
            //Generate Texture map
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
            return imageFile.getAbsolutePath();
    }
    
    private static void processGoxelVertex(String[] v, List<Vector3f> vertices, List<Integer> colorIndices,HashMap<Integer, Vector2f> colorIdsToTexCoords, List<Vector3f> normals, List<Integer> indices, HashMap<Float[], Integer> trueVerts){
        int vertPointer = Integer.parseInt(v[0])-1;
        int normalPointer = Integer.parseInt(v[2])-1;
        Float[] components = new Float[8];
        
        Vector3f vertex = vertices.get(vertPointer);
        components[0] = vertex.x;
        components[1] = vertex.y;
        components[2] = vertex.z;
        
        Vector2f uv = colorIdsToTexCoords.get(colorIndices.get(vertPointer));
        components[3] = uv.x;
        components[4] = uv.y;
        
        Vector3f normal = normals.get(normalPointer);
        components[5] = normal.x;
        components[6] = normal.y;
        components[7] = normal.z;
        trueVerts.putIfAbsent(components, indices.size());
        indices.add(trueVerts.get(components));
    }
//</editor-fold>
}
