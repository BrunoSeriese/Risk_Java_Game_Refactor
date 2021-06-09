package application;

import controllers.SpelbordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        controllertest.startGame();
        controllertest.handInCard();



        System.out.println("in main");

    }

    public static void main(String[] args) {
        launch(args);


    }
}