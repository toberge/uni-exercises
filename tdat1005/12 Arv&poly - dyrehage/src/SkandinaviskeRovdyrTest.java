import dyrehage.*; // just Rovdyrfabrikk
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lag en enkel testklient som tester hver av metodene i interfacet SkandinaviskeRovdyr.
 * Du skal altså lage dyreobjekter ved hjelp av et RovdyrFabrikk-objekt, og kun bruke metodene i SkandinaviskeRovdyr.
 * Husk at du må importere pakken dyrehage for å kunne bruke den (klienten skal ikke være en del av pakken).
 */

class SkandinaviskeRovdyrTest {

    SkandinaviskeRovdyr[] instance;

    @BeforeEach
    void setUp() {
        instance = new SkandinaviskeRovdyr[4];

        instance[0] = Rovdyrfabrikk.nyBinne();
        instance[1] = Rovdyrfabrikk.nyHannbjørn();
        instance[2] = Rovdyrfabrikk.nyUlvetispe();
        instance[3] = Rovdyrfabrikk.nyUlvehann();
    }

    @AfterEach
    void tearDown() {
        instance = null; // enough?
    }

    @Test
    public void testGetNavn() {
        System.out.println("SkandinaviskeRovdyr: getNavn()");
        String expected = "Malene Bjørnsdottir";
        String actual = instance[0].getNavn();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFdato() {
        System.out.println("SkandinaviskeRovdyr: getFdato()");
        int expected = 20120409;
        int actual = instance[3].getFdato();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAlder() {
        System.out.println("SkandinaviskeRovdyr: getAlder()");
        int expected = 6; // I think
        int actual = instance[3].getAlder();
        assertEquals(expected, actual);
    }
    @Test
    public void testGetAdresse() {
        System.out.println("SkandinaviskeRovdyr: getAdresse()");
        String expected = "Bur 45, Bjørnestua";
        String actual1 = instance[0].getAdresse();
        String actual2 = instance[1].getAdresse();
        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }
    @Test
    public void testFlytt() {
        System.out.println("SkandinaviskeRovdyr: getAdresse()");
        String expected = "Bur 0, Helvete";
        instance[2].flytt(expected);
        String actual = instance[2].getAdresse();
        assertEquals(expected, actual);
    }

    /**
     * Ja, jeg vet, det er en ganske messy toString
     */
    @Test
    public void testSkrivUtInfo() {
        System.out.println("SkandinaviskeRovdyr: skrivUtInfo()");
        String expected = "Malene Bjørnsdottir, født 20120409, et farlig hunndyr av arten" + System.lineSeparator() +
                "\tNorsk navn: Brunbjørn\nLatinsk navn og familie: Ursus arctos arctos, Ursidae\nAdresse: Bur 45, Bjørnestua --> antall kull: 3";
        String actual = instance[0].skrivUtInfo();
        assertEquals(expected, actual);
        expected = "Bjarte Bjørnson, født 20120409, et farlig hanndyr av arten" + System.lineSeparator() +
                "\tNorsk navn: Brunbjørn\nLatinsk navn og familie: Ursus arctos arctos, Ursidae\nAdresse: Bur 45, Bjørnestua";
        actual = instance[1].skrivUtInfo();
        assertEquals(expected, actual);
    }
    @Test
    public void testGetAntKull() {
        System.out.println("SkandinaviskeRovdyr: getAntKull()");
        int expected = 3; // satt i fabrikken
        int actual = instance[0].getAntKull();
        assertEquals(expected, actual);
        expected = 0; // for alle av hankjønn
        actual = instance[1].getAntKull();
        assertEquals(expected, actual);
    }
    @Test
    public void testLeggTilKull() {
        System.out.println("SkandinaviskeRovdyr: leggTilKull()");
        int expected = 4;
        instance[0].leggTilNyttKull();
        int actual = instance[0].getAntKull();
        assertEquals(expected, actual);
    }
    @Test
    public void testLeggTilNyttKull() {
        System.out.println("SkandinaviskeRovdyr: leggTilNyttKull()");
        int expected = 7;
        instance[0].leggTilKull(4);
        int actual = instance[0].getAntKull();
        assertEquals(expected, actual);
    }
}