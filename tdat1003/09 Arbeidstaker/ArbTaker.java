public class ArbTaker {
	// object variables
	private final Person personalia;
	private final int arbtakernr; // 8-sifra tall (maks 9-sifra siden int)
	private final short ansettelsesår;
	private float månedslønn;
	private float skatteprosent;

	// constructor(s)

	public ArbTaker(Person personalia, int arbtakernr, short ansettelsesår, float månedslønn, float skatteprosent) {
		this.personalia = new Person(personalia.getFornavn(), personalia.getEtternavn(), personalia.getFødselsår());
		this.arbtakernr = arbtakernr;
		this.ansettelsesår = ansettelsesår;
		this.månedslønn = månedslønn;
		this.skatteprosent = skatteprosent;
	}

	// personal information fetching

	public Person getPersonalia() {

		return new Person(personalia.getFornavn(), personalia.getEtternavn(), personalia.getFødselsår());
	}

	public String getNavn() { // SEKVENSDIAG

		return personalia.getEtternavn() + ", " + personalia.getFornavn();
	}

	private static short currentYear() {

		java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
		return (short) calendar.get(java.util.Calendar.YEAR);
	}

	public byte getAlder() { // SEKVENSDIAG

		return (byte) (currentYear() - personalia.getFødselsår());
	}

	public byte ansattHvorLenge() {

		return (byte) (currentYear() - ansettelsesår);
	}

	public boolean ansattLengreEnn(byte år) {

		return (ansattHvorLenge() > år);
	}

	// end of personal information

	public int getArbtakernr() {
		return arbtakernr;
	}

	public short getAnsettelsesår() {
		return ansettelsesår;
	}

	public float getMånedslønn() {
		return månedslønn;
	}

	public float getSkatteprosent() {
		return skatteprosent;
	}

	// more complex get methods

	public double bruttolønn() // år, regner jeg med
	{
		return 12 * månedslønn; // akkurat 12?
	}

	public float skattMåned() // som i skatte*trekk*
	{
		return månedslønn * (skatteprosent / 100);
	}

	public double skattÅr()
	{
		return 10.5 * skattMåned(); // juli uten skatt + halv skatt i desember = halvannen uskattet måned
	}

	// set methods

	public void setMånedslønn(float månedslønn)
	{
		this.månedslønn = månedslønn;
	}

	public void setSkatteprosent(float skatteprosent)
	{
		this.skatteprosent = skatteprosent;
	}

	// ultimate string making

	public String toString()
	{
		String res = getNavn() + ", f. " + personalia.getFødselsår() + " (" + getAlder() +" år)\n" +
			   "Ansatt nr. " + arbtakernr + ", ansatt i " + ansattHvorLenge() + " år, siden " + ansettelsesår + "\n" +
			   "Månedslønn " + månedslønn + " kr, skatter " + skatteprosent + "% av lønna" + "\n";
		java.util.Formatter formatted = new java.util.Formatter();
		formatted.format("Skatter %.2f kr i måneden, %.2f kr i året, tjener %.2f kr hvert år (Bruttolønn %.2f kr).", skattMåned(), skattÅr(), (bruttolønn() - skattÅr()), bruttolønn());
		return res + formatted;
	}

	public static void main(String[] args)
	{
		Person pæra = new Person("Grete", "Overåker", (short) 1989);
		ArbTaker dust1 = new ArbTaker(pæra, 20846728, (short) 2009, 16000.0f, 3.14f);
		System.out.println(dust1);
		float lønna = 25000.0f;
		dust1.setMånedslønn(lønna);
		float prosenten = 20.0f;
		dust1.setSkatteprosent(prosenten);
		System.out.println(dust1);

		float toleranse = 0.001f;

		if ((Math.abs(dust1.skattMåned() - 5000) < toleranse) && (Math.abs(dust1.skattÅr() - 52500)< toleranse))
		{
			System.out.println(dust1.bruttolønn());
		}
	}

} // end class
