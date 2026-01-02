package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Material;
import model.EventLog;
import model.Inventory;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Inventory manager application
public class InventoryApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/inventory.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JPanel panel;
    private JButton inventoryButton;
    private JButton addMatButton;
    private JButton reorderButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton removeButton;

    private ImageIcon savedImage;
    private ImageIcon loadedImage;

    // EFFECTS: sets up GUI frame and necessary inventory and JSON objects
    public InventoryApp() throws FileNotFoundException {  
        super("Inventory UI");
        inventory = new Inventory();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame = new JFrame();

        addOverallButtonPanels();
        loadImages();

        loadButton = new JButton("Load inventory file");
        loadButton.addActionListener(this);
        panel.add(loadButton);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        panel.setVisible(true);

        frame.add(panel);
        frame.setTitle("Inventory Manager");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        frame.setVisible(true);
        frame.setBounds(50,50,WIDTH,HEIGHT);
    }  

    // MODIFIES: panel
    // EFFECTS: Adds buttons to panel and activates their functionality
    private void addOverallButtonPanels() {
        panel = new JPanel();
        saveButton = new JButton(new SaveButtonAction());
        removeButton = new JButton(new RemoveMatButtonAction());
        addMatButton = new JButton(new AddMatButtonAction());
        inventoryButton = new JButton(new InventoryButtonAction());
        reorderButton = new JButton(new ReorderButtonAction());

        panel.add(inventoryButton);
        panel.add(reorderButton);
        panel.add(saveButton);
        panel.add(addMatButton);
        panel.add(removeButton);
    }

    // EFFECTS: Prints EventLog to console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // MODIFIES: savedImage and loadedImage
    // EFFECTS: assigns image variables to right image
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        savedImage = new ImageIcon(System.getProperty("user.dir") + sep
        + "images" + sep + "saved.jpg");
        loadedImage = new ImageIcon(System.getProperty("user.dir") + sep
        + "images" + sep + "loaded.jpg");
    }

    // Represents reorder button and its functionality
    private class ReorderButtonAction extends AbstractAction {
        ReorderButtonAction() {
            super("Show low level items");
        }

        // EFFECTS: displays materials that need to be reordered in a popup frame
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame pop = new JFrame("Items to reorder");
            pop.setSize(600, 300);

            String result = inventory.checkLowQuantity();
            if (result.equals("")) {
                result = "None";
            }

            JTextArea reordering = new JTextArea(20,20);
            
            reordering.setText(result);
            reordering.setWrapStyleWord(true);
            reordering.setLineWrap(true);
            reordering.setEditable(false);
            reordering.setVisible(true);

            JScrollPane scrollPane = new JScrollPane(reordering);


            pop.add(scrollPane, BorderLayout.CENTER);
            pop.setVisible(true);
        }
    }

    // Represents a button to show inventory
    private class InventoryButtonAction extends AbstractAction {
        InventoryButtonAction() {
            super("Show inventory");
        }

        // EFFECTS: Prints inventory to GUI in a JScrollPane
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame pop = new JFrame("Current inventory");
            pop.setSize(600, 400);
          
            JTextArea inv = new JTextArea(20,20);
            String adding = inventory.printInventory();

            if (adding.equals("")) {
                adding = "Nothing in inventory!";
            }
            
            inv.setText(adding);
            inv.setWrapStyleWord(true);
            inv.setLineWrap(true);
            inv.setEditable(false);
            inv.setVisible(true);

            JScrollPane scrollPane = new JScrollPane(inv);


            pop.add(scrollPane, BorderLayout.CENTER);
            pop.setVisible(true);
        }
    }

    // Represents a button to add a new material
    private class AddMatButtonAction extends AbstractAction {
        JFrame pop = new JFrame("Add new material");
        JPanel result = new JPanel();
        JButton done = new JButton("Done");
        String inName;
        int quantityNew;
        String inLocation;

        AddMatButtonAction() {
            super("Add new material");
            result.setLayout(null);
            done.setBounds(10, 80, 80, 25);
            done.setEnabled(true);

            // EFFECTS: sets button to add material when pressed
            done.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addingMaterial(inName, quantityNew, inLocation);
                    pop.dispose();
                }
            });
        }

        // MODIFIES: pop, result
        // EFFECTS: takes input for new material
        @Override 
        public void actionPerformed(ActionEvent e) {
            pop.setSize(600, 400);
            inName = JOptionPane.showInputDialog("Item name");
            String inQuantity = JOptionPane.showInputDialog("Quantity");
            inLocation = JOptionPane.showInputDialog("Location");
            quantityNew = Integer.parseInt(inQuantity);
            result.add(done);
            pop.add(result);
            pop.setVisible(true);
        }
    }

    // Represents button action for removing a material
    private class RemoveMatButtonAction extends AbstractAction {
        JFrame pop = new JFrame("Remove a material");
        JButton done = new JButton("Remove");
        JPanel resultHere = new JPanel();
        String inputName;

        RemoveMatButtonAction() {
            super("Remove material");
            done.setBounds(10, 80, 80, 25);
            done.setEnabled(true);

            // EFFECTS: sets button action to remove material and close when clicked
            done.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removingMaterial(inputName);
                    pop.dispose();
                }
            });
        }
        
        // MODIFIES: this
        // EFFECTS: creates a new frame with a popup image of savedImage
        @Override 
        public void actionPerformed(ActionEvent e) {
            pop.setSize(600, 300);
        
            String inName = JOptionPane.showInputDialog("Item name to remove: ");

            resultHere.add(done);
            inputName = inName;
           
            pop.add(resultHere);
            pop.setVisible(true);
        }
    }

    // Represents a button for saving inventory to file
    private class SaveButtonAction extends AbstractAction {
        SaveButtonAction() {
            super("Save to file");
        }

        // MODIFIES: this
        // EFFECTS: creates a new frame with a popup image of savedImage
        @Override
        public void actionPerformed(ActionEvent e) {
            saveInventory();

            // Showing image
            JFrame pop = new JFrame("Saved!");
            pop.setSize(1000, 800);

            JLabel output = new JLabel(savedImage);
            JPanel result = new JPanel();
            result.add(output);
            pop.add(result);
            pop.setVisible(true);
        }
    }

    // EFFECTS: loads inventory onto file, with a popup image
    @Override
    public void actionPerformed(ActionEvent e) {
        loadInventory();
        JFrame pop = new JFrame("Loaded!");
        pop.setSize(900, 700);

        JLabel output = new JLabel(loadedImage);
        JPanel result = new JPanel();
        result.add(output);
        pop.add(result);
        pop.setVisible(true);
    }

    // MODIFIES: inventory
    // EFFECTS: adds new material to inventory
    private void addingMaterial(String inName, int inQuantity, String location) {
        Material inputMat = new Material(inName, inQuantity, location);
        inventory.addMaterial(inputMat);
     
    }

     // MODIFIES: inventory
    // EFFECTS: removes a material from the inventory if contains, else does nothing
    private void removingMaterial(String inName) {
        Material result = inventory.checkForMaterial(inName);
        if (result != null) {
            inventory.removeMaterial(result);
        } 
    }

    // saveInventory() source code
    // UBC CPSC 210
    // 2025
    // https://github.com/stleary/JSON-java

    // MODIFIES: inventory.json
    // EFFECTS: saves the inventory to file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved " + inventory.getStatement() + " to file " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // loadInventory() source code
    // UBC CPSC 210
    // 2025
    // https://github.com/stleary/JSON-java

    // MODIFIES: this
    // EFFECTS: loads inventory from file
    private void loadInventory() {
        try {
            inventory = jsonReader.read();
            System.out.println("Loaded " + inventory.getStatement() + " from file " +  JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

