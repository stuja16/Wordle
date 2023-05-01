package edu.lawrence.wordle;

import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class WordRow {
    private final static int letters = 5;
    private ArrayList<Cell> cells;
    private static String secretWord;
    private int cursor;
    private static Font font = new Font("Arial", 25);
    
    public WordRow(GamePane pane, int row) {
        cells = new ArrayList<Cell>();
        cursor = 0;
        for(int i = 0; i < letters; i++) {
            Cell c = new Cell(row, i);
            cells.add(c);
            Shape[] s = c.getShapes();
            pane.getChildren().add(s[0]); // add rect, not empty text object yet
        }
    }
    
    public void addLetter(GamePane pane, Key k) {
        if (cursor < 5) {
            Cell c = cells.get(cursor);
            c.setLetter(pane, k);
            cursor++;
        }
    }
    
    public void deleteLetter(GamePane pane) {
        // Must be a letter entered to be deleted
        if (cursor == 0) { return; }
        
        Cell c = cells.get(cursor - 1);
        c.removeLetter(pane);
        cursor--;
    }
    
    // Check if guessed word is valid (contained in dictionary)
    public boolean isWord() {
        String word = "";
        for (Cell c: cells) {
            word = word + c.getLetter();
        }
        if (dictionary.contains(word.toLowerCase())) {
            return true;
        }
        return false;
    }
    
    public boolean checkWord(GamePane pane) {
        boolean correct = true;
        ArrayList<Integer> used = new ArrayList<Integer>();
        
        // iterates over the cells in the row
        for (int i = 0; i < 5; i++) {
            Cell c = cells.get(i);
            boolean green = false;
            boolean yellow = false;
            int yellowed = 0;
            
            // iterates over characters in the secret word
            for (int j = 0; j < 5; j++) {

                // checks if character has already been used to avoid false duplicates
                boolean copy = false;
                for (int k : used) {
                    if (j == k) {
                        copy = true;
                    }
                }
                if (copy) {
                    if (j == 4 && ! yellow) {
                        c.setColor(pane, 3);
                        correct = false;
                        break;
                    }
                    continue;
                }
                
                
                boolean match = c.getLetter().equals(secretWord.substring(j,j+1));
                if (i == j && match) {
                    c.setColor(pane, 1);
                    green = true;
                    used.add(j);
                    break;
                } else if (match) {
                    c.setColor(pane, 2);
                    yellow = true;
                    yellowed = j;
                } else if (j == 4 && !yellow) {
                    c.setColor(pane, 3);
                    correct = false;
                    break;
                }
                if (yellow && j == 4) {
                    correct = false;
                    used.add(yellowed);
                }
            }
        }
        
        return correct;
    }
    
    public void displayNotFound(GamePane pane) {
        Rectangle rect = new Rectangle(370, 55, 264, 50);
        rect.setStroke(Color.BLACK);
        Text text = new Text(rect.getX() + 10, rect.getY() + 35, "Valid Word Not Found");
        text.setFill(Color.WHITE);
        text.setFont(font);
        pane.getChildren().addAll(rect, text);
        System.out.println("Error Message Displayed");
        
        App.delay(1200, () -> resetGuess(pane, rect, text));
    }
    
    // Called from displayNotFound to finish processes after the delay
    private void resetGuess(GamePane pane, Rectangle rect, Text text) {
        pane.getChildren().removeAll(rect, text);
        int i = 5;
        while(i > 0) {
            deleteLetter(pane);
            i--;
        }
    }
    
    /* private void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }*/
    
    public void wordleFailed() {
        System.out.println("Good try! The word was \"" + secretWord + "\".");
    }
    
    public int getCursor() { return cursor; }
    
    private static Set<String> dictionary;
    
    public static void Init() {
        dictionary = new TreeSet<String>();
        try {
            Scanner in = new Scanner(new File("words.txt"));
            while(in.hasNext()) {
                String s = in.next();
                dictionary.add(s);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        setSecretWord();
    }
    
    public static void setSecretWord() {
        int index = dictionary.size();
        index = (int)(Math.random() * (index - 1) + 1);
        String word = "";
        
        Iterator<String> iter = dictionary.iterator();
        while(index > 0) {
            word = iter.next();
            index--;
        
        }
        System.out.println(word);
        
        secretWord = word.toUpperCase();
    }
}