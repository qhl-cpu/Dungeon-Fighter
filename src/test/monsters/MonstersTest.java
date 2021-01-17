package monsters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MonstersTest {

    @Test
    void testGoblin() {
        Monster goblin = new Goblin();
        assertEquals(goblin.getName(), "Goblin");
        assertEquals(goblin.getAttack(), 3);
        assertEquals(goblin.getLife(), 25);
        goblin.setLife(50);
        assertEquals(goblin.getLife(),50);
    }

    @Test
    void testArcher() {
        Monster archer = new Archer();
        assertEquals(archer.getName(), "Archer");
        assertEquals(archer.getAttack(), 4);
        assertEquals(archer.getLife(), 15);
        archer.setLife(50);
        assertEquals(archer.getLife(),50);
    }

    @Test
    void testMinotaur() {
        Monster minotaur = new Minotaur();
        assertEquals(minotaur.getName(), "Minotaur");
        assertEquals(minotaur.getAttack(), 5);
        assertEquals(minotaur.getLife(), 35);
        minotaur.setLife(50);
        assertEquals(minotaur.getLife(),50);
    }


}
