import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Globe {
    private float rotationAngle = 0.0f;
    private int textureId;

    public void init(GL2 gl) {
        textureId = Textures.loadTexture(gl, "assets/textures/earth_texture.jpg");
    }

    public void rotate(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(-3.5f, -4.5f, 2.0f); // Adjusted position to the left corner
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
        gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
        rotationAngle += 0.5f;

        // Render the globe sphere here (custom sphere drawing code or library)
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, true);
        glu.gluSphere(quadric, 1.0, 30, 30);  // Radius and tessellation levels

        glu.gluDeleteQuadric(quadric);
        gl.glPopMatrix();
    }
}
