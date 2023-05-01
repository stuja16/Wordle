package edu.lawrence.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.Task;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        PrimaryController controller = (PrimaryController) loader.getController();
        controller.focusGame();
        stage.setScene(scene);
        scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");
        stage.setTitle("Wordle");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}