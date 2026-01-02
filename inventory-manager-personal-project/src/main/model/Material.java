package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents an item with a name, quantity, and location
public class Material implements Writable {
    private String name;        // name of material
    private int quantity;       // quantity 
    private static int minimumLevel = 10; // minimum level of quantity needed
    private String location;    // building location

    // Constructor for material
    // EFFECTS: name of material is set to matName; quantity is a non-negative integer
    //           location is set, and checks lowlevel
    public Material(String matName, int initialQuantity, String location) {
        this.name = matName;

        if (initialQuantity >= 0) {
            this.quantity = initialQuantity;
        } else {
            quantity = 0;
        }
        this.location = location;
        isLowLevel();
    }

    // getters
    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public Material getMaterial() {
        return this;
    }

    // EFFECTS: return true if quantity < minimumLevel, otherwise reutrns false
    public boolean isLowLevel() {
        if (quantity < minimumLevel) {
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: |negative amount| <= getQuantity()
    // MODIFIES: this
    // EFFECTS: updates the quantity of the material where a positive input adds to the quantity;
    //          negative input takes away from the quantity
    public void updateQuantity(int amount) {
        this.quantity += amount;
    }
    

    // EFFECTS: prints the material in string form
    public String toString() {
        return "Material name: " + name + " \n Quantity: " + quantity + " \n Location: " + location; 
    }


    // toJson() source code
    // UBC CPSC 210
    // 2025
    // https://github.com/stleary/JSON-java

    @Override
    // EFFECTS: write a material to JSON file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("quantity", quantity);
        json.put("location", location);
        return json;
    }
}
