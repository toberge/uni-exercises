import qqlib.cli.ScanWrapper;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class PasswordTest  {

    private static final BASE64Encoder BASE_64_ENCODER = new BASE64Encoder();

    // these constants must not be changed once we begin storing passwords in the database
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;

    /*
    /**
     *
     *
     * @param password The password to be hashed
     * @return ITERATIONS:haSHeDpASSwoRd:sALt - both hash and salt must be stored, TODO decide wether iterations are gonna change sometime or not
     *//*
    public static String hashThisPassword(String password) {

        char[] passedArray = password.toCharArray(); // make it array cuz PBE needs it
        byte[] salt = getSalty();
        PBEKeySpec speccie = new PBEKeySpec(passedArray, salt, ITERATIONS, KEY_LENGTH); // what we'll use to hash shit

        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashed = factory.generateSecret(speccie).getEncoded();

            return ITERATIONS + ":" + encode(hashed) + ":" + encode(salt);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) { // no need to handle them differently, this WILL happen BEFORE the array is filled
            e.printStackTrace();
            // quite the fatal error if it actually happens...
        }

        return null;
    }
    */


    /**
     * Method for hashing a salted password
     *
     * @param password  The password to be hashed, should be read directly as an array for more certain removal
     * @param salt      The salt we'll use to hash it - please use the salting method provided here
     *
     * @return          byte[] with hashed password
     */
    public static byte[] hashThisPassword(char[] password, byte[] salt) {

        PBEKeySpec speccie = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH); // what we'll use to hash shit
        Arrays.fill(password, 'H'); // example used MIN_VALUE
        password = null; // like clearPassword() does, making garbage collection thrash it if it looks in this method immediately
                         // a lil' bit excessive tho

        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); // originally SHA1 but we want it higher don't we

            return factory.generateSecret(speccie).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) { // no need to handle them differently, this WILL happen BEFORE the array is filled
            e.printStackTrace();
            // quite the fatal error if it actually happens...
        } finally {
            speccie.clearPassword(); // yay we clear it
        }

        return null;
    }


    /**
     *
     * @param input     What the user typed, as a char[]
     * @param actual    The char[] stored in the database
     * @param salt      The byte[] with the salt used to hash the actual password
     * @return          Whether this is the same password or not
     */
    public static boolean isCorrectPassword(char[] input, char[] actual, byte[] salt) {

        byte[] hashedInput = hashThisPassword(input, salt);
        Arrays.fill(input, 'C'); // not strictly necessary

        char[] encodedInput = encode(hashedInput); // HOW DID YOU OVERLOOK THIS OMG

        for (int i = 0; i < encodedInput.length; i++) {

            if (actual[i] != encodedInput[i]) {
                return false; // WAS NOT THE SAME
            }

        }

        return true; // passed the test

    }


    /**
     * Generates a random salt to use in the hashing method.
     * Uses SecureRandom to do so, preferring the NativePRNG in the OS
     *
     *
     * @return      the byte[] with the tasteless salt, NULL in case of failure
     */
    public static byte[] getSalty() {

        byte[] theSalt = null;

        try {

            SecureRandom rng = SecureRandom.getInstance("NativePRNG");
            // SecureRandom rng = SecureRandom.getInstance("SHA1PRNG"); is an alternative - I presume the above is present on all OSes supporting Java?

            theSalt = new byte[SALT_LENGTH]; // if that's normal salt length

            rng.nextBytes(theSalt); // filling it with sparkling random crystals

        } catch (NoSuchAlgorithmException nsa) {
            nsa.printStackTrace();
        }

        return theSalt; // return null if we fail
    }


    /**
     * Encodes an array of bytes in base-64.
     * This seems to be rather common.
     *
     * @param array     byte[] to be encoded (a hashed 'n salted password)
     * @return          encoded char[] to store in database or use to test against a password
     */
    public static char[] encode(byte[] array) {

        return BASE_64_ENCODER.encode(array).toCharArray(); // making it a char[] at once

    }

    // used for testing, please ignore.
    public static boolean isCorrectPassword(byte[] actual, char[] input, byte[] salt) {

        byte[] hashedInput = hashThisPassword(input, salt);
        Arrays.fill(input, 'C');

        for (int i = 0; i < hashedInput.length; i++) {

            if (actual[i] != hashedInput[i]) {
                return false; // WAS NOT THE SAME
            }

        }

        return true; // was the same
    }

    public static void main(String[] args) throws Exception {
        byte[] hello = {1, 2, 3, 4};
        String goodbye = BASE_64_ENCODER.encode(hello);
        System.out.println(goodbye);
        SecureRandom rng = SecureRandom.getInstance("NativePRNG"); // OS's own RNG - if not then use SHA1PRNG
                // see https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom
                // getInstanceStrong is a stronker alternative (possibly) but there you can't pick one yourself...
        System.out.println(rng.nextGaussian());
        byte[] saltyStuff = rng.generateSeed(SALT_LENGTH);
        for (byte b : saltyStuff) {
            System.out.print(b + " ");
        }
        System.out.println(System.lineSeparator());
        Arrays.fill(saltyStuff, Byte.MAX_VALUE); // general stuff to OVERWRITE THE ARRAY IN MEMORY
        rng.nextBytes(saltyStuff);
        for (byte b : saltyStuff) {
            System.out.print(b + " ");
        }
        System.out.println(System.lineSeparator() + rng.getAlgorithm() + " by, uh, " + rng.getProvider());

        ScanWrapper sc = new ScanWrapper();
        System.out.print("Your password, please: ");

        byte[] theSalt = getSalty();
        byte[] thePass = hashThisPassword("kake".toCharArray(), theSalt);

        //thePass.toString().toCharArray() lol wtf

        char[] storedPass = encode(thePass); // uhhhhhhhhhhhhh wait now it's never encoded in the first place

        if(isCorrectPassword(sc.fetchString().toCharArray(), storedPass, theSalt)) {

            System.out.println("yes");

        } else {

            System.out.println("um nope");

        }

        System.out.println("We try again:");

        String superSafe = "thisISnoPA33";
        System.out.println("Type password to hash: ");
        superSafe = sc.fetchString();
        char[] password = superSafe.toCharArray();
        byte[] salt = getSalty();
        byte[] actual = hashThisPassword(password, salt);

        System.out.println(isCorrectPassword(actual, superSafe.toCharArray(), salt)?
                                "Det gikk gitt" : "Det gikk itte");


        System.out.println("Salt length:" + salt.length + "\nPass length: " + actual.length);

        System.out.println("Encoded pass length: " + encode(actual).length);


    }


}
