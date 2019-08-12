import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;


/**
 * This will be an øving.
 * Perhaps more than one.
 * We will see what will become
 * of this stupid Java code.
 */





public class DisplayThing extends GLCanvas implements GLEventListener, Runnable {

    private static String TITLE = "Jogler mig her og jogler mig der";
    private static final int CANVAS_WIDTH = 1600;
    private static final int CANVAS_HEIGHT = 900;
    private static final byte FPS = 60;

    private GLU glu = new GLU(); // GL utility
    private GLUT glut = new GLUT();

    private PlayerControl control;
    private BotAnimator theBot;
    private Scenery scenery = new Scenery();

    public DisplayThing(PlayerControl control) {
        this.addGLEventListener(this);
        this.addKeyListener(control.getKeyHandler());
        this.addMouseListener(control.getMouseHandler());
        this.theBot = control.getTheBot();
        this.control = control;
    }

    public void look() {
        glu.gluLookAt(control.getEyeX(),control.getEyeY(),control.getEyeZ(),
                      control.getPosX(),control.getPosY(),control.getPosZ(),
                      control.getUpX(),control.getUpY(),control.getUpZ());
        // eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ
        // - loc of eye,        center of view,         direction of up
    }

    public void readyToDraw(GL2 gl) {
        gl.glLoadIdentity();
        look();
    }

    @Override
    public void init(GLAutoDrawable drawable) { //this and others are from GLEventListener
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context
        gl.glClearColor(Colors.palette[Colors.CRIMSON][0],
                        Colors.palette[Colors.CRIMSON][1],
                        Colors.palette[Colors.CRIMSON][2], 0.0f); // background color, RGBA
        gl.glEnable(GL2.GL_DEPTH_TEST); // otherwise you'll get weird results
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // perspective correction

        //fantastic attempts at copy-pasting stuff
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        //gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        //gl.glEnable(GL2.GL_BLEND);
        // THE CULPRIT: gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        //gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_DONT_CARE);
        gl.glLineWidth(4f);

        control.update();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2(); // get GL2 context

        if (height == 0) height = 1; // avoid invalid division
        float aspect = (float)width / height; // set aspect ratio

        gl.glViewport(0,0, width, height); //  set w/h of viewport

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // choose then reset projection matrix
        glu.gluPerspective(90.0f, aspect, 0.1f, 400.0f);
        //fovy, aspect, zNear, zFar

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // enable then reset model-view transform
    }

    // insert draw methods here
    // remember to scale, then rotate, then translate
    // (which means write them the opposite way)
    private void drawWireCube(GL2 gl, float scale, int rotation) {
        gl.glRotated(rotation, 0,0,1); // degrees of rotation around axis x y z
        //gl.glScaled(scale,scale,scale); // scale in x y z
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



    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2(); // get grpahics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers

        // updating positions and such
        control.update();

        // DRAW THE BOTTO
        readyToDraw(gl);
        theBot.draw(gl);

        // DRAW THE FLOTTO
        readyToDraw(gl);
        gl.glColor3fv(Colors.palette[Colors.DIM_CRIMSON],0);
        Scenery.drawFloor(gl,400);

        //draw things
        gl.glColor3f(0.8f, 0.8f,0.8f); // R G B

        //drawing the old things that should be remade maybe
        readyToDraw(gl);
        gl.glTranslatef(-43,5f,4f);
        drawWireCube(gl, 7f, 30);
        drawWireCubeThoroughly(gl,4.0f,13f - 43f,5f,-1.5f + 4f);

        readyToDraw(gl);
        gl.glTranslatef(-43,5f,4f);
        Scenery.drawWeirdThings(gl);

        // draw a more proper spawn area - could of course be put in method
        gl.glColor3fv(Colors.palette[Colors.WHITE],0); // R G B
        readyToDraw(gl);
        gl.glTranslatef(12f,0f,0f);
        gl.glPushMatrix();
        scenery.drawWall(gl,30f,6f, Scenery.ROT_TWO);
        gl.glPopMatrix();
        gl.glTranslatef(-24f,0f,0f);
        gl.glPushMatrix();
        scenery.drawWall(gl,30f,6f, Scenery.ROT_TWO);
        gl.glPopMatrix();
        //first cube
        gl.glTranslatef(12f,6f,-32f);
        gl.glPushMatrix();
        drawWireCube(gl,6,45);
        gl.glPopMatrix();
        //second set of walls
        gl.glTranslatef(24f,-6f,-32f);
        gl.glPushMatrix();
        scenery.drawWall(gl,30f,6f, Scenery.ROT_TWO);
        gl.glPopMatrix();
        gl.glTranslatef(-48f,0f,0f);
        gl.glPushMatrix();
        scenery.drawWall(gl,30f,6f, Scenery.ROT_TWO);
        gl.glPopMatrix();
        gl.glTranslatef(24f,0f,0f);

    }

    @Override
    public void dispose(GLAutoDrawable drawable) { } // release resources before destructions

    @Override
    public void run() {
        GLCanvas canvas = DisplayThing.this;
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final JFrame frame = new JFrame(); // making window
        frame.getContentPane().add(canvas); // adding canvas to window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // obvious
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);

        FPSAnimator fps = new FPSAnimator(canvas, FPS, true);
        fps.start();

        //boolean cont = true;
        /*
        while (!control.esc) {
            repaint();
            try {
                Thread.sleep(16);
            } catch (Exception e) {
                System.out.println("OH MY GOOOOD");
            }
        }
        */
    }
}
