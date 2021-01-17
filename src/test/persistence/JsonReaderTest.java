package persistence;

import inventory.Inventory;
import inventory.Item;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This test class is based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentCharacterFile() {
        JsonReader jsonReader= new JsonReader("./data/noSuchFile.json");
        try {
            Character character = jsonReader.readCharacter();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNonExistentInventoryFile() {
        JsonReader jsonReader= new JsonReader("./data/noSuchFile.json");
        try {
            Inventory inventory = jsonReader.readInventory();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderCharacter() {
        JsonReader jsonReader = new JsonReader("./data/testReaderCharacter.json");
        try {
            Character character = jsonReader.readCharacter();
            assertEquals(character.getName(),"EARL");
            assertEquals(character.getAttack(),14);
            assertEquals(character.getLife(),2);
            assertEquals(character.getLevel(),5);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testEmptyInventory() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            Inventory inventory = jsonReader.readInventory();
            assertEquals(inventory.getSize(),0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInventory() {
        JsonReader jsonReader = new JsonReader("./data/testReaderInventory.json");
        try {
            Inventory inventory = jsonReader.readInventory();
            List<Item> items = inventory.getInventoryList();
            assertEquals(items.size(),5);
            checkItems("FlamingEssentialOil","Attack",2,items.get(0));
            checkItems("Steak","Recovery",2,items.get(1));
            checkItems("FriedEgg","Recovery",1,items.get(2));
            checkItems("FlamingEssentialOil","Attack",2,items.get(3));
            checkItems("MiracleFlower","Revive",0,items.get(4));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}
