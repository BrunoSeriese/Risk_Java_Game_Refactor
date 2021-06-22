package application;

import controllers.LoginController;
import models.DiceModel;
import models.GameModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

//import configuration.Database;
//import sun.rmi.runtime.Log;

class JUnit5ExampleTest {

    @Test
    public void Should_Return_True_When_Number_Between_One_And_Six(){
        boolean x = false;
        DiceModel diceModel = new DiceModel();
        ArrayList<Integer> i = diceModel.roll(1);
        for (int y : i){
            if (y <= 6 && y >= 1){
                x = true;
            }
        }
        assertTrue(x);
    }

    @Test
    public void Should_Return_True_When_Lobbycode_Between_100000_And_999999(){
        boolean x = false;
        LoginController loginController = new LoginController();
        int y = Integer.valueOf(loginController.createLobbyCode().toString()) ;
        if (y >= 100000 && y <= 999999){
            x = true;
        }
        assertTrue(x);
    }

    @Test
    public void Should_Return_Username_Is_Empty(){
        LoginController loginController = new LoginController();
        assertEquals( false,loginController.checkJoin("", "123456"));
    }

    @Test
    public void Should_Return_False_After_Empty_String_In_Lobbycode(){
        LoginController loginController = new LoginController();
        assertEquals( false,loginController.validateLobby(""));
    }

    @Test
    public void Should_Return_PhaseID_Of_One(){
        GameModel gameModel = new GameModel();
        assertEquals(1,gameModel.getPhaseID() + 1);
    }

    @Test
    public void Should_Return_PhaseID_Of_One_From_Three(){
        boolean phaseID = false;
        GameModel gameModel = new GameModel();
        gameModel.setPhaseID(3);
        gameModel.updatePhaseID();
        if (gameModel.getPhaseID() == 1){
            phaseID = true;
        }
        assert(phaseID);
    }

    @Test
    public void Should_Return_False_When_Lobbycode_Are_Letters(){
        LoginController loginController = new LoginController();
        assertEquals(false,loginController.validateLobby("asdadad"));
    }

    @Test
    public void Should_Return_Message_When_ID_Not_Between_One_And_Four(){
        LoginController loginController = new LoginController();
        assertEquals(new UnsupportedOperationException("fout"), loginController.assignColorToPlayer(5));
    }


}
