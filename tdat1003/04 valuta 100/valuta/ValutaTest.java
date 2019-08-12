import org.junit.*;
import static org.junit.Assert.*;

public class ValutaTest {
	
	private Valuta instance;
	private static final double PRECISION = 0.0001;

	@Before
	public void setUp() {
		instance = new Valuta("nisser", 2);
	}

	@After
	public void tearDown() {
		instance = null;
	}

	@Test
	public void testToString() {
		System.out.println("Valuta: toString");
		String expResult = "1 nisser er 2.0 NOK.";
		String result = instance.toString();
		assertEquals(expResult, result);
	}

	@Test
	public void testRegnOmTil() {
		System.out.println("Valuta: regnOmTil");
		double expResult = 20;
		double result = instance.regnOmTil(10);
		assertEquals(expResult, result, PRECISION); // (double, double) is deprecated cuz inaccuracy problem
	}

	@Test
	public void testRegnOmFra() {
		System.out.println("Valuta: regnOmFra");
		double expResult = 5;
		double result = instance.regnOmFra(10);
		assertEquals(expResult, result, PRECISION); // so there's now a (double, double, delta)
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(ValutaTest.class.getName());
	}
}