package edu.lawrence.wordle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Key {
    private static final double KEY_HEIGHT = 55.0;
    private static final double KEY_WIDTH = 40.0;
    private static final double ARC = 10.0;
    private String letter;
    private Text text;
    private Rectangle rect;
    final private static Font font = new Font("Arial", 12);

    public Key(String letter, double rowLength, int row, double col) {
        double xVal = col - (rowLength/2);
        xVal = 500 + xVal*50;
        double yVal = 500 + row*65;
        
        if (letter.length() == 1) {
            rect = new Rectangle(xVal,yVal,KEY_WIDTH,KEY_HEIGHT);
        } else if (letter.equals("Enter")) {
            xVal = xVal - KEY_WIDTH*.5;
            rect = new Rectangle(xVal,yVal,KEY_WIDTH*1.5,KEY_HEIGHT);
        } else {
            rect = new Rectangle(xVal,yVal,KEY_WIDTH*1.6,KEY_HEIGHT);
        }
        
        rect.setFill(Color.LIGHTGREY);
        rect.setArcHeight(ARC);
        rect.setArcWidth(ARC);
        
        this.letter = letter;
        text = new Text(rect.getX() + 15, rect.getY() + 32.5, this.letter);
        text.setFont(font);
        text.setStroke(Color.BLACK);
    }
    
    public void setColor(GamePane pane, Color color) {
        if (rect.getFill() == Color.rgb(105, 171, 100)) { return; }
        
        pane.getChildren().removeAll(rect, text);
        rect.setFill(color);
        text.setStroke(Color.WHITE);
        pane.getChildren().addAll(rect, text);
    }
    
    public boolean containsPoint(double x, double y) {
        return rect.contains(x, y);
    }
    
    public Shape[] getShapes() {
        Shape[] shapes = new Shape[2];
        shapes[0] = rect;
        shapes[1] = text;
        return shapes;
    }
    
    public String getLetter() {
        return this.letter;
    }
}