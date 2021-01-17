package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusChangeFrame extends JFrame implements ActionListener {
    private static final String LEVEL_UP_STRING = "levelUp";
    private static final String BUFF_GAINED_STRING = "buffGained";

    // open a status change frame after a battle
    public StatusChangeFrame() {
        super("Status change window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //GUMainLobby.gold.incrementGold((int)(Math.random() * 100) + 50);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.setActionCommand(LEVEL_UP_STRING);
        button.addActionListener(this);

        JTextArea textArea = new JTextArea(10,35);
        textArea.setEditable(false);
        textArea.append("\tCongratulation! You defeated the monster!\n"
                + "\n\tYour character's status is increased\n"
                + "\tlevel:      " + (GUMainLobby.character.getLevel() - 1) + "--->"
                + (GUMainLobby.character.getLevel()) + "\n"
                + "\tattack:    " + (GUMainLobby.character.getAttack() - 1) + "--->"
                + (GUMainLobby.character.getAttack()) + "\n"
                + "\tlife:         " + (GUMainLobby.character.getLife() - 1) + "--->"
                + (GUMainLobby.character.getLife()) + "\n");

        add(textArea);

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer
    }

    // also create status change frame, but this is used for receiving buff, or status change without leveling up
    public StatusChangeFrame(int attack, int life) {
        super("Status change window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.setActionCommand(BUFF_GAINED_STRING);
        button.addActionListener(this);

        JTextArea textArea = new JTextArea(10,35);
        textArea.setEditable(false);
        textArea.append("\tYour character's status is increased\n"
                + "\n\tlevel:      " + (GUMainLobby.character.getLevel()) + "--->"
                + (GUMainLobby.character.getLevel()) + "\n"
                + "\tattack:    " + (GUMainLobby.character.getAttack() - attack) + "--->"
                + (GUMainLobby.character.getAttack()) + "\n"
                + "\tlife:         " + (GUMainLobby.character.getLife() - life) + "--->"
                + (GUMainLobby.character.getLife()) + "\n");

        add(textArea);

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer
    }

    // click confirm after level up, a choose trophies frame will display
    // click confirm after status change without level up, this frame will be closed;
    public void actionPerformed(ActionEvent e) {
        if (LEVEL_UP_STRING.equals(e.getActionCommand())) {
            setVisible(false);
            dispose();
            processChooseTrophies();
        } else {
            setVisible(false);
            dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command, create a choosing trophy frame
    private void processChooseTrophies() {
        JFrame trophyFrame = new TrophyFrame();
        trophyFrame.setSize(new Dimension(1000, 1000));
        trophyFrame.setLocation(1080,750);

        trophyFrame.pack();
        trophyFrame.setVisible(true);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "StatusChangeFrame";
    }

}
