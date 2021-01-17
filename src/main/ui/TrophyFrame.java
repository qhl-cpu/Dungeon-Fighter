package ui;

import inventory.FlamingEssentialOil;
import inventory.FriedEgg;
import inventory.MiracleFlower;
import inventory.Steak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// represent a frame that allow user to choose trophies.
public class TrophyFrame extends JFrame {

    private String gainAttackString = "Character's atk + 1 permanently";
    private String gainLifeString = "Recover 3 health";
    private String searchMonsterString = "Search monster's body";
    private JRadioButton gainAttackButton;
    private JRadioButton gainLifeButton;
    private JRadioButton searchMonsterButton;
    private ButtonGroup group;
    private JPanel radioPanel;


    //create a choose trophy frame
    public TrophyFrame() {
        JButton confirmButton = new JButton("Confirm");
        JLabel title = new JLabel("Please select your trophies");

        createButtons();
        putButtonsInPanel();

        add(title);
        add(radioPanel, BorderLayout.LINE_START);
        add(confirmButton);

        confirmButtonAddListener(confirmButton);

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(1000,1000);
    }

    // read user's action and gain trophy according to selected option
    private void confirmButtonAddListener(JButton confirmButton) {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gainAttackButton.isSelected()) {
                    GUMainLobby.character.incrementAttack(1);
                    setVisible(false);
                    createStatusChangeFrame(1,0);
                    //1080x750 is the perfect location for statusChangeFrame after battle
                } else if (gainLifeButton.isSelected()) {
                    GUMainLobby.character.recoverHealth(3);
                    setVisible(false);
                    createStatusChangeFrame(0,3);
                } else {
                    receiveRandomItem();
                }

                GUMainLobby.characterPanel.update();

                setVisible(false);
                dispose();
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: Put the radio buttons in a column in a panel.
    private void putButtonsInPanel() {
        radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(gainAttackButton);
        radioPanel.add(gainLifeButton);
        radioPanel.add(searchMonsterButton);
    }

    // MODIFIES: this
    // EFFECTS: create all option buttons
    private void createButtons() {
        gainAttackButton = new JRadioButton(gainAttackString);

        gainAttackButton.setMnemonic(KeyEvent.VK_A);
        gainAttackButton.setActionCommand(gainAttackString);
        gainAttackButton.setSelected(true);

        gainLifeButton = new JRadioButton(gainLifeString);
        gainLifeButton.setMnemonic(KeyEvent.VK_L);
        gainLifeButton.setActionCommand(gainLifeString);

        searchMonsterButton = new JRadioButton(searchMonsterString);
        searchMonsterButton.setMnemonic(KeyEvent.VK_S);
        searchMonsterButton.setActionCommand(searchMonsterString);

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(gainAttackButton);
        group.add(gainLifeButton);
        group.add(searchMonsterButton);
    }


    //MODIFIES: GUMainLobby.inventory
    //EFFECTS: receive a random item from defeated monster
    private void receiveRandomItem() {
        int a = (int)(Math.random() * 4) + 1;//int a = (int)(Math.random() * 4) + 1;
        int b = (int)(Math.random() * 1) + 1;
//        GUMainLobby.inventory.addItem(new FriedEgg());
//        GUMainLobby.inventory.addItem(new FlamingEssentialOil());
//        GUMainLobby.inventory.addItem(new Steak());//these 3 lines are for testing
        if (b == 1) {
            receiveSpecialItem();
        }
        if (a == 1) {
            GUMainLobby.inventory.addItem(new FriedEgg());
            createGetItemFrame("You find a fried egg!");

        } else if (a == 2) {
            GUMainLobby.inventory.addItem(new FlamingEssentialOil());
            createGetItemFrame("You find a Flaming Essential Oil!");

        } else if (a == 3) {
            GUMainLobby.inventory.addItem(new Steak());
            createGetItemFrame("You find a Steak!");

        } else if (a == 4 && b != 1) {
            createGetItemFrame("You find nothing, better luck next time.");
        }
    }

    // MODIFIES: GUMainLobby.inventory
    // EFFECTS: add a miracle flower to GUMainLobby.inventory
    private void receiveSpecialItem() {
        GUMainLobby.inventory.addItem(new MiracleFlower());
        createGetItemFrame("Bravo! You find a secret item: Miracle Flower");
    }

    // MODIFIES: this
    // EFFECTS: open a new window that display which random items you receive
    private void createGetItemFrame(String s) {
        JFrame frame = new RandomItemFrame(s);
        frame.setSize(new Dimension(400, 200));
        frame.setLocation(1080,750);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: open a new window that display status change (after selecting gain attack/life options)
    private void createStatusChangeFrame(int attack, int life) {
        JFrame frame = new StatusChangeFrame(attack,life);
        frame.setSize(new Dimension(400, 200));
        frame.setLocation(1080,750);
        frame.setVisible(true);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "TrophyPanel";
    }



}
