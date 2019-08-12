import java.util.Scanner;

public class OldClient {

    private static int fetchInt(Scanner sc, int min, int max) {

        int number = -1;
        boolean invalid = true;

        do {
            number = sc.nextInt();
            sc.nextLine();

            if (number < min || number > max) {
                System.out.println("Number should be within [" + min + ", " + max + "]");
            } else {
                invalid = false;
            }

        } while (invalid);

        return number;

    }

    private static String fetchString(Scanner sc) {

        String res;
        boolean invalid = true;

        do {
            res = sc.nextLine();

            if (res == null || res.trim().equals("")) {
                System.out.println("Can't accept \"" + res + "\" as input.");
            } else {
                invalid = false;
            }

        } while (invalid);

        return res.trim();

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] heh = {"Five", "Three", "One"};

        int theNumber = fetchInt(sc, 0, 2);

        System.out.println("hey that is " + theNumber + " which means " + heh[theNumber]);

        System.out.println(fetchString(sc) + " scanny boi's delimiter is " + sc.delimiter());

        sc.close();

    }
}
