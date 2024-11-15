package com.spacecraft;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

public class Globe {
    private float rotationAngle = 0.0f;
    private Texture globeTexture;
    private Texture pedestalTexture;

    public void init(GL2 gl) {
        globeTexture = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "earth_texture.jpg");
        pedestalTexture = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "pedestal_texture.jpg");
    }

    private Texture loadTexture(GL2 gl, String path) {
        try {
            Texture texture = TextureIO.newTexture(new File(path), true);
            System.out.println("Loaded texture from " + path);
            return texture;
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public void render(GL2 gl) {
        gl.glPushMatrix();

        // Position the globe on the pedestal for visibility
        gl.glTranslatef(3.5f, -4.0f, 2.0f);
        drawPedestal(gl); // Draw the pedestal first
        drawGlobe(gl);    // Draw the globe on top of the pedestal

        gl.glPopMatrix();
    }

    private void drawPedestal(GL2 gl) {
        if (pedestalTexture != null) {
            pedestalTexture.bind(gl);
        }
        
        gl.glPushMatrix();
        gl.glScalef(1.0f, 0.2f, 1.0f); // Scale to create a cuboid pedestal base

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        drawCube(gl); // Draw the pedestal as a textured cube

        gl.glPopMatrix();
    }

    private void drawGlobe(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.6f, 0.0f); // Position globe above the pedestal

        if (globeTexture != null) {
            globeTexture.bind(gl); // Bind the globe texture
        }

        gl.glEnable(GL2.GL_TEXTURE_2D);
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, true);
        gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f); // Rotate the globe around the y-axis
        rotationAngle += 0.5f; // Adjust rotation speed if needed

        glu.gluSphere(quadric, 0.5, 30, 30); // Render the globe as a smooth textured sphere
        glu.gluDeleteQuadric(quadric);

        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glPopMatrix();
    }

    // Helper method to draw a textured cube for the pedestal
    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Front face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);

        // Back face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Left face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Right face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, -0.5f);

        // Top face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Bottom face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);

        gl.glEnd();
    }
}
