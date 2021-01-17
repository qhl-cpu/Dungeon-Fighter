package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represent a frame can be used in many situation with customized textArea
public class UltimateConfirmFrame extends JFrame implements ActionListener {
    String label;

    // create a frame with String s as textArea with a confirm button
    public UltimateConfirmFrame(String s) {
        super(s);
        label = s;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.
        JButton button = new JButton("Confirm");
        button.addActionListener(this);

        displayText(s);

        //Place the button near the bottom of the window.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);

        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        contentPane.add(Box.createVerticalStrut(5)); //spacer
    }


    // EFFECTS: add a textArea to frame
    public void displayText(String s) {
        JTextArea textArea = new JTextArea(12,20);
        textArea.setEditable(false);
        textArea.append(s);
        add(textArea);
    }

    // close the frame when click confirm button
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return label;
    }

}
