package com.spacecraft;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

public class Room {
    private Texture[] textures = new Texture[5]; // Store textures for walls, floor, and space background

    public void init(GL2 gl) {
        textures[0] = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "diffuse_name.jpg"); // Back Wall
        textures[1] = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "right.jpeg"); // Right Wall
        textures[2] = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "floor_path.jpg"); // Floor
        textures[3] = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "space_texture.jpg"); // Space Background
        textures[4] = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "window_view_wall.jpg"); // Left Wall with window view

        float[] ambientLight = {0.6f, 0.6f, 0.6f, 1.0f};
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, ambientLight, 0);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
    }

    private Texture loadTexture(GL2 gl, String path) {
        try {
            return TextureIO.newTexture(new File(path), true);
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public void render(GL2 gl) {
        gl.glDisable(GL2.GL_LIGHTING); // Disable lighting to focus on texture visibility
        gl.glEnable(GL2.GL_TEXTURE_2D);

        renderSpaceBackground(gl, textures[3], -6.0f, 0.0f, -6.0f); // Space Background
        renderWall(gl, textures[0], 0.0f, 0.0f, -5.0f, 0); // Back Wall
        renderWindowWall(gl, textures[4], -5.0f, 0.0f, 0.0f, 90); // Left Wall with window and edges
        renderWall(gl, textures[1], 5.0f, 0.0f, 0.0f, -90); // Right Wall
        renderFloor(gl, textures[2]); // Floor

        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LIGHTING);  // Re-enable lighting
    }

    private void renderWall(GL2 gl, Texture texture, float x, float y, float z, float rotation) {
        if (texture != null) {
            texture.bind(gl);
        }
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glRotatef(rotation, 0, 1, 0); // Apply rotation if needed
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White wall color
        drawQuad(gl, -5.0f, -5.0f, 0.0f, 5.0f, 5.0f, 0.0f); // Draw quad for the wall
        gl.glPopMatrix();
    }

    private void renderWindowWall(GL2 gl, Texture texture, float x, float y, float z, float rotation) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glRotatef(rotation, 0, 1, 0);

        if (texture != null) {
            texture.bind(gl);
        }
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.7f); // Set transparency for window
        drawQuad(gl, -5.0f, -5.0f, 0.0f, 5.0f, 5.0f, 0.0f);

        gl.glColor3f(0.2f, 0.2f, 0.2f); // Dark edge color for the window frame
        drawEdge(gl, -5.3f, 5.3f, 0.01f, 5.3f, 5.5f, 0.01f); // Frame edges
        gl.glPopMatrix();
    }

    private void renderSpaceBackground(GL2 gl, Texture texture, float x, float y, float z) {
        if (texture != null) {
            texture.bind(gl);
        }
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        drawQuad(gl, -6.0f, -6.0f, -10.0f, 6.0f, 6.0f, -10.0f);
        gl.glPopMatrix();
    }

    private void renderFloor(GL2 gl, Texture texture) {
        if (texture != null) {
            texture.bind(gl);
        }
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -5.0f, 0.0f); // Floor at y = -5.0
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        drawQuad(gl, -5.0f, 0.0f, -5.0f, 5.0f, 0.0f, 5.0f);
        gl.glPopMatrix();
    }

    private void drawEdge(GL2 gl, float x1, float y1, float z1, float x2, float y2, float z2) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x2, y1, z1);
        gl.glVertex3f(x2, y2, z2);
        gl.glVertex3f(x1, y2, z2);
        gl.glEnd();
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
