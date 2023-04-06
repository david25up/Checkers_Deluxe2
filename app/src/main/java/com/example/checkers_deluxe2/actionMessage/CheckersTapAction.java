package com.example.checkers_deluxe2.actionMessage;

/*
 * When the human player taps on a piece that they want to move
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.Tile;

public class CheckersTapAction extends GameAction {
    // Instance Variables //
    int row;
    int col;

    /**
     * Constructor for CheckersTapAction
     * @param player
     * @param row
     * @param col
     * @param board
     */
    public CheckersTapAction(GamePlayer player, int row, int col) {
        super(player);
        this.row = row;
        this.col = col;
    }

    /* --- GETTER METHODS --- */
    public int getRow() {return row;}
    public int getCol() {return col;}
}
