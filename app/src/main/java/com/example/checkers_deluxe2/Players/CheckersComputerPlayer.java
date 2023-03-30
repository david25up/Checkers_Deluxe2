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

    }
}
