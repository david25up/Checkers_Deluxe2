package com.example.checkers_deluxe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Players.CheckersComputerPlayer;
import com.example.checkers_deluxe2.Players.CheckersHumanPlayer;

import java.util.ArrayList;

/*
 * Sets everything up
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

public class CheckersMainActivity extends GameMainActivity {
    //The port number to be used IF network implementation is made
    private static final int PORT_NUMBER = 8080;


    /**
     * Creates the default configuration of the game
     *
     * There are three types available: human, smart AI,
     * and base AI, where the default chooses to add
     * @return   The default configuration of the game
     */
    @Override
    public GameConfig createDefaultConfig() {
        //Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        //Adds the human and computer types
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new CheckersHumanPlayer(name, R.layout.activity_main);
            }});
        playerTypes.add(new GamePlayerType("Smart AI Player") {
            public GamePlayer createPlayer(String name) {return new CheckersComputerPlayer(name);}});
        playerTypes.add(new GamePlayerType("Base AI Player") {
            public GamePlayer createPlayer(String name) {return new CheckersComputerPlayer(name);}});

        // Create a game configuration class for Checkers:
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Checkers", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player

        return defaultConfig;
    }//createDefaultConfig

    /**
     * Creates a local game
     * @return The local game, checkers
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if (gameState == null) {
            return new LocalCheckers();
        }

        return new LocalCheckers((CheckersState) gameState);
    }//createLocalGame
}