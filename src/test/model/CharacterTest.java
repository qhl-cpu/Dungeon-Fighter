package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character character;

    @BeforeEach
    void runBefore () {
        character = new Character("Earl");
    }

    @Test
    void testGetName() {
        assertEquals(character.getName(),"Earl");
    }

    @Test
    void testLevelUp() {
        assertEquals(character.getLevel(), 1);
        character.levelUp();
        assertEquals(character.getLevel(), 2);
    }

    @Test
    void testRecoverHealth() {
        assertEquals(character.getLife(), 20);
        character.recoverHealth(10);
        assertEquals(character.getLife(), 30);
    }

    @Test
    void testIncrementAttack() {
        assertEquals(character.getAttack(), 10);
        character.incrementAttack(10);
        assertEquals(character.getAttack(), 20);
    }

    @Test
    void testViewStatus() {
        character.viewStatus();
    }


}