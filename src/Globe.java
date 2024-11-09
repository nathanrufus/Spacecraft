import com.jogamp.opengl.*;

public class Globe {
    private float rotationAngle = 0.0f;
    private int textureId;

    public void init(GL2 gl) {
        textureId = Textures.loadTexture(gl, "assets/textures/earth_texture.jpg");
    }

    public void rotate(GL2 gl) {
        gl.glPushMatrix();
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
        gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
        rotationAngle += 0.5f;
        gl.glPopMatrix();
    }
}
