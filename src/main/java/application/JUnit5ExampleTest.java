package application;

import com.google.firebase.database.ThrowOnExtraProperties;
//import configuration.Database;
import controllers.LoginController;
import controllers.SpelbordController;
import models.DiceModel;
import models.GameModel;
import org.junit.jupiter.api.Test;
import views.RulesViewController;
//import sun.rmi.runtime.Log;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LoggingPermission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JUnit5ExampleTest {

    @Test
    void justAnExample() {
        System.out.println("prrrrrr");
    }

//    @Test
//    public void Should_Return_Number_Between_One_And_Six(){
//        int x = 6;
//        DiceModel diceModel = new DiceModel();
//        ArrayList<Integer> i = diceModel.roll(1);
//        ArrayList<Integer> yourArray = new ArrayList<>();
//        yourArray.add(1);
//        yourArray.add(2);
//        yourArray.add(3);
//        yourArray.add(4);
//        yourArray.add(5);
//        yourArray.add(6);
//
////        assertEquals(, diceModel.roll(1));
//        assertTrue(Arrays.asList(yourArray).contains(diceModel.roll(1)));
//    }

    @Test
    public void Should_Return_Username_Is_Empty(){
        LoginController loginController = new LoginController();
        assertEquals( false,loginController.checkJoin("", "123456"));
    }

    @Test
    public void Should_Return_False_After_Empty_String(){
        LoginController loginController = new LoginController();
        assertEquals( false,loginController.validateLobby(""));
    }

    @Test
    public void empty() throws ExecutionException, InterruptedException {
        LoginController loginController = new LoginController();
        assertEquals( false,loginController.enoughPlayers());
    }

//    @Test
//    public void ada() throws ExecutionException, InterruptedException {
//        State.database = new Database();
//        State.lobbycode = "123456";
//        GameModel gameModel = new GameModel();
//        gameModel.clearSelectedCountries("EU4");
//        gameModel.clearSelectedCountries("EU5");
//        SpelbordController spelbordController = new SpelbordController();
//        assertEquals( true,spelbordController.getNeighborsFirebase());
//    }

    @Test
    public void Should_Return_PhaseID_Of_One(){
        GameModel gameModel = new GameModel();
        assertEquals(1,gameModel.getPhaseID() + 1);
    }

    @Test
    public void sss(){
        GameModel gameModel = new GameModel();

        assertEquals(1,gameModel.getPhaseID() + 1);
    }

    @Test
    public void Should_Return_False_When_Lobbycode_Are_Letters(){
        LoginController loginController = new LoginController();
        assertEquals(false,loginController.validateLobby("asdadad"));
    }

//    @Test
//    public void Should(){
//        RulesViewController rulesViewController = new RulesViewController();
//        rulesViewController.backToMenu();
//    }


}
