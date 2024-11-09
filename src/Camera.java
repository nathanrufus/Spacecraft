import com.jogamp.opengl.*;

public class Camera {
    private float xPos = 0.0f, yPos = 1.0f, zPos = 5.0f;

    public void setup(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-xPos, -yPos, -zPos);
    }
}
