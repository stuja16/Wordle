package edu.lawrence.wordle;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
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
        clickKey(keys.findKey(lastX, lastY));
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
        
        clickKey(keys.findKey(letter));
    }
    
    // Processes valid key inputs
    private void clickKey(Key k) {
        if (k == null) { return; }
        String letter = k.getLetter();
        WordRow w = words[cursor];
        
        // Separate logic for the enter and delete keys from normal letters
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
            stats.gameWon(cursor);  // Update statistics with the win
            cursor = 7;
            /*
                Known bug: cells aren't colored during "wait" function call
                Not sure why, all "setColor" runs have completed
            */
            wait(750);  // Pause so the player can see their correct guess b4 displaying stats
            stats.displayStatistics(this);
            statsVisible = true;
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

    // Method to delay the program for a specified amount of milliseconds
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public boolean getStatsVisible() { return statsVisible; }
    
    public void setStatsVisible(boolean s) { statsVisible = s; }
    
    public Statistics getStatistics() { return stats; }
}
