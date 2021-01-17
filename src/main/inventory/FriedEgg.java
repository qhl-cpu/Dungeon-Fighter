package inventory;

// Represent a FriedEgg having name, amount of the buffing effect, and type
public class FriedEgg extends Item {

    //EFFECTS: create a new FriedEgg with 1 points buffing effect with type Recovery
    public FriedEgg() {
        super("FriedEgg", 1,"Recovery");

    }

    public void printS() {
        System.out.println(1);
    }

}
