package edu.cmu.cs.cs214.medannot.selection;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableSelect {
    public String getInstructions() {
        return "Place table into resources/availableTables and " +
                "select table from list";
    }

    public View[] getCells() {
        List<String> files = getAllTablesInFolder();
        View[] views = new View[files.size()];
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i);
            views[i] = new View("selecttable?table=" +
                    stripExtension(files.get(i)), stripExtension(files.get(i)));
        }
        return views;
    }

    String stripExtension(String s) {
        Integer firstDotFromEnd = null;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '.') {
                firstDotFromEnd = i;
                break;
            }
        }
        if (firstDotFromEnd == null) return s;
        return s.substring(0, firstDotFromEnd);
    }

    public String getSelect() {
        return "Table";
    }

    private static List<String> getAllTablesInFolder() {
        File dir = new File("src/main/resources/availableTables");
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.list())));
    }
}
