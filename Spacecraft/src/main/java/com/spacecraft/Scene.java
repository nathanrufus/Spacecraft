package com.spacecraft;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Scene implements GLEventListener, KeyListener {
    private Camera camera;
    private Room room;
    private Robot1 robot1;
    private Robot2 robot2;
    private Globe globe;
    private SpaceBackground spaceBackground;

    // General lighting control variables
    private boolean generalLightOn = true;
    private float generalLightIntensity = 0.6f;
    
    // Spotlight control variable
    private boolean spotlightOn = true;
    
    // Animation control variables
    private boolean robot1Moving = true;
    private boolean robot2Moving = true;

    public Scene() {
        camera = new Camera();
        room = new Room();
        robot1 = new Robot1();
        robot2 = new Robot2();
        globe = new Globe();
        spaceBackground = new SpaceBackground();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);

        // Initialize objects
        room.init(gl);
        robot1.init(gl);
        robot2.init(gl);
        globe.init(gl);
        spaceBackground.init(gl);

        // Set up general room light (GL_LIGHT0)
        setupGeneralLight(gl);
    }

    private void setupGeneralLight(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHT0);

        float[] ambientLight = {generalLightIntensity, generalLightIntensity, generalLightIntensity, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, ambientLight, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, ambientLight, 0);

        float[] lightPosition = {0.0f, 5.0f, 0.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        camera.setup(gl);

        // Render space background
        spaceBackground.render(gl);

        // Set general and spotlight based on controls
        if (generalLightOn) {
            setupGeneralLight(gl);
        } else {
            gl.glDisable(GL2.GL_LIGHT0);
        }
        
        if (spotlightOn) {
            gl.glEnable(GL2.GL_LIGHT1);
        } else {
            gl.glDisable(GL2.GL_LIGHT1);
        }

        // Render scene elements
        room.render(gl);
        
        // Pass Robot2’s position to Robot1’s render method
        if (robot1Moving) robot1.render(gl, robot2.getPositionX(), robot2.getPositionZ());
        if (robot2Moving) robot2.render(gl);
        
        globe.render(gl);

        gl.glFlush();
    }

    public void toggleGeneralLight() {
        generalLightOn = !generalLightOn;
    }

    public void dimGeneralLight() {
        if (generalLightIntensity > 0.1f) {
            generalLightIntensity -= 0.1f;
        }
    }

    public void brightenGeneralLight() {
        if (generalLightIntensity < 1.0f) {
            generalLightIntensity += 0.1f;
        }
    }

    public void toggleSpotlight() {
        spotlightOn = !spotlightOn;
    }

    public void toggleRobot1Movement() {
        robot1Moving = !robot1Moving;
    }

    public void toggleRobot2Movement() {
        robot2Moving = !robot2Moving;
    }

    // Implementing KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: camera.moveForward(); break;
            case KeyEvent.VK_S: camera.moveBackward(); break;
            case KeyEvent.VK_A: camera.moveLeft(); break;
            case KeyEvent.VK_D: camera.moveRight(); break;
            case KeyEvent.VK_R: camera.moveUp(); break;
            case KeyEvent.VK_F: camera.moveDown(); break;
            case KeyEvent.VK_Q: camera.rotateLeft(); break;
            case KeyEvent.VK_E: camera.rotateRight(); break;
            case KeyEvent.VK_G: toggleGeneralLight(); break;
            case KeyEvent.VK_B: dimGeneralLight(); break;
            case KeyEvent.VK_N: brightenGeneralLight(); break;
            case KeyEvent.VK_T: toggleSpotlight(); break;
            case KeyEvent.VK_1: toggleRobot1Movement(); break;
            case KeyEvent.VK_2: toggleRobot2Movement(); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implement if needed for key-release actions
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
