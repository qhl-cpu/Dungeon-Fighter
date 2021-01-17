package inventory;

import model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void runBefore () {
        inventory = new Inventory();
    }

    @Test
    void testGetSize() {
        assertEquals(inventory.getSize(),0);
        inventory.addItem(new Steak());
        assertEquals(inventory.getSize(),1);
    }

    @Test
    void testEquals() {
        Steak steak = new Steak();
        Steak steak2 = new Steak();
        FriedEgg friedEgg = new FriedEgg();

        assertFalse(steak.equals(friedEgg));
        assertFalse(steak.equals(null));
        assertTrue(steak.equals(steak2));
    }

    @Test
    void testHashCode() {
        Steak steak = new Steak();
        Steak steak2 = new Steak();
        FriedEgg friedEgg = new FriedEgg();

        assertEquals(steak.hashCode(),steak2.hashCode());
        assertNotEquals(steak.hashCode(),friedEgg.hashCode());
    }

    @Test
    void testAddItem() {
        assertTrue(inventory.isEmpty());
        Steak steak = new Steak();
        assertFalse(inventory.contains(steak));
        inventory.addItem(steak);
        assertTrue(inventory.contains(steak));
        assertTrue(inventory.getInventoryList().contains(steak));

        FriedEgg friedEgg = new FriedEgg();
        inventory.addItem(friedEgg);
        assertTrue(inventory.getInventoryList().contains(friedEgg));

        FlamingEssentialOil flamingEssentialOil = new FlamingEssentialOil();
        inventory.addItem(flamingEssentialOil);
        assertTrue(inventory.getInventoryList().contains(flamingEssentialOil));

        MiracleFlower miracleFlower = new MiracleFlower();
        inventory.addItem(miracleFlower);
        assertTrue(inventory.getInventoryList().contains(miracleFlower));
        assertFalse(inventory.isEmpty());
    }

    @Test
    void testUseItem() {
        Character character = new Character("Earl");
        assertEquals(character.getAttack(),10);
        assertEquals(character.getLife(),20);
        Steak steak = new Steak();
        FriedEgg friedEgg = new FriedEgg();
        FlamingEssentialOil flamingEssentialOil = new FlamingEssentialOil();
        inventory.addItem(steak);
        inventory.addItem(friedEgg);
        inventory.addItem(flamingEssentialOil);

        inventory.useItem(character,steak);
        assertEquals(character.getAttack(),10);
        assertEquals(character.getLife(),22);

        inventory.useItem(character,new FriedEgg());
        assertEquals(character.getAttack(),10);
        assertEquals(character.getLife(),23);

        inventory.useItem(character,new FlamingEssentialOil());
        assertEquals(character.getAttack(),12);
        assertEquals(character.getLife(),23);

    }

    @Test
    void testUseMiracleFlower() {
        Character character = new Character("Earl");
        character.setLife(-1);
        inventory.addItem(new Steak());
        inventory.addItem(new MiracleFlower());
        inventory.useMiracleFlower(character);
        assertEquals(character.getLife(), 15);

        character.setLife(0);
        inventory.addItem(new Steak());
        inventory.addItem(new MiracleFlower());
        inventory.useMiracleFlower(character);
        assertEquals(character.getLife(), 15);

        character.setLife(0);
        inventory.useMiracleFlower(character);
        assertEquals(character.getLife(), 0);
    }

    @Test
    void testDeleteItem() {
        Steak steak = new Steak();
        inventory.addItem(steak);
        assertTrue(inventory.getInventoryList().contains(steak));
        inventory.deleteItem(steak);
        assertFalse(inventory.getInventoryList().contains(steak));


        FriedEgg friedEgg = new FriedEgg();
        inventory.addItem(friedEgg);
        assertTrue(inventory.getInventoryList().contains(friedEgg));
        inventory.deleteItem(friedEgg);
        assertFalse(inventory.getInventoryList().contains(friedEgg));

        FlamingEssentialOil flamingEssentialOil = new FlamingEssentialOil();
        inventory.addItem(flamingEssentialOil);
        assertTrue(inventory.getInventoryList().contains(flamingEssentialOil));
        inventory.deleteItem(flamingEssentialOil);
        assertFalse(inventory.getInventoryList().contains(flamingEssentialOil));

        MiracleFlower miracleFlower = new MiracleFlower();
        inventory.addItem(miracleFlower);
        assertTrue(inventory.getInventoryList().contains(miracleFlower));
        inventory.deleteItem(miracleFlower);
        assertFalse(inventory.getInventoryList().contains(miracleFlower));

    }

    @Test
    void testCountItems() {
        inventory.countItems();

        assertEquals(inventory.getCountFriedEgg(), 0);
        assertEquals(inventory.getCountSteak(), 0);
        assertEquals(inventory.getCountFlamingEssentialOil(), 0);
        assertEquals(inventory.getCountMiracleFlower(), 0);

        for (int i = 0; i< 10; i++) {
            inventory.addItem(new Steak());
            inventory.addItem(new Steak());
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FlamingEssentialOil());
            inventory.addItem(new FlamingEssentialOil());
            inventory.addItem(new FlamingEssentialOil());
        }
        inventory.addItem(new MiracleFlower());
        inventory.addItem(new MiracleFlower());
        inventory.countItems();

        assertEquals(inventory.getCountFriedEgg(), 10);
        assertEquals(inventory.getCountSteak(), 20);
        assertEquals(inventory.getCountFlamingEssentialOil(), 30);
        assertEquals(inventory.getCountMiracleFlower(), 2);
    }

    @Test
    void testInitialCounters() {
        for (int i = 0; i< 10; i++) {
            inventory.addItem(new Steak());
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FriedEgg());
            inventory.addItem(new FlamingEssentialOil());
            inventory.addItem(new FlamingEssentialOil());
        }
        inventory.addItem(new MiracleFlower());
        inventory.addItem(new MiracleFlower());
        inventory.countItems();

        assertEquals(inventory.getCountFriedEgg(), 30);
        assertEquals(inventory.getCountSteak(), 10);
        assertEquals(inventory.getCountFlamingEssentialOil(), 20);
        assertEquals(inventory.getCountMiracleFlower(), 2);

        inventory.initialCounters();
        assertEquals(inventory.getCountFriedEgg(), 0);
        assertEquals(inventory.getCountSteak(), 0);
        assertEquals(inventory.getCountFlamingEssentialOil(), 0);
        assertEquals(inventory.getCountMiracleFlower(), 0);
    }

    @Test
    void testFindUseSteak() {
        Character character = new Character("Earl");
        inventory.useItem(character,new Steak());
        assertEquals(character.getLife(),20);

        inventory.addItem(new FriedEgg());
        inventory.countItems();
        inventory.useItem(character,new Steak());
        assertEquals(character.getLife(),20);

        inventory.addItem(new Steak());
        inventory.countItems();

        assertEquals(inventory.getSize(),2);
        inventory.useItem(character,new Steak());
        assertEquals(inventory.getSize(),1);
        assertEquals(character.getLife(),22);
    }

    @Test
    void testFindUseFriedEgg() {
        Character character = new Character("Earl");
        inventory.useItem(character,new FriedEgg());
        assertEquals(character.getLife(),20);

        inventory.addItem(new Steak());
        inventory.countItems();
        inventory.useItem(character,new FriedEgg());
        assertEquals(character.getLife(),20);

        inventory.addItem(new FriedEgg());
        inventory.countItems();

        assertEquals(inventory.getSize(),2);
        inventory.useItem(character,new FriedEgg());
        assertEquals(inventory.getSize(),1);
        assertEquals(character.getLife(),21);
    }

    @Test
    void testFindUseFlamingEssentialOil() {
        Character character = new Character("Earl");
        inventory.useItem(character,new FlamingEssentialOil());
        assertEquals(character.getAttack(),10);

        inventory.addItem(new Steak());
        inventory.countItems();
        inventory.useItem(character,new FlamingEssentialOil());
        assertEquals(character.getAttack(),10);

        inventory.addItem(new FlamingEssentialOil());
        inventory.countItems();

        assertEquals(inventory.getSize(),2);
        inventory.useItem(character,new FlamingEssentialOil());
        assertEquals(inventory.getSize(),1);
        assertEquals(character.getAttack(),12);
    }

    @Test
    void testHaveSteak() {
        inventory.addItem(new FriedEgg());
        assertFalse(inventory.haveItem(new Steak()));

        inventory.addItem(new Steak());
        assertTrue(inventory.haveItem(new Steak()));
    }

    @Test
    void testHaveFriedEgg() {
        inventory.addItem(new Steak());
        assertFalse(inventory.haveItem(new FriedEgg()));

        inventory.addItem(new FriedEgg());
        assertTrue(inventory.haveItem(new FriedEgg()));
    }

    @Test
    void testHaveFlamingEssentialOil() {
        inventory.addItem(new Steak());
        assertFalse(inventory.haveItem(new FlamingEssentialOil()));

        inventory.addItem(new FlamingEssentialOil());
        assertTrue(inventory.haveItem(new FlamingEssentialOil()));
    }

    @Test
    void testHaveMiracleFlower() {
        inventory.addItem(new FlamingEssentialOil());
        assertFalse(inventory.haveItem(new MiracleFlower()));

        inventory.addItem(new MiracleFlower());
        assertTrue(inventory.haveItem(new MiracleFlower()));
    }

}
