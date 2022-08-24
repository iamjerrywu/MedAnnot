package edu.cmu.cs.cs214.medannot.framework.core;

import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.image.ImageError;
import edu.cmu.cs.cs214.medannot.plugin.XRay;
import edu.cmu.cs.cs214.medannot.table.Table;
import edu.cmu.cs.cs214.medannot.table.TableError;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class AnnotFrameworkTest extends TestCase {
    private Table tableShouldAccept;
    private Table tableShouldReject;
    private Image image;
    private AnnotPlugin plugin;
    @Before
    public void setUp() {
        plugin = new XRay();
        try {
            image = new Image(4096, 4096,"src/test/java/edu/cmu/cs/cs214/medannot/framework/core/testfiles/XRayImage1.png");
        } catch (ImageError.InvalidImage e) {
            throw new AssertionFailedError("Invalid Image");
        }
        try {
            tableShouldAccept = new Table("src/test/java/edu/cmu/cs/cs214/medannot/framework/core/testfiles/XRayTable1.csv");
            tableShouldReject = new Table("src/test/java/edu/cmu/cs/cs214/medannot/framework/core/testfiles/MRITable1.csv");
        } catch (TableError.EmptyCSV e) {
            throw new AssertionFailedError("Empty CSV Error");
        } catch (TableError.InvalidFile e) {
            throw new AssertionFailedError("Invalid File error");
        } catch (TableError.MissingCoreFields e) {
            throw new AssertionFailedError("Missing Core Fields error");
        }
    }
    @Test
    public void testCompabitibilityFunctionExpectTrue() {
        assertTrue(AnnotFramework.isImageTablePluginValid(image, tableShouldAccept, plugin));
    }
    @Test
    public void testCompatibilityFunctionExpectFalse() {
        assertFalse(AnnotFramework.isImageTablePluginValid(image, tableShouldReject, plugin));
    }
}