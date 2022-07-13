package rs.raf.student.jun_2022.model;

public enum Gender {
    MAN {
        @Override
        public byte value() {
            return 1;
        }

        @Override
        public String toString() {
            return "M";
        }
    },
    WOMAN {
        @Override
        public byte value() {
            return 3;
        }

        @Override
        public String toString() {
            return "Z";
        }
    };

    /**
     * Identifikaciona vrednost koriscena za uparivanje dva tima
     * @return 1 za igrace muskog, a 3 za igrace zenskog pola.
     */
    public abstract byte value();

    /**
     * Nadjacava podrazumevanu toString metodu koja ima vrednost "MAN" za igraca muskog i "WOMAN" za igraca zenskog pola
     * @return "M" za igraca muskog i "Z" za igraca zenskog pola
     */
    @Override
    public abstract String toString();

}
