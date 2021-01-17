package ui;

import inventory.FriedEgg;
import inventory.Item;

import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;

public class Main {
    public static void main(String[] args) {
//        try {
//            new MainLobby();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run the game: file not found");
//        }

        try {
//            String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//            for (int i = 0; i < fonts.length; i++ ) {
//                System.out.println(fonts[i]);
//            }

            new GUMainLobby();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to run the game: file not found");
        }
    }
}

