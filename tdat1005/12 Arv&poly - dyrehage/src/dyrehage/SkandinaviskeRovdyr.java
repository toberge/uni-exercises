package dyrehage;

/**
 * @link http://www.aitel.hist.no/fag/vprg/TDAT1005/ovinger/TDAT1005_arvpoly3.php
 *
 * this damn interface is simple copy-paste lol
 */

public interface SkandinaviskeRovdyr{
    String getNavn();
    int getFdato();
    int getAlder();
    String getAdresse();
    void flytt(String nyAdresse);
    String skrivUtInfo();
    int getAntKull();
    void leggTilKull(int antall);
    void leggTilNyttKull();
}
