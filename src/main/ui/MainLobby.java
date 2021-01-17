package ui;

import inventory.*;
import model.Character;
import monsters.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Dungeon fighter(Idle game)'s main lobby
public class MainLobby {

    private Character character;
    private Inventory inventory;
    private Scanner input;
    //private Gold gold;

    private static final String JSON_CHARACTER_STORE = "./data/character.json";
    private static final String JSON_INVENTORY_STORE = "./data/inventory.json";
    private JsonWriter jsonCharacterWriter;
    private JsonWriter jsonInventoryWriter;
    private JsonReader jsonCharacterReader;
    private JsonReader jsonInventoryReader;

        // EFFECTS: runs the game
    public MainLobby() throws FileNotFoundException {
        jsonCharacterWriter = new JsonWriter(JSON_CHARACTER_STORE);
        jsonInventoryWriter = new JsonWriter(JSON_INVENTORY_STORE);
        jsonCharacterReader = new JsonReader(JSON_CHARACTER_STORE);
        jsonInventoryReader = new JsonReader(JSON_INVENTORY_STORE);
        runGame();
    }

        // MODIFIES: this
        // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        input = new Scanner(System.in);

        System.out.println("Enter you character's name (No spaces): ");
        String command = input.next();
        init(command);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                System.out.println("\nThanks for playing! See you next time");
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: return to the main lobby
    private void returnMainLobby() {
        generateWhiteSpace(3);
        displayMenu();
        String command = input.next();
        command = command.toLowerCase();
        processCommand(command);
    }

        // MODIFIES: this
        // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            enterDungeon();
        } else if (command.equals("c")) {
            nextStage();
        } else if (command.equals("i")) {
            viewInventory();
            if (inventory.getSize() != 0) {
                processUsingItem();
            }
        } else if (command.equals("b")) {
            viewBackground();
        } else if (command.equals("s")) {
            saveGameProgress();
        } else if (command.equals("l")) {
            loadGameProgress();
        } else if (command.equals("m")) {
            enterMysteryShop();
        } else if (command.equals("q")) {
            System.exit(0);
        } else {
            System.out.println("Selection not valid...");
        }
    }

        // REQUIRES: name has non-negative length
        // MODIFIES: this
        // EFFECTS: initializes character and inventory
    private void init(String name) {
        character = new Character(name);
        inventory = new Inventory();
        //gold = new Gold();
    }

    private void generateWhiteSpace(int i) {
        for (int numLines = 0; numLines < i; numLines++) {
            System.out.println("");
        }
    }

        // EFFECTS: displays lobby menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to Dungeon Fighter's main lobby!");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Enter Dungeon");
        System.out.println("\tc -> View Character status");
        System.out.println("\ti -> View Inventory");
        System.out.println("\tm -> Mystery Shop");
        System.out.println("\tb -> Game Background");
        System.out.println("");
        System.out.println("\ts -> Save game progress");
        System.out.println("\tl -> Load Game progress");
        System.out.println("\tq -> Quit");
    }

        // MODIFIES: this
        // EFFECTS: enter the dungeon and fight with different monsters,
        //          move on to the next stage when battle is over.
    private void enterDungeon() {
        generateWhiteSpace(4);
        System.out.println("---------------Battle Log-----------------");
        if (character.getLevel() <= 3) {
            encounterGoblin();
        } else if (character.getLevel() > 3 && character.getLevel() <= 5) {
            encounterArcher();
        } else if (character.getLevel() > 5) {
            encounterMinotaur();
        }
        System.out.println("------------------------------------------");
        if (character.getLife() >= 0) {
            processChooseTrophies();
            nextStage();
        }
    }

    //EFFECTS: display yes/no options for revive to user
    private void displayReviveOption() {
        System.out.println("Do you want to use *MiracleFlower* to revive your character?");
        System.out.println("\ta -> yes");
        System.out.println("\tb -> no");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processReviveOption() {
        displayReviveOption();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("a")) {
            inventory.useMiracleFlower(character);
            System.out.println("By the power of *MiracleFlower*， "
                    + "your character has revived and life has been set to 15.");

        } else if (command.equals("b")) {
            System.out.println("Your character has fall down, better luck next time!");
            System.exit(0);
        } else {
            System.out.println("Selection not valid, please try again");
            processReviveOption();
        }
    }

    // MODIFIES: this
    // EFFECTS: battle with Goblin
    private void encounterGoblin() {
        Monster goblin = new Goblin();
        System.out.println("[You encountered a wild Goblin]");
        battle(goblin);
        checkLevelAndRevive(goblin);
        System.out.println("------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: battle with Archer
    private void encounterArcher() {
        Monster archer = new Archer();
        System.out.println("[You encountered a wild Archer]");
        battle(archer);
        checkLevelAndRevive(archer);
        System.out.println("------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: battle with Minotaur
    private void encounterMinotaur() {
        Monster minotaur = new Minotaur();
        System.out.println("[You encountered a wild Minotaur]");
        battle(minotaur);
        checkLevelAndRevive(minotaur);
//        if (character.getLevel() >= 10) {
//            System.out.println("Congratulation! You achieved the max level and cleared the dungeon");
//            System.exit(0);
//        }
//        if (character.getLife() <= 0) {
//            if (inventory.haveMiracleFlower()) {
//                processReviveOption();
//                battle(minotaur);
//                if (character.getLevel() >= 10) {
//                    System.out.println("Congratulation! You achieved the max level and cleared the dungeon");
//                    System.exit(0);
//                }
//            } else {
//                System.out.println("Couldn't find MiracleFlower in your inventory, your character has fall down.");
//                System.out.println("Thanks for playing, better luck next time!");
//                System.exit(0);
//            }
//        }
        System.out.println("------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: check if character reach the max level when battling with a monster, and check if character is dying.
    //          If character's life <=0, display revive options.
    private void checkLevelAndRevive(Monster monster) {
        if (character.getLife() <= 0) {
            if (inventory.haveItem(new MiracleFlower())) {
                processReviveOption();
                battle(monster);
            } else {
                System.out.println("Couldn't find MiracleFlower in your inventory, your character has fall down.");
                System.out.println("Thanks for playing, better luck next time!");
                System.exit(0);
            }
        }
        if (character.getLevel() >= 10) {
            System.out.println("Congratulation! You achieved the max level and cleared the dungeon");
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processChooseTrophies() {
        displayTrophies();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("a")) {
            character.incrementAttack(1);
            System.out.println("Your attack has been increased by 1");
        } else if (command.equals("b")) {
            character.recoverHealth(3);
            System.out.println("Your health has been increased by 3");
        } else if (command.equals("c")) {
            receiveRandomItem();
        } else if (command.equals("d")) {
            enterDungeon();
        } else if (command.equals("e")) {
            returnMainLobby();
        } else {
            System.out.println("Selection not valid, please try again");
            processChooseTrophies();
        }
    }

    //MODIFIES: this
    //EFFECTS: receive a random item from defeated monster
    private void receiveRandomItem() {
        generateWhiteSpace(2);
        int a = (int)(Math.random() * 4) + 1;
        int b = (int)(Math.random() * 1) + 1;
        System.out.println("------------------------------------------");
        if (a == 1) {
            inventory.addItem(new FriedEgg());
            System.out.println("You find a fried egg!");
        } else if (a == 2) {
            inventory.addItem(new FlamingEssentialOil());
            System.out.println("You find a Flaming Essential Oil!");
        } else if (a == 3) {
            inventory.addItem(new Steak());
            System.out.println("You find a Steak!");
        } else if (a == 4 && b != 1) {
            System.out.println("You find nothing, better luck next time.");
        }
        if (b == 1) {
            inventory.addItem(new MiracleFlower());
            System.out.println("Bravo! You find a secret item: Miracle Flower");
        }
        System.out.println("------------------------------------------");
    }

    //EFFECTS: display trophies options to user
    private void displayTrophies() {
        System.out.println("\n[Please select your trophies]");
        System.out.println("\ta -> Character's atk + 1 permanently");
        System.out.println("\tb -> Recover 3 health");
        System.out.println("\tc -> Search monster's body");
        System.out.println("\td -> Continue without selecting trophies");
        System.out.println("\te -> Back to the main lobby");
    }

    //MODIFIES: this
    //EFFECTS: display character's status and the options of next stage of the game to user
    private void displayNextStage() {
        System.out.println(character.viewStatus());
        System.out.println("\ta -> Enter Dungeon");
        System.out.println("\tb -> View inventory");
        System.out.println("\tc -> Back to the main lobby");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void nextStage() {
        generateWhiteSpace(2);
        displayNextStage();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("a")) {
            enterDungeon();
        } else if (command.equals("b")) {
            viewInventory();

        } else if (command.equals("c")) {
            returnMainLobby();
        } else {
            System.out.println("Selection not valid, please try again");
            nextStage();
        }
    }

    // Effects: display item using options to user
    private void displayUsingItem() {
        System.out.println("\n[Press the corresponding letter to use an item]");
        System.out.println("\ta -> Steak");
        System.out.println("\tb -> Fried Egg");
        System.out.println("\tc -> Flaming Essential Oil");
        System.out.println("\td -> Miracle Flower");
        System.out.println("\te -> Back to the main lobby");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processUsingItem() {
        displayUsingItem();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("a") && inventory.haveItem(new Steak())) {
            inventory.useItem(character,new Steak());
            //nextStage();
        } else if (command.equals("b") && inventory.haveItem(new FriedEgg())) {
            inventory.useItem(character,new FriedEgg());
            //nextStage();
        } else if (command.equals("c") && inventory.haveItem(new FlamingEssentialOil())) {
            inventory.useItem(character,new FlamingEssentialOil());
            //nextStage();
        } else if (command.equals("d") && inventory.haveItem(new MiracleFlower())) {
            System.out.println("You can only use *MiracleFlower* when your character is dying.");
            //nextStage();
        } else if (command.equals("e")) {
            returnMainLobby();
        } else {
            System.out.println("Could not find this item in the inventory or Selection is not valid, please try again");
            processUsingItem();
        }
    }

        // MODIFIES: this
        // EFFECTS: display items contained in inventory
    private void viewInventory() {


        if (inventory.getSize() == 0) {
            generateWhiteSpace(2);
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("You haven't discovered any items yet, you can earn items from the dungeon");
            System.out.println("-------------------------------------------------------------------------");
            nextStage();
        } else {
            while (inventory.getSize() > 0) {
                generateWhiteSpace(3);
                viewItem(character);
                processUsingItem();
            }
        }

        //System.out.println("\n" + gold.getName() + ": " + gold.getAmount());

    }

    //MODIFIES: this
    //EFFECTS: print number of items contained in the inventory
    public void viewItem(Character character) {
        System.out.println(character.getName() + "'s inventory: ");
        inventory.countItems();

        System.out.println("-------------------");
        if (inventory.getCountSteak() != 0) {
            System.out.println("Steak *" + inventory.getCountSteak());
        }
        if (inventory.getCountFriedEgg() != 0) {
            System.out.println("Fried Egg *" + inventory.getCountFriedEgg());
        }
        if (inventory.getCountFlamingEssentialOil() != 0) {
            System.out.println("Flaming Essential Oil *" + inventory.getCountFlamingEssentialOil());
        }
        if (inventory.getCountMiracleFlower() != 0) {
            System.out.println("Miracle Flower *" + inventory.getCountMiracleFlower());
        }
        System.out.println("-------------------");
    }

    //EFFECTS: display the background of game
    private void viewBackground() {
        generateWhiteSpace(2);
        System.out.println("Welcome to dungeon fighter!");
        System.out.println("You can earn items by searching defeated monsters' body");
        System.out.println("There is small chance to earn a secret item");
        System.out.println("good luck and have fun");
        returnMainLobby();
    }

//    REQUIRES: monster.getLife() > 0
//    MODIFIES: this
//    EFFECTS: battle with a monster in a automatic way,
//             clear the game when character achieve lvl7
    public void battle(Monster monster) {
        while (monster.getLife() > 0) {
            monster.setLife(monster.getLife() - character.getAttack());
            System.out.println("\tYou dealt " + character.getAttack() + " damage to " + monster.getName());
            if (monster.getLife() <= 0) {
                System.out.println("Congratulation! You defeated " + monster.getName());
                character.levelUp();
                generateWhiteSpace(2);
                getRandomGold();
                System.out.println(character.viewStatus());
                break;
            }
            character.setLife(character.getLife() - monster.getAttack());
            System.out.println("\t" + monster.getName() + " dealt " + monster.getAttack() + " damage to you.");
            if (character.getLife() <= 0) {
                System.out.println("Your character is dying!");
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: randomly receive 50-100 gold
    private void getRandomGold() {
        int amount = (int)(Math.random() * 50) + 50;
        //gold.incrementGold(amount);
        System.out.println("You found " + amount + " gold!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command to buy corresponding item
    private void enterMysteryShop() {
        generateWhiteSpace(2);
        displayGoods();
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("1") && inventory.haveItem(new Steak())) {
            inventory.useItem(character,new Steak());
        } else if (command.equals("2") && inventory.haveItem(new FriedEgg())) {
            inventory.useItem(character,new FriedEgg());
        } else if (command.equals("3") && inventory.haveItem(new FlamingEssentialOil())) {
            inventory.useItem(character,new FlamingEssentialOil());
        } else if (command.equals("4") && inventory.haveItem(new MiracleFlower())) {
            System.out.println("You can only use *MiracleFlower* when your character is dying.findMiracleFlower");
        } else if (command.equals("5")) {
            returnMainLobby();
        } else if (command.equals("b")) {
            returnMainLobby();
        } else {
            System.out.println("Could not find this item in the inventory or Selection is not valid, please try again");
            enterMysteryShop();
        }
    }

    // EFFECTS: display a list of goods
    private void displayGoods() {
        System.out.println("---------------Welcome to the Mystery Shop-----------------");
        System.out.println("Buy some useful items to help you defeat monsters!");
        System.out.println("1.Fried Egg         2.Steak             3.Flaming Essential Oil");
        System.out.println("  100 gold            180 gold            220 gold");
        System.out.println("4.Lottery ticket    5.Miracle Flower");
        System.out.println("  200 gold            500 gold");
        System.out.println("-----------------------------------------------------------");
        displayBuyingOptions();
    }

    // EFFECTS: display buying options
    private void displayBuyingOptions() {
        System.out.println("[Press the corresponding number to buy an item]");
        System.out.println("\t1 -> Fried Egg");
        System.out.println("\t2 -> Steak");
        System.out.println("\t3 -> Flaming Essential Oil");
        System.out.println("\t4 -> Lottery ticket");
        System.out.println("\t5 -> Miracle Flower");
        System.out.println("\tb -> Back to the main lobby");
    }

    // MODIFIES: this
    // EFFECTS: saves the game progress(character and inventory) to file
    private void saveGameProgress() {
        generateWhiteSpace(2);
        try {
            jsonCharacterWriter.open();
            jsonInventoryWriter.open();
            jsonCharacterWriter.writeCharacter(character);
            jsonInventoryWriter.writeInventory(inventory);
            jsonCharacterWriter.close();
            jsonInventoryWriter.close();

            System.out.println("Saved " + character.getName() + "'s status to " + JSON_CHARACTER_STORE);
            System.out.println("Saved inventory to " + JSON_INVENTORY_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
        System.out.println("Game progress has been saved!");
    }

    // MODIFIES: this
    // EFFECTS: loads the game progress(character and inventory) from file
    private void loadGameProgress() {
        generateWhiteSpace(2);
        try {
            character = jsonCharacterReader.readCharacter();
            System.out.println("Loaded character's status from " + JSON_CHARACTER_STORE);
            inventory = jsonInventoryReader.readInventory();
            System.out.println("Loaded inventory from " + JSON_INVENTORY_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        }
        System.out.println("Game progress has been loaded!");
    }












}