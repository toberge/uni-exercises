import com.jogamp.opengl.GL2;

import java.awt.*;

public class BotDrawer {

    public static final float u = 0.25f;

    private static final float[][] headVertices = {
            {-2*u,   -2*u,    2*u}, // 0
            { 2*u,   -2*u,    2*u}, // 1
            { 2*u,    2*u,    2*u}, // 2
            {-2*u,    2*u,    2*u}, // 3
            // BACKSIDE START
            {-2*u,   -2*u,   -2*u}, // 4
            { 2*u,   -2*u,   -2*u}, // 5
            { 2*u,    2*u,   -2*u}, // 6
            {-2*u,    2*u,   -2*u}  // 7
            // Cube centered in origo, with side length 2
    };

    private static final byte[] headColors = {Colors.WHITE,Colors.YELLOW,Colors.WHITE,Colors.YELLOW,Colors.LIME,Colors.YELLOW};

    private static final float[][] wartVertices = { // right is default placement side
            // JOINT SIDE START
            { 0,    -u/2,    -u/2}, // 0
            { 0,    -u/2,     u/2}, // 1
            { 0,     u/2,     u/2}, // 2
            { 0,     u/2,    -u/2}, // 3
            // OTHER SIDE START
            {u,   -u/2,    -u/2}, // 4
            {u,   -u/2,     u/2}, // 5
            {u,    u/2,     u/2}, // 6
            {u,    u/2,    -u/2}  // 7
            // center in middle of upper part or whatever you'd say
    };

    private static final byte[] eyeColors = {0,Colors.LIME,Colors.YELLOW,Colors.LIME,Colors.LIME,Colors.LIME};

    private static final float[][] legVertices = { // right is default placement side
            // JOINT SIDE START
            { 0,   -4*u,   -u/2}, // 0
            { 0,   -4*u,    u/2}, // 1
            { 0,     u/2,     u/2}, // 2
            { 0,     u/2,    -u/2}, // 3
            // OTHER SIDE START
            {u,  -4*u,   -u/2}, // 4
            {u,  -4*u,    u/2}, // 5
            {u,    u/2,     u/2}, // 6
            {u,    u/2,    -u/2}  // 7
            // center in middle of upper part or whatever you'd say
    };

    private static final byte[] legColors = {Colors.WHITE,Colors.LIME,Colors.WHITE,Colors.LIME,Colors.WHITE,Colors.LIME};


    private static final byte[][] cubePoints = {
            {0,1,2,3}, // joint side || side facing camera
            {1,5,6,2}, // right side
            {5,4,7,6}, // opposite side
            {4,0,3,7}, // left side
            {2,6,7,3}, // top
            {0,4,5,1}  // bottom
    };

    private static final float[][] footVertices = { // right is default placement side
            {0,u,u},
            {u,u,u},
            {u,u,-u},
            {0,u,-u}, // end of top
            {0,-3*u,-u},
            {0,-3*u,u},
            {2*u,-3*u,u},
            {2*u,-3*u,-u}, // end of bottom
            {2*u,0,-u},
            {2*u,0,u} // end of wedge bit
    };

    private static final byte[] footColors = {Colors.WHITE,Colors.YELLOW,Colors.YELLOW,Colors.PINK,Colors.WHITE,Colors.PINK,Colors.YELLOW};

    private static final byte[][] footPoints = {
            {0,1,2,3}, // top
            {1,9,8,2}, // wedge bit
            {0,3,4,5}, // joint side
            {0,5,6,9,1}, // outward-facing side
            {6,7,8,9}, // other side
            {7,4,3,2,8}, // inward-facing side
            {4,7,6,5}, // bottom
    };

    public BotDrawer() {
    }

    // multi-purpose draw method
    private static void drawSide(GL2 gl, float[][] vertices, byte[] vertex, byte color) {
        gl.glColor3fv(Colors.palette[color],0);
        gl.glBegin(GL2.GL_POLYGON);
        for (byte i = 0; i < vertex.length; i++) {
            gl.glVertex3fv(vertices[vertex[i]],0);
        }
        gl.glEnd();
    }

    // drawing specific bot parts
    public static void drawHead(GL2 gl) {
        for (byte i = 0; i < cubePoints.length; i++){
            drawSide(gl, headVertices,cubePoints[i], headColors[i]);
        }
    }

    public static void drawDarkness(GL2 gl) {
        for (byte i = 0; i < cubePoints.length; i++){
            drawSide(gl, headVertices,cubePoints[i], Colors.BLACK);
        }
    }

    public static void drawDarkFangs(GL2 gl) {
        for (byte i = 0; i < cubePoints.length; i++){
            drawSide(gl, legVertices,cubePoints[i], Colors.BLACK);
        }
    }

    public static void drawEye(GL2 gl) {
        for (byte i = 0; i < cubePoints.length; i++){
            drawSide(gl, wartVertices,cubePoints[i], eyeColors[i]);
        }
    }

    public static void drawLeg(GL2 gl) {
        for (byte i = 0; i < cubePoints.length; i++){
            drawSide(gl, legVertices,cubePoints[i], legColors[i]);
        }
    }

    public static void drawFoot(GL2 gl) {
        for (byte i = 0; i < footPoints.length; i++){
            drawSide(gl, footVertices,footPoints[i], footColors[i]);
        }
    }
}
