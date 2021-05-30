package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button btn = new Button("CLICK ME");
        btn.setStyle("-fx-background-image: url('../images/left.png')");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/menu.fxml"));
        primaryStage.setTitle("Risk Game");
        primaryStage.setScene(new Scene(root,1400, 860));
        primaryStage.show();





//        Button btn = new Button();
//
//        btn.setText("Find Lobby");
//        btn.setOnAction(event -> System.out.println("Find Lobby"));
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//
//        Scene scene = new Scene(root, 1400, 860);
//
//
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}