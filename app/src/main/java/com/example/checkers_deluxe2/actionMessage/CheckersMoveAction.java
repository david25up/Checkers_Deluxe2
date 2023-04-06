package com.example.checkers_deluxe2.actionMessage;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class CheckersMoveAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CheckersMoveAction(GamePlayer player) {
        super(player);
    }

 }