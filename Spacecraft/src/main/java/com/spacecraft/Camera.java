package com.spacecraft;

import com.jogamp.opengl.GL2;

public class Camera {
    private float xPos = 0.0f, yPos = 1.5f, zPos = 18.0f;
    private float rotationAngle = 0.0f;

    public void setup(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f); 
        gl.glTranslatef(-xPos, -yPos, -zPos); 
    }

    
    public void moveForward() { zPos -= 0.5f; }
    public void moveBackward() { zPos += 0.5f; }
    public void moveLeft() { xPos -= 0.5f; }
    public void moveRight() { xPos += 0.5f; }
    public void moveUp() { yPos += 0.5f; }
    public void moveDown() { yPos -= 0.5f; }
    public void rotateLeft() { rotationAngle -= 5.0f; }
    public void rotateRight() { rotationAngle += 5.0f; }
}
