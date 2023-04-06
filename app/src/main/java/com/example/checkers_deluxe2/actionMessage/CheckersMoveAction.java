package com.example.checkers_deluxe2.actionMessage;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.Tile;

public class CheckersMoveAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    int row;
    int col;
    Tile[][] board;
    public CheckersMoveAction(GamePlayer player, int row, int col, Tile[][] board) {
        super(player);
        this.row = row;
        this.col = col;
        this.board = board;
    }

    public int getRow() {return row;}
    public int getCol() {return col;}
    public Tile[][] getBoard() {return board;}
 }