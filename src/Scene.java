import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;

public class Scene implements GLEventListener {
    private Camera camera;
    private Room room;
    private Robot1 robot1;
    private Robot2 robot2;

    public Scene() {
        camera = new Camera();
        room = new Room();
        robot1 = new Robot1();
        robot2 = new Robot2();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        room.init(gl);
        robot1.init(gl);
        robot2.init(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        camera.setup(gl);
        room.render(gl);

        // Position Robot1 and Robot2 on the floor
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -4.5f, 0.0f); // Floor level
        robot1.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(1.5f, -4.5f, 0.0f); // Offset Robot2 slightly
        robot2.render(gl);
        gl.glPopMatrix();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, (float) width / height, 1.0, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}
