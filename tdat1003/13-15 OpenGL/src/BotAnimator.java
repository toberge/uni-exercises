import com.jogamp.opengl.GL2;

/**
 * BotAnimator.java
 * This class will draw the player-controlled robot model (hopefully)
 *
 */

public class BotAnimator {

    private static final double HEIGHT = -1;

    private final float u; //unit
    //private float h = 2*u; // possible head size thing

    private byte state = 1;
    private byte lastState = 0;
    public static final byte IDLE = 0;
    public static final byte FORWARD = 1;
    public static final byte BACKWARD = 2;

    private double xPos = 0;
    private double yPos = 0;
    private double zPos = 0;

    private static final double offset = HEIGHT + 3 + Math.cos(0); // cos(0) = 1, so... well.

    private double turnRot = 0;
    private byte animRot = 0;
    private boolean positivePendulum = true;
    private boolean lastPendulum = true;
    private static final byte INCREMENT = 2;

    public BotAnimator() {
        this.u = BotDrawer.u;
    }

    public static double getOffset() {
        return offset;
    }

    public void setPos(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public void setRot(double turnRot) {
        this.turnRot = turnRot;
    }

    public void update(boolean forward, boolean backward) { // WIP
        /*
         * TODO: Make there only be an 'idle' boolean
         * State is needlessly complex, we only need to know the input (I think)
         * Also, positivePendulum is only set when in motion,
         * so a lastPendulum save is not necessary. Should only check what was going on last.
         * --- oh, I may need the state. or a boolean replacement.
         * And I could make a level reader and such. Yeeeeeah.
         */

        if (Math.abs(animRot) + 1 > 45) {
            turnPendulum();
        }

        if (!forward && !backward) {
            relaxPendulum();
        } else if (forward && !backward) {
            if (state == FORWARD) {
                swingPendulum();
            } else if (state == BACKWARD) {
                turnPendulum();
                swingPendulum();
                state = FORWARD;
            }
        } else if (!forward && backward) {
            if (state == BACKWARD) {
                swingPendulum();
            } else if (state == FORWARD) {
                turnPendulum();
                swingPendulum();
                state = BACKWARD;
            }
        } else if (forward && backward) {
            relaxPendulum();
        }
    }

    private void turnPendulum() {
        if (animRot >= 0) {
            positivePendulum = false;
        } else {
            positivePendulum = true;
        }
    }

    private void swingPendulum() {
        if (positivePendulum) {
            animRot += INCREMENT;
        } else {
            animRot -= INCREMENT;
        }
    }

    private void relaxPendulum() {
        if (animRot > 1) {
            animRot -= INCREMENT;
        } else if (animRot < -1){
            animRot += INCREMENT;
        }
    }

    public void draw(GL2 gl) {
        double relativeOffset = Math.cos(Math.abs(Math.toRadians(animRot)));
        gl.glTranslated(xPos,yPos + HEIGHT + relativeOffset,zPos);
        gl.glRotated(turnRot,0,1,0);

        drawHead(gl);
        drawFeet(gl);
        drawDarkness(gl);

    }

    public void drawHead(GL2 gl) {
        gl.glPushMatrix();
            BotDrawer.drawHead(gl);
            gl.glTranslated(u,u,-2*u);
            gl.glPushMatrix();
                gl.glRotated(90,0,1,0);
                BotDrawer.drawEye(gl);
                gl.glTranslated(0,0,-2*u);
                BotDrawer.drawEye(gl);
            gl.glPopMatrix();
        gl.glPopMatrix();
    }

    public void drawFeet(GL2 gl) {
        gl.glPushMatrix();
            gl.glTranslated(2*u,0,0);
            gl.glRotated(animRot,1,0,0);
            BotDrawer.drawLeg(gl);
            gl.glTranslated(u,-4*u,0);
            gl.glRotated(-animRot,1,0,0);
            BotDrawer.drawFoot(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
            gl.glRotated(180,0,1,0);
            gl.glTranslated(2*u,0,0);
            gl.glRotated(animRot,1,0,0);
            BotDrawer.drawLeg(gl);
            gl.glTranslated(u,-4*u,0);
            gl.glRotated(-animRot,1,0,0);
            BotDrawer.drawFoot(gl);
        gl.glPopMatrix();
    }

    public void drawDarkness(GL2 gl) {
        gl.glPushMatrix();
            gl.glTranslated(0,34*u,0);
            gl.glScaled(3,3,3);
            gl.glPushMatrix();
                gl.glScaled(1.5,1.5,1.5);
                BotDrawer.drawDarkness(gl);
            gl.glPopMatrix();
            gl.glTranslated(2*u,u,-u/2);
            gl.glPushMatrix();
                gl.glRotated(15 + animRot * 1.5f,1,0,0);
                gl.glRotated(30,0,0,1);
                BotDrawer.drawDarkFangs(gl);
            gl.glPopMatrix();
            gl.glTranslated(-5*u,0,0);
            gl.glPushMatrix();
                gl.glRotated(-15 - animRot * 1.5f,1,0,0);
                gl.glRotated(-30,0,0,1);
                BotDrawer.drawDarkFangs(gl);
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
