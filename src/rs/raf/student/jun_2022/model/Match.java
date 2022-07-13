package rs.raf.student.jun_2022.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Match {

    private Court court;
    private List<Player> firstTeam = null;
    private List<Player> secondTeam = null;
    private String startTime;

    public Match(Court court, String startTime) {
        this.court = court;
        this.startTime = startTime;
    }

    //region Seteri i geteri

    public void setFirstTeam(List<Player> firstTeam) {
        this.firstTeam = firstTeam;
    }

    public void setSecondTeam(List<Player> secondTeam) {
        this.secondTeam = secondTeam;
    }

    public Court getCourt() {
        return court;
    }

    public String getCourtStr() {
        return court.getName();
    }

    public List<Player> getFirstTeam() {
        return firstTeam;
    }

    public String getFirstTeamStr() {
        if (firstTeam == null)
            return "";

        String firstTeamStr = firstTeam.get(0).getFullName();

        if (firstTeam.size() == 2)
            firstTeamStr += "/" + firstTeam.get(1).getFullName();

        return firstTeamStr;
    }

    public List<Player> getSecondTeam() {
        return secondTeam;
    }

    public String getSecondTeamStr() {
        if (secondTeam == null)
            return "";

        String secondTeamStr = secondTeam.get(0).getFullName();

        if (secondTeam.size() == 2)
            secondTeamStr += "/" + secondTeam.get(1).getFullName();

        return secondTeamStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public List<Player> getAllPlayers() {
        if (firstTeam == null)
            return new ArrayList<>();

        List<Player> players = new ArrayList<>(firstTeam);

        if (secondTeam == null)
            return players;

        players.addAll(secondTeam);

        return players;
    }

    //endregion

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(court.getName()).append(',').append(startTime).append(',').append(getFirstTeamStr()).append(',').append(getSecondTeamStr()).append('\n');

        return stringBuilder.toString();
    }

}
