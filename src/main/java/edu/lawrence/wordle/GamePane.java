package edu.lawrence.wordle;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {
    
    private final int rows = 6;
    private Keyboard keys;
    private WordRow[] words;
    private int cursor;
    private Statistics stats;
    private boolean statsVisible;

    public GamePane() {
        this.setStyle("-fx-background-color: white;");
        keys = new Keyboard(this);
        words = new WordRow[rows];
        WordRow.Init();
        cursor = 0;
        for (int row = 0; row < rows; row++) {
            WordRow w = new WordRow(this, row);
            words[row] = w;
        }
        stats = new Statistics();
        statsVisible = false;
        this.setOnMouseReleased(e -> clickKey(e));
        this.setOnKeyPressed(e -> clickKey(e));
    }
    
    // Responds to mouse inputs to add letters
    private void clickKey(MouseEvent e) {
        if (cursor >= 6) { return; }
        
        double lastX = e.getX();
        double lastY = e.getY();
        Key k = keys.findKey(lastX, lastY);
        clickKey(k);
    }
    
    // Responds to keyboard inputs to add letters
    private void clickKey(KeyEvent e) {
        if (cursor >= 6) { return; }

        // Get letter name
        String letter = e.getCode().getName();
        
        // Correct naming discrepancy
        if (letter.equalsIgnoreCase("BACKSPACE")) {
            letter = "Delete";
        }
        
        // Call next function
        clickKey(keys.findKey(letter));
    }
    
    // Processes valid keyboard inputs
    private void clickKey(Key k) {
        if (k == null) { return; }
        String letter = k.getLetter();
        WordRow w = words[cursor];
        
        // Logic between letter key, enter key, and delete key
        if (letter.length() == 1) {
            w.addLetter(this, k);
        } else if (letter.equals("Enter")) {
            attemptGuess(w);
        } else if (letter.equals("Delete")) {
            w.deleteLetter(this);
        }
    }
    
    private void attemptGuess(WordRow w) {
        // Checks if row is full
        if (w.getCursor() != 5) { return; }

        if (!w.isWord()) {
            System.out.println("Word not found");
            w.displayNotFound(this);
            return;
        }
        
        if (w.checkWord(this)) {
            System.out.println("Correct!");
            stats.gameWon(cursor);
            stats.displayStatistics(this);
            statsVisible = true;
            cursor = 7;
        } else {
            System.out.println("Incorrect Guess");
            cursor++;
            if (cursor == 6) {
                w.wordleFailed();
                stats.gameLost();
                stats.displayStatistics(this);
                statsVisible = true;
            }
        }
    }
    
    public boolean getStatsVisible() { return statsVisible; }
    
    public void setStatsVisible(boolean s) { statsVisible = s; }
    
    public Statistics getStatistics() { return stats; }
}
