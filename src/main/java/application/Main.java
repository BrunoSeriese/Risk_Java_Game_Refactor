package application;

import controllers.DiceController;
import controllers.SpelbordController;
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
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        primaryStage.setTitle("Risk Game");
        primaryStage.setScene(new Scene(root,1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
        SpelbordController controllertest=new SpelbordController();
        controllertest.onClick();
        controllertest.aanval();


    }

    public static void main(String[] args) {
        launch(args);


    }
}