package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class VictoryViewController {


    public Label playerNameWinner;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setPlayerNameWinner(String username) {
        playerNameWinner.setText(username);
    }

    public void backToMenu(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/mainMenuScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
