package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RulesViewController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void backToMenu(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openRuleSet(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/rules1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void openRuleSet2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/rules2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void openRuleSet3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/rules3.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
