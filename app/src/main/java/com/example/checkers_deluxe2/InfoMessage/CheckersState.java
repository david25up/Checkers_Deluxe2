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
import com.example.checkers_deluxe2.TileTraversal;

import java.util.ArrayList;


public class CheckersState extends GameState {
    // Board Dimensions //
    public static final int HEIGHT = 8, WIDTH = 8;

    // Instance Variables //
    private Tile[][] board;
    private int whoseTurn; //0 if P1's Turn, 1 if P2's turn
    private double timeElapsed;
    private ArrayList<TileTraversal> moves;

    /** Default constructor for the game state */
    public CheckersState() {
        board = new Tile[WIDTH][HEIGHT];
        whoseTurn = 0; //Allows player 1 to go first
        timeElapsed = 0;
    }//default ctor

    /**
     * Copy constructor of the default game state
     * @param original   The game state object we are copying over
     */
    public CheckersState(CheckersState original) {
        board = new Tile[WIDTH][HEIGHT];
        for (int row = 0; row < WIDTH; row++) {
            for (int col = 0; col < HEIGHT; col++) {
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
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
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
     * @param traversal
     *      The TileTraversal object that contains the path the user
     *      would like to move the piece along
     */
    public void movePieces(TileTraversal traversal) {
        int eRow, eCol;
        Tile piece = traversal.getStart();
        Tile empty = traversal.getDestination();
        eRow = empty.getRow(); eCol = empty.getCol();

        board[eRow][eCol].setValue(piece.getValue());

        //Case where non-king piece reaches the corresponding end of the board
        if (!empty.getIsKing() && (eRow == 0 && empty.getValue().equals(Tile.Value.BLACK) ||
                                    eRow == (CheckersState.HEIGHT - 1) && empty.getValue().equals(Tile.Value.RED))) {
            board[eRow][eCol].setIsKing(true);
        } else { board[eRow][eCol].setIsKing(piece.getIsKing()); }

        for (int i = 0; i < traversal.getTraversalLength() - 1; i++) {
            int row = traversal.getTile(i).getRow();
            int col = traversal.getTile(i).getCol();
            board[row][col].revertTile();
        }
    }//movePieces



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
    public ArrayList<TileTraversal> getMoves() {return moves;}
    public int getTurn() {return whoseTurn;}
    public Tile[][] getBoard() {return board;}

    /* --- SETTER METHODS --- */
    public void setMoves(ArrayList<TileTraversal> moves){this.moves = moves;}
    public void setBoard(Tile[][] board) {this.board = board;}
}//CheckersState

