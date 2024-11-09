import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;

public class Textures {
    public static int loadTexture(GL2 gl, String fileName) {
        Texture texture = null;
        try {
            texture = TextureIO.newTexture(new java.io.File(fileName), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texture != null ? texture.getTextureObject(gl) : -1;
    }
}
