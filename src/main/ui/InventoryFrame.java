package ui;

import inventory.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//represents an inventory frame

public class InventoryFrame extends JFrame implements ActionListener {
    private ImageIcon steakImage;
    private ImageIcon friedEggImage;
    private ImageIcon flamingEssentialOilImage;
    private ImageIcon miracleFlowerImage;
    // all item images are retrieved from GenshinImpact

    private JRadioButton steakButton;
    private JRadioButton friedEggButton;
    private JRadioButton flamingEssentialOilButton;
    private JRadioButton miracleFlowerButton;
    private JButton confirmButton;
    private JButton backButton;
    private ButtonGroup group;

    private JPanel imagePanel;
    private JPanel buttonPanel;
    private JPanel descriptionPanel;//panel contains both effectDescPanel and backGroundDescPanel
    private JPanel effectDescPanel;//panel displaying the effect of item
    private JPanel backGroundDescPanel;//panel displaying the background of item
    private JTextArea effectsTextArea;
    private JTextArea backgroundDescTextArea;

    private static final String CONFIRM_STRING = "confirm";
    private static final String BACK_STRING = "back";


    // create an inventory frame with confirm button
    public InventoryFrame() {
        super("Inventory Frame");
        buttonPanel = new JPanel();
        imagePanel = new JPanel();
        descriptionPanel = new JPanel(new GridBagLayout());//create all 3 panels
        descriptionPanel.setBounds(0,0,500,600);
        descriptionPanel.setBackground(Color.GRAY);

        effectDescPanel = new JPanel(new BorderLayout());//create 2 panels hold textArea
        backGroundDescPanel = new JPanel(new BorderLayout());
        effectsTextArea = new JTextArea(2,15);
        backgroundDescTextArea = new JTextArea(10,15);

        setEffectsTextAreaStyle(); //define styles
        setDescriptionTextAreaStyle();
        setGridBagConstraints();

        //set main frame's size
        setPreferredSize(new Dimension(1000,600));

        loadItemAndImagesButtons();
        setItemImage();

        createSetConfirmBackButtons();

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        add(descriptionPanel,BorderLayout.EAST);
        add(imagePanel);
        add(buttonPanel,BorderLayout.PAGE_END);//add all 3 panels

        setVisible(true);

    }

    // EFFECTS: set grid bag constraints
    private void setGridBagConstraints() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 100;
        gc.weighty = 0;
        gc.gridx = 0;
        gc.gridy = 0;
        descriptionPanel.add(effectsTextArea,gc);
        gc.weightx = 100;
        gc.weighty = 0;
        gc.gridx = 0;
        gc.gridy = 1;
        descriptionPanel.add(backgroundDescTextArea,gc);
    }

    //MODIFIES: this
    //EFFECTS: set the style of textarea
    private void setEffectsTextAreaStyle() {
        effectsTextArea.setEditable(false);
        effectsTextArea.setBackground(Color.GRAY);
        effectsTextArea.setForeground(Color.orange);
        effectsTextArea.setLineWrap(true);
        effectsTextArea.setWrapStyleWord(true);
        effectsTextArea.setFont(new Font("Comic Sans",Font.BOLD,20));
    }

    //MODIFIES: this
    //EFFECTS: set the style of textarea
    private void setDescriptionTextAreaStyle() {
        backgroundDescTextArea.setEditable(false);
        backgroundDescTextArea.setBackground(Color.GRAY);
        backgroundDescTextArea.setForeground(new Color(179, 178, 178));
        backgroundDescTextArea.setLineWrap(true);
        backgroundDescTextArea.setWrapStyleWord(true);
        backgroundDescTextArea.setFont(new Font("Comic Sans",Font.BOLD,20));
    }

    // MODIFIES: this
    // EFFECTS: create confirm and back buttons
    private void createSetConfirmBackButtons() {
        confirmButton = new JButton("Confirm");
        confirmButton.setActionCommand(CONFIRM_STRING);
        confirmButton.addActionListener(this);
        //confirmButtonAddListener(confirmButton);
        backButton = new JButton("Back");
        backButton.setActionCommand(BACK_STRING);
        backButton.addActionListener(this);

        confirmButton.setBounds(100, 100, 100, 50);
        confirmButton.setSize(100,100);
        backButton.setBounds(300, 300, 100, 50);
        confirmButton.setSize(100,100);
    }

    // MODIFIES: GUMainLobby.inventory, this
    // EFFECTS: read user's input and perform corresponding action
    public void actionPerformed(ActionEvent e) {
        printDescriptionActionPerformed(e);
        if (CONFIRM_STRING.equals(e.getActionCommand())) {
            if (friedEggButton.isSelected()) {
                //GUMainLobby.inventory.findUseFriedEgg(GUMainLobby.character);
                GUMainLobby.inventory.useItem(GUMainLobby.character,new FriedEgg());
                processStatusChange(0,1);
            } else if (steakButton.isSelected()) {
                //GUMainLobby.inventory.findUseSteak(GUMainLobby.character);
                GUMainLobby.inventory.useItem(GUMainLobby.character,new Steak());
                processStatusChange(0,2);
            } else if (flamingEssentialOilButton.isSelected()) {
                //GUMainLobby.inventory.findUseFlamingEssentialOil(GUMainLobby.character);
                GUMainLobby.inventory.useItem(GUMainLobby.character,new FlamingEssentialOil());
                processStatusChange(2,0);
            } else if (miracleFlowerButton.isSelected()) {
                UltimateConfirmFrame frame = new UltimateConfirmFrame("You only can use Miracle Flower"
                        + "when your character is dying");
                GUMainLobby.setFrameSizeLocation(frame,1000,500,1050,500);
            }
            setItemImage();
            GUMainLobby.characterPanel.update();
        } else if (BACK_STRING.equals(e.getActionCommand())) {
            setVisible(false);
            dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: read user's input and perform corresponding action
    private void printDescriptionActionPerformed(ActionEvent e) {
        effectsTextArea.setText(null);
        backgroundDescTextArea.setText(null);
        if (e.getSource() == friedEggButton) {
            effectsTextArea.append("·Restore 1 HP\n");
            backgroundDescTextArea.append("\nAn egg fried on one side.The yolk flows out"
                    + " nicely when broken.So satisfying.");
        } else if (e.getSource() == steakButton) {
            effectsTextArea.append("·Restore 2 HP\n");
            backgroundDescTextArea.append("\nA grilled steak. Juicy, flavorsome, fragrant steak. One bite and you feel"
                    + "yourself come alive.");
        } else if (e.getSource() == flamingEssentialOilButton) {
            effectsTextArea.append("\n·Increase character's attack by 2\n");
            backgroundDescTextArea.append("Grants greater affinity for Pyro, boosting attack damag. "
                    + "It is made of materials that gestate Pyro, which makes the user"
                    + " more fired-up and passionate.");
        } else if (e.getSource() == miracleFlowerButton) {
            effectsTextArea.append("\n·Revives your character and restore 15 HP\n");
            backgroundDescTextArea.append("An extremely ancient flower that was said to be commonly seen in Egypt."
                    + "It transforms the memories of the land into its fragrance during florescence.");
        }
        repaint();
    }

    public void addActionListenerToButtons() {
        friedEggButton.addActionListener(this);
        steakButton.addActionListener(this);
        flamingEssentialOilButton.addActionListener(this);
        miracleFlowerButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set the owned item's image
    private void setItemImage() {
        updateAmountItem();

        if (GUMainLobby.inventory.contains(new Steak())) {
            setSteakImage();
        }
        if (GUMainLobby.inventory.contains(new FriedEgg())) {
            setFriedEggImage();
        }
        if (GUMainLobby.inventory.contains(new FlamingEssentialOil())) {
            setFlamingEssentialOilImage();
        }
        if (GUMainLobby.inventory.contains(new MiracleFlower())) {
            setMiracleFlowerImage();
        }
        if (GUMainLobby.inventory.isEmpty()) {
            JTextArea textArea = new JTextArea(20,20);
            textArea.setEditable(false);
            textArea.append("Your inventory is currently empty");

            add(textArea);
        }

    }

    // EFFECTS: create a new frame displaying status change
    private void processStatusChange(int attack, int life) {
        JFrame statusChangeFrame = new StatusChangeFrame(attack,life);
        GUMainLobby.setFrameSizeLocation(statusChangeFrame,1000,1000,1080,680);
    }

    // MODIFIES: this
    // EFFECTS: load all item images and add buttons
    private void loadItemAndImagesButtons() {
        String sep = System.getProperty("file.separator");
        steakImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "Steak.png");
        steakButton = new JRadioButton(steakImage);

        setInitialButtonProperty(steakButton);

        friedEggImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "FriedEgg.png");
        friedEggButton = new JRadioButton(friedEggImage);
        setInitialButtonProperty(friedEggButton);

        flamingEssentialOilImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "FlamingEssentialOil.png");
        flamingEssentialOilButton = new JRadioButton(flamingEssentialOilImage);
        setInitialButtonProperty(flamingEssentialOilButton);

        miracleFlowerImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "MiracleFlower.png");
        miracleFlowerButton = new JRadioButton(miracleFlowerImage);
        setInitialButtonProperty(miracleFlowerButton);

        group = new ButtonGroup();
        group.add(steakButton);
        group.add(friedEggButton);
        group.add(flamingEssentialOilButton);
        group.add(miracleFlowerButton);
        addActionListenerToButtons();//add action lisners to image buttons
    }


    // EFFECTS: Set item's number as text to corresponding position
    private void setInitialButtonProperty(JRadioButton itemButton) {
        itemButton.setText("1");
        itemButton.setHorizontalTextPosition(JButton.CENTER);
        itemButton.setVerticalTextPosition(JButton.BOTTOM);
    }

    private void setSteakImage() {
        imagePanel.add(steakButton);
    }

    private void setFriedEggImage() {
        imagePanel.add(friedEggButton);
    }

    private void setFlamingEssentialOilImage() {
        imagePanel.add(flamingEssentialOilButton);
    }

    private void setMiracleFlowerImage() {
        imagePanel.add(miracleFlowerButton);
    }

    // MODIFIES: this, GUMainLobby.inventory
    // EFFECTS: update the amount of all items after using an item or open inventory
    private void updateAmountItem() {
        loadItemAndImagesButtons();
        GUMainLobby.inventory.countItems();
        removeNonExistItem();

        if (GUMainLobby.inventory.getCountSteak() > 0) {
            steakButton.setText("" + GUMainLobby.inventory.getCountSteak());
        }
        if (GUMainLobby.inventory.getCountFriedEgg() > 0) {
            friedEggButton.setText("" + GUMainLobby.inventory.getCountFriedEgg());
        }
        if (GUMainLobby.inventory.getCountFlamingEssentialOil() > 0) {
            flamingEssentialOilButton.setText("" + GUMainLobby.inventory.getCountFlamingEssentialOil());
        }
        if (GUMainLobby.inventory.getCountMiracleFlower() > 0) {
            miracleFlowerButton.setText("" + GUMainLobby.inventory.getCountMiracleFlower());
        }

    }

    // MODIFIES: this
    // EFFECTS: if inventory run out of an item, remove that image/button
    private void removeNonExistItem() {
        imagePanel.removeAll();
        imagePanel.updateUI();
        repaint();
    }

}
