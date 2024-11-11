import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Robot2 {
    private float x = 0.0f, z = 0.0f;
    private float spotlightAngle = 0.0f;
    private float direction = 1.0f;
    private int bodyTexture;

    public void init(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT1);

        float[] spotlightColor = {1.0f, 1.0f, 1.0f, 1.0f};

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, spotlightColor, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, spotlightColor, 0);
        gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 30.0f);
        gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 2.0f);

        // Load the body texture
        // bodyTexture = Textures.loadTexture(gl, "assets/textures/robot2_texture.jpg");

        System.out.println("Robot2 initialized with spotlight setup and texture.");
    }

    public void animate(GL2 gl) {
        System.out.println("Animating Robot2...");
        gl.glPushMatrix();

        // Update spotlight position
        float[] spotlightPos = {x, 1.5f, z, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, spotlightPos, 0);

        // Move robot back and forth
        x += 0.05f * direction;
        if (x > 3.0f || x < -3.0f) direction *= -1.0f;

        gl.glTranslatef(x, 0.0f, z);
        drawBody(gl);
        drawHead(gl);
        drawAntenna(gl);

        gl.glPopMatrix();
    }
    public void render(GL2 gl) {
        animate(gl); // Calls the existing animate method for rendering
    }
    

    private void drawBody(GL2 gl) {
        gl.glPushMatrix();
        gl.glScalef(0.5f, 1.0f, 0.3f);
        gl.glColor3f(0.2f, 0.8f, 0.2f);

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bodyTexture);
        drawCube(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);

        gl.glPopMatrix();
    }

    private void drawHead(GL2 gl) {
        // Draw head cube
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.2f, 0.0f);
        gl.glScalef(0.3f, 0.3f, 0.3f);
        gl.glColor3f(0.9f, 0.8f, 0.6f);
        drawCube(gl);
        gl.glPopMatrix();

        // Draw two eyes (spheres)
        drawEye(gl, 0.1f, 1.3f, 0.15f); // Left eye
        drawEye(gl, -0.1f, 1.3f, 0.15f); // Right eye
    }

    private void drawEye(GL2 gl, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, false);
        glu.gluSphere(quadric, 0.05, 10, 10); // Small sphere for eye
        glu.gluDeleteQuadric(quadric);
        gl.glPopMatrix();
    }

    private void drawAntenna(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.5f, 0.0f); // Position on top of head

        // Antenna base
        gl.glPushMatrix();
        gl.glScalef(0.05f, 0.3f, 0.05f);
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        drawCube(gl);
        gl.glPopMatrix();

        // Spotlight bulb (sphere) rotating at the top
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.3f, 0.0f); // Move up to top of antenna
        gl.glRotatef(spotlightAngle, 0.0f, 1.0f, 0.0f); // Rotate spotlight
        spotlightAngle += 2.0f;

        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, false);
        glu.gluSphere(quadric, 0.1, 10, 10); // Sphere for spotlight bulb
        glu.gluDeleteQuadric(quadric);

        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Front face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);

        // (Other faces omitted for brevity but should be implemented)
        
        gl.glEnd();
    }
}
