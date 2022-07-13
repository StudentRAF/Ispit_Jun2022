package rs.raf.student.jun_2022.database;

import rs.raf.student.jun_2022.model.Court;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DbCourts {

    private static final String fullFilePathCourt = "resources/tereni.txt";

    private static final List<Court> courts = new ArrayList<>();

    protected static void load() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fullFilePathCourt));

            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split("-");
                courts.add(new Court(data[0], data.length == 2));
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

    public static List<Court> get() {
        return courts;
    }
}
