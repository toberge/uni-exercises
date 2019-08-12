public class Currency {

    private String name;
    private double factor;
    private int unit;

    public Currency(String name, double factor, int unit) {

        if (name == null || unit < 0 || factor < 0 || name.trim().equals("")) {
            throw new IllegalArgumentException("Invalid currency args");
        }

        this.name = name;
        this.factor = factor;
        this.unit = unit;

    }

    public String getName() {
        return name;
    }

    public double getFactor() {
        return factor;
    }

    public int getUnit() {
        return unit;
    }

    public double convert(double amount, Currency from) {

        if (this.equals(from)) return amount;

        double generalizedFrom = from.getFactor() / from.getUnit();
        double generalizedTo = factor / unit;

        return (amount / generalizedTo) * generalizedFrom; // HOW DID YOU GET THE ORDER WRONG **AGAIN**

    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;

        if (obj == this) return true;

        if (!(obj instanceof Currency)) return false;

        Currency temp = (Currency) obj;

        return this.name.equals(temp.getName()); // name is deciding factor
    }

    @Override
    public String toString() {
        return name;
    }

}
