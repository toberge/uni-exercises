import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PlayerControl {

    private float distCam = 3;
    private static final byte MAX_DIST = 30;
    private static final byte MIN_DIST = 2;

    private double posX = 0;
    private double posY;
    private double posZ = 0;

    private double eyeX = 0;
    private double eyeY = 3;
    private double eyeZ = 0;

    private double upX = 0;
    private double upY = 1;
    private double upZ = 0;

    private boolean w = false;
    private boolean a = false;
    private boolean s = false;
    private boolean d = false;

    private boolean q = false;
    private boolean e = false;

    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;

    private boolean r = false;
    private boolean f = false;

    private boolean shift = false;
    private boolean space = false;
    public boolean esc = false;

    private KeyHandler keys = new KeyHandler();
    private MouseHandler mouse = new MouseHandler();
    private BotAnimator theBot = new BotAnimator();

    /*
    private boolean forthLast = false;
    private boolean leftLast = false;
    private boolean ascendedLast = false;
    */

    private double walkDir = 0; // horizontal walk angle
    private double camDir = 0; // added to walkDir when I get the bot working
    private double camTilt = Math.PI / 2; // needed for mouselook, might have a walkTilt if I get to them physics.

    public PlayerControl() {
        posY = 3 + BotAnimator.getOffset(); // set offset there to be three more?
    }

    // GET METHODS
    public KeyHandler getKeyHandler() {
        return keys;
    }

    public MouseHandler getMouseHandler() {
        return mouse;
    }

    public BotAnimator getTheBot() {
        return theBot;
    }

    public double getEyeX() {
        return eyeX;
    }

    public double getEyeY() {
        return eyeY;
    }

    public double getEyeZ() {
        return eyeZ;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public double getUpX() {
        return upX;
    }

    public double getUpY() {
        return upY;
    }

    public double getUpZ() {
        return upZ;
    }

    // Method for passing draw to botto
    /*
    public void drawBot(GL2 gl) {
        theBot.draw(gl);
    }
    */

    // MOVE METHODS
    private void goForward() {
        posX -= Math.sin(walkDir);
        posZ -= Math.cos(walkDir);
    }

    private void goBackward() {
        posX += Math.sin(walkDir);
        posZ += Math.cos(walkDir);
    }

    private void strafeLeft() {
        posX -= Math.sin(walkDir + Math.PI / 2);
        posZ -= Math.cos(walkDir + Math.PI / 2);
    }

    private void strafeRight() {
        posX -= Math.sin(walkDir - Math.PI / 2);
        posZ -= Math.cos(walkDir - Math.PI / 2);
    }

    private void flyUp() {
        posY++;
    }

    private void flyDown() {
        posY--;
    }

    private void turnLeft() {
        walkDir += 0.05;
        testDirection();
    }

    private void turnRight() { // TODO: Check if the difference becomes high enough to force camDir to move?
        walkDir -= 0.05;
        testDirection();
    }

    private void turnCamLeft() {
        camDir += 0.05;
        testCamDir();
    }

    private void turnCamRight() {
        camDir -= 0.05;
        testCamDir();
    }

    private void tiltCamUp() {
        if (camTilt - 0.05 > 0) {
            camTilt -= 0.05;
        }
    }

    private void tiltCamDown() {
        if (camTilt + 0.05 < Math.PI) {
            camTilt += 0.05;
        }
    }

    private void moveCamAway() {
        if (distCam + 0.4f <= MAX_DIST) {
            distCam += 0.4f;
        }
    }

    private void moveCamCloser() {
        if (distCam - 0.4f >= MIN_DIST) {
            distCam -= 0.4f;
        }
    }

    private void testDirection() {
        if (walkDir > 2 * Math.PI) {
            walkDir -= 2 * Math.PI;
        } else if (walkDir < 2 * Math.PI){
            walkDir += 2 * Math.PI;
        }
    }

    private void testCamDir() {
        if (camDir > 2 * Math.PI) {
            camDir -= 2 * Math.PI;
        } else if (camDir < 2 * Math.PI){
            camDir += 2 * Math.PI;
        }
    }

    // UPDATE METHOD
    public void update() {
        checkKeys();
        eyeY = posY + distCam * Math.cos(camTilt);
        eyeX = posX + distCam * Math.sin(walkDir + camDir) * Math.sin(camTilt);
        eyeZ = posZ + distCam * Math.cos(walkDir + camDir) * Math.sin(camTilt);

        theBot.setPos(posX,posY,posZ);
        theBot.setRot(Math.toDegrees(walkDir));
        theBot.update(w,s);
    }

    private void checkKeys() {
        if (w) {
            goForward();
        }
        if (s) {
            goBackward();
        }
        if (a) {
            turnLeft();
        }
        if (d) {
            turnRight();
        }
        if (q) {
            strafeLeft();
        }
        if (e) {
            strafeRight();
        }


        if (j) {
            turnCamLeft();
        }
        if (l) {
            turnCamRight();
        }
        if (i) {
            tiltCamUp();
        }
        if (k) {
            tiltCamDown();
        }
        if (r) {
            moveCamCloser();
        }
        if (f) {
            moveCamAway();
        }


        if (space) {
            flyUp();
        }
        if (shift) {
            flyDown();
        }
    }


    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent thisEvent) {
            switch (thisEvent.getKeyCode()) {
                case KeyEvent.VK_W: //W
                    w = true;
                    break;
                case KeyEvent.VK_S: //S
                    s = true;
                    break;
                case KeyEvent.VK_A: //A
                    a = true;
                    break;
                case KeyEvent.VK_D: //D
                    d = true;
                    break;
                case KeyEvent.VK_Q: //Q
                    q = true;
                    break;
                case KeyEvent.VK_E: //E
                    e = true;
                    break;
                case KeyEvent.VK_J: //J
                    j = true;
                    break;
                case KeyEvent.VK_L: //L
                    l = true;
                    break;
                case KeyEvent.VK_I: //I
                    i = true;
                    break;
                case KeyEvent.VK_K: //K
                    k = true;
                    break;
                case KeyEvent.VK_R: //I
                    r = true;
                    break;
                case KeyEvent.VK_F: //K
                    f = true;
                    break;
                case KeyEvent.VK_SPACE: //SPACE
                    space = true;
                    break;
                case KeyEvent.VK_SHIFT:
                    shift = true;
                    break;
                case KeyEvent.VK_ESCAPE: // unused as of right now, don't know how to make it work yet
                    esc = true;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent thisEvent) {
            switch (thisEvent.getKeyCode()) {
                case KeyEvent.VK_W: //W
                    w = false;
                    break;
                case KeyEvent.VK_S: //S
                    s = false;
                    break;
                case KeyEvent.VK_A: //A
                    a = false;
                    break;
                case KeyEvent.VK_D: //D
                    d = false;
                    break;
                case KeyEvent.VK_Q: //Q
                    q = false;
                    break;
                case KeyEvent.VK_E: //E
                    e = false;
                    break;
                case KeyEvent.VK_J: //J
                    j = false;
                    break;
                case KeyEvent.VK_L: //L
                    l = false;
                    break;
                case KeyEvent.VK_I: //I
                    i = false;
                    break;
                case KeyEvent.VK_K: //K
                    k = false;
                    break;
                case KeyEvent.VK_R: //I
                    r = false;
                    break;
                case KeyEvent.VK_F: //K
                    f = false;
                    break;
                case KeyEvent.VK_SPACE: //SPACE
                    space = false;
                    break;
                case KeyEvent.VK_SHIFT:
                    shift = false;
                    break;
                default:
                    break;
            }
        }

        // keyTyped occurs only when there's a discernible char associated with the key.
    }

    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
        }
    }
}
