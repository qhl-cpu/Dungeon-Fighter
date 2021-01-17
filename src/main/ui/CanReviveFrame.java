package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Create a Revive frame that allow user to choose to revive or not
public class CanReviveFrame extends JFrame implements ActionListener {
    private JButton yesButton;
    private JButton noButton;
    private String yesString = "Yes";
    private String noString = "No";

    private JPanel revivePanel;

    //create revive frame and assign buttons
    public CanReviveFrame() {
        super("Revive window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        yesButton = new JButton(yesString);
        yesButton.setActionCommand(yesString);
        yesButton.addActionListener(this);
        noButton = new JButton(noString);
        noButton.setActionCommand(noString);
        noButton.addActionListener(this);

        JLabel title = new JLabel("Do you want to use *MiracleFlower* to revive your character?");
        revivePanel = new JPanel(new GridLayout(0, 1));
        revivePanel.add(yesButton);
        revivePanel.add(noButton);

        add(title);
        add(revivePanel,BorderLayout.LINE_START);
        Toolkit.getDefaultToolkit().beep();

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(1000,1000);
    }


    // EFFECTS: read user's action
    public void actionPerformed(ActionEvent e) {
        if (yesString.equals(e.getActionCommand())) {
            setVisible(false);
            dispose();
            processReviveSucceed();
        } else {
            setVisible(false);
            dispose();
            processReviveFailed();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command, create reviveSucceed frame
    private void processReviveSucceed() {
        JFrame reviveSucceedFrame = new ReviveSucceedFrame();
        reviveSucceedFrame.setSize(new Dimension(1000, 1000));
        reviveSucceedFrame.setLocation(1050,500);

        reviveSucceedFrame.pack();
        reviveSucceedFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: processes user command, create revive failed frame
    private void processReviveFailed() {
        JFrame reviveFailedFrame = new ReviveFailedFrame();
        reviveFailedFrame.setSize(new Dimension(1000, 1000));
        reviveFailedFrame.setLocation(1050,500);

        reviveFailedFrame.pack();
        reviveFailedFrame.setVisible(true);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "CanReviveFrame";
    }
}

