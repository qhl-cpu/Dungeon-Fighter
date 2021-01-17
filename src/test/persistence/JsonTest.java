package persistence;

import inventory.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This test class is based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
public class JsonTest {
    protected  void checkItems(String name, String type, int effectAmount, Item item) {
        assertEquals(name,item.getName());
        assertEquals(type,item.getType());
        assertEquals(effectAmount,item.getEffectAmount());
    }
}
