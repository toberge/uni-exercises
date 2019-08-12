package dyrehage;

public class Rovdyrfabrikk {

    private static final String BJØRN_NOR = "Brunbjørn";
    private static final String BJØRN_LAT = "Ursus arctos arctos";
    private static final String BJØRN_FAM = "Ursidae"; // latin btw
    private static final String BJØRN_ADR = "Bur 45, Bjørnestua";


    private static final String ULV_NOR = "Ulv";
    private static final String ULV_LAT = "Canis lupus"; // ops, ikke lupus lupus
    private static final String ULV_FAM = "Canidae";
    private static final String ULV_ADR = "Bur 45, Bjørnestua";

    // TODO argumenter
    // because OF COURSE I didn't look properly into what a factory class should do,
    // and my brain didn't work this out by itself since I wrote this right before I submitted my work.
    // Glorious.

    public static SkandinaviskeRovdyr nyBinne() {
        return new Hunnindivid(BJØRN_NOR, BJØRN_LAT, BJØRN_FAM, 20190323, BJØRN_ADR, "Malene Bjørnsdottir", 20120409, true, 3);
    }
    public static SkandinaviskeRovdyr nyHannbjørn() {
        return new Hannindivid(BJØRN_NOR, BJØRN_LAT, BJØRN_FAM, 20190323, BJØRN_ADR, "Bjarte Bjørnson", 20120409, true);
    }
    public static SkandinaviskeRovdyr nyUlvetispe() {
        return new Hunnindivid(ULV_NOR, ULV_LAT, ULV_FAM, 20190323, ULV_ADR, "Alvira Ulvesen", 20120409, true, 3);
    }
    public static SkandinaviskeRovdyr nyUlvehann() {
        return new Hannindivid(ULV_NOR, ULV_LAT, ULV_FAM, 20190323, ULV_ADR, "Alf Ulvesen", 20120409, true);
    }

}
