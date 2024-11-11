import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class Textures {
    public static int loadTexture(GL2 gl, String filePath) {
        try {
            Texture texture = TextureIO.newTexture(new File(filePath), true);
            System.out.println("Loaded texture from " + filePath);
            return texture.getTextureObject(gl);
        } catch (IOException e) {
            System.err.println("Error loading texture: " + filePath);
            e.printStackTrace();
            return -1;
        }
    }
}
