package application;

import configuration.Database;
import javafx.stage.Stage;

public class State {
    public static Database database = new Database();
    public static String lobbycode;
    public static int TurnID;
    public static Stage stage;
    public static double RED = 0.0;
    public static double BLUE = -0.85;
    public static double GREEN = 0.70;
    public static double ORANGE = 0.20;
}
