package edu.cmu.cs.cs214.medannot.plugin;


import edu.cmu.cs.cs214.medannot.framework.core.AnnotPlugin;
import edu.cmu.cs.cs214.medannot.framework.core.Pair;
import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.table.Table;

import java.awt.*;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * The XRay plugin requires the Tumor, Fracture and Infection fields in the CSV tables provided as input.
 */
public class XRay implements AnnotPlugin {

    private final String pluginName = "XRay";
    private final String fontName = "TimeRoman";
    private final int fontStyle = Font.PLAIN;
    private final int fontSize = 80;
    private final Color color = Color.RED;
    private final Set<String> requiredField = new HashSet<>() {{
       add("Tumor");
       add("Fracture");
       add("Infection");
    }};

    public boolean isTableValid(Table data) {
        return data.getFields().containsAll(requiredField);
    }

    public boolean isTarget(Pair pair, Table data) {
        if (data.getParams(pair) != null)
            return true;
        return false;
    }

    public Integer getNumberForCoordinate(Pair pair, Table data) {
        int result = data.getTargets().indexOf(pair);
        if (result == -1)
            return null;
        return result + 1;
    }

    public String getHTMLForTarget(Pair pair, Table data) {
        return "<h1>" + data.getParams(pair).get("Title") + "</h1>" +
                "<ul>" +
                  "<li>Tumor:" + data.getParams(pair).get("Tumor") + "</li>" +
                  "<li>Fracture:" + data.getParams(pair).get("Fracture") + "</li>" +
                  "<li>Infection:" + data.getParams(pair).get("Infection") + "</li>" +
                "</ul>";
    }

    public Integer getFontSizeForCoordinate(Pair pair, Image image, Table data) { return this.fontSize; }

    public Integer getFontStyleForCoordinate(Pair pair, Image image, Table data) { return this.fontStyle; }

    public String getFontNameForCoordinate(Pair pair, Image image, Table data) { return fontName; }

    public Color getColorForCoordinate(Pair pair, Image image) { return this.color; }

    public String getPluginName() { return this.pluginName; }
}
