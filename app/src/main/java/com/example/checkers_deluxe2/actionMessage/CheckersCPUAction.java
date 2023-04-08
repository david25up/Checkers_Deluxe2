package com.example.checkers_deluxe2.actionMessage;

/*
 * When an "available" spot is clicked and a piece is to be moved
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.Tile;

public class CheckersCPUAction extends GameAction {

    /**
     * Constructor for CheckersMoveAction
     * @param player
     */
    public CheckersCPUAction(GamePlayer player) {
        super(player);
    }//CheckersMoveAction

 }