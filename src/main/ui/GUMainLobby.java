package ui;

import inventory.*;
import model.Character;
import monsters.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/*
 * Represents the main lobby in which the Dungeon Fighter
 * game is played
 */
public class GUMainLobby extends JFrame implements ActionListener {
    protected static Character character;
    protected static Inventory inventory;
    protected static Monster monster;
    //protected static Gold gold;

    private static final String JSON_CHARACTER_STORE = "./data/character.json";
    private static final String JSON_INVENTORY_STORE = "./data/inventory.json";
    private JsonWriter jsonCharacterWriter;
    private JsonWriter jsonInventoryWriter;
    private JsonReader jsonCharacterReader;
    private JsonReader jsonInventoryReader;


    //Originally from CharacterNamePanel
    protected static final String textFieldString = "CharacterName";
    protected JLabel actionLabel;
    private String characterName;
    private JPanel characterNamePanel;
    public static CharacterPanel characterPanel;
    public static MonsterPanel monsterPanel;
    private GamePanel gamePanel;
    private StatusPanel statusPanel;
    public static BattleLogFrame battleLogFrame;

    private BattleBackgroundPanel battleBackgroundPanel;

    private JTextField textField;
    private JLabel textFieldLabel;
    private JPanel characterNameControlsPane;

    private JLabel monsterImageAsLabel;


    private JLabel battleBackgroundAsLabel;
    private JLabel knightAsLabel;
    private JLabel lobbyDoorAsLabel;
    private JPanel lobbyButtonsPanel;
    private JPanel battleButtonsPanel;

    private ImageIcon characterImage1;
    private ImageIcon characterImage2;
    private ImageIcon goblinImage;
    private ImageIcon archerImage;
    private ImageIcon minotaurImage;
    private ImageIcon lobbyImage;

    private ImageIcon battleBackgroundImage;
    private ImageIcon knightImage;
    private ImageIcon lobbyDoorImage;


    private ArrayList<JLabel> allLabels = new ArrayList<>();

    private static final int GAME_SPEED = 3000;
    private static final String GOBLIN_NAME = "Goblin";
    private static final String ARCHER_NAME = "Archer";
    private static final String MINOTAUR_NAME = "Minotaur";

    // Constructs main window
    // effects: sets up window in which Dungeon Fighter game will be played
    public GUMainLobby() throws FileNotFoundException {
        super("Dungeon Fighter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1050,850));
        monsterImageAsLabel = new JLabel();
        createReaderWriter();

        startWithName();

        battleBackgroundPanel = null;
        gamePanel = new GamePanel();
        add(gamePanel);
        loadImages();

        setIconImage(knightImage.getImage());// get an image for imageIcon

        pack();
        centreOnScreen(this);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: create JsonReader/Writers
    private void createReaderWriter() {
        jsonCharacterWriter = new JsonWriter(JSON_CHARACTER_STORE);
        jsonInventoryWriter = new JsonWriter(JSON_INVENTORY_STORE);
        jsonCharacterReader = new JsonReader(JSON_CHARACTER_STORE);
        jsonInventoryReader = new JsonReader(JSON_INVENTORY_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input, given a name to character
    private void startWithName() {
        characterNamePanel = createCharacterNamePanel();
        add(characterNamePanel,BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: create a character name panel
    private JPanel createCharacterNamePanel() {
        JPanel characterNamePanel = new JPanel();
        //JPanel characterNamePanel = new CharacterNamePanel();

        characterNamePanel.setLayout(new BorderLayout());

        //Create a regular text field.
        textField = new JTextField(10);
        textField.setActionCommand(textFieldString);
        textField.addActionListener(this);

        //Create some labels for the fields.
        textFieldLabel = new JLabel(textFieldString);
        textFieldLabel.setLabelFor(textField);

        //Create a label to put messages during an action event.
        actionLabel = new JLabel("Type your character's name and press Enter.");
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        //Lay out the text controls and the labels.
        characterNameControlsPane = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        characterNameControlsPane.setLayout(gridBag);

        addLabelTextRows(textFieldLabel, textField, characterNameControlsPane);

        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        characterNameControlsPane.add(actionLabel, c);
        characterNameControlsPane.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Please enter your character's name"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        //Put everything together.
        add(characterNameControlsPane, BorderLayout.PAGE_START);

        return characterNamePanel;
    }

    // Modifies: GridBagConstraints c
    // EFFECTS: give labels text rows
    private void addLabelTextRows(JLabel labels,JTextField textFields,Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;

        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
        c.fill = GridBagConstraints.NONE;      //reset to default
        c.weightx = 0.0;                       //reset to default
        container.add(labels, c);

        c.gridwidth = GridBagConstraints.REMAINDER;     //end row
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        container.add(textFields, c);

    }

    // MODIFIES: this
    // EFFECTS: read user's action
    public void actionPerformed(ActionEvent e) {
        if (textFieldString.equals(e.getActionCommand())) {
            JTextField source = (JTextField)e.getSource();
            setCharacterName(source.getText());
            remove(characterNameControlsPane);


            init(getCharacterName());
            createCharacterPanelAndLobby();
        }
    }

    // MODIFIES: this
    // EFFECTS: create a character panel and display lobby options, add a observer to character
    private void createCharacterPanelAndLobby() {
        //create character panel
        characterPanel = new CharacterPanel();

        statusPanel = new StatusPanel();
        statusPanel.add(characterPanel);

        character.addObserver(characterPanel); // add a observer to character

        monster = new Goblin();
        monsterPanel = new MonsterPanel(); // instantiate a monster panel,
                                           // this panel will be added to status panel when enter a dungeon


        add(statusPanel,BorderLayout.NORTH);


        //create lobby option buttons
        createLobbyOptions();

        pack();
        centreOnScreen(this);
        setVisible(true);
    }

    private String getCharacterName() {
        return characterName;
    }

    private void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    // REQUIRES: name has non-negative length
    // MODIFIES: this
    // EFFECTS: initializes character and inventory and gold
    private void init(String name) {
        character = new Character(name);
        inventory = new Inventory();


        //gold = new Gold();
    }

    // this method is based on
    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    public void centreOnScreen(Frame frame) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);

    }

    // MODIFIES: this
    // EFFECTS: create all lobby buttons
    private void createLobbyOptions() {
        lobbyButtonsPanel = new JPanel();
        lobbyButtonsPanel.setLayout(new GridLayout(0,1));
        lobbyButtonsPanel.setSize(new Dimension(0,0));
        add(lobbyButtonsPanel, BorderLayout.SOUTH);

        JButton enterDungeonButton = new JButton("EnterDungeon");
        setEnterDungeonButtonFunction(enterDungeonButton);
        lobbyButtonsPanel.add(enterDungeonButton);


        JButton inventoryButton = new JButton("Inventory");
        setInventoryButtonFunction(inventoryButton);
        lobbyButtonsPanel.add(inventoryButton);

        JButton backgroundButton = new JButton("Background");
        setBackgroundButtonFunction(backgroundButton);
        lobbyButtonsPanel.add(backgroundButton);

        JButton saveProgressButton = new JButton("SaveProgress");
        setSaveProgressButtonFunction(saveProgressButton);
        lobbyButtonsPanel.add(saveProgressButton);

        JButton loadProgressButton = new JButton("LoadProgress");
        setLoadProgressButtonFunction(loadProgressButton);
        lobbyButtonsPanel.add(loadProgressButton);

    }

    // EFFECTS: enter dungeon
    private void setEnterDungeonButtonFunction(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterDungeon();
            }
        });
    }

    // EFFECTS: open inventory
    private void setInventoryButtonFunction(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inventoryFrame = new InventoryFrame();
                setFrameSizeLocation(inventoryFrame,1000,1000,780,390);

            }
        });
    }

    // EFFECTS: save game progress
    private void setSaveProgressButtonFunction(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame saveProgressFrame = new UltimateConfirmFrame("Game Progress has been saved!");
                saveGameProgress();
                setFrameSizeLocation(saveProgressFrame,1000,1000,1125,500);
            }
        });
    }

    // EFFECTS: load game progress
    private void setLoadProgressButtonFunction(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame loadProgressFrame = new UltimateConfirmFrame("Game Progress has been loaded!");
                loadGameProgress();
                setFrameSizeLocation(loadProgressFrame,1000,1000,1050,500);
            }
        });
    }

    // EFFECTS: open background window
    private void setBackgroundButtonFunction(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBackgroundWindow();
            }
        });
    }

    // EFFECTS: create a background frame with background information
    private void showBackgroundWindow() {
        JFrame frame = new BackgroundFrame();
        centreOnScreen(frame);
        frame.setLocation(frame.getX() + 250,frame.getY() + 200);

        frame.setSize(520,500);

        frame.setVisible(true);
    }

    // EFFECTS: create a battle log frame when battling with a monster
    public void showBattleLog() {
        if (battleLogFrame != null) {
            battleLogFrame.dispose();
        }
        battleLogFrame = new BattleLogFrame();
        centreOnScreen(battleLogFrame);
        battleLogFrame.setLocation(battleLogFrame.getX() - 343,battleLogFrame.getY());

        battleLogFrame.setSize(350,842);

        battleLogFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: load all game images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        characterImage1 = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Character0.JPG");
        characterImage2 = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Character1.JPG");
        goblinImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Goblin.png");
        archerImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Archer.png");
        minotaurImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Minotaur.png");
        battleBackgroundImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "BattleBackground.png");
        knightImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Knight.png");
        lobbyDoorImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Lobby.jpg");
    }

    //MODIFIES: this
    //EFFECTS: generate a knight image and add it to the battle background panel
    public void setKnightImage() {
        knightAsLabel = new JLabel(knightImage);

        battleBackgroundPanel.add(knightAsLabel);
        allLabels.add(knightAsLabel);
        knightAsLabel.setHorizontalAlignment(JLabel.CENTER);
        knightAsLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: battle with goblin when character's level <= 3,
    //          battle with archer when character's level > 3 <= 5
    //          battle with minotaur when character's level > 5
    //          move on to the next stage if battle is over, character's life > 0 and level < 10.
    private void enterDungeon() {
        remove(gamePanel);
        remove(lobbyButtonsPanel);// remove lobby panels and create bettle panels
        createDungeonButtons();

        showBattleLog();

        statusPanel.add(monsterPanel);//add a monster panel

        if (battleBackgroundPanel != null) { // regenerate battle background to prevent duplication
            remove(battleBackgroundPanel);
            repaint();
        }
        // change the background to battle background
        battleBackgroundPanel = new BattleBackgroundPanel();
        add(battleBackgroundPanel);
        battleBackgroundPanel.setVisible(true);

        setKnightImage();


        if (character.getLevel() <= 3) {
            encounterMonster(GOBLIN_NAME);
        } else if (character.getLevel() > 3 && character.getLevel() <= 5) {
            encounterMonster(ARCHER_NAME);
        } else if (character.getLevel() > 5) {
            encounterMonster(MINOTAUR_NAME);
        }
        if (character.getLife() > 0 && character.getLevel() < 10) {
            processStatusChange();
        }
    }

    // EFFECTS: create dungeon buttons
    private void createDungeonButtons() {
        if (battleButtonsPanel != null) { // this prevent battle buttons still appear when go back to lobby
            remove(battleButtonsPanel);
        }
        battleButtonsPanel = new JPanel();
        battleButtonsPanel.setLayout(new GridLayout(0,1));
        add(battleButtonsPanel, BorderLayout.SOUTH);

        JButton enterDungeonButton = new JButton("Enter the Next Dungeon");
        setEnterDungeonButtonFunction(enterDungeonButton);
        battleButtonsPanel.add(enterDungeonButton);


        JButton inventoryButton = new JButton("Inventory");
        setInventoryButtonFunction(inventoryButton);
        battleButtonsPanel.add(inventoryButton);

        JButton backToMainLobby = new JButton("Back to the Main Lobby");
        setBackToLobbyFunction(backToMainLobby);
        battleButtonsPanel.add(backToMainLobby);

        setVisible(true);

    }

    // when click on backToLobby button, remove the battleBackgroundPanel, battle buttons,
    // and the status panel(status panel will be regenerated). Generate lobby functions.
    private void setBackToLobbyFunction(JButton backToMainLobby) {
        backToMainLobby.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(battleBackgroundPanel);
                remove(battleButtonsPanel);
                remove(statusPanel);
                gamePanel = new GamePanel();//regenerate game panel
                add(gamePanel);
                createCharacterPanelAndLobby();//generate lobby image/buttons/status panel
                repaint();
            }
        });
    }


    // EFFECTS: create a new frame displaying status change
    private void processStatusChange() {
        JFrame statusChangeFrame = new StatusChangeFrame();
        setFrameSizeLocation(statusChangeFrame,1000,1000,1080,750);
    }

    // MODIFIES: this
    // EFFECTS: battle with assigned monster and set the image
    private void encounterMonster(String monsterName) {
        switch (monsterName) {
            case "Goblin":
                monster = new Goblin();
                monsterImageAsLabel.setIcon(goblinImage);
                break;
            case "Archer":
                monster = new Archer();
                monsterImageAsLabel.setIcon(archerImage);
                break;
            case "Minotaur":
                monster = new Minotaur();
                monsterImageAsLabel.setIcon(minotaurImage);
                break;
        }
        monster.addObserver(monsterPanel);
        character.addObserver(battleLogFrame);
        monster.addObserver(battleLogFrame);

        battleBackgroundPanel.add(monsterImageAsLabel);
        character.battle(monster);
        repaint();
        checkLevelAndRevive(monster);
    }

    // MODIFIES: this
    // EFFECTS: if the label is already exist, remove it
    private void allLabelsContain(JLabel label) {
        if (allLabels.contains(label)) {
            battleBackgroundPanel.remove(label);
            allLabels.remove(label);
            battleBackgroundPanel.repaint();
        }
    }


    // MODIFIES: this
    // EFFECTS: if character reach level 10 when battling with a monster, display max level window
    //          If character's life <= 0, display revive options.
    private void checkLevelAndRevive(Monster monster) {
        if (character.getLife() <= 0) {
            if (inventory.haveItem(new MiracleFlower())) {
                processReviveOption();
                character.battle(monster);
            } else {
                processNoReviveOption();
            }
        }
        if (character.getLevel() >= 10) {
            JFrame maxLevelFrame = new MaxLevelFrame();
            setFrameSizeLocation(maxLevelFrame,1000,1000,1080,500);

        }
    }


    // EFFECTS: processes Revive Option
    private void processReviveOption() {
        JFrame canReviveFrame = new CanReviveFrame();
        setFrameSizeLocation(canReviveFrame,1000,1000,1080,500);
    }


    // EFFECTS: processes cannot revive Option
    private void processNoReviveOption() {
        JFrame noReviveFrame = new NoReviveFrame();
        setFrameSizeLocation(noReviveFrame,1000,1000,1080,500);
    }

    // MODIFIES: this
    // EFFECTS: saves the game progress(character and inventory) to file
    private void saveGameProgress() {
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
        try {
            character = jsonCharacterReader.readCharacter();
            System.out.println("Loaded character's status from " + JSON_CHARACTER_STORE);
            inventory = jsonInventoryReader.readInventory();
            System.out.println("Loaded inventory from " + JSON_INVENTORY_STORE);
            characterPanel.update();
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        }
        System.out.println("Game progress has been loaded!");
    }


    // EFFECTS: set the frame's dimension as 1000 x 1000 to location (1080,500)
    public static void setFrameSizeLocation(JFrame frame,int width, int height, int x, int y) {
        frame.setSize(new Dimension(width, height));
        frame.setLocation(x,y);

        frame.pack();
        frame.setVisible(true);
    }
}
