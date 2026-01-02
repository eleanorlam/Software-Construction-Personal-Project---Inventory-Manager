package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents an inventory 
public class Inventory implements Writable {
    private ArrayList<Material> inventory;
    private static String statement = "Geering Up Inventory";

    // Constructor
    public Inventory() {
        inventory = new ArrayList<Material>();
    }

    // MODIFIES: this
    // EFFECTS: adds a material to the inventory list
    //          if material is already in inventory, does nothing
    public void addMaterial(Material material) {
        if (!inventory.contains(material)) {
            this.inventory.add(material);
            String desc = "Added new material " + material.getName();
            EventLog.getInstance().logEvent(new Event(desc));
        }
    }


    // MODIFIES: this
    // EFFECTS: removes a material to the inventory list using a string input
    //          if material is not in inventory, does nothing
    public void removeMaterial(Material material) {
        if (inventory.contains(material)) {
            this.inventory.remove(material);
            String desc = "Removed " + material.getName();
            EventLog.getInstance().logEvent(new Event(desc));
        }
    }

    // EFFECTS: checks for a material match based on the item name inputted
    public Material checkForMaterial(String item) {
        for (Material material : inventory) {
            if (material.getName().equalsIgnoreCase(item)) {
                return material.getMaterial(); 
            }
        }
        return null;
    }


    // EFFECTS: returns material that have a low quantity
    public String checkLowQuantity() {
        StringBuilder results = new StringBuilder("");
        if (inventory.isEmpty()) {
            EventLog.getInstance().logEvent(new Event("Displayed empty reorder list"));
            return "Nothing in inventory";
        } else {
            for (Material material : inventory) {
                if (material.isLowLevel()) {
                    results.append("======").append("\n").append(material.toString()).append("\n");
                }
            }
            EventLog.getInstance().logEvent(new Event("Displayed reordering list"));
            return results.toString();
        }
    }

    // EFFECTS: prints details of the material by searching through inventory
    public String viewMaterial(String item) {
        for (Material material : inventory) {
            if (material.getName().equalsIgnoreCase(item)) {
                return material.toString();
            }
        }
        return "Error: " + item + " material is not found in inventory";
    }

    // getter
    public int getSize() {
        return inventory.size();
    }

    // getter
    public ArrayList<Material> getInventory() {
        return inventory;
    }

    // EFFECTS: returns the most recently added material, returns null if empty
    public Material getRecentMaterial() {
        int size = getSize() - 1;
        return inventory.get(size);
    }

    // EFFECTS: prints the entire inventory, returns empty message if inventory is empty
    public String printInventory() {
        StringBuilder results = new StringBuilder("");
        if (inventory.isEmpty()) {
            EventLog.getInstance().logEvent(new Event("Displayed empty inventory"));
            return results.toString();
        } else {
            for (Material material : inventory) {
                results.append("======").append("\n").append(material.toString()).append("\n");
            }
            EventLog.getInstance().logEvent(new Event("Displayed inventory"));
            return results.toString();
        }
    }

    public String getStatement() {
        return statement;
    }


    // toJson() source code
    // UBC CPSC 210
    // 2025
    // https://github.com/stleary/JSON-java

    @Override
    // EFFECTS: creating new JSON inventory to load into
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", statement);
        json.put("materials", materialsToJson());
        return json;
    }


    // materialsToJson() source code
    // UBC CPSC 210
    // 2025
    // https://github.com/stleary/JSON-java

    // EFFECTS: returns materials in this inventory as a JSON array
    private JSONArray materialsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Material mat : inventory) {
            jsonArray.put(mat.toJson());
        }
        return jsonArray;
    }
}
