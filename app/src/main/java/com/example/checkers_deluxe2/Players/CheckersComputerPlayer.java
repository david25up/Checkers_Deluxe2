package com.example.checkers_deluxe2.Players;

/*
 * The computer player of the game
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.R;
import com.example.checkers_deluxe2.Tile;
import com.example.checkers_deluxe2.actionMessage.CheckersCPUAction;
import com.example.checkers_deluxe2.actionMessage.CheckersTapAction;
import com.example.checkers_deluxe2.animation.CheckersAnimationSurface;

public class CheckersComputerPlayer extends GameComputerPlayer {
    // Tag for logging //
    private static final String TAG = "CheckersComputerPlayer";

    /* --- INSTANCE VARIABLES --- */
    private CheckersAnimationSurface surfaceView;
    private int layoutId;
    private Tile[][] board;

    /**
     * The inherited constructor from GameComputerPlayer class
     * @param name the player's name
     */
    public CheckersComputerPlayer(String name) {
        super(name);
    }//ctor

    /**
     * Taken from GameHumanPlayer
     * @param activity   The activity we are running under
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        // Load the layout resource for our GUI
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (CheckersAnimationSurface) myActivity.findViewById(R.id.surfaceView);
    }//setAsGui

    /**
     * Takes in the information for the game, performs an action,
     * and flips whose turn it is
     * @param info   The information from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return;
        if (!(info instanceof CheckersState)) {return;}

        Logger.log(TAG, "receiving");

        game.sendAction(new CheckersCPUAction(this));
    }//receiveInfo
}//CheckersComputerPlayer
