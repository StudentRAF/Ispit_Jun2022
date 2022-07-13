package rs.raf.student.jun_2022.model;

import java.util.Objects;

public class Player implements Comparable<Player> {

    private String fullName;
    private String country;
    private int position;
    private Gender gender;

    public Player(String fullName, String country, String position, Gender gender) {
        this.fullName = fullName;
        this.country = country;
        this.position = Integer.parseInt(position);
        this.gender = gender;
    }

    //region Seteri i geteri

    public String getFullName() {
        return fullName;
    }

    public String getCountry() {
        return country;
    }

    public int getPosition() {
        return position;
    }

    public Gender getGender() {
        return gender;
    }

    //endregion

    /**
     * @param player igrac sa kojim se uporedjuje.
     * @return -1 ukoliko je pozicija igraca sa kojim se uporedjuje veca. 0 ukoliko su pozicije oba igraca jednake. 1 ukoliko je pozicija igraca sa kojim se uporedjuje manja.
     */
    @Override
    public int compareTo(Player player) {
        return Integer.signum(Integer.compare(position, player.position));
    }

    /**
     * Example: 3. (SRB) Novak Djokovic (M)
     * @return position. (country) fullName (gender)
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(position).append(". (").append(country).append(") ").append(fullName).append(" (").append(gender).append(')');

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Player))
            return false;

        Player player = (Player) obj;
        return position == player.position && Objects.equals(fullName, player.fullName) && Objects.equals(country, player.country) && gender.equals(player.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, country, position, gender);
    }
}
