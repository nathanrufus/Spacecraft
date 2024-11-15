package com.spacecraft;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Robot2 {
    private float posX = -4.5f;
    private float posZ = -4.5f;
    private float spotlightAngle = 0.0f;
    private int bodyTexture;

    private int currentWaypoint = 0;
    private float[][] waypoints = {
        {-4.5f, -4.5f}, {4.5f, -4.5f}, {4.5f, 4.5f}, {-4.5f, 4.5f}, {-4.5f, -4.5f}
    };

    public void init(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT1);

        float[] spotlightDiffuse = {1.0f, 0.5f, 0.0f, 1.0f}; // Orange diffuse color
        float[] spotlightSpecular = {1.0f, 0.5f, 0.0f, 1.0f}; // Orange specular color
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, spotlightDiffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, spotlightSpecular, 0);
        gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 20.0f);
        gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 100.0f); // Higher focus for bright spotlight
    }

    public void animate(GL2 gl) {
        gl.glPushMatrix();

        float targetX = waypoints[currentWaypoint][0];
        float targetZ = waypoints[currentWaypoint][1];
        float deltaX = targetX - posX;
        float deltaZ = targetZ - posZ;

        float moveStep = 0.05f;
        if (Math.abs(deltaX) > moveStep) posX += Math.signum(deltaX) * moveStep;
        if (Math.abs(deltaZ) > moveStep) posZ += Math.signum(deltaZ) * moveStep;

        if (Math.abs(deltaX) <= moveStep && Math.abs(deltaZ) <= moveStep) {
            currentWaypoint = (currentWaypoint + 1) % waypoints.length;
        }

        float[] spotlightPos = {posX, -4.0f, posZ, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, spotlightPos, 0);

        float spotlightDirectionX = (float) Math.sin(Math.toRadians(spotlightAngle));
        float spotlightDirectionZ = (float) Math.cos(Math.toRadians(spotlightAngle));
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, new float[]{spotlightDirectionX, -1.0f, spotlightDirectionZ}, 0);
        spotlightAngle += 2.0f;

        gl.glTranslatef(posX, -4.8f, posZ);
        drawBody(gl);
        drawHead(gl);
        drawAntenna(gl);

        gl.glPopMatrix();
    }

    public void render(GL2 gl) {
        animate(gl); // Calls the animate method for rendering
    }

    private void drawBody(GL2 gl) {
        gl.glPushMatrix();
        gl.glScalef(0.5f, 1.0f, 0.3f);
        gl.glColor3f(0.2f, 0.8f, 0.2f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawHead(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.2f, 0.0f);
        gl.glScalef(0.3f, 0.3f, 0.3f);
        gl.glColor3f(0.9f, 0.8f, 0.6f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawAntenna(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.5f, 0.0f);
        gl.glScalef(0.05f, 0.3f, 0.05f);
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glEnd();
    }

    public float getPositionX() {
        return posX;
    }

    public float getPositionZ() {
        return posZ;
    }
}
