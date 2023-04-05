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
    public void onClick(View button) {

    }//onClick

    /**
     * After a piece is clicked, the available moves will be shown
     * by changing those tiles to "AVAIL"
     * @param start
     */
    public void checkMoves(Tile start) {
        // remove all present marks in a loop
        // flag if there is a legal capture elsewhere
        // if (capture available, mark capture and call checkMovesHelper on that square)
        // if (forward diagonals are empty, mark those diagonals)
        // if (king, check backward diagonals)
        // invalidate
    }

    /**
     *
     * @param curr
     */
    private void checkMovesHelper(Tile curr) {
        // recursively check if available captures are available on curr
            // if (available capture, mark jump and recall helper method)
                // if king, also check backward
            // call checkMovesHelper
    }//checkMovesHelper

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
                return new CheckersHumanPlayer(name);
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
        return new LocalCheckers(gameState);
    }//createLocalGame
}