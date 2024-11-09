import com.jogamp.opengl.*;

public class Room {
    private int[] textures = new int[5];

    public void init(GL2 gl) {
        textures[0] = Textures.loadTexture(gl, "assets/textures/diffuse_name.jpg");
        textures[1] = Textures.loadTexture(gl, "assets/textures/specular_name.jpg");
        textures[2] = Textures.loadTexture(gl, "assets/textures/right_wall.jpg");
        textures[3] = Textures.loadTexture(gl, "assets/textures/floor_path.jpg");
        textures[4] = Textures.loadTexture(gl, "assets/textures/window_view.jpg");
    }

    public void render(GL2 gl) {
        // Draw each wall, floor, and ceiling with texture bindings
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]); // Back wall
        // Render other walls, floor, and window following similar approach
    }
}
