package inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import model.Character;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represent a inventory having a list of items, having counters for different items
public class Inventory extends Observable implements Writable {
    private List<Item> inventory;                     // a list containing different items
    private int countSteak = 0;                 // counter for Steak
    private int countFriedEgg = 0;              // counter for FriedEgg
    private int countFlamingEssentialOil = 0;   // counter for FlamingEssentialOil
    private int countMiracleFlower = 0;         // counter for MiracleFlower

    private static final String RECOVERY_TYPE = "Recovery";
    private static final String ATTACK_TYPE = "Attack";

    //EFFECTS: create a new inventory that can contain different items
    public Inventory() {
        inventory = new ArrayList<Item>();
    }

    //EFFECTS: return the size of inventory
    public int getSize() {
        return inventory.size();
    }

    // EFFECTS: return true if inventory contains a item
    public boolean contains(Item item) {
        return inventory.contains(item);
    }

    // EFFECTS: return true if inventory is empty
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    //MODIFIES: this,item
    //EFFECTS: add items to inventory
    public void addItem(Item item) {
        if (item.getAssignedInventory() != null) {
            item.getAssignedInventory().deleteItem(item);
        }
        inventory.add(item);
        item.setInventory(this);
    }

    //EFFECTS: return the inventory list
    public List<Item> getInventoryList() {
        return inventory;
    }

    //REQUIRES: item is contained in the inventory
    //MODIFIES: this, Character
    //EFFECTS: use a Recovery/Attack type item on the character, and delete the used item from inventory
    public void useItem(Character character,Item item) {
        for (int i = 0; i < getSize(); i++) {
            if (getInventoryList().get(i).getName() == item.getName()) {
                if (getInventoryList().get(i).getType() == RECOVERY_TYPE) {
                    character.recoverHealth(item.getEffectAmount());
                } else {
                    character.incrementAttack(item.getEffectAmount());
                }
                deleteItem(item);
                break;
            }
        }
    }

    // EFFECTS: returns an unmodifiable list of items in this inventory
    public List<Item> getItems() {
        return Collections.unmodifiableList(inventory);
    }

    //REQUIRES: MiracleFlower is contained in the inventory, character's life <= 0
    //MODIFIES: this, Character
    //EFFECTS: use MiracleFlower to revive current character
    public void useMiracleFlower(Character character) {
        for (int i = 0; i < getSize(); i++) {
            if (getInventoryList().get(i).getName().equals("MiracleFlower")) {
                character.setLife(15);
                deleteItem(getInventoryList().get(i));
                break;
            }
        }

    }



    //REQUIRES: item is assigned to an inventory
    //MODIFIES: this,item
    //EFFECTS: remove an item from inventory
    public void deleteItem(Item item) {
        item.setInventory(null);
        inventory.remove(item);
    }

    //MODIFIES: this
    //EFFECTS: count different items in inventory
    public void countItems() {
        initialCounters();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals("Steak")) {
                countSteak++;
            } else if (inventory.get(i).getName().equals("FriedEgg")) {
                countFriedEgg++;
            } else if (inventory.get(i).getName().equals("FlamingEssentialOil")) {
                countFlamingEssentialOil++;
            } else  {
                countMiracleFlower++;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize item counters
    public void initialCounters() {
        countSteak = 0;
        countFriedEgg = 0;
        countFlamingEssentialOil = 0;
        countMiracleFlower = 0;
    }

    //EFFECTS: check if the inventory contains an item
    public boolean haveItem(Item item) {
        for (int i = 0; i < getSize(); i++) {
            if (getInventoryList().get(i).getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    // EFFECTS: returns inventory as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inventory",inventoryToJson());
        return json;
    }

    // EFFECTS: returns items in this inventory as a JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : inventory) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

    public int getCountSteak() {
        return countSteak;
    }

    public int getCountFriedEgg() {
        return countFriedEgg;
    }

    public int getCountFlamingEssentialOil() {
        return countFlamingEssentialOil;
    }

    public int getCountMiracleFlower() {
        return countMiracleFlower;
    }
}
