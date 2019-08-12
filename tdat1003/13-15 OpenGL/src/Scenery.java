import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;


public class Scenery {

    public static byte ORIENT_EXPRESS = 0;
    public static float ROT_ONE = 0f;
    public static float ROT_TWO = 90f;

    private static final float[][] weirdVertices = {
            {0.0f,2.0f,0.0f},
            {1.5f,1.5f,0.0f},
            {2.0f,0.0f,0.0f},
            {1.5f,-1.5f,0.0f},
            {0.0f,-2.0f,0.0f},
            {-1.5f,-1.5f,0.0f},
            {-2.0f,0.0f,0.0f},
            {-1.5f,1.5f,0.0f}
    };

    private static final float[][] weirdCoords = {
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

    private static final int[] weirdPrimitives = {
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

    public void drawWall(GL2 gl, float width, float height, float rot) {
        width /= 2;
        height *= 2;
        gl.glRotatef(rot,0,1,0);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(width,height,0);
            gl.glVertex3f(width,0,0);
            gl.glVertex3f(-width,0,0);
            gl.glVertex3f(-width,height,0);
        gl.glEnd();
    }

    public static void drawFloor(GL2 gl, float size) {
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(size,0,size);
            gl.glVertex3f(size,0,-size);
            gl.glVertex3f(-size,0,-size);
            gl.glVertex3f(-size,0,size);
        gl.glEnd();
    }

    private void drawWireCube(GL2 gl, GLUT glut, float scale, int rotation) {
        gl.glRotated(rotation, 0,0,1); // degrees of rotation around axis x y z
        //gl.glScaled(scale,scale,scale); // scale in x y z
        glut.glutWireCube(scale); // argument is length of sides, actually
    }

    public static void drawWeirdThings(GL2 gl) {
        for (int i = 0; i < weirdPrimitives.length; i++) {
            gl.glTranslatef(weirdCoords[i][0], 0, weirdCoords[i][1]);
            gl.glBegin(weirdPrimitives[i]);
            for (int j = 0; j < weirdVertices.length; j++) {
                //gl.glColor3fv(); could set colors
                gl.glVertex3fv(weirdVertices[j], 0);
            }
            gl.glEnd();
        }
    }
}
