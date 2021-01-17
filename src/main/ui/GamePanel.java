package ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Image image;
    private ImageIcon lobbyBackgroundImage;

    // Constructs a game panel
    // Effects: sets size and background colour of panel
    public GamePanel() {
        setPreferredSize(new Dimension(1000,700));

        initializeBackgroundImage();

    }

    public void initializeBackgroundImage() {
        String sep = System.getProperty("file.separator");
        lobbyBackgroundImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Lobby.jpg");
    }

//    public void setDungeonBackground(Image image) {
//        this.image = image;
//        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
//        setPreferredSize(size);
//        setMinimumSize(size);
//        setMaximumSize(size);
//        setSize(size);
//        //setLayout(null);
//    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(lobbyBackgroundImage.getImage(), 0, 0, null);
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        g.drawImage(image, 0, 0, null);
//    }

}
