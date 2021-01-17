package inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {
    Item item;
    Inventory inventory;

    @BeforeEach
    void runBefore() {
        item = new Steak();
        inventory = new Inventory();
    }

    @Test
    void testGetIsAssignedInventory() {
        assertEquals(item.getAssignedInventory(),null);
        assertFalse(item.isAssignedToInventory());

        item.assignToInventory(inventory);

        assertEquals(item.getAssignedInventory(), inventory);
        assertTrue(item.isAssignedToInventory());
    }

    @Test
    void testRemoveFromInventory() {
        item.assignToInventory(inventory);
        assertEquals(item.getAssignedInventory(), inventory);
        item.removeFromInventory();
        assertFalse(item.isAssignedToInventory());
    }

    @Test
    void testAssignToInventory() {
        item.assignToInventory(inventory);
        assertEquals(item.getAssignedInventory(), inventory);

        Inventory inventory2 = new Inventory();
        item.assignToInventory(inventory2);
        assertEquals(item.getAssignedInventory(),inventory2);

        assertEquals(inventory.contains(item),false);
    }

}
