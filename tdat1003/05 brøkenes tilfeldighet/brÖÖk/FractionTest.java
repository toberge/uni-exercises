import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class FractionTest {
	
	Fraction instance;

	@Before
	public void setUp() {
		instance = new Fraction(3, 2);
	}

	@After
	public void tearDown() {
		instance = null;
	}

	@Test
	public void testSub() {
		System.out.println("Fraction: sub()");
		Fraction loss = new Fraction(2, 2);
		String exp = "1 / 2";
		instance.sub(loss);
		String res = instance.toString();
		assertEquals(exp, res);
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(FractionTest.class.getName());
	}

}