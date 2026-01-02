package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InventoryTest {
    private Inventory testInventory;
    private Material testMaterial;
    private Material testMaterial2;
    private Material testMaterial3;

    @BeforeEach
    void runBefore() {
        testInventory = new Inventory();
        testMaterial = new Material("Skewer", 10, "CHBE");
        testMaterial2 = new Material("Glue", 11, "TEF");
        testMaterial3 = new Material("Cows", 9, "Rusty");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testInventory.getSize());
    }

    @Test
    void testAddMaterial() {
        testInventory.addMaterial(testMaterial);
        assertEquals(1, testInventory.getSize());
        assertEquals(testMaterial, testInventory.getRecentMaterial());
    }


    @Test
    void testAddMaterialMultipleTimes() {
        testInventory.addMaterial(testMaterial);
        assertEquals(1, testInventory.getSize());
        assertEquals(testMaterial, testInventory.getRecentMaterial());
        testInventory.addMaterial(testMaterial2);
        assertEquals(2, testInventory.getSize());
        assertEquals(testMaterial2, testInventory.getRecentMaterial());
    }

    @Test
    void testAddMaterialAlreadyThere() {
        testInventory.addMaterial(testMaterial);
        assertEquals(1, testInventory.getSize());
        testInventory.addMaterial(testMaterial);
        assertEquals(1, testInventory.getSize());
    }


    @Test
    void testRemoveMaterial() {
        testInventory.addMaterial(testMaterial);
        assertEquals(1,testInventory.getSize());
        testInventory.removeMaterial(testMaterial);
        assertEquals(0,testInventory.getSize());
    }

    @Test
    void testRemoveMaterialMultipleTimes() {
        testInventory.addMaterial(testMaterial);
        testInventory.addMaterial(testMaterial2);
        assertEquals(2,testInventory.getSize());

        testInventory.removeMaterial(testMaterial);
        testInventory.removeMaterial(testMaterial2);
        assertEquals(0,testInventory.getSize());
    }

    @Test
    void testRemoveMaterialNotThere() {
        testInventory.addMaterial(testMaterial);
        assertEquals(1,testInventory.getSize());
        testInventory.removeMaterial(testMaterial2);
        assertEquals(1,testInventory.getSize());
    }


    @Test
    void testGetRecentMaterial() {
        testInventory.addMaterial(testMaterial);
        assertEquals(testMaterial, testInventory.getRecentMaterial());
    }

    @Test
    void testViewMaterial() {
        testInventory.addMaterial(testMaterial);
        assertEquals("Material name: Skewer \n Quantity: 10 \n Location: CHBE", testInventory.viewMaterial("Skewer"));

    }

    @Test
    void testViewMaterialNotThere() {
        assertEquals("Error: Skewer material is not found in inventory", testInventory.viewMaterial("Skewer"));
    }

    @Test
    void testViewMaterialWithMultipleItems() {
        testInventory.addMaterial(testMaterial2);
        testInventory.addMaterial(testMaterial);
        assertEquals("Material name: Skewer \n Quantity: 10 \n Location: CHBE", testInventory.viewMaterial("Skewer"));

    }

    @Test
    void testViewMaterialCapitalization() {
        testInventory.addMaterial(testMaterial2);
        testInventory.addMaterial(testMaterial);
        assertEquals("Material name: Skewer \n Quantity: 10 \n Location: CHBE", testInventory.viewMaterial("skewer"));

    }

    @Test
    void testPrintInventory() {
        StringBuilder results = new StringBuilder("");
        testInventory.addMaterial(testMaterial);
        testInventory.addMaterial(testMaterial2);
        results.append("======").append("\n")
        .append("Material name: Skewer \n Quantity: 10 \n Location: CHBE").append("\n")
        .append("======").append("\n")
        .append("Material name: Glue \n Quantity: 11 \n Location: TEF").append("\n");
        assertEquals(results.toString(), testInventory.printInventory());
    }

    @Test
    void testPrintInventoryEmpty() {
        StringBuilder results = new StringBuilder("");
        assertEquals(results.toString(), testInventory.printInventory());
    }

    @Test
    void testCheckForMaterial() {
        testInventory.addMaterial(testMaterial2);
        testInventory.addMaterial(testMaterial);
        assertEquals(testMaterial, testInventory.checkForMaterial("Skewer"));
    }

    @Test
    void testCheckForMaterialEmpty() {
        testInventory.addMaterial(testMaterial2);
        testInventory.addMaterial(testMaterial);
        assertEquals(null, testInventory.checkForMaterial("Chicken"));
    }

    @Test
    void testCheckLowQuantityEmptyInventory() {
        assertEquals("Nothing in inventory", testInventory.checkLowQuantity());
    }

    @Test
    void testCheckLowQuantity() {
        StringBuilder results = new StringBuilder("");
        testInventory.addMaterial(testMaterial); 
        testInventory.addMaterial(testMaterial3); 
        results.append("======").append("\n")
        .append("Material name: Cows \n Quantity: 9 \n Location: Rusty").append("\n");
        assertEquals(results.toString(), testInventory.checkLowQuantity());
    }

    @Test
    void testCheckLowQuantityFalse() {
        StringBuilder results = new StringBuilder("");
        testInventory.addMaterial(testMaterial); 
        assertEquals(results.toString(), testInventory.checkLowQuantity());
    }
}
