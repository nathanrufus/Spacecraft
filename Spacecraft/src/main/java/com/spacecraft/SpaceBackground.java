package com.spacecraft;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

public class SpaceBackground {
    private Texture spaceTexture;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    public void init(GL2 gl) {
        spaceTexture = loadTexture(gl, "assets" + File.separator + "textures" + File.separator + "space_texture.jpg");
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
        if (spaceTexture == null) {
            System.err.println("Space texture not loaded; skipping render.");
            return; // Skip rendering if the texture is not available
        }

        gl.glPushMatrix();
        gl.glEnable(GL2.GL_TEXTURE_2D);

        spaceTexture.bind(gl); // Safe to bind now that we checked for null

        // Increment texture coordinates for animation
        offsetX += 0.0001f;
        offsetY += 0.00005f;

        // Render the quad with the texture applied
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(offsetX, offsetY); gl.glVertex3f(-6.0f, -5.0f, -10.0f);
        gl.glTexCoord2f(1.0f + offsetX, offsetY); gl.glVertex3f(6.0f, -5.0f, -10.0f);
        gl.glTexCoord2f(1.0f + offsetX, 1.0f + offsetY); gl.glVertex3f(6.0f, 5.0f, -10.0f);
        gl.glTexCoord2f(offsetX, 1.0f + offsetY); gl.glVertex3f(-6.0f, 5.0f, -10.0f);
        gl.glEnd();

        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glPopMatrix();
    }
}
