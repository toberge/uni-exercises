import static javax.swing.JOptionPane.*;

public class Main {
    public static void main(String[] args) {
        if (1 == showConfirmDialog(null,
                "Avoid running surveillance program?",
                "Pre-Launch Option Handler",
                YES_NO_OPTION)) {
            MultiCam cams = new MultiCam();
            cams.run();
        } else {
            PlayerControl control = new PlayerControl();
            DisplayThing display = new DisplayThing(control);
            display.run();
        }
        //Thread displayThread = new Thread(display);
        //displayThread.start();
    }
}
