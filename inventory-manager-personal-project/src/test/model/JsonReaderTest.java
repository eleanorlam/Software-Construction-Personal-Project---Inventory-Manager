package model;

import org.junit.jupiter.api.Test;

import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// JsonReaderTest source code
// UBC CPSC 210
// 2025
// https://github.com/stleary/JSON-java
public class JsonReaderTest extends JsonTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testJReaderEmptyInventory.json");
        try {
            Inventory inv = reader.read();
            assertEquals("Geering Up Inventory", inv.getStatement());
            assertEquals(0, inv.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testJReaderInventory.json");
        try {
            Inventory inv = reader.read();
            assertEquals("Geering Up Inventory", inv.getStatement());
            List<Material> testList = inv.getInventory();
            assertEquals(2, testList.size());
            checkCorrectMaterial("Kuromi", 19, Locations.TEF, testList.get(0));
            checkCorrectMaterial("Cheese", 87, Locations.CHBE, testList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
