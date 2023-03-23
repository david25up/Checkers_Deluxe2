package com.example.checkers_deluxe2.InfoMessage;

/*
 * Contains the state of the game :)
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import androidx.annotation.NonNull;

import com.example.GameFramework.infoMessage.GameState;

import com.example.checkers_deluxe2.Tile;


public class CheckersState extends GameState {

    // Board Dimensions //
    private static final int WIDTH = 8, HEIGHT = 8;

    // Instance Variables //
    private Tile[][] board;
    private boolean isTurn; //true if P1's Turn, false if P2's turn
    private boolean isClicked; //A boolean
    private double timeElapsed;

    /** Default constructor for the game state */
    public CheckersState() {
        board = new Tile[HEIGHT][WIDTH];
        isTurn = true; //Allows player 1 to go first
        isClicked = false;
        timeElapsed = 0;
    }//default ctor

    /**
     * Copy constructor of the default game state
     * @param original   The game state object we are copying over
     */
    public CheckersState(CheckersState original) {
        board = new Tile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            System.arraycopy(original.board[i], 0, board[i], 0, WIDTH);
        }

        isTurn = original.isTurn;
        isClicked = original.isClicked;
        timeElapsed = original.timeElapsed;
    }//ctor

    /**
     * Turns all board data into one long string
     * @return The appended string
     */
    @NonNull
    @Override
    public String toString() {
        String result = "PlayerTurn: ";
        if (isTurn) {
            result += "Red's turn";
        } else if (!isTurn) {
            result += "Black's turn";
        }
        result += "\n ";

        result += "Time Elapsed: " + timeElapsed + "\n ";

        // Actual conversion of the board's content
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                String board1 = "";
                switch (board[i][j].getInTile()) {
                    case EMPTY: {
                        board1 = "X";
                        break;
                    }
                    case BLACK: {
                        board1 = "B";
                        break;
                    }
                    case RED: {
                        board1 = "R";
                        break;
                    }
                }
                result += " " + board1;
            }
            result += "\n ";
        }

        return result;
    }//toString

    /**
     * --- HELPER METHOD ---
     * Checks the surrounding tiles for valid moves
     * @return True if valid, false if out of bounds
     */
    public boolean validMove (int row, int col) {
        if (row < 0 || col < 0 || row > HEIGHT || col > HEIGHT) {
            return false;
        } else if (board[row][col].getInTile() != Tile.Value.EMPTY) {
            return false;
        }
        return true;
    }//validMove

    // *** createBoard //
    public boolean initBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    board[i][j] = new Tile(i, j);
                if (i < 3 && ((j % 2 != 0 && i % 2 == 0) || (j % 2 == 0 && i % 2 != 0))) {
                    board[i][j].setInTile(Tile.Value.RED);
                }
                else if (i > 4 && ((j % 2 != 0 && i % 2 != 0) || (j % 2 == 0 && i % 2 == 0))) {
                    board[i][j].setInTile(Tile.Value.BLACK);
                }
            }
        }
        return true;

    }
    // *** RESET *** //
    public Tile[][] resetBoard(Tile[][] board_) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board_[i][j].setKing(false);
                if (i < 3 && j % 2 != 0) {
                    board_[i][j].setInTile(Tile.Value.RED);
                }
                else if (i > 4 && j % 2 == 0) {
                    board_[i][j].setInTile(Tile.Value.BLACK);
                }
                else {
                    board_[i][j].setInTile(Tile.Value.EMPTY);
                }
            }
        }
        return board_;
    }
    // *** DRAW *** //


    public boolean drawGame(Tile[][] board_) {
        // add Draw Message
        resetBoard(board_);
        return true;
    }

    /**
     * Swaps the position of two given pieces under the assumption that
     * piece2 will always be a blank spot
     * @param piece1   The initial piece clicked that needs to be moved
     * @param piece2   The blank space the piece will move to
     * @return         Returns true if the swap worked
     */
    public boolean swapPieces(Tile piece1, Tile piece2) {
        if (!validMove(piece1.getRow(), piece1.getCol())) {
            return false;
        }
        //Start putting piece1's data into piece2
        piece2.setInTile(piece1.getInTile());
        if (piece1.getKing()) {piece2.setKing(true);}

        //Make piece1 empty
        piece1.setInTile(Tile.Value.EMPTY);
        piece1.setKing(false);

        return true;
    }//swapPieces

    // *** MOVE PIECE *** //

    /** --- GETTER METHOD --- */

    public Tile[][] getBoard() {return board;}


}//CheckersState
