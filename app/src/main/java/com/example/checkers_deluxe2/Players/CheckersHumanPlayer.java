package com.example.checkers_deluxe2.Players;

/*
 * The human player of the game
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;

public class CheckersHumanPlayer extends GameHumanPlayer {


    /**
     * The inherited constructor from GameComputerPlayer class
     * @param name the name of the player
     */
    public CheckersHumanPlayer(String name) {
        super(name);
    }//ctor

    @Override
    public View getTopView() {
        return null;
    }//getTopView

    @Override
    public void receiveInfo(GameInfo info) {

    }//receiveInfo

    @Override
    public void setAsGui(GameMainActivity activity) {

    }//setAsGui
}
