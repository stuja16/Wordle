package edu.lawrence.wordle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {
    // boolean statsVisible false;
    
    @FXML
    private VBox vBox;
    private GamePane game;
    
    @FXML
    private void exit() {
        Platform.exit();
    }
    
    @FXML
    private void newGame() {
        vBox.getChildren().remove(game);
        game = new GamePane();
        game.setPrefSize(1000, 800);
        vBox.getChildren().add(game);
        focusGame();
        
        if (game.getStatsVisible()) {
            game.getStatistics().removeStatistics(game);
            game.setStatsVisible(false);
        }
    }
    
    @FXML
    private void displayStatistics() {
        if (!game.getStatsVisible()) {
            game.getStatistics().displayStatistics(game);
            game.setStatsVisible(true);
        } else {
            game.getStatistics().removeStatistics(game);
            game.setStatsVisible(false);
        }
    }
    
    public void focusGame() {
        game.requestFocus();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new GamePane();
        game.setPrefSize(1000,800);
        vBox.getChildren().add(game);
    }  
}
