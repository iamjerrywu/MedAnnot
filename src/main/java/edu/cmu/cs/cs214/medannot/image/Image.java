package edu.cmu.cs.cs214.medannot.image;

import edu.cmu.cs.cs214.medannot.annotation.Annotation;
import edu.cmu.cs.cs214.medannot.framework.core.Pair;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;


public class Image {
    private static int width;
    private static int height;
    private BufferedImage image = null;
    private Map<Pair, Text<String, Integer, Color>> renderInfo = new HashMap<>();

    /**
     * Takes in the width, height, and filepath as input
     *
     * @param width  The maximum supported image width
     * @param height  The maximum supported image height
     * @param filePath The image file directory
     *
     */
    public Image(int width, int height, String filePath) throws ImageError.InvalidImage {
        try {
            File file = new File(filePath);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            throw new ImageError.InvalidImage(filePath);
        }
    }

    /**
     * Takes in x, y coordination, target number, and the customized parameters for the annotation text
     *
     * @param x  The value for x coordination
     * @param y  The value for y coordination
     * @param n   The target number
     * For Font customization, please refer to:  https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html
     * @return {@code true} if (x, y) is within image target, {@code false} otherwise
     */
    public Boolean addNumber(int x, int y, int n, String FontName, int FontStyle, int fontSize, Color color) {
        if (x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight()) {
            this.renderInfo.put(new Pair(x, y), new Text(Integer.toString(n), FontName, FontStyle, fontSize,
                    color));
            return true;
        }
        return false;
    }
    /**
     * Get the image width
     *
     * @return image width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the image height
     *
     * @return image height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Construct the image render information, and render the image and output it as a new image
     *
     */
    public void render() {
        // render image
        Graphics2D g = image.createGraphics();
        for (Map.Entry<Pair, Text<String, Integer, Color>> entry :
                renderInfo.entrySet()) {
            Pair coordinations = entry.getKey();
            Text text = entry.getValue();
            // set font's attributes
            g.setFont(new Font((String)text.getFontName(), text.getFontStyle(), text.getFontSize()));
            // set font colors
            g.setColor((Color)text.getColor());
            // draw string on image

            g.drawString((String)text.getTarNum(), (int)coordinations.getFirst(),(int)coordinations.getSecond());
        }
        g.dispose();

        // write image
        try {
            File file = new File("src/main/resources/renderedImages/rendered.png");
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            System.out.println("Writing image error!" + e);
        }
    }

    public Map<Pair, Text<String, Integer, Color>> getRenderInfo() {
        return this.renderInfo;
    }

    public record Text<String, Integer, Color>(String tarNum, String fontName, int fontStyle, int fontSize,
    Color color) {

        public String getTarNum() { return this.tarNum; }

        public String getFontName() { return this.fontName; }

        int getFontStyle() { return this.fontStyle; }

        int getFontSize() { return this.fontSize; }

        Color getColor() { return this.color; }
    }
}
