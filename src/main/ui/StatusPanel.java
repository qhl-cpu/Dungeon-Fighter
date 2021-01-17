package ui;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private static final String LEVEL_TXT = "lvl: ";
    private static final String ATTACK_TXT = "attack: ";
    private static final String LIFE_TXT = "life: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;

    private JLabel nameLbl;
    private JLabel levelLbl;
    private JLabel attackLbl;
    private JLabel lifeLbl;

    //create character frame and status labels
    StatusPanel() {
        setBackground(Color.GRAY);
        setLayout(new FlowLayout());
    }

//
//    // Updates the character panel
//    // modifies: this
//    // effects:  updates status of character (attack, life, level)
//    public void update() {
//        nameLbl.setText(GUMainLobby.character.getName() + ": ");
//        levelLbl.setText(LEVEL_TXT + GUMainLobby.character.getLevel());
//        attackLbl.setText(ATTACK_TXT + GUMainLobby.character.getAttack());
//        lifeLbl.setText(LIFE_TXT + GUMainLobby.character.getLife());
//
//        repaint();
//    }

}
