//import org.junit.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentTest {
	
	Student instance;

	@Before
	public void setUp() {
		instance = new Student("Geir Åse Ovesen", 10);
	}

	@After
	public void tearDown() {
		instance = null;
	}

	@Test 
	public void testCompareTo1() {
		System.out.println("Student: compareTo() equal --> also equals()"); // 2 know where we @
		Student candidate = new Student("Geir Åse Ovesen", 10);
		int exp = 0; // should be equal!
		int res = instance.compareTo(candidate); // bruker metoden som skal testes
		assertEquals(exp, res);
	}

	@Test
	public void testCompareTo2() {
		System.out.println("Student: compareTo() less");
		Student candidate = new Student("Åse Marie Gunleiksrud", 4); // skal ligge etter Geir i alfabetet
		int exp = -1; // instance skal først, altså gir compareTo verdi -1 (eller mindre...)
		int res = instance.compareTo(candidate);
		assertEquals(exp, res); // MOTSATT REKKEFØLGE NB
	}

	@Test
	public void testCompareTo3() {
		System.out.println("Student: compareTo() more");
		Student candidate = new Student("Gammel Flyvende Hollender", 100); // før Geir i alfabetet
		int exp = 1; // instance skal etter candidate -> returnerer 1 (eller større, med en annen compareTo-metode)
		int res = instance.compareTo(candidate);
		assertEquals(exp, res); // aye
	}

	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main(StudentTest.class.getName()); // I think?

	}

}