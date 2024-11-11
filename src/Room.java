import com.jogamp.opengl.*;

public class Room {
    private int[] textures = new int[5];

    public void init(GL2 gl) {
        // Load textures for each wall
        textures[0] = Textures.loadTexture(gl, "assets/textures/diffuse_name.jpg");
        textures[1] = Textures.loadTexture(gl, "assets/textures/window_view.jpg");
        textures[2] = Textures.loadTexture(gl, "assets/textures/right_wall.jpg");
        textures[3] = Textures.loadTexture(gl, "assets/textures/floor_path.jpg");

        // Set texture parameters
        for (int texture : textures) {
            if (texture != -1) {
                gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            }
        }
    }

    public void render(GL2 gl) {
        // Disable lighting temporarily to verify texture visibility
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        // Draw back wall
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);
        drawQuad(gl, -5, -5, -5, 5, 5, -5);

        // Draw left wall
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[1]);
        gl.glPushMatrix();
        gl.glTranslatef(-5.0f, 0.0f, 0.0f);
        gl.glRotatef(90, 0, 1, 0);
        drawQuad(gl, -5, -5, -5, 5, 5, -5);
        gl.glPopMatrix();

        // Draw right wall
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[2]);
        gl.glPushMatrix();
        gl.glTranslatef(5.0f, 0.0f, 0.0f);
        gl.glRotatef(-90, 0, 1, 0);
        drawQuad(gl, -5, -5, -5, 5, 5, -5);
        gl.glPopMatrix();

        // Draw floor
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[3]);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -5.0f, 0.0f);
        drawQuad(gl, -5, -5, -5, 5, -5, 5);
        gl.glPopMatrix();

        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LIGHTING);  // Re-enable lighting if it was previously enabled
    }

    private void drawQuad(GL2 gl, float x1, float y1, float z1, float x2, float y2, float z2) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0, 0); gl.glVertex3f(x1, y1, z1);
        gl.glTexCoord2f(1, 0); gl.glVertex3f(x2, y1, z1);
        gl.glTexCoord2f(1, 1); gl.glVertex3f(x2, y2, z2);
        gl.glTexCoord2f(0, 1); gl.glVertex3f(x1, y2, z2);
        gl.glEnd();
    }
}
