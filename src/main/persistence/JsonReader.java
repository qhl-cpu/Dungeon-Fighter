package persistence;

import model.Character;
import inventory.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads character and inventory from JSON data stored in file
// This class is based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads character from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Character readCharacter() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharacter(jsonObject);
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory readInventory() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source)throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source),StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses character from JSON object and returns it
    private Character parseCharacter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");
        int attack = jsonObject.getInt("attack");
        int life = jsonObject.getInt("life");

        Character character = new Character(name,level,attack,life);

        return character;
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inventory = new Inventory();
        addItems(inventory,jsonObject);
        return inventory;
    }

    // MODIFIES: inventory
    // EFFECTS: parses items from JSON object and adds them to inventory
    private void addItems(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inventory,nextItem);
        }
    }

    // MODIFIES: inventory
    // EFFECTS: parses item from JSON object and add it to inventory
    private void addItem(Inventory inventory, JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        if (name.equals("FriedEgg")) {
            Item item = new FriedEgg();
            inventory.addItem(item);
        } else if (name.equals("Steak")) {
            Item item = new Steak();
            inventory.addItem(item);
        } else if (name.equals("FlamingEssentialOil")) {
            Item item = new FlamingEssentialOil();
            inventory.addItem(item);
        } else {
            Item item = new MiracleFlower();
            inventory.addItem(item);
        }
    }
}
