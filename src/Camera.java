import com.jogamp.opengl.*;

public class Camera {
    private float xPos = 0.0f, yPos = 1.5f, zPos = 25.0f; // Further adjusted zPos for a closer view

    public void setup(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-xPos, -yPos, -zPos); // Position the camera closer
    }
}
