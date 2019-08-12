import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lag en enkel testklient der spesielt metoden sjekkMedlemmer() blir prøvd ut.
 * Du kan finne det hensiktsmessig å lage flere metoder i klassen Medlemsarkiv for å få testet tilstrekkelig.
 * Hvis du trenger å skrive ut hvilken klasse et objekt tilhører, kan du bruke metoden getClass() i klassen Object.
 */

class MemberArchiveTest {

    private MemberArchive instance;
    private static final Personalia PER_PERSSON = new Personalia("Per", "Persson", "per@person.se", "Lotusbl00mst");
    private int perNum = 0;
    private static final String YU_PASS = "Parad0x";
    private static final Personalia MORGAN_YU = new Personalia("Morgan", "Yu", "morgan.yu@transtar.space", String.valueOf(YU_PASS)); // WHY ON EARTH
    private int yuNum;
    private static final Personalia KONG1 = new Personalia("Kongle", "Sivertsen", "konglen@stuten.nu", "123456");
    private int kongNum = 0;
    private static final LocalDate START = LocalDate.of(1978, 5, 23);
    private static final LocalDate INNAFOR = LocalDate.of(1979, 5,1);
    private static final LocalDate UTAFOR = LocalDate.of(1979, 6,25);

    @BeforeEach
    void setUp() {
        instance = new MemberArchive();
        perNum = instance.newMember(PER_PERSSON, START);
        yuNum = instance.newMember(MORGAN_YU, START);
        kongNum = instance.newMember(KONG1, START);
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    @Test
    void findPoints() {
        System.out.println("MemberArchive: findPoints()");
        int expected = 45000;
        assertTrue(instance.registerPoints(yuNum, expected));
        int actual = instance.findPoints(yuNum, YU_PASS);
        assertEquals(actual, expected);
    }

    @Test
    void registerPointsSuccess() {
        System.out.println("MemberArchive: registerPoints");
        assertTrue(instance.registerPoints(yuNum, 45000));
    }

    @Test
    void registerPointsFail() {
        System.out.println("MemberArchive: registerPoints");
        assertFalse(instance.registerPoints(yuNum, -32));
    }

    @Test
    void newMember() {
        assertEquals(-1, instance.newMember(null, null)); // not much more trouble to make here
    }

    // be particularly careful with this one, they say
    @Test
    void updateMembersUpgrade() {
        System.out.println("MemberArchive: updateMembers upgrade");
        instance.registerPoints(yuNum, 76000);
        instance.registerPoints(kongNum, 30000);
        instance.updateMembers(INNAFOR);
        System.out.println(instance);
        assertEquals("Gold", instance.findClass(yuNum));
        assertEquals("Silver", instance.findClass(kongNum));
        assertEquals("Basic", instance.findClass(perNum));

    }

    @Test
    void updateMembersOutOfRange() {
        System.out.println("MemberArchive: updateMembers out of starting year's range");
        instance.registerPoints(yuNum, 76000);
        instance.registerPoints(kongNum, 30000);
        instance.updateMembers(UTAFOR);
        System.out.println(instance);
        assertEquals("Basic", instance.findClass(yuNum));
        assertEquals("Basic", instance.findClass(kongNum));
        assertEquals("Basic", instance.findClass(perNum));
    }

    @Test
    void updateMembersNoUpgrade() {
        System.out.println("MemberArchive: updateMembers don't upgrade");
        instance.registerPoints(yuNum, 3000);
        instance.registerPoints(kongNum, 24);
        instance.updateMembers(INNAFOR);
        System.out.println(instance);
        assertEquals("Basic", instance.findClass(yuNum));
        assertEquals("Basic", instance.findClass(kongNum));
        assertEquals("Basic", instance.findClass(perNum));
    }

}