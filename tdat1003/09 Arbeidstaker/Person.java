public class Person // very simple
{
	private final String fornavn;
	private final String etternavn;
	private final short fødselsår;

	public Person(String fornavn, String etternavn, short fødselsår)
	{
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.fødselsår = fødselsår;
	}

	public String toString()
	{
		return etternavn + ", " + fornavn + ", f. " + fødselsår;
	}

	public String getFornavn()
	{
		return fornavn;
	}

	public String getEtternavn()
	{
		return etternavn;
	}

	public short getFødselsår()
	{
		return fødselsår;
	}
}