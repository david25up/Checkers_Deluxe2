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
import com.example.GameFramework.utilities.Logger;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Tile;
import com.example.checkers_deluxe2.actionMessage.CheckersMoveAction;
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
     * Takes in the information for the game, performs an action,
     * and flips whose turn it is
     * @param info   The information from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (surfaceView == null) {return;}

        //Makes sure info is a CheckersState object
        if (!(info instanceof CheckersState)) {return;}
        board = ((CheckersState) info).getBoard();

        surfaceView.setState((CheckersState)info);
        surfaceView.invalidate();
        Logger.log(TAG, "receiving");

        //Looks for the first possible piece
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getValue() != Tile.Value.EMPTY) {
                    game.sendAction(new CheckersMoveAction(this, row, col, board));
                } break;
            }
        }
        Logger.log(TAG, "Found a piece");

        //Moves on the first possible move
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getValue() == Tile.Value.AVAIL) {
                    game.sendAction(new CheckersTapAction(this, row, col, board));
                } break;
            }
        }
        Logger.log(TAG, "Piece has been moved");

        ((CheckersState) info).flipTurn();
    }//receiveInfo
}
