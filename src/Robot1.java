import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Robot1 {
    private float angle = 0.0f;
    private float armSwing = 0.0f;
    private float legSwing = 0.0f;
    private boolean swingDirection = true;

    public void init(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        
        float[] lightPos = {0.0f, 5.0f, 10.0f, 1.0f};
        float[] lightColor = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColor, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightColor, 0);
    }

    public void animate(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(-1.0f, 0.0f, -3.0f);
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);

        drawBase(gl);
        drawLegs(gl);
        drawBody(gl);
        drawArms(gl);
        drawHead(gl);

        angle += 1.0f;

        if (swingDirection) {
            armSwing += 2.0f;
            legSwing += 1.0f;
            if (armSwing > 30.0f) swingDirection = false;
        } else {
            armSwing -= 2.0f;
            legSwing -= 1.0f;
            if (armSwing < -30.0f) swingDirection = true;
        }

        gl.glPopMatrix();
    }
    public void render(GL2 gl) {
        animate(gl); // Call the animate method inside render
    }
    
    private void drawBase(GL2 gl) {
        gl.glPushMatrix();
        gl.glScalef(0.8f, 0.1f, 0.8f);
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawLegs(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.3f, 0.0f);

        // Left leg
        gl.glPushMatrix();
        gl.glTranslatef(-0.2f, -0.5f, 0.0f);
        gl.glRotatef(legSwing, 1.0f, 0.0f, 0.0f);
        gl.glScalef(0.15f, 0.6f, 0.15f);
        gl.glColor3f(0.3f, 0.3f, 0.3f);
        drawCube(gl);
        gl.glPopMatrix();

        // Right leg
        gl.glPushMatrix();
        gl.glTranslatef(0.2f, -0.5f, 0.0f);
        gl.glRotatef(-legSwing, 1.0f, 0.0f, 0.0f);
        gl.glScalef(0.15f, 0.6f, 0.15f);
        drawCube(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void drawBody(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.6f, 0.0f);
        gl.glScalef(0.4f, 0.8f, 0.3f);
        gl.glColor3f(0.8f, 0.2f, 0.2f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawArms(GL2 gl) {
        // Left arm
        gl.glPushMatrix();
        gl.glTranslatef(-0.35f, 0.9f, 0.0f);
        gl.glRotatef(armSwing, 1.0f, 0.0f, 0.0f);
        gl.glScalef(0.1f, 0.5f, 0.1f);
        gl.glColor3f(0.2f, 0.2f, 0.8f);
        drawCube(gl);
        gl.glPopMatrix();

        // Right arm
        gl.glPushMatrix();
        gl.glTranslatef(0.35f, 0.9f, 0.0f);
        gl.glRotatef(-armSwing, 1.0f, 0.0f, 0.0f);
        gl.glScalef(0.1f, 0.5f, 0.1f);
        gl.glColor3f(0.2f, 0.2f, 0.8f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawHead(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.4f, 0.0f);

        // Main head sphere
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, true);
        gl.glColor3f(0.9f, 0.8f, 0.7f);
        glu.gluSphere(quadric, 0.3, 20, 20);

        // Eyes or additional head parts
        drawHeadFeature(gl, 0.1f, 1.0f, 0.2f);
        drawHeadFeature(gl, -0.1f, 1.0f, 0.2f);

        glu.gluDeleteQuadric(quadric);
        gl.glPopMatrix();
    }

    private void drawHeadFeature(GL2 gl, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glScalef(0.05f, 0.05f, 0.05f);
        gl.glColor3f(0.1f, 0.1f, 0.1f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Front face
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);

        // Back face
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Left face
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Right face
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);

        // Top face
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Bottom face
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);

        gl.glEnd();
    }
}
