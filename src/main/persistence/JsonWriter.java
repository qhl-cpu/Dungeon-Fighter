package persistence;

import model.Character;
import inventory.Inventory;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of the game progress to file
// This class is based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.

public class JsonWriter {
    public static final int INDENT_FACTOR = 3;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: open writer; throw FileNotFoundException when
    // destination file cannot be opened for writing.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of character to file
    public void writeCharacter(Character character) {
        JSONObject jsonCharacter = character.toJson();
        saveToFile(jsonCharacter.toString(INDENT_FACTOR));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of inventory to file
    public void writeInventory(Inventory inventory) {
        JSONObject jsonInventory = inventory.toJson();
        saveToFile(jsonInventory.toString(INDENT_FACTOR));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
