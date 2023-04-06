package com.example.checkers_deluxe2.actionMessage;

/*
 *
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class CheckersAction extends GameAction {

    /**
     * The inherited constructor from AnimationSurface class
     *
     * @param player the player who created the action
     */
    public CheckersAction(GamePlayer player) {
        super(player);
    }//ctor
}
