public class Person {

    private int persnr;
    private String fornavn;
    private String etternavn;

    public Person(int persnr, String fornavn, String etternavn) {
        this.persnr = persnr;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public int getPersnr() {
        return persnr;
    }

    public void setPersnr(int persnr) {
        this.persnr = persnr;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "persnr=" + persnr +
                ", fornavn='" + fornavn + '\'' +
                ", etternavn='" + etternavn + '\'' +
                '}';
    }
}
