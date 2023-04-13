package com.example.checkers_deluxe2.actionMessage;

/*
 * When the human player taps on a piece that they want to move
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  April 2023
 */

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class CheckersResetAction extends GameAction {

    /**
     * Constructor for CheckersResetAction
     * @param player
     *      The human player seeking a reset
     */
    public CheckersResetAction(GamePlayer player) {
        super(player);
    }//CheckersResetAction
}
