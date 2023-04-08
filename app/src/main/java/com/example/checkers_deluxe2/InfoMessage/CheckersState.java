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
    private int whoseTurn; //0 if P1's Turn, 1 if P2's turn
    private double timeElapsed;

    /** Default constructor for the game state */
    public CheckersState() {
        board = new Tile[HEIGHT][WIDTH];
        whoseTurn = 0; //Allows player 1 to go first
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

        whoseTurn = original.whoseTurn;
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
        if (whoseTurn == 0) {
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
                    case AVAIL: {
                        board1 = "A";
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

    /**
     * Swaps the position of two given pieces under the assumption that
     * piece2 will always be a blank spot
     * @param piece
     *      The initial piece clicked that needs to be moved
     * @param avail
     *      The available space clicked that will inherit the original piece
     */
    public void swapPieces(Tile piece, Tile avail) {
        Tile temp = piece;
        board[piece.getRow()][piece.getCol()].setIsKing(false);
        board[avail.getRow()][avail.getCol()].setValue(temp.getValue());
        board[avail.getRow()][avail.getCol()].setIsKing(temp.getIsKing());
        board[piece.getRow()][piece.getCol()].setValue(Tile.Value.EMPTY);
    }//swapPieces



    /**
     * --- HELPER METHOD ---
     * Flips whose turn it is
     */
    public void flipTurn() {
        if (whoseTurn == 0) {
            whoseTurn = 1;
        } else {
            whoseTurn = 0;
        }
    }//flipTurn

    /* --- GETTER METHODS --- */
    public Tile[][] getBoard() {return board;}
    public int getTurn() {return whoseTurn;}

    /* --- SETTER METHODS --- */
    public void setBoard(Tile[][] board) {this.board = board;}
}//CheckersState

