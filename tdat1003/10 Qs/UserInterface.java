// absolutely brilliant GUI (tm)

import static javax.swing.JOptionPane.*;
//import java.util.Formatter;

public class UserInterface {
  private final String[] THEM_CHOICES = {"Registrer ny student", "Øk antall oppgaver", "Endre sortering", "Avslutt"};
  private final String[] SORT_MODES = {"Alfabetisk etter navn", "Etter antall løste oppgaver (stigende)", "Etter antall løste oppgaver (synkende)"};
  private static final javax.swing.ImageIcon logo = new javax.swing.ImageIcon("logo.png");
  private final byte REGISTER_STUDENT = 0;
  private final byte INCREASE_TASKS_DONE = 1;
  private final byte SET_SORT_MODE = 2;
  private final byte EXIT = 3;

  private final Overseer object;

  public UserInterface
(Overseer theObject) {
    object = theObject;
  }

  @Override
  public String toString() { // absolutely pointless easter egg
    return "I am a machine. You can't just string me up.";
  }

  public int promptUser() {
    int choice = showOptionDialog(null, "Our magnificent list of fantastic students:\n" + object, "Qs: Death and Rebirth",
                                DEFAULT_OPTION, PLAIN_MESSAGE, logo,
                                THEM_CHOICES, THEM_CHOICES[0]);
    if (choice == EXIT) {
      return - 1;
    }

    return choice;
  } // end questioning

  public void obey(int choice) {
    switch (choice) {

      case REGISTER_STUDENT:
        regsterStudent();
        break;
      case INCREASE_TASKS_DONE:
        increaseTasksDone();
        break;
      case SET_SORT_MODE:
        setSortMode();
        break;
      default:
        break;
    } // could call the sort method after this
  } // end command section

  private String fetchInput(String msg) {
    return showInputDialog(msg); // should check if null or something, but not necessary here
  }

  private void regsterStudent() {
    String studName = fetchInput("Hva heter denne studenten?");
    try {
    object.registerStudent(studName);
    } catch (IllegalArgumentException e) {
      showMessageDialog(null, "Either your name is invalid\nor the student is already registered.");
    } 
    /* YOU ALREADY CHECK FOR THIS IN STUDENT CLASS
    catch (NullPointerException e) {
      showMessageDialog(null, "Can't accept empty input");
    }
    */
  }

  private void increaseTasksDone() {
    //Student theStudent = new Student(fetchInput("Og hva heter denne studenten?")); // evt sjekke om studenten eksisterer
    String studName = fetchInput("Hva heter denne heldige studenten?"); // should check if student exists first
    int increase = Integer.parseInt(fetchInput("Hvor mange flere oppgaver har studenten løst?"));

    try {
    object.increaseTasksDone(studName, increase);
    } catch (IllegalArgumentException e) {
      showMessageDialog(null, "The goddamn student doesn't exist,\nplease try typing the name again... later.\nAlternatively, don't try decreasing, dumbass.");
    } catch (NullPointerException e) {
      showMessageDialog(null, "Can't accept empty input");
    }
  }

  private void setSortMode() {
    int choice = showOptionDialog(null, "Sorter...", "Qs: Death and Rebirth",
                                DEFAULT_OPTION, QUESTION_MESSAGE, null,
                                SORT_MODES, SORT_MODES[0]);
    object.setSortMode((byte) choice);
  }
}