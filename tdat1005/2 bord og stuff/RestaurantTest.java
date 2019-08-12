import org.junit.*;
import static org.junit.Assert.*;

public class RestaurantTest {

	Restaurant instance;

    @Before
    public void setUp() {
    	instance = new Restaurant("Ka f", 1987, 12);

    	instance.reserveTables("Apollo 13", 2); //0 start
    	instance.reserveTables("Noen", 2); //2 start
    	instance.reserveTables("Apollo 13", 3); //4 start
    }

    @After
    public void tearDown() {
    	instance = null;
    }

    // Sørg også for at metodene som finner alderen og endrer restaurantnavnet blir prøvd ut.

    @Test
    public void testSetName() {

    	System.out.println("Restaurant: setName");
    	String expResult = "e denna fesken";
    	instance.setName("e denna fesken"); // the same, could've just used it
    	String result = instance.getName();
    	assertEquals(expResult, result);

    }

    @Test
    public void testGetAge() {

    	// placeholdery stuff I guess
    	System.out.println("Restaurant: getAge");
    	int expResult = 2019 - 1987;
    	int result = instance.getAge();
    	assertEquals(expResult, result);

    }

    @Test 
    public void testFindThenFree() {

    	System.out.println("Restaurant: findTables then freeTables");
    	System.out.println(instance);
    	int[] arr = instance.findTables("Apollo 13");
    	boolean result = instance.freeTables(arr);
    	boolean expResult = true;
    	System.out.println(instance);
    	assertEquals(expResult, result);

    }

    @Test
    public void testFailAtFreeing() {

    	System.out.println("Restaurant: freeTables w/invalid array");
    	int[] arr = {1,4, -1, 23, 0};
    	boolean result = instance.freeTables(arr);
    	boolean expResult = false;
    	System.out.println(instance);
    	assertEquals(expResult, result);

    }

    public static void main(String args[]) {
		org.junit.runner.JUnitCore.main(RestaurantTest.class.getName());
    }

}