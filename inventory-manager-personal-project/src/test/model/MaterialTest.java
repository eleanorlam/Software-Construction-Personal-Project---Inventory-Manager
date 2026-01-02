package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialTest {
    private Material testMaterial;

    @BeforeEach
    void runBefore() {
        testMaterial = new Material("Masking tape", 15, "Rusty");
    }

    @Test
    void testConstructor() {
        assertEquals("Masking tape", testMaterial.getName());
        assertEquals(15, testMaterial.getQuantity());
        assertEquals(Locations.Rusty, testMaterial.getLocation());
    }

    @Test   
    void testConstructorNegQuantity() {
        testMaterial = new Material("Masking tape", -2, "Rusty");
        assertEquals("Masking tape", testMaterial.getName());
        assertEquals(0, testMaterial.getQuantity());
        assertEquals(Locations.Rusty, testMaterial.getLocation());
    }

    @Test
    void testToString() {
        assertEquals("Material name: Masking tape \n Quantity: 15 \n Location: Rusty",testMaterial.toString());
    }

    @Test 
    void testUpdateQuantity() {
        testMaterial.updateQuantity(10);
        assertEquals(25, testMaterial.getQuantity());
    }

    @Test 
    void testUpdateNegQuantity() {
        testMaterial.updateQuantity(-3);
        assertEquals(12, testMaterial.getQuantity());
    }

    @Test 
    void testUpdateQuantityMultipelTimes() {
        testMaterial.updateQuantity(10);
        assertEquals(25, testMaterial.getQuantity());
        testMaterial.updateQuantity(2);
        assertEquals(27, testMaterial.getQuantity());
    }

    @Test 
    void testLowLevelFalse() {
        assertEquals(15, testMaterial.getQuantity());
        assertFalse(testMaterial.isLowLevel());
    }

    @Test 
    void testLowLevelTrue() {
        testMaterial = new Material("Masking tape", 9, "Rusty");
        assertTrue(testMaterial.isLowLevel());
    }

    @Test 
    void testLowLevelFalseBoundary() {
        testMaterial = new Material("Masking tape", 10, "Rusty");
        assertFalse(testMaterial.isLowLevel());
    }
}
