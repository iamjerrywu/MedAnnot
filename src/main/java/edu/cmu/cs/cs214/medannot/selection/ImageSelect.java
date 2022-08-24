package edu.cmu.cs.cs214.medannot.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImageSelect implements Selection {
    public String getInstructions() {
        return "Place image into resources/availableImages and " +
                "select image from list";
    }

    public View[] getCells() {
        List<String> files = getAllImagesInFolder();
        files = files.stream().filter(file -> file.endsWith(".png")).collect(Collectors.toList());
        View[] views = new View[files.size()];
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i);
            views[i] = new View("selectimage?image=" +
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
        return "Image";
    }

    private static List<String> getAllImagesInFolder() {
        File dir = new File("src/main/resources/availableImages");
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.list())));
    }
}
