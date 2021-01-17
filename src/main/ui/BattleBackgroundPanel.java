package ui;

import javax.swing.*;
import java.awt.*;

public class BattleBackgroundPanel extends JPanel {
    private Image image;
    private ImageIcon battleBackgroundImage;

    // Constructs a game panel
    // Effects: sets size and background colour of panel
    public BattleBackgroundPanel() {
        setPreferredSize(new Dimension(1000,700));

        initializeBackgroundImage();

        //setBackground(Color.orange);
    }

    public void initializeBackgroundImage() {
        String sep = System.getProperty("file.separator");
        battleBackgroundImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "BattleBackground.png");
    }

    public void setDungeonBackground(Image image) {
        this.image = image;
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(battleBackgroundImage.getImage(), 0, 0, null);
    }
}
