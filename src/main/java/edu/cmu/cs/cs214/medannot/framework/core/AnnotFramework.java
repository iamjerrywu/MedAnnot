package edu.cmu.cs.cs214.medannot.framework.core;

import edu.cmu.cs.cs214.medannot.annotation.Annotation;
import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.table.Table;

public interface AnnotFramework {
    /**
     * Takes in the input image which is an m x n png file that is converted using a Table of the data and
     * the relevant plugin to an annotated image diagram.
     *
     * @param image  The image to process
     * @param data   The data table based on which the plugin will create the annotations in the image
     * @param plugin The plugin that will process the data and the table to produce the image
     * @return an {@link Annotation} object if the rendering succeeds, {@code null} otherwise.
     */
    Annotation render(Image image, Table data, AnnotPlugin plugin);

    static boolean isImageTablePluginValid(Image image, Table table, AnnotPlugin plugin) {
        if (!plugin.isTableValid(table)) return false;
        return true;
    }

    static AnnotFramework getNewFramework() {
        return new Framework();
    }
}