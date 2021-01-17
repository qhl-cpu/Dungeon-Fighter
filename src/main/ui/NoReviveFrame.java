package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represent a frame displaying cannot revive
public class NoReviveFrame extends JFrame implements ActionListener {

    // create a cannot revive frame
    public NoReviveFrame() {
        super("No revive window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.addActionListener(this);

        displayText();
//        GUMainLobby.inventory.useMiracleFlower(GUMainLobby.character);
//        GUMainLobby.cp.update();

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer
    }


    // EFFECTS: display revive succeed text
    public void displayText() {
        JTextArea textArea = new JTextArea(20,20);
        textArea.setEditable(false);
        textArea.append("Couldn't find MiracleFlower in your inventory, your character has fall down."
                        + "\nThanks for playing, better luck next time!");
        add(textArea);
    }

    //close game after click confirm
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "No Revive";
    }

}
