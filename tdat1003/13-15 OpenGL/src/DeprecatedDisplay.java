import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;


/**
 * This will be an øving.
 * Perhaps more than one.
 * We will see what will become
 * of this stupid Java code.
 */





public class DeprecatedDisplay extends GLCanvas implements GLEventListener, Runnable {

    private static String TITLE = "Jogler mig her og jogler mig der";
    private static final int CANVAS_WIDTH = 1280;
    private static final int CANVAS_HEIGHT = 720;

    private GLU glu = new GLU(); // GL utility
    private GLUT glut = new GLUT();

    PlayerControl control;

    int rot = 30; // pfft

    public DeprecatedDisplay(PlayerControl control) {
        this.addGLEventListener(this);
        this.addKeyListener(control.getKeyHandler());
        this.addMouseListener(control.getMouseHandler());
        this.control = control;
    }

    private void look() {
        glu.gluLookAt(control.getEyeX(),control.getEyeY(),control.getEyeZ(),
                      control.getPosX(),control.getPosY(),control.getPosZ(), 0,1,0);
        // eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ - loc of eye, center of view, direction of up
    }

    @Override
    public void init(GLAutoDrawable drawable) { //this and others are from GLEventListener
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context
        gl.glClearColor(0.4f, 0.4f, 0.6f, 0.0f); // background color, RGB**A**
        gl.glEnable(GL2.GL_DEPTH_TEST); // otherwise you'll get weird results
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // perspective correction
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context

        if (height == 0) height = 1; // avoid invalid division
        float aspect = (float)width / height; // set aspect ratio

        gl.glViewport(0,0, width, height); //  set w/h of viewport

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // choose then reset projection matrix
        glu.gluPerspective(90.0f, aspect, 0.1f, 100.0f);
        //fovy, aspect, zNear, zFar

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // enable then reset model-view transform
    }

    // insert draw methods here
    // remember to scale, then rotate, then translate
    private void drawSimpleCube(GL2 gl, float scale, int rotation) {
        gl.glLoadIdentity(); // reset
        //glu.gluLookAt(2, 0, 9, 0, 0, 0, 0, 1, 0); // why do this?
        gl.glScaled(scale,scale,scale); // scale in x y z
        gl.glRotated(rotation, 0,0,1); // degrees of rotation around axis x y z
        gl.glTranslated(0,0,0); // x y z
        glut.glutSolidCube(1.0f);
    }

    private void drawWireCube(GL2 gl, float scale, int rotation) {
        gl.glLoadIdentity(); // reset
        look();
        //gl.glScaled(scale,scale,scale); // scale in x y z
        gl.glRotated(rotation, 0,0,1); // degrees of rotation around axis x y z
        //gl.glTranslated(0,0,0); // x y z
        glut.glutWireCube(scale); // argument is length of sides, actually
    }

    private void drawWireCubeThoroughly(GL2 gl, float s, float x, float y, float z) {
        gl.glLoadIdentity();
        look();
        gl.glTranslatef(x,y,z);
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex3f(s,s,s);
            gl.glVertex3f(-s,s,s);
            gl.glVertex3f(-s,s,-s);
            gl.glVertex3f(s,s,-s);
            gl.glVertex3f(s,-s,-s);
            gl.glVertex3f(-s,-s,-s);
            gl.glVertex3f(-s,s,-s);
            gl.glVertex3f(-s,-s,-s);
            gl.glVertex3f(-s,-s,s);
            gl.glVertex3f(-s,s,s);
            gl.glVertex3f(-s,-s,s);
            gl.glVertex3f(s,-s,s);
            gl.glVertex3f(s,s,s);
            gl.glVertex3f(s,-s,s);
            gl.glVertex3f(s,-s,-s);
            gl.glVertex3f(s,s,-s);
        gl.glEnd();
    }

    private void drawWeirdThings(GL2 gl) {

        float[][] weirdVertices = {
                {0.0f,2.0f,0.0f},
                {1.5f,1.5f,0.0f},
                {2.0f,0.0f,0.0f},
                {1.5f,-1.5f,0.0f},
                {0.0f,-2.0f,0.0f},
                {-1.5f,-1.5f,0.0f},
                {-2.0f,0.0f,0.0f},
                {-1.5f,1.5f,0.0f}
        };

        float[][] coords = {
                {0f,-10f},
                {-10f,-10f}, // drawn
                {-10f,0f}, // drawn
                {0f,0f}, // drawn
                {0f,10f}, // drawn
                {10f,10f}, // drawn
                {10f,0f},
                {0f,5f}, // drawn
                {5f,5f}, // drawn
                {5f,0f},
        };

        int[] primitives = {
                GL2.GL_POINT, // might not be able to be shown at all
                GL2.GL_LINES, // drawn
                GL2.GL_LINE_STRIP, // drawn
                GL2.GL_LINE_LOOP, // drawn
                GL2.GL_TRIANGLES, // drawn
                GL2.GL_TRIANGLE_STRIP, // drawn
                GL2.GL_TRIANGLE_FAN, // hm now
                GL2.GL_QUADS, // drawn
                GL2.GL_QUAD_STRIP, // drawn
                GL2.GL_POLYGON // should get drawn, so why not?
        };

        for (int i = 0; i < primitives.length; i++) {
            gl.glLoadIdentity();
            look();
            gl.glTranslatef(coords[i][0], 0, coords[i][1]);
            gl.glBegin(primitives[i]);
                for (int j = 0; j < weirdVertices.length; j++) {
                    //gl.glColor3fv(); could set colors
                    gl.glVertex3fv(weirdVertices[j], 0);
                }
            gl.glEnd();
        }
    }

    private void demonstrateWrongOrder(GL2 gl) {
        gl.glLoadIdentity();
        look();
        // first the right way
        gl.glColor3d(0,1,0);
        gl.glScaled(2,2,5);
        gl.glRotated(45,1,1,1);
        gl.glTranslated(13,3,-8);
        glut.glutWireCube(1.0f);

        // then some wronk ways - using all the same values (except colors of course)
        gl.glColor3d(1,0.5,0.5);
        gl.glLoadIdentity();
        look();
        gl.glRotated(45,1,1,1); // rot before scale - becomes lonker and weirdly placed
        gl.glScaled(2,2,5);
        gl.glTranslated(13,3,-8);
        glut.glutWireCube(1.0f);
        gl.glColor3d(1,0,0);

        gl.glLoadIdentity();
        look();
        gl.glTranslated(13,3,-8); // trans before all - coordinates modified, placed elsewhere
        gl.glScaled(2,2,5);
        gl.glRotated(45,1,1,1);
        glut.glutWireCube(1.0f);
        gl.glColor3d(1,1,0);
        gl.glLoadIdentity();
        look();
        gl.glTranslated(13,3,-8);
        gl.glRotated(45,1,1,1);
        gl.glScaled(2,2,5);
        glut.glutWireCube(1.0f);
        // OKAY. This is actually the RIGHT way
        // didn't you notice before? the coords you give shouldn't place these things
        // all the way down THERE - that's too far away.
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2(); // get grpahics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers

        look();
        //glu.gluLookAt(0,15,0,0,0,0,0,0,1); // CAMERA WOW what now
        // eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ - loc of eye, center of view, direction of up

        /*
        gl.glLoadIdentity();
        gl.glRotated(rot,1,1,1);
        gl.glTranslatef(0f,0f,0f);
        */

        //draw things
        gl.glColor3f(0.8f, 0.8f,0.8f); // R G B
        //drawSimpleCube(gl, 4f, rot);
        drawWireCube(gl, 7f, rot);
        drawWeirdThings(gl);
        drawWireCubeThoroughly(gl,4.0f,13f,0f,-1.5f);
        demonstrateWrongOrder(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { } // release resources before destructions

    @Override
    public void run() {
        GLCanvas canvas = DeprecatedDisplay.this;
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));


        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas); // adding canvas to window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // obvious
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);

        boolean cont = true;

        while (cont) {
            repaint();
            try {
                Thread.sleep(16);
            } catch (Exception e) {
                System.out.println("OH MY GOOOOD");
            }
        }
    }
}
