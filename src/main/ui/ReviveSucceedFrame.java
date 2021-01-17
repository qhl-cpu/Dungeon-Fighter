package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represent a frame displaying revive succeed
public class ReviveSucceedFrame extends JFrame implements ActionListener {

    // create a revive succeed frame
    public ReviveSucceedFrame() {
        super("Revive succeed window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.addActionListener(this);

        displayText();
        GUMainLobby.inventory.useMiracleFlower(GUMainLobby.character);
        GUMainLobby.characterPanel.update();

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
        textArea.append("By the power of *MiracleFlower*， "
                + "your character has revived and life has been set to 15.");
        add(textArea);
    }

    // close the frame when click the button
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "ReviveSucceed";
    }

}
