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

public class CheckersTapAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CheckersTapAction(GamePlayer player) {
        super(player);
    }
}
