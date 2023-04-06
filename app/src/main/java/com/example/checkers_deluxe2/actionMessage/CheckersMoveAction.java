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

public class CheckersMoveAction extends GameAction {
    // Instance Variables //
    int row;
    int col;
    Tile[][] board;

    /**
     * Constructor for CheckersMoveAction
     * @param player
     * @param row
     * @param col
     * @param board
     */
    public CheckersMoveAction(GamePlayer player, int row, int col, Tile[][] board) {
        super(player);
        this.row = row;
        this.col = col;
        this.board = board;
    }//CheckersMoveAction

    /* --- GETTER METHODS --- */
    public int getRow() {return row;}
    public int getCol() {return col;}
    public Tile[][] getBoard() {return board;}
 }