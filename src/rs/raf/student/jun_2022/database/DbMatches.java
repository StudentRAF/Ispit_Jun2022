package rs.raf.student.jun_2022.database;

import rs.raf.student.jun_2022.model.Court;
import rs.raf.student.jun_2022.model.Match;
import rs.raf.student.jun_2022.model.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DbMatches {

    private static final String fullFilePathMatch = "resources/mecevi.txt";

    private static final List<Match> matches = new ArrayList<>();

    //region Kreiranje meca

    /**
     * @param court teren za koji se kreira mec
     * @throws IllegalArgumentException
     */
    public static void createMatch(Court court) throws IllegalArgumentException {
        // Ukoliko je teren null, to znaci da teren nije selektovan
        if (court == null)
            throw new IllegalArgumentException("Kreiranje meca nije moguce!\nNeophodno je selektovati teren iz liste terena.");

        if (!canCreateMatch())
            throw new IllegalArgumentException("Kreiranje meca nije moguce!\nNeophodno je popuniti sve podatke za poslednje dodati mec.");

        matches.add(new Match(court, getStartTime(court)));
    }

    /**
     * @return true ako mec moze da se kreira, u suprotnom false
     */
    private static boolean canCreateMatch() {
        // Mec se moze kreirati ukoliko ne postoji ni jedan mec (tada je lista prazna) ili
        // ukoliko za poslednje dodati mec vazi da je drugi tim popunjen (da drugi tim nije null)
        return matches.isEmpty() || matches.get(matches.size() - 1).getSecondTeam() != null;
    }

    private static String getStartTime(Court court) {
        Match lastMatch = getLastMatchForCourt(court);
        String startTime = null;

        if (lastMatch == null)
            startTime = "09:00";

        else if (lastMatch.getStartTime().equals("09:00"))
            startTime = "12:00";

        else if (lastMatch.getStartTime().equals("12:00")) {
            if (!lastMatch.getCourt().hasLighting())
                Database.Courts.get().remove(court);
            startTime ="15:00";
        }

        else if (lastMatch.getStartTime().equals("15:00")) {
            Database.Courts.get().remove(court);
            startTime ="18:00";
        }

        return startTime;
    }

    /**
     * @param court teren za koji se trazi poslenji mec
     * @return null ukoliko se dati teren ne nalazi u listi, ili poslednji mec za dati teren
     */
    private static Match getLastMatchForCourt(Court court) {
        Match lastMatch = null;

        for (Match match : matches)
            if (match.getCourt().equals(court))
                lastMatch = match;

        return lastMatch;
    }

    //endregion

    //region Dodavanje igraca

    /**
     * @param players igraci koji se dodaju za mec
     * @throws IllegalArgumentException
     */
    public static void addPlayers(List<Player> players) throws IllegalArgumentException {
        // Ukoliko je lista igraca null, to znaci da nijedan igrac nije selektovan
        if (players == null)
            throw new IllegalArgumentException("Nije moguce dodati igrace!\nNeophodno je selektovati bar jednog igraca iz liste igraca.");

        // Ukoliko je velicina liste igraca veca od dva, to znaci da je selektovano vise od dva igraca sto nije dozvoljeno
        if (players.size() > 2)
            throw new IllegalArgumentException("Nije moguce dodati igrace!\nNeophodno je selektovati ne vise od dva igraca.");

        Match lastMatch = matches.get(matches.size() - 1);

        if (lastMatch.getSecondTeam() != null)
            throw new IllegalArgumentException("Nije moguce dodati igrace!\nMecu su dodati svi igraci.");

        if (!canPlayersPlay(players, lastMatch.getStartTime())) {
            if (players.size() == 1)
                throw new IllegalArgumentException("Nije moguce dodati igrace!\nSelektovani igrac ne moze da igra u izabranom terminu.");
            else
                throw new IllegalArgumentException("Nije moguce dodati igrace!\nBar jedan od selektovanih igraca ne moze da igra u izabranom terminu.");
        }

        // Ukoliko je prvi tim ima vrednost null, tada se igraci dodeljuju prvom timu i zavrsava se funkcija
        if (lastMatch.getFirstTeam() == null) {
            // Svi uslovi su ispunjeni, pa se igraci dodaju u prvi tim
            lastMatch.setFirstTeam(new ArrayList<>(players));
            return;
        }

        // Ukoliko timovi nisu izbalansirani, to znaci da ih nije moguce upariti odnosno dodati igrace
        if (getTeamIdentificationCode(lastMatch.getFirstTeam()) != getTeamIdentificationCode(players))
            throw new IllegalArgumentException("Nije moguce dodati igrace!\nIgraci moraju biti izbalansirani da bi se dodali.");

        // Svi uslovi su ispunjeni, pa se igraci dodaju u drugi tim
        lastMatch.setSecondTeam(new ArrayList<>(players));
    }

    /**
     *
     * @param players igraci koji se dodaju
     * @param startTime vreme pocetka za poslednje dodati mec
     * @return true ukoliko svi izabrani igraci mogu da igraju, u suprotnom false
     */
    private static boolean canPlayersPlay(List<Player> players, String startTime) {
        for (Player player : players)
            // Ukoliko se u listi sa pocetnim vremenima za sve meceve za igraca nalazi pocetno vreme meca kome se dodeljuje igrac,
            // to znaci da igrac nije slobodan i nije ga moguce dodati
            if (getPlayerMatchesStartTimes(player).contains(startTime))
                return false;

        return true;
    }

    /**
     * @param player igrac za kog se traze mecevi
     * @return lista svih termina u kojima igrac igra
     */
    private static List<String> getPlayerMatchesStartTimes(Player player) {
        List<String> playerMatches = new ArrayList<>();

        for (Match match : matches)
            // Ukoliko igrac ucestvuje u mecu, onda se termin dodaje u listu
            if (match.getAllPlayers().contains(player))
                playerMatches.add(match.getStartTime());

        return playerMatches;
    }

    /**
     * @param players igraci za koje se odredjuje kod
     * @return jedinstven kod koji odgovara samo istom tipu igraca
     */
    private static byte getTeamIdentificationCode(List<Player> players) {
        byte identificationNumber = 0;

        for (Player player : players)
            identificationNumber += player.getGender().value();

        return identificationNumber;
    }

    //endregion

    public static void save() throws IllegalArgumentException {
        if (Database.Matches.get().get(Database.Matches.get().size() - 1).getSecondTeam() == null)
            throw new IllegalArgumentException("Cuvanje meceva nije moguce.\nNeophodno je popuniti sve meceve da bi se mogli sacuvati.");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullFilePathMatch));

            for (Match match : Database.Matches.get())
                writer.write(match.toString());

            writer.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static List<Match> get() {
        return matches;
    }

}
