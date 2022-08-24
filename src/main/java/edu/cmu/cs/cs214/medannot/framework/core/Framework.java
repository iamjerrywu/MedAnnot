package edu.cmu.cs.cs214.medannot.framework.core;

import edu.cmu.cs.cs214.medannot.annotation.Annotation;
import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.table.Table;

import java.util.HashMap;
import java.util.Map;

public class Framework implements AnnotFramework {
    public Annotation render(Image image, Table table, AnnotPlugin plugin) {
        Map<Integer, String> annotations = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (plugin.isTarget(new Pair(x, y), table)) {
                    annotations.put(plugin.getNumberForCoordinate(new Pair(x, y), table),
                            plugin.getHTMLForTarget(new Pair(x, y), table));
                    image.addNumber(x, y, plugin.getNumberForCoordinate(new Pair(x, y), table),
                            plugin.getFontNameForCoordinate(new Pair(x, y), image, table),
                            plugin.getFontStyleForCoordinate(new Pair(x, y), image, table),
                            plugin.getFontSizeForCoordinate(new Pair(x, y), image, table),
                            plugin.getColorForCoordinate(new Pair(x, y), image));
                }
            }
        }
        image.render();
        return new Annotation(annotations);
    }
}
