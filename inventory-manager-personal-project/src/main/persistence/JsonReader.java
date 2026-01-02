package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Inventory;
import model.Material;

// JsonReader source code
// UBC CPSC 210
// 2025
// https://github.com/stleary/JSON-java

// Represents a reader that reads inventory from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inventory = new Inventory();
        addNewToInventory(inventory, jsonObject);
        return inventory;
    }

    // MODIFIES: inv
    // EFFECTS: parses materials from JSON object and adds them to inventory
    private void addNewToInventory(Inventory inv, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("materials");
        for (Object json : jsonArray) {
            JSONObject nextMat = (JSONObject) json;
            addMaterialToFile(inv, nextMat);
        }
    }

    // MODIFIES: inv
    // EFFECTS: parses materials from JSON object and adds it to inventory
    private void addMaterialToFile(Inventory inv, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int quantity = jsonObject.getInt("quantity");
        String locations = jsonObject.getString("location");
        Material mat = new Material(name, quantity, locations);
        inv.addMaterial(mat);
    }
}


