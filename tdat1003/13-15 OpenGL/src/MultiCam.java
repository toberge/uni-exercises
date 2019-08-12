import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;

public class MultiCam extends GLCanvas implements GLEventListener, Runnable {

    private static String TITLE = "Advanced Surveillance Technology (tm) - Currently observing a magnificent cardboard box.";
    private static final int CANVAS_WIDTH = 1280;
    private static final int CANVAS_HEIGHT = 720;
    private int width = CANVAS_WIDTH;
    private int height = CANVAS_HEIGHT;

    private static final float fov = 40.0f;
    private static final float near = 0.1f;
    private static final float far = 100f;

    private GLU glu = new GLU(); // GL utility
    private GLUT glut = new GLUT();

    private static final float[][] vertices = {
            {-1f,   -1f,    1f}, // 0
            { 1f,   -1f,    1f}, // 1
            { 1f,    1f,    1f}, // 2
            {-1f,    1f,    1f}, // 3
            // BACKSIDE START
            {-1f,   -1f,   -1f}, // 4
            { 1f,   -1f,   -1f}, // 5
            { 1f,    1f,   -1f}, // 6
            {-1f,    1f,   -1f}  // 7
            // Cube centered in origo, with side length 2
    };

    private static final byte[][] relevantPoints = {
            {0,1,2,3},
            {1,5,6,2},
            {5,4,7,6},
            {4,0,3,7},
            {2,6,7,3},
            {0,4,5,1} // had to change this, made an exception
            // sjekka nettopp med min høyre hånd, tror dette skal være som beskrevet i oppgaveteksten
    };

    private static final float[][] colors = {
            {218f / 255f, 212f / 255f, 239f / 255f}, // 0 Pale lavender
            {183f / 255f, 159f / 255f, 173f / 255f}, // 1 Pastel purple
            {121f / 255f, 128f / 255f, 134f / 255f}, // 2 Trolley grey
            { 85f / 255f, 111f / 255f, 122f / 255f}, // 3 Payne's grey
            { 46f / 255f,  97f / 255f, 113f / 255f}, // 4 Myrtle green
            {183f / 255f, 179f / 255f, 161f / 255f}  // 5 Laurel green (replaced myrtle in palette)
            // 6 different colors, generated with coolors.co
    };

    private static final float[] bgColor = {
              98f / 255f, 146f / 255f, 158f / 255f  // Desaturated cyan (replaced lavender in palette)
    };

    public MultiCam() {
        this.addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) { //this and others are from GLEventListener
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context
        gl.glClearColor(bgColor[0], bgColor[1], bgColor[2], 0.0f); // background color, RGB**A**
        gl.glEnable(GL2.GL_DEPTH_TEST); // otherwise you'll get weird results
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // perspective correction
    }


    // called on resize AND when drawable is first set visible
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context

        if (height == 0) height = 1; // avoid invalid division
        this.width = width;
        this.height = height;
        float aspect = (float)width / height; // set aspect ratio

        //gl.glViewport(0,0, width, height); //  set w/h of viewport
        //drawViewports(gl);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // choose then reset projection matrix
        glu.gluPerspective(fov, aspect, near, far); // seems to apply to all viewports
        //fovy, aspect, zNear, zFar

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // enable then reset model-view transform
    }


    // draw methods
    private void drawSimpleCube(GL2 gl, float size) {
        glut.glutSolidCube(size);
    }

    private void drawSide(GL2 gl, byte[] vertex, byte color) {
        gl.glColor3fv(colors[color],0);
        gl.glBegin(GL2.GL_POLYGON);
            for (byte i = 0; i < vertex.length; i++) {
                gl.glVertex3fv(vertices[vertex[i]],0);
            }
        gl.glEnd();
    }

    private void drawCube(GL2 gl) {


        for (byte i = 0; i < relevantPoints.length; i++){
            drawSide(gl,relevantPoints[i], i);
        }
    }

    private void drawXAxis(GL2 gl) {
        gl.glColor3f(1,1,1);
        gl.glBegin(GL2.GL_LINE);
            gl.glVertex3f(0,0,0);
            gl.glVertex3f(3,0,0);
        gl.glEnd();
    }

    private void drawYAxis(GL2 gl) {
        gl.glColor3f(1,0,1);
        gl.glBegin(GL2.GL_LINE);
            gl.glVertex3f(0,0,0);
            gl.glVertex3f(0,3,0);
        gl.glEnd();
    }

    private void drawzAxis(GL2 gl) {
        gl.glColor3f(0,1,0);
        gl.glBegin(GL2.GL_LINE);
            gl.glVertex3f(0,0,0);
            gl.glVertex3f(0,0,3);
        gl.glEnd();
    }

    private void drawXYZAxis(GL2 gl) {
        drawXAxis(gl);
        drawYAxis(gl);
        drawzAxis(gl);
    }

    // viewport method - KEEP IN MIND y is UP on screen here for some reason.
    private void drawViewports(GL2 gl) {
        //float aspect = (float)width / height;

        byte zCube = -2;
        float dist = 4;

        gl.glLoadIdentity();
        glu.gluLookAt(0,dist / 2,dist / 2,0,0,zCube,0,1,0);

        gl.glPushMatrix();
        gl.glViewport(0,0,width / 2,height / 2); // LOWER LEFT
        glu.gluLookAt(0,-dist,-dist * 1.5f,0,0,0,0,1,0);
        //glu.gluPerspective(fov, aspect, near, far);
        drawXYZAxis(gl);
        gl.glTranslatef(0,-2,zCube);
        drawCube(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glViewport(width / 2,0,width,height / 2); // LOWER RIGHT
        glu.gluLookAt(0,-dist, dist / 2,0,0,0,0,1,0);
        drawXYZAxis(gl);
        gl.glTranslatef(0,0,zCube);
        drawCube(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glViewport(0,height / 2,width / 2,height); // UPPER LEFT
        glu.gluLookAt(0,dist / 2,-dist,0,0,0,0,1,0);
        drawXYZAxis(gl);
        gl.glTranslatef(0,0,zCube);
        drawCube(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glViewport(width / 2,height / 2,width,height); // UPPER RIGHT
        glu.gluLookAt(dist / 2,-dist,-dist * 1.6,0,0,0,0,1,0);
        drawXYZAxis(gl);
        gl.glTranslatef(0,0,zCube);
        drawCube(gl);
        gl.glPopMatrix();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2(); // get grpahics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers

        gl.glColor3f(0.8f, 0.8f,0.8f); // set mah milky color

        drawViewports(gl);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) { }

    @Override
    public void run() {
        GLCanvas canvas = MultiCam.this;
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas); // adding canvas to window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // obvious
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);

        FPSAnimator fps = new FPSAnimator(canvas, 60, true);
        fps.start();
    }
}
