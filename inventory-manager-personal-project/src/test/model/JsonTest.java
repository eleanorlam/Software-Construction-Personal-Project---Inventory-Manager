package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JsonTest source code
// UBC CPSC 210
// 2025
// https://github.com/stleary/JSON-java
public class JsonTest {
    protected void checkCorrectMaterial(String name, int quantity, Locations location, Material material) {
        assertEquals(name, material.getName());
        assertEquals(quantity, material.getQuantity());
        assertEquals(location, material.getLocation());
    }
}
