package com.spacecraft;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Robot1 {
    private float angle = 0.0f;
    private float armSwing = 0.0f;
    private float legSwing = 0.0f;
    private boolean swingDirection = true;
    private boolean isDancing = false;
    private int danceTimer = 0; // Timer to control periodic dancing
    private static final int DANCE_PERIOD = 300; // Duration of one dance cycle (adjust as needed)

    public void init(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        float[] lightPos = {0.0f, 5.0f, 10.0f, 1.0f};
        float[] lightColor = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColor, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightColor, 0);
    }

    public void render(GL2 gl, float robot2PosX, float robot2PosZ) {
        animate(gl); // Render and animate Robot1
    }

    private void animate(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(-2.0f, -4.8f, -2.0f); // Position Robot1 on the floor

        // Toggle dancing state based on the timer for periodic dancing
        danceTimer++;
        if (danceTimer >= DANCE_PERIOD) {
            isDancing = !isDancing; // Toggle dancing state
            danceTimer = 0; // Reset timer
        }

        // Rotate for dancing effect
        if (isDancing) {
            angle += 2.0f;
        }
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);

        drawBase(gl);
        drawLegs(gl);
        drawBody(gl);
        drawArms(gl);
        drawHead(gl);

        // Animate arm and leg swings if Robot1 is dancing
        if (isDancing) {
            if (swingDirection) {
                armSwing += 2.0f;
                legSwing += 1.5f;
                if (armSwing > 30.0f) swingDirection = false;
            } else {
                armSwing -= 2.0f;
                legSwing -= 1.5f;
                if (armSwing < -30.0f) swingDirection = true;
            }
        } else {
            resetSwing(); // Reset to neutral pose when not dancing
        }

        gl.glPopMatrix();
    }

    private void resetSwing() {
        armSwing = 0.0f;
        legSwing = 0.0f;
    }

    private void drawBase(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glScalef(1.2f, 0.3f, 1.2f);
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Color for the base
        glu.gluSphere(quadric, 0.5, 20, 20);
        gl.glPopMatrix();

        glu.gluDeleteQuadric(quadric);
    }

    private void drawLegs(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.3f, 0.0f);

        gl.glPushMatrix();
        gl.glTranslatef(-0.3f, -0.5f, 0.0f);
        gl.glRotatef(legSwing, 1.0f, 0.0f, 0.0f); // Apply leg swing rotation
        gl.glColor3f(0.3f, 0.3f, 0.3f); // Leg color
        glu.gluSphere(quadric, 0.2, 20, 20);
        gl.glTranslatef(0.0f, -0.3f, 0.0f);
        glu.gluSphere(quadric, 0.2, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.3f, -0.5f, 0.0f);
        gl.glRotatef(-legSwing, 1.0f, 0.0f, 0.0f); // Apply opposite leg swing
        glu.gluSphere(quadric, 0.2, 20, 20);
        gl.glTranslatef(0.0f, -0.3f, 0.0f);
        glu.gluSphere(quadric, 0.2, 20, 20);
        gl.glPopMatrix();

        gl.glPopMatrix();
        glu.gluDeleteQuadric(quadric);
    }

    private void drawBody(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.8f, 0.0f);
        gl.glScalef(0.6f, 1.2f, 0.4f);
        gl.glColor3f(0.8f, 0.2f, 0.2f); // Body color
        glu.gluSphere(quadric, 0.3, 20, 20);
        gl.glPopMatrix();

        glu.gluDeleteQuadric(quadric);
    }

    private void drawArms(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslatef(-0.5f, 1.0f, 0.0f);
        gl.glRotatef(armSwing, 1.0f, 0.0f, 0.0f); // Apply arm swing
        gl.glColor3f(0.2f, 0.2f, 0.8f); // Arm color
        glu.gluSphere(quadric, 0.15, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.5f, 1.0f, 0.0f);
        gl.glRotatef(-armSwing, 1.0f, 0.0f, 0.0f);
        glu.gluSphere(quadric, 0.15, 20, 20);
        gl.glPopMatrix();

        glu.gluDeleteQuadric(quadric);
    }

    private void drawHead(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.8f, 0.0f);
        gl.glColor3f(0.9f, 0.8f, 0.7f); // Head color
        glu.gluSphere(quadric, 0.3, 20, 20);
        gl.glPopMatrix();

        glu.gluDeleteQuadric(quadric);
    }
}
