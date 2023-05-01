package edu.lawrence.wordle;

import javafx.scene.shape.Shape;

public class Keyboard {
    private static final String[][] KEY_VALUES = {
        {"Q", "W","E", "R", "T", "Y", "U", "I", "O", "P"},
        {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
        {"Enter", "Z", "X", "C", "V", "B", "N", "M", "Delete"},
    };
    private Key[][] keys;
    
    public Keyboard(GamePane pane) {
        keys = new Key[3][10];
        for (int row = 0; row < 3; row++) {
            int maxLength = KEY_VALUES[row].length;
            for (int i = 0; i < maxLength; i++) {
                Key k = new Key(KEY_VALUES[row][i], maxLength, row, i);
                keys[row][i] = k;
                Shape[] s = k.getShapes();
                pane.getChildren().addAll(s[0], s[1]);
            }
        }
    }
    
    public Key findKey(double x, double y) {
        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[0].length; col++) {
                Key k = keys[row][col];
                if (k != null && k.containsPoint(x, y)) {
                    return k;
                }
            }
        }
        return null;
    }
    
    public Key findKey(String s) {
        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[0].length; col++) {
                Key k = keys[row][col];
                if (k != null && k.getLetter().equalsIgnoreCase(s)) {
                    return k;
                }
            }
        }
        return null;
    }
    
}