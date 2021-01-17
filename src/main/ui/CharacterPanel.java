package ui;

import model.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

// Represents the panel in which the Character's status is displayed.
public class CharacterPanel extends JPanel implements Observer {
    private static final String LEVEL_TXT = "lvl: ";
    private static final String ATTACK_TXT = "attack: ";
    private static final String LIFE_TXT = "life: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;

    private JLabel nameLbl;
    private JLabel levelLbl;
    private JLabel attackLbl;
    private JLabel lifeLbl;

    private ImageIcon characterPanelBackground;

    //create character frame and status labels
    CharacterPanel() {
        setBackground(Color.orange);
        setLayout(new FlowLayout());
        initializeBackgroundImage();

        nameLbl = new JLabel(GUMainLobby.character.getName() + ": ");
        nameLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        levelLbl = new JLabel(LEVEL_TXT + GUMainLobby.character.getLevel());
        levelLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        attackLbl = new JLabel(ATTACK_TXT + GUMainLobby.character.getAttack());
        attackLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        lifeLbl = new JLabel(LIFE_TXT + GUMainLobby.character.getLife());
        lifeLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        nameLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        levelLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        attackLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        lifeLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));

        add(nameLbl);
        add(levelLbl);
        add(attackLbl);
        add(Box.createHorizontalStrut(10));

        add(lifeLbl);

    }


    //TODO: try to decrese coupling and delete this method

    // Updates the character panel
    // modifies: this
    // effects:  updates status of character (attack, life, level)
    public void update() {
        nameLbl.setText(GUMainLobby.character.getName() + ": ");
        levelLbl.setText(LEVEL_TXT + GUMainLobby.character.getLevel());
        attackLbl.setText(ATTACK_TXT + GUMainLobby.character.getAttack());
        lifeLbl.setText(LIFE_TXT + GUMainLobby.character.getLife());

        if (GUMainLobby.character.getLife() < 0) {
            lifeLbl.setText(LIFE_TXT + 0);
        }
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Character) {
            Character character = (Character) o;

            nameLbl.setText(character.getName() + ": ");
            levelLbl.setText(LEVEL_TXT + character.getLevel());
            attackLbl.setText(ATTACK_TXT + character.getAttack());
            lifeLbl.setText(LIFE_TXT + character.getLife());

            if (character.getLife() < 0) {
                lifeLbl.setText(LIFE_TXT + 0);
            }
            repaint();
        }
    }

    // load panel's background
    public void initializeBackgroundImage() {
        String sep = System.getProperty("file.separator");
        characterPanelBackground = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "CharacterPanelBackground.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(characterPanelBackground.getImage(), 0, 0, null);
    }

}
