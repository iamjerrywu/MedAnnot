package edu.cmu.cs.cs214.medannot.framework.core;

import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.table.Table;

import java.awt.*;

public interface AnnotPlugin {
    /**
     * A function to check whether a given data table has all the required fields that will be used by the plugin.
     * @param data The data table to check for
     * @return {@code} if the table contains all the required fields, {@code false} otherwise.
     */
    boolean isTableValid(Table data);

    /**
     * A function to check whether a given Pair(x, y) coordinate is a target i.e. a location to be annotated in the image.
     * @param pair The coordinates to check for
     * @param data The data table to check against
     * @return {@code true} if Pair(x, y) is a target, {@code false} otherwise
     */
    boolean isTarget(Pair pair, Table data);

    /**
     * A function that returns an integer corresponding to the target number in the image.
     * The image is annotated by the Framework where the numbers are placed at the target coordinates Pair(x, y)
     * given as input. Then, the Framework renders buttons below the image with the same numbers as the
     * ones used to annotate the image.
     * @param pair The coordinates to mark with the number
     * @return an {@link Integer} value if Pair(x, y) is a target, {@code null} otherwise
     */
    Integer getNumberForCoordinate(Pair pair, Table data);

    /**
     * A function to generate the HTML formatting for the annotation at some target coordinates
     * @param pair The coordinates
     * @param data The data table to check against
     * @return A {@link String} corresponding to the HTML of the annotation at Pair(x, y) if
     * {@code isTarget(x, y, data)} is {@code true}, {@code null} otherwise.
     */
    String getHTMLForTarget(Pair pair, Table data);

    /**
     * Return the font size to be rendered at the target coordinate location in the image.
     * @param pair the x, y coordinates
     * @param image the input image
     * @param data the input table
     * @return the font size of the target
     */
    Integer getFontSizeForCoordinate(Pair pair, Image image, Table data);

    /**
     * Return the font style (e.g: Font.BOLD, Font.PLAIN) to be rendered at the target coordinate location in the
     * image.
     * @param pair the x, y coordinates
     * @param image the input image
     * @param data the input table
     * @return the font style of the target (Refer to: https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html)
     */
    Integer getFontStyleForCoordinate(Pair pair, Image image, Table data);

    /**
     * Return the font Name (e.g: TimesRoman) to be rendered at the target coordinate location in the image.
     * @param pair the x, y coordinates
     * @param image the input image
     * @param data the input table
     * @return the font Name of the target (Refer to: https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html)
     */
    String getFontNameForCoordinate(Pair pair, Image image, Table data);

    /**
     * Return the font color (e.g: Color.WHITE) to be rendered at the target coordinate location in the image.
     * @param pair the x, y coordinates
     * @param image the input image
     * @return the font color of the target (Refer to: https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html)
     */
    Color getColorForCoordinate(Pair pair, Image image);

    String getPluginName();
}
