import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main method started.");
        
        // Set up OpenGL profile and capabilities
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(capabilities);

        // Create and add Scene
        Scene scene = new Scene();
        canvas.addGLEventListener(scene);

        // Set up JFrame
        JFrame frame = new JFrame("Spacecraft Scene");
        frame.setSize(800, 600);
        frame.add(canvas);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Animator to refresh screen at 60 FPS
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();

        System.out.println("Main method complete. JFrame and Animator initialized.");
    }
}
