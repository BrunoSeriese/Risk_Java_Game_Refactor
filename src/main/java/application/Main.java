package application;

import controllers.SpelbordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import views.SpelbordViewController;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        State.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        primaryStage.setTitle("Risk");
        primaryStage.getIcons().add(new Image("images/risklogo.png"));
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);


    }
}