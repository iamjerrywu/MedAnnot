package edu.cmu.cs.cs214.medannot.image;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.util.*;
import static org.junit.Assert.*;

import edu.cmu.cs.cs214.medannot.framework.core.Pair;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ImageTest {

    Image image = null;
    Graphics2D g = null;

    @Before
    public void setup() {
        try {
            image = new Image(4096, 4096, "src/test/testImages/testImage.png");
        } catch (ImageError.InvalidImage e) {
            System.out.println("Error: " + e);
        }
    }

    @Test
    public void testAddNumber() {
        int tarNum = 1;
        String FontName = "TimeRoman";
        int FontStyle = Font.BOLD;
        int fontSize = 30;
        Color color = Color.BLACK;
        int x = 50;
        int y = 50;

        // X, Y within range
        assertTrue(image.addNumber(x, y, tarNum, FontName, FontStyle, fontSize, color));

        x = 1500;
        y = 3000;
        // X, Y out of bound
        assertFalse(image.addNumber(x, y, tarNum, FontName, FontStyle, fontSize, color));
    }

    @Test
    public void testGetWidth() {
        assertEquals(1200, image.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(1200, image.getHeight());
    }

    @Test
    public void testRender() {
        int tarNum = 1;
        String FontName = "TimeRoman";
        int FontStyle = Font.BOLD;
        int fontSize = 30;
        Color color = Color.BLACK;
        int x = 50;
        int y = 50;

        image.addNumber(x, y, tarNum, FontName, FontStyle, fontSize, color);
        assertEquals(String.valueOf(tarNum), image.getRenderInfo().get(new Pair(x, y)).getTarNum());
        assertEquals(FontName, image.getRenderInfo().get(new Pair(x, y)).getFontName());
        assertEquals(FontStyle, image.getRenderInfo().get(new Pair(x, y)).getFontStyle());
        assertEquals(color, image.getRenderInfo().get(new Pair(x, y)).getColor());
    }
}