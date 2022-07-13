package rs.raf.student.jun_2022.model;

public enum Gender2 {
    MAN("M", (byte)1),
    WOMAN("Z", (byte)3);

    private String flag;
    private byte value;

    Gender2(String flag, byte value) {
        this.flag = flag;
        this.value = value;
    }

    /**
     * Identifikaciona vrednost koriscena za uparivanje dva tima
     * @return 1 za igrace muskog, a 3 za igrace zenskog pola.
     */
    public byte value() {
        return value;
    }

    /**
     * Nadjacava podrazumevanu toString metodu koja ima vrednost "MAN" za igraca muskog i "WOMAN" za igraca zenskog pola
     * @return "M" za igraca muskog i "Z" za igraca zenskog pola
     */
    @Override
    public String toString() {
        return flag;
    }

}
