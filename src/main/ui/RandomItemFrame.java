package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// represent a frame displaying received random item
public class RandomItemFrame extends JFrame implements ActionListener {

    // create a random item received frame
    public RandomItemFrame(String s) {
        super("Receive random item window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Close window");
        button.addActionListener(this);

        JTextArea textArea = new JTextArea(20,20);
        textArea.setEditable(false);
        textArea.append(s);
        add(textArea);

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer
    }


    // close this frame when click the button
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "RandomItemFrame";
    }
}
