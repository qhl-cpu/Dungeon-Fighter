package ui;

import model.Character;
import monsters.Monster;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

// Represents the panel in which the monster's status is displayed.
public class MonsterPanel extends JPanel implements Observer {
    private static final String ATTACK_TXT = "attack: ";
    private static final String LIFE_TXT = "life: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;

    private JLabel nameLbl;
    private JLabel attackLbl;
    private JLabel lifeLbl;

    private ImageIcon monsterPanelBackground;


    //create monster frame and status labels
    MonsterPanel() {
        setBackground(Color.GREEN);
        setLayout(new FlowLayout());
        initializeBackgroundImage();

        nameLbl = new JLabel();
        nameLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        attackLbl = new JLabel();
        attackLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        lifeLbl = new JLabel();
        lifeLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        nameLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        attackLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        lifeLbl.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));

        add(Box.createHorizontalStrut(100));
        add(nameLbl);
        add(attackLbl);
        add(Box.createHorizontalStrut(10));
        add(lifeLbl);

    }

    // load panel's background
    public void initializeBackgroundImage() {
        String sep = System.getProperty("file.separator");
        monsterPanelBackground = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "MonsterPanelBackground.png");
    }

//    // Updates the character panel
//    // modifies: this
//    // effects:  updates status of character (attack, life, level)
//    public void update() {
//        nameLbl.setText(GUMainLobby.monster.getName() + ": ");
//        attackLbl.setText(ATTACK_TXT + GUMainLobby.monster.getAttack());
//        lifeLbl.setText(LIFE_TXT + GUMainLobby.monster.getLife());
//
//        if (GUMainLobby.monster.getLife() < 0) {
//            lifeLbl.setText(LIFE_TXT + 0);
//        }
//        repaint();
//    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Monster) {
            Monster monster = (Monster) o;

            nameLbl.setText(monster.getName() + ": ");
            attackLbl.setText(ATTACK_TXT + monster.getAttack());
            lifeLbl.setText(LIFE_TXT + monster.getLife());

            if (monster.getLife() < 0) {
                lifeLbl.setText(LIFE_TXT + 0);
            }
            repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(monsterPanelBackground.getImage(), 0, 0, null);
    }
}
