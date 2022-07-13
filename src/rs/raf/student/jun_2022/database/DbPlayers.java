package rs.raf.student.jun_2022.database;

import rs.raf.student.jun_2022.model.Gender;
import rs.raf.student.jun_2022.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class DbPlayers {

    private static final String fullFilePathMen = "resources/muskarci.txt";
    private static final String fullFilePathWomen = "resources/zene.txt";

    private static final List<Player> players = new ArrayList<>();

    //region Ucitavanje igraca u bazu podataka

    protected static void load() {
        load(fullFilePathMen, Gender.MAN);
        load(fullFilePathWomen, Gender.WOMAN);
        Collections.sort(players);
    }

    private static void load(String fullFilePath, Gender gender) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split(",");
                players.add(new Player(data[2], data[1], data[0], gender));
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        finally {
            if (scanner != null)
                scanner.close();
        }
    }

    //endregion

    //region Filtriranje igraca

    /**
     * Filtrira listu svih igraca
     * @param filter puno ime igraca ili drzava koja se pretrazuje
     * @param isMan da li pretraga sadrzi muske igrace
     * @param isWoman da li pretraga sadrzi zenske igrace
     * @return filtrirana lista napravljena na osnovu svih parametara
     */
    public static List<Player> filter(String filter, boolean isMan, boolean isWoman) {
        List<Player> filteredPlayers = new ArrayList<>();

        for (Player player : players)
            if (playerHasFilterText(player, filter) && playerMatchSelectedGender(player, isMan, isWoman))
                filteredPlayers.add(player);

        Collections.sort(filteredPlayers);
        return filteredPlayers;
    }

    private static boolean playerHasFilterText(Player player, String filter) {
        return player.getFullName().equalsIgnoreCase(filter) || player.getCountry().equalsIgnoreCase(filter) || filter.isEmpty();
    }

    private static boolean playerMatchSelectedGender(Player player, boolean isMan, boolean isWoman) {
        return (player.getGender().equals(Gender.MAN) && isMan) || (player.getGender().equals(Gender.WOMAN) && isWoman);
    }


    //endregion

    public static List<Player> get() {
        return players;
    }

}
