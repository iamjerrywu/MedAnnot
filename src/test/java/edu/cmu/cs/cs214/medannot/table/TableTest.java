package edu.cmu.cs.cs214.medannot.table;

import edu.cmu.cs.cs214.medannot.framework.core.Pair;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TableTest {
    private Table table;
    @Before
    public void setUp() {
        try {
            table = new Table("src/test/java/edu/cmu/cs/cs214/medannot/table/testtables/tableToTest.csv");
        } catch (TableError.EmptyCSV e) {
            throw new AssertionFailedError("Empty CSV Error");
        } catch (TableError.InvalidFile e) {
            throw new AssertionFailedError("Invalid File Error");
        } catch (TableError.MissingCoreFields e) {
            throw new AssertionFailedError("MissingCoreFieldsError");
        }
    }
    @Test
    public void testGetParamsSuccess() {
        Map<String, String> params = table.getParams(new Pair(150, 150));
        Assert.assertNotNull(params);
        Assert.assertTrue(params.keySet().containsAll(List.of("Title", "Fracture", "Tumor", "Infection", "Blockage")));
        Assert.assertEquals("One", params.get("Title"));
        Assert.assertEquals("Y", params.get("Fracture"));
        Assert.assertEquals("N", params.get("Tumor"));
        Assert.assertEquals("Y", params.get("Infection"));
        Assert.assertEquals("Y", params.get("Blockage"));
    }
    @Test
    public void testGetParamsFail() {
        Map<String, String> params = table.getParams(new Pair(159, 150));
        Assert.assertNull(params);
    }
    @Test
    public void testGetFields() {
        Set<String> fields = table.getFields();
        Assert.assertEquals(5, fields.size());
        Assert.assertTrue(fields.containsAll(Set.of("Title", "Fracture", "Tumor", "Infection", "Blockage")));
    }
    @Test
    public void testGetTargets() {
        List<Pair> targets = table.getTargets();
        Assert.assertEquals(2, targets.size());
        Assert.assertTrue(targets.containsAll(Set.of(new Pair(150, 150), new Pair(200, 200))));
    }
}