package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represent a frame of character achieving max level
public class MaxLevelFrame extends JFrame implements ActionListener {
    String label;


    //create a max level frame and confirm button
    public MaxLevelFrame() {
        super("maxLevelFrame");
        label = "maxLevelFrame";
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.addActionListener(this);

        displayText();

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
        textArea.append("Congratulation! You achieved "
                + "the max level and cleared the dungeon");
        add(textArea);
    }

    //close game when click confirm
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
        System.exit(0);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return label;
    }

}
