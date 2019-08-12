/**
 * In this application, strings from the GUI are <i>never</i> null.
 */

public class Validator {

    /*
    public static boolean isInt(String input, int min, int max) {

        try {

            int parsed = Integer.parseInt(input);
            return (parsed >= min && parsed <= max);

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean isInt(String input, int min) {
        return isInt(input, min, Integer.MAX_VALUE);
    }

    public static boolean isInt(String input) {
        return isInt(input, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    */

    public static boolean isPositiveInteger(String input) {

        try {

            int parsed = Integer.parseInt(input);
            return (parsed > 0);

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean isPositiveDouble(String input) {

        try {

            double parsed = Double.parseDouble(input);
            return (parsed > 0);

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean isText(String input) {
        return !input.trim().equals("");
    }

}
