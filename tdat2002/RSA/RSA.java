public class RSA {

    public static String stringify(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int c : array) {
            builder.append((char) c);
        }
        return builder.toString();
    }

    public static int[] decrypt(int[] array, int n, int d) {
        int[] retArr = new int[array.length];
        for (int j = 0; j < array.length; j++) {
            int res = 1;
            for (int i = 0; i < d; i++) { // as many times as you should
                res *= array[j]; // multiply
                res %= n; // reduce
            }
            retArr[j] = res;
        }
        return retArr;
    }

    public static void main(String[] args) {
        int[] things = decrypt(new int[]{28, 18, 675, 129}, 713, 307);
        for (int thing :
                things) {
            System.out.println(thing);
        }
        System.out.println();
        System.out.println(stringify(decrypt(
                new int[]{24, 159, 186, 121, 100, 90, 186, 188, 100, 143, 172,
                117, 186, 50, 143, 100, 36, 186, 36, 158, 118, 152, 118, 186, 121, 100,
                90, 186, 36, 3, 143, 117, 186, 117, 100, 186, 141, 100, 99, 186, 117,
                158, 118, 143, 186, 51, 117, 186, 188, 100, 118, 191, 143, 172, 117, 186,
                21, 3, 117, 117, 118, 152, 186, 36, 158, 51, 66, 158, 186, 206, 3,
                117, 158, 186, 121, 100, 90, 186, 117, 3, 50, 118, 8},
                209, 7)));
    }
}
