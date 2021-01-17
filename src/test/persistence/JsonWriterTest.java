package persistence;

import inventory.FlamingEssentialOil;
import inventory.FriedEgg;
import inventory.Inventory;
import inventory.Item;
import org.junit.jupiter.api.Test;

import model.Character;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This test class is based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/\0illegal:fileName.json");
            jsonWriter.open();
            fail("OPException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteCharacter() {
        try {
            Character character = new Character("Earl", 2, 11, 12);
            JsonWriter jsonWriter = new JsonWriter("./data/testWriterCharacter.json");
            jsonWriter.open();
            jsonWriter.writeCharacter(character);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterCharacter.json");
            character = jsonReader.readCharacter();
            assertEquals(character.getName(),"Earl");
            assertEquals(character.getLevel(),2);
            assertEquals(character.getAttack(),11);
            assertEquals(character.getLife(),12);

        }catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteInventory() {
        try {
            Inventory inventory = new Inventory();
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FlamingEssentialOil());

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterInventory.json");
            jsonWriter.open();
            jsonWriter.writeInventory(inventory);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterInventory.json");
            inventory = jsonReader.readInventory();
            List<Item> items = inventory.getItems();
            assertEquals(items.size(),3);
            checkItems("FriedEgg","Recovery",1,items.get(0));
            checkItems("FriedEgg","Recovery",1,items.get(1));
            checkItems("FlamingEssentialOil","Attack",2,items.get(2));

        }catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory inventory = new Inventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.writeInventory(inventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            inventory = reader.readInventory();
            assertEquals(0, inventory.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
