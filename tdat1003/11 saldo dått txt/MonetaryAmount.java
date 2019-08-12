/*
 * MonetaryAmount.java
 *
 */

public class MonetaryAmount {

	private int amount;
	//private byte layers; // or get this from a currency class
	private int[] parts;

	public MonetaryAmount(int[] input, byte layers, int[] parts) {
	}

	public MonetaryAmount(int layer1, int layer0, int parts) { // two-layer currency constructor
		this.parts = {parts};
		amount = unify(layer1, layer0);
	}

	public MonetaryAmount(double decimal) {
		amount = unify(decimal);
	}


	// get methods
	public int getCrowns() { // for zis
		return amount / parts[0];
	}

	public int getØre() { // djeez, these are placeholders, ok?
		return amount % parts[0];
	}

	public double getDouble() {
		return makeDouble();
	}

	// set methods
	public void setAmount(int layer1, int layer0) {
		amount = unify(layer1, layer0);
	}

	public void setAmount(double decimal) {
		amount = unify(decimal);
	}

	// add methods
	public void addAmount(int layer1, int layer0) {
		amount += unify(layer1, layer0);
	}

	public void addAmount(double decimal) {
		amount += unify(decimal);
	}

	// unifying methods
	private int unify(int layer1, int layer0) { // two-layer currency unifier
		return layer1 * 100 + layer0;
	}

	private int unfiy(double decimal) {
		return (int) ((decimal * 100) + 0.5);
	}

	// returning methods
	private double makeDouble() {
		return (double) amount / 100.0;
	}
}