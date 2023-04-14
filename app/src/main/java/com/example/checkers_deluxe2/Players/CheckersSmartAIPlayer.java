package com.example.checkers_deluxe2.Players;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.R;
import com.example.checkers_deluxe2.Tile;
import com.example.checkers_deluxe2.actionMessage.CheckersSmartAIAction;
import com.example.checkers_deluxe2.animation.CheckersAnimationSurface;

public class CheckersSmartAIPlayer extends GameComputerPlayer {
    // Tag for logging //
    private static final String TAG = "CheckersSmartAIPlayer";

    /* --- INSTANCE VARIABLES --- */
    private CheckersAnimationSurface surfaceView;
    private int layoutId;
    private Tile[][] board;

    /**
     * The inherited constructor from GameComputerPlayer class
     * @param name the player's name
     */
    public CheckersSmartAIPlayer(String name) {
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
        if (!(info instanceof CheckersState)) {return;}
        if (((CheckersState) info).getTurn() != playerNum) {return;}

        Logger.log(TAG, "receiving");

        game.sendAction(new CheckersSmartAIAction(this));
    }//receiveInfo

    /**
     * returns type of player
     */

}//CheckersSmartAIPlayer
