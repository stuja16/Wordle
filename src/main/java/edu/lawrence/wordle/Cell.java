package edu.lawrence.wordle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Cell {
    private static final double CELL_SIZE = 60.0;
    private static final int MARGINS = 10;
    private Key key;
    private Text text;
    private Rectangle rect;
    private int row, col;
    private static Font font = new Font("Monospaced Bold", 40);
    
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        rect = new Rectangle(330+col*(MARGINS+CELL_SIZE), 50+row*(MARGINS+CELL_SIZE), CELL_SIZE, CELL_SIZE);
        rect.setStroke(Color.LIGHTGREY);
        rect.setFill(Color.WHITE);
    }
    
    public Shape[] getShapes() {
        Shape[] shapes = new Shape[2];
        shapes[0] = rect;
        shapes[1] = text;
        return shapes;
    }
    
    public void setLetter(GamePane pane, Key k) {
        pane.getChildren().remove(rect);
        
        this.key = k;
        text = new Text(rect.getX() + 18, rect.getY() + 40, key.getLetter());
        text.setFont(font);
        text.setFill(Color.BLACK);
        text.setStroke(null);
        rect.setStroke(Color.BLACK);
        
        pane.getChildren().addAll(rect, text);
    }
    
    public void setColor(GamePane pane, int value) {
        pane.getChildren().removeAll(rect, text);
        
        if (value == 1) {
            rect.setFill(Color.GREEN);
            key.setColor(pane, Color.GREEN);
        } else if (value == 2) {
            rect.setFill(Color.GOLD);
            key.setColor(pane, Color.GOLD);
        } else {
            rect.setFill(Color.DARKSLATEGREY);
            key.setColor(pane, Color.DARKSLATEGREY);
        }
        rect.setStroke(null);
        text.setFill(Color.WHITE);
        pane.getChildren().addAll(rect, text);
    }
    
    public void removeLetter(GamePane pane) {
        pane.getChildren().removeAll(rect, text);
        this.key = null;
        text = null;
        
        rect.setStroke(Color.LIGHTGREY);
        pane.getChildren().add(rect);
    }
    
    public String getLetter() { return key.getLetter(); }
    public int getRow() { return row; }
    public int getCol() { return col; }
}
