package monsters;

import java.util.Observable;

// Represent an monster having name, attack, and life.
public abstract class Monster extends Observable {
    protected String name;  // name of the monster
    protected int attack;   // amount of damage the monster can do
    protected int life;     // amount of the monster's life points

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: Monster's name is set to name; monster's attack is set
     *          to attack; and monster's life is set to life.
     */
    public Monster(String name, int attack, int life) {
        this.name = name;
        this.attack = attack;
        this.life = life;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        setChanged();
        notifyObservers();
    }

    public void receiveDamage(int damage) {
        life -= damage;
        setChanged();
        notifyObservers(damage);
    }
}
