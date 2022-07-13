package rs.raf.student.jun_2022.model;

public class Court {

    private String name;
    private boolean hasLighting;

    public Court(String name, boolean hasLighting) {
        this.name = name;
        this.hasLighting = hasLighting;
    }

    //region Seteri i geteri

    public String getName() {
        return name;
    }

    public boolean hasLighting() {
        return hasLighting;
    }

    //endregion

    //region Nadjacane Object metode

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Court))
            return false;

        Court court = (Court) obj;
        return name.equals(court.name);
    }

    @Override
    public String toString() {
        String courtStr = name;

        if (hasLighting)
            courtStr += "-O";

        return courtStr;
    }

    //endregion

}
