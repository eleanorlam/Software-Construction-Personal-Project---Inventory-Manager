package model;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import persistence.JsonReader;
import persistence.JsonWriter;

import static org.junit.jupiter.api.Assertions.*;

// JsonWriterTest source code
// UBC CPSC 210
// 2025
// https://github.com/stleary/JSON-java
public class JsonWriterTest extends JsonTest {
    
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Inventory inv = new Inventory();
            JsonWriter writer = new JsonWriter("./data/testJWriterEmptyInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJWriterEmptyInventory.json");
            inv = reader.read();
            assertEquals("Geering Up Inventory", inv.getStatement());
            assertEquals(0, inv.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Inventory inv = new Inventory();
            inv.addMaterial(new Material("Pochacco", 25, "Rusty"));
            inv.addMaterial(new Material("Blueberries", 87, "CHBE"));
            JsonWriter writer = new JsonWriter("./data/testJWriterInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJWriterInventory.json");
            inv = reader.read();
            assertEquals("Geering Up Inventory", inv.getStatement());
            List<Material> materials = inv.getInventory();
            assertEquals(2, materials.size());
            checkCorrectMaterial("Pochacco", 25, Locations.Rusty, materials.get(0));
            checkCorrectMaterial("Blueberries", 87, Locations.CHBE, materials.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
