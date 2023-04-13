package com.example.checkers_deluxe2.InfoMessage;

import static org.junit.Assert.*;

import com.example.checkers_deluxe2.Tile;

import org.junit.Test;

public class CheckersStateTest {

    @Test
    public void testToString() {
    }

    @Test
    public void initBoard() {
    }

    @Test
    public void resetBoard() {
        CheckersState checkersState = new CheckersState();
        checkersState.initBoard();
        Tile[][] board1 = checkersState.getBoard();
        checkersState.resetBoard();
        Tile[][] board2 = checkersState.getBoard();
            for(int i = 0; i < 8; i++) {
                assertArrayEquals(board1[i], board2[i]);
            }
    }

    @Test
    public void movePieces() {
    }

    @Test
    public void flipTurn() {
        CheckersState checkersState = new CheckersState();
        int i = checkersState.getTurn();
        checkersState.flipTurn();
        int j = checkersState.getTurn();
        assertNotEquals(i, j);
    }

    @Test
    public void getMoves() {
    }

    @Test
    public void getTurn() {

    }

    @Test
    public void getBoard() {
        CheckersState checkersState = new CheckersState();
        checkersState.initBoard();
        Tile[][] board1 = checkersState.getBoard();
        checkersState.resetBoard();
        Tile[][] board2 = checkersState.getBoard();
        for(int i = 0; i < 8; i++) {
            assertArrayEquals(board1[i], board2[i]);
        }
    }

    @Test
    public void setMoves() {
    }

    @Test
    public void setBoard() {
    }
}