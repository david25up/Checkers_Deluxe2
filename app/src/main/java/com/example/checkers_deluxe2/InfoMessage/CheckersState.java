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
    public static final int WIDTH = 8, HEIGHT = 8;

    // Instance Variables //
    private Tile[][] board;
    private int isTurn; //0 if P1's Turn, 1 if P2's turn
    private double timeElapsed;

    /** Default constructor for the game state */
    public CheckersState() {
        board = new Tile[HEIGHT][WIDTH];
        isTurn = 0; //Allows player 1 to go first
        timeElapsed = 0;
    }//default ctor

    /**
     * Copy constructor of the default game state
     * @param original   The game state object we are copying over
     */
    public CheckersState(CheckersState original) {
        board = new Tile[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = original.board[row][col];
            }
        }

        isTurn = original.isTurn;
        timeElapsed = original.timeElapsed;
    }//ctor

    /**
     * Turns all board data into one long string
     * @return The appended string
     */
    @NonNull
    @Override
    public String toString() {
        String result = "Player's Turn: ";
        if (isTurn == 0) {
            result += "Red's turn";
        } else {
            result += "Black's turn";
        }
        result += "\n ";

        result += "Time Elapsed: " + timeElapsed + "\n ";

        // Actual conversion of the board's content
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                String board1 = "";
                switch (board[i][j].getValue()) {
                    case EMPTY: {
                        board1 = "E";
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
        result += "\n";

        return result;
    }//toString



    /**
     * Creates a new board and initializes the pieces of the board to the right spots
     */
    public void initBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Tile(i, j);
            }
        }

        resetBoard();

    }//initBoard

    /**
     * --- HELPER METHOD ---
     * Empties out the board and puts each piece back to their "starting positions"
     */
    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setIsKing(false);
                board[i][j].setIsStart(false);
                board[i][j].setValue(Tile.Value.EMPTY);
               if (i < 3 && ((j % 2 != 0 && i % 2 == 0) || (j % 2 == 0 && i % 2 != 0))) {
                    board[i][j].setValue(Tile.Value.RED);
                }
                else if (i > 4 && ((j % 2 == 0 && i % 2 != 0) || (j % 2 != 0 && i % 2 == 0))) {
                    board[i][j].setValue(Tile.Value.BLACK);
                }
            }
        }
    }
    public void swapPieces(Tile piece, Tile avail) {
        Tile temp = board[piece.getRow()][piece.getCol()];
        board[piece.getRow()][piece.getCol()] = board[avail.getRow()][avail.getCol()];
        board[avail.getRow()][avail.getCol()] = temp;
    }//swapPieces

    /**
     * Swaps the position of two given pieces under the assumption that
     * piece2 will always be a blank spot
     * @param piece   The initial piece clicked that needs to be moved
     * @param blank   The blank space the piece will move to
     * @return         Returns true if the swap worked
     */

    /**
     * --- HELPER METHOD ---
     * Flips whose turn it is
     */
    public void flipTurn() {
        if (isTurn == 0) {
            isTurn = 1;
        } else {
            isTurn = 0;
        }
    }//setWhoseTurn

    /* --- GETTER METHODS --- */
    public Tile[][] getBoard() {return board;}
    public int getTurn() {return isTurn;}
    public double getTimeElapsed() {return timeElapsed;}

    /* --- SETTER METHODS --- */
    public void setBoard(Tile[][] board) {this.board = board;}
}//CheckersState

