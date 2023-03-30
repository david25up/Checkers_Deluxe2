package com.example.checkers_deluxe2.Players;

/*
 * The computer player of the game
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;

import java.util.Random;

public class CheckersComputerPlayer extends GameComputerPlayer {


    /**
     * The inherited constructor from GameComputerPlayer class
     * @param name the player's name
     */
    public CheckersComputerPlayer(String name) {
        super(name);
    }//ctor

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof CheckersState)) {
            //info is not a CheckersState object
            return;
        }

        CheckersState checkersState = new CheckersState( (CheckersState) info);

        if (checkersState.getTurn() == playerNum) {
            return;
        } else {
            //Create an action state via: "NameOfAction" action = new "NameOfAction(this)"
            // and send action via: game.sendAction(action)
        }

        ((CheckersState) info).flipTurn();
    }
}
