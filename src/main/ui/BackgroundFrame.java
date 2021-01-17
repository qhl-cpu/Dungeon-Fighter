package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represent a frame displaying background info
public class BackgroundFrame extends JFrame implements ActionListener {

    //Create a background frame displaying background information with a confirm button.
    public BackgroundFrame() {
        super("Background");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.addActionListener(this);

        displayBackground();

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer

        centreOnScreen(this);
    }

    // MODIFIES: this
    // EFFECTS: add a text area to the background frame
    public void displayBackground() {
        JTextArea textArea = new JTextArea(20,20);
        textArea.setEditable(false);
        textArea.append("Dungeon fight is an idle game.\n" + "\n"
                + "You can level up your character and receive trophies by entering dungeon." + "\n"
                + "You can use items in inventory." + "\n"
                + "You can save and load game progress at the main lobby." + "\n"
                + "\n" + "Fight in dungeon" + "\n"
                + "You will encounter Goblin when level <= 3, Archer when level > 3 && <=5 " + "\n"
                + "and encounter Minotaur when level >5" + "\n"
                + "The game will be cleared after reaching level 10" + "\n"
                + "\n" + "Search monster body" + "\n"
                + "There is equivalent chances of getting four random food for buffing" + "\n"
                + "There is small chance of receiving a secret item which revives your character, " + "\n"
                + "which now the chance has changed to 100%" + "\n");

        add(textArea);

    }

    // modifies: this
    // effects:  location of frame is set to the center of screen
    public void centreOnScreen(Frame frame) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);

    }

    // EFFECTS: close the frame when click the confirm button
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "Background";
    }


}
