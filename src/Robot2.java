import com.jogamp.opengl.*;

public class Robot2 {
    private float x = 0.0f, z = 0.0f;
    private float spotlightAngle = 0.0f;

    public void init(GL2 gl) {
        // Initialize textures or spotlight settings
    }

    public void animate(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, 0.0f, z);
        gl.glRotatef(spotlightAngle, 0.0f, 1.0f, 0.0f);

        spotlightAngle += 2.0f;
        x += 0.1f; // Example movement adjustment

        gl.glPopMatrix();
    }
}
