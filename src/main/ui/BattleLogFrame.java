package ui;

import model.Character;
import monsters.Monster;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

// represent a battle log frame displaying battle details
public class BattleLogFrame extends JFrame implements Observer {

    private JTextArea textArea;
    private Monster monster;

    //Create a battle log frame displaying battle details
    public BattleLogFrame() {
        super("Battle log");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textArea = new JTextArea(20,20);
        setTextAreaStyle();
        add(textArea);

        setUndecorated(true);
        setVisible(true);
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "Battle log";
    }

    //MODIFIES: this
    //EFFECTS: set the style of textarea
    private void setTextAreaStyle() {
        textArea.setEditable(false);
        textArea.setBackground(Color.GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Comic Sans",Font.BOLD,20));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Monster && arg != null) {
            monster = (Monster) o;
            textArea.append("You dealt " + arg.toString() + " damge to " + monster.getName() + "\n");
        } else if (o instanceof Character && arg != null) {
            textArea.append(monster.getName() + " dealt " + arg.toString() + " damge to you!\n");
        }
        repaint();
    }
}
