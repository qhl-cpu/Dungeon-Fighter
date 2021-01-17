package model;

import monsters.Monster;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Observable;

// Represents a character having name, amount of attack, defence, and life.
public class Character extends Observable implements Writable {
    private int attack;         // amount of damage the character can do
    private int life;           // amount of the character's life points
    private int level;          // character's level
    private final String name;  // character's name

    /*
     * REQUIRES: characterName has a non-zero length
     * EFFECTS: name on character is set to characterName. level,
     *         attack and life are set to default value
     */
    public Character(String characterName) {
        name = characterName;
        level = 1;
        attack = 10;
        life = 20;
    }

    /*
     * REQUIRES: characterName has a non-zero length
     * EFFECTS: name on character is set to characterName. level is set to level,
     *         attack is set to attack and life is set to life
     */
    public Character(String characterName, int level, int attack, int life) {
        name = characterName;
        this.level = level;
        this.attack = attack;
        this.life = life;
    }


    //MODIFIES: this
    //EFFECTS: increment character's level, attack and life by 1
    public void levelUp() {
        level++;
        attack++;
        life++;
        setChanged();
        notifyObservers();
    }

    //Requires: health is a positive integer
    //MODIFIES: this
    //EFFECTS: increase character's life points by health
    public void recoverHealth(int health) {
        life += health;

    }

    //Requires: attack is a positive integer
    //MODIFIES: this
    //EFFECTS: increase character's attack by the parameter attack
    public void incrementAttack(int attack) {
        this.attack += attack;

    }

    //    REQUIRES: monster.getLife() > 0
//    MODIFIES: this, monster
//    EFFECTS: battle with a monster in a automatic way,
//             Level up after defeating monster,
    public void battle(Monster monster) {
        while (monster.getLife() > 0 && life > 0) {
            //stopTimer(2000);

            //monster.setLife(monster.getLife() - attack);
            monster.receiveDamage(attack);

            if (monster.getLife() <= 0) {
                levelUp();
                break;
            }
            //stopTimer(4000);

            //setLife(life - monster.getAttack());
            receiveDamage(monster.getAttack());
        }
    }

    public void stopTimer(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public int getLevel() {
        return level;
    }

    public int getAttack() {
        return attack;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void receiveDamage(int damage) {
        life -= damage;
        setChanged();
        notifyObservers(damage);
    }


    public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns character as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("level",level);
        json.put("attack",attack);
        json.put("life",life);
        return json;
    }

    //EFFECTS: return character's current status containing name, level, attack, and life
    public String viewStatus() {
        return ("\n[ " + name + "'s status: " + "lvl." + level + ". atk = " + attack + ". life = " + life + " ]");
    }
}
