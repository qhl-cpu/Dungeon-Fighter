package inventory;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represent an item having name, amount of the buffing effect, and type
public abstract class Item implements Writable {
    private String name;         // name of the item
    private int effectAmount;          // amount of item's buffing effects
    private String type;         // type of the item

    private Inventory inventory;

    /*
     * REQUIRES: name, type has a non-zero length; amount is a non-negative integer
     * EFFECTS: name on item is set to name; assign item amount of
     *          its buffing effects; type on item is set to type;
     */
    public Item(String name, int effectAmount, String type) {
        this.name = name;
        this.effectAmount = effectAmount;
        this.type = type;

        inventory = null;
    }

    // EFFECTS: returns true if this item is assigned to an inventory, false otherwise
    public boolean isAssignedToInventory() {
        return !(inventory == null);
    }

    // MODIFIES: this
    // EFFECTS: assigns this item to an inventory
    public void assignToInventory(Inventory inventory) {
        removeFromInventory();
        this.inventory = inventory;
        this.inventory.addItem(this);
    }

    // MODIFIES: this, invnetory
    // EFFECTS: if item is assigned to an invnetory, removes item from assigned invnetory;
    // otherwise has no effect
    public void removeFromInventory() {
        if (isAssignedToInventory()) {
            Inventory inventory = getAssignedInventory();
            inventory.deleteItem(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public int getEffectAmount() {
        return effectAmount;
    }

    public String getType() {
        return type;
    }

    public Inventory getAssignedInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    // EFFECTS: returns item as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("effectAmount", effectAmount);
        json.put("type",type);
        return json;
    }
}
