package rs.raf.student.jun_2022.database;

public final class Database {

    public static void load() {
        Players.load();
        Courts.load();
    }

    public static final class Courts extends DbCourts { }

    public static final class Matches extends DbMatches { }

    public static final class Players extends DbPlayers { }

}
