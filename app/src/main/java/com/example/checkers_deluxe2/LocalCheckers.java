package com.example.checkers_deluxe2;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;

public class LocalCheckers extends LocalGame {
    CheckersState gameInstance;

    public LocalCheckers (GameState gameState) {
        gameInstance = (CheckersState) gameState;
    }//ctor

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        CheckersState temp = new CheckersState(gameInstance);
        p.sendInfo(temp);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return (playerIdx == gameInstance.getTurn());
    }

    @Override
    protected String checkIfGameOver() {
        //if (# of available moves for player 1) == 0
            //return "Player 2 has won the game";
        //else if (# of available moves for player 1 == 0){//Checks player2
            //return "Player 1 has won the game";
        //else return null;

        //temp
        return null;
    }//checkIfGameOver

    @Override
    protected boolean makeMove(GameAction action) {
        //if (action instanceof ...)
            //perform action here
            //return true

        //temp
        return true;
    }
}
