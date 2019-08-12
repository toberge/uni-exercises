public class Pendulum {


    private final Object limit;
    private final Object increment;

    public Pendulum(byte limit, byte increment) {
        if (Math.abs(limit) > 127 || Math.abs(increment) > 73) {
            throw new IllegalArgumentException("There's a limit to your limits/increments, pal");
        }
        this.limit = (Object) (Byte) limit;
        this.increment = (Object) (Byte) increment;
    }

    // uhh go make a BytePendulum

}
